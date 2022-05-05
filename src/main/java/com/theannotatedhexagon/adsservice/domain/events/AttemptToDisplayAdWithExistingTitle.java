package com.theannotatedhexagon.adsservice.domain.events;

import com.google.common.base.Preconditions;
import lombok.Builder;

public class AttemptToDisplayAdWithExistingTitle implements DomainEvent {

    private final String adTitle;

    @Builder(builderMethodName = "of")
    public AttemptToDisplayAdWithExistingTitle(String adTitle) {
        Preconditions.checkNotNull(adTitle);
        this.adTitle = adTitle;
    }

    @Override
    public String getMessage() {
        return "Add with title " + adTitle + " can not be displayed, because there is already an add with the same title.";
    }
}
