package com.theannotatedhexagon.clientads.domain.services;

import com.theannotatedhexagon.clientads.domain.errors.AdAlreadyStopped;
import com.theannotatedhexagon.clientads.domain.errors.AdWithExistingTitle;
import com.theannotatedhexagon.clientads.domain.errors.DomainError;
import com.theannotatedhexagon.clientads.domain.errors.NonExistingAd;
import com.theannotatedhexagon.clientads.domain.models.Ad;
import com.theannotatedhexagon.clientads.domain.models.AdId;
import com.theannotatedhexagon.clientads.ports.driven.AdsStoragePort;
import com.theannotatedhexagon.clientads.ports.driven.ObservabilityPort;
import com.theannotatedhexagon.clientads.ports.driver.AdsPort;
import com.theannotatedhexagon.clientads.domain.events.*;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ClientAdsService implements AdsPort {

    private AdsStoragePort adsStoragePort;
    private ObservabilityPort observabilityPort;

    @Override
    public Either<DomainError, Ad> startAdDisplaying(String adTitle, String adDescription) {
        var maybeAd = adsStoragePort.findAdByTitle(adTitle);
        if (maybeAd.isPresent() && maybeAd.get().isActive()) {
            observabilityPort.observe(new AttemptToDisplayAdWithExistingTitle(adTitle));
            return Either.left(AdWithExistingTitle.of().title(adTitle).build());
        }
        var newAd = Ad.of()
                .id(AdId.generate())
                .title(adTitle)
                .description(adDescription)
                .active(true)
                .build();
        return adsStoragePort.save(newAd)
                .peekLeft(ignore -> observabilityPort.observe(new AdDisplayingStartFailed(newAd)))
                .peek(ignore -> observabilityPort.observe(new AdDisplayingStarted(newAd)));
    }

    @Override
    public Either<DomainError, Ad> stopAdDisplaying(AdId id) {
        var maybeAd = adsStoragePort.findById(id);
        if (maybeAd.isEmpty()) {
            observabilityPort.observe(new AttemptToStopNonExistingAd(id));
            return Either.left(NonExistingAd.of().id(id).build());
        }
        var existingAd = maybeAd.get();
        if (!existingAd.isActive()) {
            observabilityPort.observe(new AdToStopAlreadyStopped(existingAd));
            return Either.left(AdAlreadyStopped.of().id(id).build());
        }
        return adsStoragePort.save(existingAd.deactivate())
                .peekLeft(ignore -> observabilityPort.observe(new AdDisplayingStopFailed(existingAd)))
                .peek(ignore -> observabilityPort.observe(new AdDisplayingStopped(existingAd)));
    }

    @Override
    public Either<DomainError, List<Ad>> getAllActiveAds() {
        return adsStoragePort.getAllActiveAds()
                .peekLeft(ignore -> observabilityPort.observe(new AdsRetrievalFailed()))
                .peek(ignore -> observabilityPort.observe(new AdsRetrieved()));
    }

}
