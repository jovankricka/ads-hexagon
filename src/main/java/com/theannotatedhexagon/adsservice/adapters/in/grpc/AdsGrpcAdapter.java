package com.theannotatedhexagon.adsservice.adapters.in.grpc;

import com.theannotatedhexagon.grpc.AdServiceGrpc;
import com.theannotatedhexagon.grpc.GrpcApi;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class AdsGrpcAdapter extends AdServiceGrpc.AdServiceImplBase {

    @Override
    public void startAdDisplaying(GrpcApi.StartAdDisplayingRequest request, StreamObserver<GrpcApi.AdId> responseObserver) {

    }

    @Override
    public void stopAdDisplaying(GrpcApi.StopAdDisplayingRequest request, StreamObserver<GrpcApi.AdId> responseObserver) {
    }
}