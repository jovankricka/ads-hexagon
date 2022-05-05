package com.theannotatedhexagon.adsservice.domain.events;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AdsRetrievalFailed implements DomainEvent {

    @Override
    public String getMessage() {
        return "Failed to retrieve ads.";
    }
}
