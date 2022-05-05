package com.theannotatedhexagon.adsservice.ports.out;

import com.theannotatedhexagon.adsservice.domain.errors.DomainError;
import com.theannotatedhexagon.adsservice.domain.models.Ad;
import com.theannotatedhexagon.adsservice.domain.models.AdId;
import io.vavr.control.Either;

import java.util.List;
import java.util.Optional;

public interface AdsStoragePort {

    Either<DomainError, Ad> save(Ad ad);

    List<Ad> getAll();

    Optional<Ad> findAdByTitle(String title);

    Optional<Ad> findById(AdId id);
}
