package com.theannotatedhexagon.clientads.ports.driven;

import com.theannotatedhexagon.clientads.domain.errors.DomainError;
import com.theannotatedhexagon.clientads.domain.models.Ad;
import com.theannotatedhexagon.clientads.domain.models.AdId;
import io.vavr.control.Either;

import java.util.List;
import java.util.Optional;

public interface AdsStoragePort {

    Either<DomainError, Ad> save(Ad ad);

    Either<DomainError, List<Ad>> getAllActiveAds();

    Optional<Ad> findAdByTitle(String title);

    Optional<Ad> findById(AdId id);
}
