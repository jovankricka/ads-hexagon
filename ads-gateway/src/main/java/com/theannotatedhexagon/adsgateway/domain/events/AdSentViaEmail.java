package com.theannotatedhexagon.adsgateway.domain.events;

import com.google.common.base.Preconditions;
import com.theannotatedhexagon.adsgateway.domain.models.Ad;
import lombok.Builder;

public class AdSentViaEmail implements DomainEvent {

    private final Ad ad;

    @Builder(builderMethodName = "of")
    public AdSentViaEmail(Ad ad) {
        Preconditions.checkNotNull(ad);
        this.ad = ad;
    }

    @Override
    public String getMessage() {
        return "Ad " + ad + " sent via email.";
    }
}
