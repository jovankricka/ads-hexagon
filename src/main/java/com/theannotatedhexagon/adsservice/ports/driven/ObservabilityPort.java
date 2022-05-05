package com.theannotatedhexagon.adsservice.ports.driven;

import com.theannotatedhexagon.adsservice.domain.events.DomainEvent;

public interface ObservabilityPort {

    void observe(DomainEvent domainEvent);
}
