package com.theannotatedhexagon.emailads.domain.events;

import com.google.common.base.Preconditions;
import com.theannotatedhexagon.emailads.domain.models.Email;
import lombok.Builder;

public class AttemptToSentEmailFailed implements DomainEvent {

    private final Email email;
    private final String error;

    @Builder(builderMethodName = "of")
    public AttemptToSentEmailFailed(Email email, String error) {
        Preconditions.checkNotNull(email);
        Preconditions.checkNotNull(error);
        this.email = email;
        this.error = error;
    }

    @Override
    public String getMessage() {
        return "Sending of email " + email + " failed due to: " + error;
    }
}
