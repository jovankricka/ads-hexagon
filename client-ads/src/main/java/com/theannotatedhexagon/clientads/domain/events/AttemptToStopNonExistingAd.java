package com.theannotatedhexagon.clientads.domain.events;

import com.google.common.base.Preconditions;
import com.theannotatedhexagon.clientads.domain.models.AdId;
import lombok.Builder;

public class AttemptToStopNonExistingAd implements DomainEvent {

    private final AdId adId;

    @Builder(builderMethodName = "of")
    public AttemptToStopNonExistingAd(AdId adId) {
        Preconditions.checkNotNull(adId);
        this.adId = adId;
    }

    @Override
    public String getMessage() {
        return "Add with ID " + adId + " can not be stopped, because it does not exist.";
    }
}
