package com.theannotatedhexagon.adsgateway.adapters.driven.client;

import com.theannotatedhexagon.adsgateway.domain.errors.DomainError;
import com.theannotatedhexagon.adsgateway.domain.errors.FailedToDeliverClientAd;
import com.theannotatedhexagon.adsgateway.domain.models.Ad;
import com.theannotatedhexagon.adsgateway.ports.driven.ClientDeliveryPort;
import com.theannotatedhexagon.clientads.adapters.driver.grpc.ClientAdsGrpcAdapter;
import io.grpc.StatusRuntimeException;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ClientDeliveryAdapter implements ClientDeliveryPort {

    private final ClientAdsGrpcAdapter clientAdsGrpcAdapter;
    private final ClientConverter clientConverter;

    @Override
    public Either<DomainError, Void> publishAd(Ad ad) {
        try {
            clientAdsGrpcAdapter.startAdDisplaying(clientConverter.toStartAdDeliveryRequest(ad));
            return Either.right(null);
        } catch (StatusRuntimeException ex) {
            return Either.left(FailedToDeliverClientAd.of()
                    .ad(ad)
                    .error(ex.getMessage())
                    .build());
        }
    }

}
