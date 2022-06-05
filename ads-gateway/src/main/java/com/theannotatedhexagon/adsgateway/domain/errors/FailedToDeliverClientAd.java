package com.theannotatedhexagon.adsgateway.domain.errors;

import com.google.common.base.Preconditions;
import com.theannotatedhexagon.adsgateway.domain.models.Ad;
import lombok.Builder;
import lombok.Value;

@Value
public class FailedToDeliverClientAd implements DomainError {

    String error;
    Ad ad;

    @Builder(builderMethodName = "of")
    public FailedToDeliverClientAd(String error, Ad ad) {
        Preconditions.checkNotNull(error);
        Preconditions.checkNotNull(ad);
        this.error = error;
        this.ad = ad;
    }

    @Override
    public String getMessage() {
        return "Failed to deliver ad " + ad + " to the client due to " + error;
    }

}
