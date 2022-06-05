package com.theannotatedhexagon.adsgateway.adapters.driven.client;

import com.theannotatedhexagon.adsgateway.domain.models.Ad;
import com.theannotatedhexagon.clientads.grpc.GrpcApi;
import org.springframework.stereotype.Component;

@Component
public class ClientConverter {

    public GrpcApi.StartAdDisplayingRequest toStartAdDeliveryRequest(Ad ad) {
        return GrpcApi.StartAdDisplayingRequest.newBuilder()
                .setTitle(ad.getTitle())
                .setDescription(ad.getBody())
                .build();
    }
}
