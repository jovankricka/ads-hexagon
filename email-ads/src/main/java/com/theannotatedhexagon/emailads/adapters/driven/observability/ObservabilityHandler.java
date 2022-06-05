package com.theannotatedhexagon.emailads.adapters.driven.observability;

import com.theannotatedhexagon.emailads.domain.events.DomainEvent;

public interface ObservabilityHandler {

    <T extends DomainEvent> boolean isSubscribedTo(T domainEvent);

    <T extends DomainEvent> void handle(T domainEvent);

}
