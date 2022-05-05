package com.theannotatedhexagon.adsservice.adapters.in.rest;

import com.theannotatedhexagon.adsservice.domain.errors.DomainError;
import com.theannotatedhexagon.adsservice.domain.models.Ad;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RestConverter {

    private static final Map<Class<? extends DomainError>, HttpStatus> ERROR_CODE_DICTIONARY = Map.of(
            DomainError.class, HttpStatus.INTERNAL_SERVER_ERROR
    );

    com.theannotatedhexagon.adsservice.api.models.Ad fromDomainModel(Ad ad) {
        return new com.theannotatedhexagon.adsservice.api.models.Ad()
                .id(ad.getId().getValue())
                .title(ad.getTitle())
                .description(ad.getDescription());
    }

    ResponseEntity<List<com.theannotatedhexagon.adsservice.api.models.Ad>> fromDomainModel(List<Ad> ads) {
        return ResponseEntity
                .ok(ads.stream()
                        .map(this::fromDomainModel)
                        .collect(Collectors.toList()));
    }

    public <T> ResponseEntity<T> fromDomainError(DomainError error) {
        return ResponseEntity
                .status(ERROR_CODE_DICTIONARY.get(error.getClass()).value())
                .build();
    }

}
