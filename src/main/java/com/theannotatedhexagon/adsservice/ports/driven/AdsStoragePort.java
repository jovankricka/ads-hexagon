package com.theannotatedhexagon.adsservice.ports.driven;

import com.theannotatedhexagon.adsservice.domain.errors.DomainError;
import com.theannotatedhexagon.adsservice.domain.models.Ad;
import com.theannotatedhexagon.adsservice.domain.models.AdId;
import io.vavr.control.Either;

import java.util.List;
import java.util.Optional;

public interface AdsStoragePort {

    Either<DomainError, Ad> save(Ad ad);

    Either<DomainError, List<Ad>> getAllActiveAds();

    Optional<Ad> findAdByTitle(String title);

    Optional<Ad> findById(AdId id);
}
