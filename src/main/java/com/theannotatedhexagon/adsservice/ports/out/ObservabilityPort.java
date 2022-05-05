package com.theannotatedhexagon.adsservice.ports.out;

import com.theannotatedhexagon.adsservice.domain.events.DomainEvent;

public interface ObservabilityPort {

    void observe(DomainEvent domainEvent);
}
