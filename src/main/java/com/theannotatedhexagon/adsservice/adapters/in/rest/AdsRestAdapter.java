package com.theannotatedhexagon.adsservice.adapters.in.rest;

import com.theannotatedhexagon.adsservice.api.AdsApi;
import com.theannotatedhexagon.adsservice.api.models.Ad;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdsRestAdapter implements AdsApi {

    @Override
    public ResponseEntity<List<Ad>> getAds() {
        return null;
    }

}
