package com.theannotatedhexagon.adsservice.ports.in;

import com.theannotatedhexagon.adsservice.domain.errors.DomainError;
import com.theannotatedhexagon.adsservice.domain.models.Ad;
import com.theannotatedhexagon.adsservice.domain.models.AdId;
import io.vavr.control.Either;

public interface AdsPort {

    Either<DomainError, Ad> startAdDisplaying(String adTitle, String adDescription);

    Either<DomainError, Ad> stopAdDisplaying(AdId id);

}
