package com.theannotatedhexagon.adsgateway.ports.driven;

import com.theannotatedhexagon.adsgateway.domain.errors.DomainError;
import com.theannotatedhexagon.adsgateway.domain.models.Ad;
import io.vavr.control.Either;

public interface EmailSendingPort {

    Either<DomainError, Void> sendEmailWithAd(Ad ad);
}
