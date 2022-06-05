package com.theannotatedhexagon.emailads.domain.events;

import com.google.common.base.Preconditions;
import com.theannotatedhexagon.emailads.domain.models.Email;
import lombok.Builder;

public class EmailSent implements DomainEvent {

    private final Email email;

    @Builder(builderMethodName = "of")
    public EmailSent(Email email) {
        Preconditions.checkNotNull(email);
        this.email = email;
    }

    @Override
    public String getMessage() {
        return "Email " + email + " sent.";
    }
}
