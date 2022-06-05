package com.theannotatedhexagon.adsgateway.ports.driven;

import com.theannotatedhexagon.adsgateway.domain.events.DomainEvent;

public interface ObservabilityPort {

    void observe(DomainEvent domainEvent);
}
