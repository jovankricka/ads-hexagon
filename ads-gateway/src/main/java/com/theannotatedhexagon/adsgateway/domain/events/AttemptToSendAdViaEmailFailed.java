package com.theannotatedhexagon.adsgateway.domain.events;

import com.google.common.base.Preconditions;
import com.theannotatedhexagon.adsgateway.domain.models.Ad;
import lombok.Builder;

public class AttemptToSendAdViaEmailFailed implements DomainEvent {

    private final Ad ad;
    private final String error;

    @Builder(builderMethodName = "of")
    public AttemptToSendAdViaEmailFailed(Ad ad, String error) {
        Preconditions.checkNotNull(ad);
        Preconditions.checkNotNull(error);
        this.ad = ad;
        this.error = error;
    }

    @Override
    public String getMessage() {
        return "Sending of email " + ad + " failed due to: " + error;
    }
}
