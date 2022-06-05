package com.theannotatedhexagon.adsgateway.adapters.driven.email;

import com.theannotatedhexagon.adsgateway.domain.errors.DomainError;
import com.theannotatedhexagon.adsgateway.domain.errors.FailedToSendAdViaEmail;
import com.theannotatedhexagon.adsgateway.domain.models.Ad;
import com.theannotatedhexagon.adsgateway.ports.driven.EmailSendingPort;
import com.theannotatedhexagon.emailads.grpc.EmailAdServiceGrpc;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.vavr.control.Either;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailSendingAdapter implements EmailSendingPort {

    private final EmailAdServiceGrpc.EmailAdServiceBlockingStub emailAdServiceBlockingStub;
    private final EmailConverter emailConverter;

    public EmailSendingAdapter(EmailConverter emailConverter) {
        this.emailAdServiceBlockingStub = EmailAdServiceGrpc.newBlockingStub(
                ManagedChannelBuilder.forAddress("localhost", 9090)
                        .usePlaintext()
                        .build());
        this.emailConverter = emailConverter;
    }

    @Override
    public Either<DomainError, Void> sendEmailWithAd(Ad ad) {
        try {
            emailAdServiceBlockingStub.sendEmailAd(emailConverter.toEmailAd(ad));
            return Either.right(null);

        } catch (StatusRuntimeException ex) {
            return Either.left(FailedToSendAdViaEmail.of()
                    .ad(ad)
                    .error(ex.getMessage())
                    .build());
        }
    }

}
