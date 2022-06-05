package com.theannotatedhexagon.clientads.ports.driven;

import com.theannotatedhexagon.clientads.domain.events.DomainEvent;

public interface ObservabilityPort {

    void observe(DomainEvent domainEvent);
}
