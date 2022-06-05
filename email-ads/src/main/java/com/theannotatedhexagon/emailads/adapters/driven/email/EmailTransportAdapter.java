package com.theannotatedhexagon.emailads.adapters.driven.email;

import com.theannotatedhexagon.emailads.domain.errors.DomainError;
import com.theannotatedhexagon.emailads.domain.errors.FailedToSendEmail;
import com.theannotatedhexagon.emailads.domain.models.Email;
import com.theannotatedhexagon.emailads.ports.driven.EmailTransportPort;
import io.vavr.control.Either;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailTransportAdapter implements EmailTransportPort {

    @Override
    public Either<DomainError, Void> sendEmail(Email email) {
        return Either.left(FailedToSendEmail.of().error("Email sending adapter not yet implemented").build());
    }

}
