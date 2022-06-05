package com.theannotatedhexagon.adsgateway.adapters.driven.client;

import com.theannotatedhexagon.adsgateway.domain.errors.DomainError;
import com.theannotatedhexagon.adsgateway.domain.errors.FailedToDeliverClientAd;
import com.theannotatedhexagon.adsgateway.domain.models.Ad;
import com.theannotatedhexagon.adsgateway.ports.driven.ClientDeliveryPort;
import com.theannotatedhexagon.clientads.grpc.ClientAdServiceGrpc;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.vavr.control.Either;
import org.springframework.stereotype.Component;

@Component
public class ClientDeliveryAdapter implements ClientDeliveryPort {

    private final ClientAdServiceGrpc.ClientAdServiceBlockingStub clientAdServiceBlockingStub;
    private final ClientConverter clientConverter;

    public ClientDeliveryAdapter(ClientConverter clientConverter) {
        this.clientAdServiceBlockingStub = ClientAdServiceGrpc.newBlockingStub(
                ManagedChannelBuilder.forAddress("localhost", 9090)
                        .usePlaintext()
                        .build());
        this.clientConverter = clientConverter;
    }

    @Override
    public Either<DomainError, Void> publishAd(Ad ad) {
        try {
            clientAdServiceBlockingStub.startAdDisplaying(clientConverter.toStartAdDeliveryRequest(ad));
            return Either.right(null);
        } catch (StatusRuntimeException ex) {
            return Either.left(FailedToDeliverClientAd.of()
                    .ad(ad)
                    .error(ex.getMessage())
                    .build());
        }
    }

}
