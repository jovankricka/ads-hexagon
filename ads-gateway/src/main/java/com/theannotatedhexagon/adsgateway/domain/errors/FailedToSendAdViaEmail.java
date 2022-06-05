package com.theannotatedhexagon.adsgateway.domain.errors;

import com.google.common.base.Preconditions;
import com.theannotatedhexagon.adsgateway.domain.models.Ad;
import lombok.Builder;
import lombok.Value;

@Value
public class FailedToSendAdViaEmail implements DomainError {

    String error;
    Ad ad;

    @Builder(builderMethodName = "of")
    public FailedToSendAdViaEmail(String error, Ad ad) {
        Preconditions.checkNotNull(error);
        Preconditions.checkNotNull(ad);
        this.error = error;
        this.ad = ad;
    }

    @Override
    public String getMessage() {
        return "Failed to send ad " + ad + " via email due to " + error;
    }

}
