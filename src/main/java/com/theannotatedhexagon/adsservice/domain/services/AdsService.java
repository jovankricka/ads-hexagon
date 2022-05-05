package com.theannotatedhexagon.adsservice.domain.services;

import com.theannotatedhexagon.adsservice.domain.errors.AdAlreadyStopped;
import com.theannotatedhexagon.adsservice.domain.errors.AdWithExistingTitle;
import com.theannotatedhexagon.adsservice.domain.errors.DomainError;
import com.theannotatedhexagon.adsservice.domain.errors.NonExistingAd;
import com.theannotatedhexagon.adsservice.domain.events.*;
import com.theannotatedhexagon.adsservice.domain.models.Ad;
import com.theannotatedhexagon.adsservice.domain.models.AdId;
import com.theannotatedhexagon.adsservice.ports.in.AdsPort;
import com.theannotatedhexagon.adsservice.ports.out.AdsStoragePort;
import com.theannotatedhexagon.adsservice.ports.out.ObservabilityPort;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class AdsService implements AdsPort {

    private AdsStoragePort adsStoragePort;
    private ObservabilityPort observabilityPort;

    @Override
    public Either<DomainError, Ad> startAdDisplaying(String adTitle, String adDescription) {
        var maybeAd = adsStoragePort.findAdByTitle(adTitle);
        if (maybeAd.isPresent() && maybeAd.get().isActive()) {
            observabilityPort.observe(new AttemptToDisplayAdWithExistingTitle());
            return Either.left(AdWithExistingTitle.of().title(adTitle).build());
        }
        var newAd = Ad.of()
                .id(AdId.generate())
                .title(adTitle)
                .description(adDescription)
                .active(true)
                .build();
        return adsStoragePort.save(newAd)
                .peekLeft(ignore -> observabilityPort.observe(new AdDisplayingStartFailed()))
                .peek(ignore -> observabilityPort.observe(new AdDisplayingStarted()));
    }

    @Override
    public Either<DomainError, Ad> stopAdDisplaying(AdId id) {
        var maybeAd = adsStoragePort.findById(id);
        if (maybeAd.isEmpty()) {
            observabilityPort.observe(new AttemptToStopNonExistingAd());
            return Either.left(NonExistingAd.of().id(id).build());
        }
        var existingAd = maybeAd.get();
        if (!existingAd.isActive()) {
            observabilityPort.observe(new AdToStopAlreadyStopped());
            return Either.left(AdAlreadyStopped.of().id(id).build());
        }
        return adsStoragePort.save(existingAd.deactivate())
                .peekLeft(ignore -> observabilityPort.observe(new AdDisplayingStopFailed()))
                .peek(ignore -> observabilityPort.observe(new AdDisplayingStopped()));
    }

    @Override
    public Either<DomainError, List<Ad>> getAllActiveAds() {
        return adsStoragePort.getAllActiveAds()
                .peekLeft(ignore -> observabilityPort.observe(new AdsRetrievalFailed()))
                .peek(ignore -> observabilityPort.observe(new AdsRetrieved()));
    }

}
