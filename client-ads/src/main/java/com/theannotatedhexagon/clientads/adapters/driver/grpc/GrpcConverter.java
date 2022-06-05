package com.theannotatedhexagon.clientads.adapters.driver.grpc;

import com.google.rpc.Code;
import com.google.rpc.Status;
import com.theannotatedhexagon.clientads.domain.errors.AdAlreadyStopped;
import com.theannotatedhexagon.clientads.domain.errors.AdWithExistingTitle;
import com.theannotatedhexagon.clientads.domain.errors.DomainError;
import com.theannotatedhexagon.clientads.domain.errors.NonExistingAd;
import com.theannotatedhexagon.clientads.domain.models.Ad;
import com.theannotatedhexagon.clientads.grpc.GrpcApi;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.StatusProto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("ClientAdsGrpcConverter")
public class GrpcConverter {

    private static final Map<Class<? extends DomainError>, Integer> ERROR_CODE_DICTIONARY = Map.of(
            AdWithExistingTitle.class, Code.ALREADY_EXISTS_VALUE,
            NonExistingAd.class, Code.NOT_FOUND_VALUE,
            AdAlreadyStopped.class, Code.FAILED_PRECONDITION_VALUE
    );

    public GrpcApi.ClientAd fromDomainModel(Ad ad) {
        return GrpcApi.ClientAd.newBuilder()
                .setId(ad.getId().getValue())
                .setTitle(ad.getTitle())
                .setDescription(ad.getDescription())
                .build();
    }

    public StatusRuntimeException fromDomainError(DomainError error) {
        return StatusProto.toStatusRuntimeException(Status.newBuilder()
                .setCode(ERROR_CODE_DICTIONARY.get(error.getClass()))
                .setMessage(error.getMessage())
                .build());
    }

    public StatusRuntimeException fromValidationErrors(List<GrpcValidator.ValidationError> errors) {
        return StatusProto.toStatusRuntimeException(Status.newBuilder()
                .setCode(Code.FAILED_PRECONDITION_VALUE)
                .setMessage(errors.stream().map(GrpcValidator.ValidationError::getMessage).collect(Collectors.joining()))
                .build());
    }
}
