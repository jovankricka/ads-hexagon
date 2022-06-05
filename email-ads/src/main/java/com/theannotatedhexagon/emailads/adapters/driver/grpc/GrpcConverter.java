package com.theannotatedhexagon.emailads.adapters.driver.grpc;

import com.google.rpc.Code;
import com.google.rpc.Status;
import com.theannotatedhexagon.emailads.domain.errors.DomainError;
import com.theannotatedhexagon.emailads.domain.errors.FailedToSendEmail;
import com.theannotatedhexagon.emailads.domain.models.Email;
import com.theannotatedhexagon.emailads.grpc.GrpcApi;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.StatusProto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("EmailAdsGrpcConverter")
public class GrpcConverter {

    private static final Map<Class<? extends DomainError>, Integer> ERROR_CODE_DICTIONARY = Map.of(
            FailedToSendEmail.class, Code.UNAVAILABLE.getNumber()
    );

    public GrpcApi.EmailAd fromDomainModel(Email email) {
        return GrpcApi.EmailAd.newBuilder()
                .setTitle(email.getTitle())
                .setDescription(email.getBody())
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
