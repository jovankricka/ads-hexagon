package com.theannotatedhexagon.emailads.domain.services;

import com.theannotatedhexagon.emailads.domain.errors.DomainError;
import com.theannotatedhexagon.emailads.domain.events.AttemptToSentEmailFailed;
import com.theannotatedhexagon.emailads.domain.events.EmailSent;
import com.theannotatedhexagon.emailads.domain.models.Email;
import com.theannotatedhexagon.emailads.ports.driven.EmailTransportPort;
import com.theannotatedhexagon.emailads.ports.driven.ObservabilityPort;
import com.theannotatedhexagon.emailads.ports.driver.EmailAdsPort;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EmailAdsService implements EmailAdsPort {

    private ObservabilityPort observabilityPort;
    private EmailTransportPort emailTransportPort;

    @Override
    public Either<DomainError, Void> sendEmail(Email email) {
        return emailTransportPort.sendEmail(email)
                .peek(ignore -> observabilityPort.observe(EmailSent.of()
                        .email(email)
                        .build()))
                .peekLeft(error -> observabilityPort.observe(AttemptToSentEmailFailed.of()
                        .email(email)
                        .error(error.getMessage())
                        .build()));
    }
}
