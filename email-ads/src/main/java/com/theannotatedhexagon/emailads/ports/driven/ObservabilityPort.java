package com.theannotatedhexagon.emailads.ports.driven;

import com.theannotatedhexagon.emailads.domain.events.DomainEvent;

public interface ObservabilityPort {

    void observe(DomainEvent domainEvent);
}
