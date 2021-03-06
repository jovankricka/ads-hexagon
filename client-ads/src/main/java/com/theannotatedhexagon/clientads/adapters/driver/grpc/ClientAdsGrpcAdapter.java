package com.theannotatedhexagon.clientads.adapters.driver.grpc;

import com.theannotatedhexagon.clientads.domain.models.AdId;
import com.theannotatedhexagon.clientads.grpc.ClientAdServiceGrpc;
import com.theannotatedhexagon.clientads.grpc.GrpcApi;
import com.theannotatedhexagon.clientads.ports.driver.AdsPort;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@AllArgsConstructor
public class ClientAdsGrpcAdapter extends ClientAdServiceGrpc.ClientAdServiceImplBase {

    private AdsPort adsPort;
    private GrpcConverter converter;
    private GrpcValidator validator;

    @Override
    public void startAdDisplaying(GrpcApi.StartAdDisplayingRequest request, StreamObserver<GrpcApi.ClientAd> responseObserver) {
        validator.validate(request)
                .peekLeft(
                        errors -> responseObserver.onError(converter.fromValidationErrors(errors))
                )
                .peek(
                        ignore -> adsPort.startAdDisplaying(request.getTitle(), request.getDescription())
                                .peek(ad -> {
                                    responseObserver.onNext(converter.fromDomainModel(ad));
                                    responseObserver.onCompleted();
                                })
                                .peekLeft(error -> responseObserver.onError(converter.fromDomainError(error)))
                );
    }

    public GrpcApi.ClientAd startAdDisplaying(GrpcApi.StartAdDisplayingRequest request) {
        var validationResult = validator.validate(request);
        if (validationResult.isLeft()) {
            throw new StatusRuntimeException(Status.fromCode(Status.Code.FAILED_PRECONDITION));
        }

        var result = adsPort.startAdDisplaying(request.getTitle(), request.getDescription());
        if (result.isLeft()) {
            throw converter.fromDomainError(result.getLeft());
        }
        return converter.fromDomainModel(result.get());
    }

    @Override
    public void stopAdDisplaying(GrpcApi.StopAdDisplayingRequest request, StreamObserver<GrpcApi.ClientAd> responseObserver) {
        adsPort.stopAdDisplaying(AdId.of().value(request.getAdId()).build())
                .peek(ad -> {
                    responseObserver.onNext(converter.fromDomainModel(ad));
                    responseObserver.onCompleted();
                })
                .peekLeft(error -> responseObserver.onError(converter.fromDomainError(error)));
    }
}