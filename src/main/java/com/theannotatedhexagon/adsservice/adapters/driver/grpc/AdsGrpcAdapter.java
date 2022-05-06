package com.theannotatedhexagon.adsservice.adapters.driver.grpc;

import com.theannotatedhexagon.adsservice.domain.models.AdId;
import com.theannotatedhexagon.adsservice.ports.driver.AdsPort;
import com.theannotatedhexagon.grpc.AdServiceGrpc;
import com.theannotatedhexagon.grpc.GrpcApi;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@AllArgsConstructor
public class AdsGrpcAdapter extends AdServiceGrpc.AdServiceImplBase {

    private AdsPort adsPort;
    private GrpcConverter converter;
    private GrpcValidator validator;

    @Override
    public void startAdDisplaying(GrpcApi.StartAdDisplayingRequest request, StreamObserver<GrpcApi.Ad> responseObserver) {
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

    @Override
    public void stopAdDisplaying(GrpcApi.StopAdDisplayingRequest request, StreamObserver<GrpcApi.Ad> responseObserver) {
        adsPort.stopAdDisplaying(AdId.of().value(request.getAdId()).build())
                .peek(ad -> {
                    responseObserver.onNext(converter.fromDomainModel(ad));
                    responseObserver.onCompleted();
                })
                .peekLeft(error -> responseObserver.onError(converter.fromDomainError(error)));
    }
}