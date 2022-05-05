package com.theannotatedhexagon.adsservice.domain.events;

import com.google.common.base.Preconditions;
import com.theannotatedhexagon.adsservice.domain.models.Ad;
import lombok.Builder;

public class AdDisplayingStarted implements DomainEvent {

    private final Ad ad;

    @Builder(builderMethodName = "of")
    public AdDisplayingStarted(Ad ad) {
        Preconditions.checkNotNull(ad);
        this.ad = ad;
    }

    @Override
    public String getMessage() {
        return "Started displaying ad " + ad + ".";
    }
}
