package com.theannotatedhexagon.adsgateway.domain.services;

import com.theannotatedhexagon.adsgateway.domain.errors.DomainError;
import com.theannotatedhexagon.adsgateway.domain.events.AdDeliveryStartedViaClient;
import com.theannotatedhexagon.adsgateway.domain.events.AdSentViaEmail;
import com.theannotatedhexagon.adsgateway.domain.events.AttemptToSendAdViaEmailFailed;
import com.theannotatedhexagon.adsgateway.domain.events.AttemptToStartDeliveringAdViaClientFailed;
import com.theannotatedhexagon.adsgateway.domain.models.Ad;
import com.theannotatedhexagon.adsgateway.ports.driven.ClientDeliveryPort;
import com.theannotatedhexagon.adsgateway.ports.driven.EmailSendingPort;
import com.theannotatedhexagon.adsgateway.ports.driven.ObservabilityPort;
import com.theannotatedhexagon.adsgateway.ports.driver.AdsPort;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AdsService implements AdsPort {

    private ObservabilityPort observabilityPort;
    private EmailSendingPort emailSendingPort;
    private ClientDeliveryPort clientDeliveryPort;

    @Override
    public Either<Seq<DomainError>, Void> publishAd(Ad ad) {
        return Either.sequence(List.of(
                clientDeliveryPort.publishAd(ad)
                        .peek(ignore -> observabilityPort.observe(AdDeliveryStartedViaClient.of().ad(ad).build()))
                        .peekLeft(error -> observabilityPort.observe(AttemptToStartDeliveringAdViaClientFailed.of()
                                .ad(ad)
                                .error(error.getMessage())
                                .build())),
                emailSendingPort.sendEmailWithAd(ad)
                        .peek(ignore -> observabilityPort.observe(AdSentViaEmail.of()
                                .ad(ad)
                                .build()))
                        .peekLeft(error -> observabilityPort.observe(AttemptToSendAdViaEmailFailed.of()
                                .ad(ad)
                                .error(error.getMessage())
                                .build()))
        )).map(ignore -> null);
    }
}
