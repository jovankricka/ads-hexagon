package com.theannotatedhexagon.clientads.domain.events;

import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder(builderMethodName = "of")
@NoArgsConstructor
public class AdsRetrieved implements DomainEvent {
    @Override
    public String getMessage() {
        return "Ads retrieved successfully.";
    }
}
