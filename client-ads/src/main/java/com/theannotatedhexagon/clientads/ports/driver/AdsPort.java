package com.theannotatedhexagon.clientads.ports.driver;

import com.theannotatedhexagon.clientads.domain.errors.DomainError;
import com.theannotatedhexagon.clientads.domain.models.Ad;
import com.theannotatedhexagon.clientads.domain.models.AdId;
import io.vavr.control.Either;

import java.util.List;

public interface AdsPort {

    Either<DomainError, Ad> startAdDisplaying(String adTitle, String adDescription);

    Either<DomainError, Ad> stopAdDisplaying(AdId id);

    Either<DomainError, List<Ad>> getAllActiveAds();

}
