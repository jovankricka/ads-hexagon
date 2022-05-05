package com.theannotatedhexagon.adsservice.adapters.in.grpc;

import com.google.rpc.Code;
import com.google.rpc.Status;
import com.theannotatedhexagon.adsservice.domain.errors.AdAlreadyStopped;
import com.theannotatedhexagon.adsservice.domain.errors.AdWithExistingTitle;
import com.theannotatedhexagon.adsservice.domain.errors.DomainError;
import com.theannotatedhexagon.adsservice.domain.errors.NonExistingAd;
import com.theannotatedhexagon.adsservice.domain.models.Ad;
import com.theannotatedhexagon.grpc.GrpcApi;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GrpcConverter {

    private static final Map<Class<? extends DomainError>, Integer> ERROR_CODE_DICTIONARY = Map.of(
            AdWithExistingTitle.class, Code.ALREADY_EXISTS_VALUE,
            NonExistingAd.class, Code.NOT_FOUND_VALUE,
            AdAlreadyStopped.class, Code.FAILED_PRECONDITION_VALUE
    );

    public GrpcApi.Ad fromDomainModel(Ad ad) {
        return GrpcApi.Ad.newBuilder()
                .setId(ad.getId().getValue())
                .setTitle(ad.getTitle())
                .setDescription(ad.getDescription())
                .build();
    }

    public Status fromDomainError(DomainError error) {
        return Status.newBuilder()
                .setCode(ERROR_CODE_DICTIONARY.get(error.getClass()))
                .setMessage(error.getMessage())
                .build();
    }
}
