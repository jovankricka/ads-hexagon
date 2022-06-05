package com.theannotatedhexagon.emailads.ports.driver;

import com.theannotatedhexagon.emailads.domain.errors.DomainError;
import com.theannotatedhexagon.emailads.domain.models.Email;
import io.vavr.control.Either;

public interface EmailAdsPort {

    Either<DomainError, Void> sendEmail(Email email);

}
