package com.theannotatedhexagon.adsgateway.adapters.driver.grpc;

import com.theannotatedhexagon.adsgateway.domain.models.Ad;
import com.theannotatedhexagon.adsgateway.grpc.AdServiceGrpc;
import com.theannotatedhexagon.adsgateway.grpc.GrpcApi;
import com.theannotatedhexagon.adsgateway.ports.driver.AdsPort;
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
    public void publishAd(GrpcApi.Ad request, StreamObserver<GrpcApi.Ad> responseObserver) {
        validator.validate(request)
                .peekLeft(
                        errors -> responseObserver.onError(converter.fromValidationErrors(errors))
                )
                .peek(
                        ignore -> adsPort.publishAd(Ad.of()
                                        .title(request.getTitle())
                                        .body(request.getDescription())
                                        .build())
                                .peek(ad -> responseObserver.onCompleted())
                                .peekLeft(error -> responseObserver.onError(converter.fromDomainErrors(error)))
                );
    }

}