package com.theannotatedhexagon.adsgateway.adapters.driven.email;

import com.theannotatedhexagon.adsgateway.domain.models.Ad;
import com.theannotatedhexagon.emailads.grpc.GrpcApi;
import org.springframework.stereotype.Component;

@Component
public class EmailConverter {


    public GrpcApi.EmailAd toEmailAd(Ad ad) {
        return GrpcApi.EmailAd.newBuilder()
                .setTitle(ad.getTitle())
                .setDescription(ad.getBody())
                .build();
    }
}
