package com.theannotatedhexagon.emailads.ports.driven;

import com.theannotatedhexagon.emailads.domain.errors.DomainError;
import com.theannotatedhexagon.emailads.domain.models.Email;
import io.vavr.control.Either;

public interface EmailTransportPort {

    Either<DomainError, Void> sendEmail(Email email);
}
