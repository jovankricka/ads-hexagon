package com.theannotatedhexagon.emailads.adapters.driver.grpc;

import com.theannotatedhexagon.emailads.domain.models.Email;
import com.theannotatedhexagon.emailads.grpc.EmailAdServiceGrpc;
import com.theannotatedhexagon.emailads.grpc.GrpcApi;
import com.theannotatedhexagon.emailads.ports.driver.EmailAdsPort;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@AllArgsConstructor
public class EmailAdsGrpcAdapter extends EmailAdServiceGrpc.EmailAdServiceImplBase {

    private EmailAdsPort emailAdsPort;
    private GrpcConverter converter;
    private GrpcValidator validator;

    @Override
    public void sendEmailAd(GrpcApi.EmailAd request, StreamObserver<GrpcApi.EmailAd> responseObserver) {
        validator.validate(request)
                .peekLeft(
                        errors -> responseObserver.onError(converter.fromValidationErrors(errors))
                )
                .peek(
                        ignore -> emailAdsPort.sendEmail(Email.of()
                                        .title(request.getTitle())
                                        .body(request.getDescription())
                                        .build())
                                .peek(ad -> responseObserver.onCompleted())
                                .peekLeft(error -> responseObserver.onError(converter.fromDomainError(error)))
                );
    }

}