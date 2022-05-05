package com.theannotatedhexagon.adsservice.domain.errors;

import com.google.common.base.Preconditions;
import lombok.Builder;
import lombok.Value;

@Value
public class AdWithDuplicateTitle implements DomainError {

    String title;

    @Builder(builderMethodName = "of")
    public AdWithDuplicateTitle(String title) {
        Preconditions.checkNotNull(title);
        this.title = title;
    }

    @Override
    public String getMessage() {
        return "Ad with duplicate title " + title + " is not allowed.";
    }

}
