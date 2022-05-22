package com.theannotatedhexagon.adsservice.adapters.driven.observability;

import com.theannotatedhexagon.adsservice.domain.events.DomainEvent;

public interface ObservabilityHandler {

    <T extends DomainEvent> boolean isSubscribedTo(T domainEvent);

    <T extends DomainEvent> void handle(T domainEvent);

}
