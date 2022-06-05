package com.theannotatedhexagon.adsgateway.adapters.driven.observability;

import com.theannotatedhexagon.adsgateway.domain.events.DomainEvent;

public interface ObservabilityHandler {

    <T extends DomainEvent> boolean isSubscribedTo(T domainEvent);

    <T extends DomainEvent> void handle(T domainEvent);

}
