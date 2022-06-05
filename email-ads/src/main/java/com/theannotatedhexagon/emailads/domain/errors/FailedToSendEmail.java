package com.theannotatedhexagon.emailads.domain.errors;

import com.google.common.base.Preconditions;
import lombok.Builder;
import lombok.Value;

@Value
public class FailedToSendEmail implements DomainError {

    String error;

    @Builder(builderMethodName = "of")
    public FailedToSendEmail(String error) {
        Preconditions.checkNotNull(error);
        this.error = error;
    }

    @Override
    public String getMessage() {
        return "Email failed to be sent due to: " + error;
    }

}
