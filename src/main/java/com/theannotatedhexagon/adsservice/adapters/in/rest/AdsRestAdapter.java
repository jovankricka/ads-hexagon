package com.theannotatedhexagon.adsservice.adapters.in.rest;

import com.theannotatedhexagon.adsservice.api.AdsApi;
import com.theannotatedhexagon.adsservice.api.models.Ad;
import com.theannotatedhexagon.adsservice.ports.in.AdsPort;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class AdsRestAdapter implements AdsApi {

    private AdsPort adsPort;
    private RestConverter converter;

    @Override
    public ResponseEntity<List<Ad>> getAds() {
        return adsPort.getAllActiveAds()
                .fold(
                        error -> converter.fromDomainError(error),
                        ads -> converter.fromDomainModel(ads)
                );
    }

}
