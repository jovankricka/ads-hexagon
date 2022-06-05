package com.theannotatedhexagon.adsgateway.adapters.driver.grpc;

import com.google.rpc.Code;
import com.google.rpc.Status;
import com.theannotatedhexagon.adsgateway.domain.errors.DomainError;
import com.theannotatedhexagon.adsgateway.domain.models.Ad;
import com.theannotatedhexagon.emailads.grpc.GrpcApi;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.StatusProto;
import io.vavr.collection.Seq;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("AdsGatewayGrpcConverter")
public class GrpcConverter {

    public GrpcApi.EmailAd fromDomainModel(Ad ad) {
        return GrpcApi.EmailAd.newBuilder()
                .setTitle(ad.getTitle())
                .setDescription(ad.getBody())
                .build();
    }

    public StatusRuntimeException fromDomainErrors(Seq<DomainError> errors) {
        return StatusProto.toStatusRuntimeException(Status.newBuilder()
                .setCode(Code.UNAVAILABLE.getNumber())
                .setMessage(errors.toStream().map(DomainError::getMessage)
                        .collect(Collectors.joining(";")))
                .build());
    }

    public StatusRuntimeException fromValidationErrors(List<com.theannotatedhexagon.adsgateway.adapters.driver.grpc.GrpcValidator.ValidationError> errors) {
        return StatusProto.toStatusRuntimeException(Status.newBuilder()
                .setCode(Code.FAILED_PRECONDITION_VALUE)
                .setMessage(errors.stream().map(GrpcValidator.ValidationError::getMessage).collect(Collectors.joining()))
                .build());
    }
}
