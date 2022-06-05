package com.theannotatedhexagon.clientads.domain.errors;

import com.google.common.base.Preconditions;
import com.theannotatedhexagon.clientads.domain.models.AdId;
import lombok.Builder;
import lombok.Value;

@Value
public class NonExistingAd implements DomainError {

    AdId id;

    @Builder(builderMethodName = "of")
    public NonExistingAd(AdId id) {
        Preconditions.checkNotNull(id);
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Ad with id " + id + " does not exist.";
    }

}