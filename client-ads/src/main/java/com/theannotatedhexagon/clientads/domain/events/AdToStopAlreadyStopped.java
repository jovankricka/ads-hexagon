package com.theannotatedhexagon.clientads.domain.events;

import com.google.common.base.Preconditions;
import com.theannotatedhexagon.clientads.domain.models.Ad;
import lombok.Builder;

public class AdToStopAlreadyStopped implements DomainEvent {

    private final Ad ad;

    @Builder(builderMethodName = "of")
    public AdToStopAlreadyStopped(Ad ad) {
        Preconditions.checkNotNull(ad);
        this.ad = ad;
    }

    @Override
    public String getMessage() {
        return "Add " + ad + " had already been stopped.";
    }
}
