package com.theannotatedhexagon.adsservice.adapters.out.storage;

import com.theannotatedhexagon.adsservice.domain.errors.DomainError;
import com.theannotatedhexagon.adsservice.domain.models.Ad;
import com.theannotatedhexagon.adsservice.domain.models.AdId;
import com.theannotatedhexagon.adsservice.ports.driven.AdsStoragePort;
import io.vavr.control.Either;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AdInMemoryStorageAdapter implements AdsStoragePort {

    private final List<Ad> ads = new LinkedList<>();

    @Override
    public Either<DomainError, Ad> save(Ad ad) {
        ads.stream().filter(candidate -> candidate.getId().equals(ad.getId())).findAny()
                .ifPresent(ads::remove);
        ads.add(ad);
        return Either.right(ad);
    }

    @Override
    public Either<DomainError, List<Ad>> getAllActiveAds() {
        return Either.right(new LinkedList<>(ads.stream().filter(Ad::isActive).collect(Collectors.toList())));
    }

    @Override
    public Optional<Ad> findAdByTitle(String title) {
        return ads.stream().filter(ad -> ad.getTitle().equals(title)).findAny();
    }

    @Override
    public Optional<Ad> findById(AdId id) {
        return ads.stream().filter(ad -> ad.getId().equals(id)).findAny();
    }
}
