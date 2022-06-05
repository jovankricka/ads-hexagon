package com.theannotatedhexagon.clientads.adapters.driven.observability;

import com.theannotatedhexagon.clientads.domain.events.DomainEvent;

public interface ObservabilityHandler {

    <T extends DomainEvent> boolean isSubscribedTo(T domainEvent);

    <T extends DomainEvent> void handle(T domainEvent);

}
