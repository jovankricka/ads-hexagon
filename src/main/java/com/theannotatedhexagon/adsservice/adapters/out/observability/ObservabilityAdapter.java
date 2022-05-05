package com.theannotatedhexagon.adsservice.adapters.out.observability;

import com.theannotatedhexagon.adsservice.domain.events.DomainEvent;
import com.theannotatedhexagon.adsservice.ports.out.ObservabilityPort;
import org.springframework.stereotype.Component;

@Component
public class ObservabilityAdapter implements ObservabilityPort {
    @Override
    public void observe(DomainEvent domainEvent) {

    }
}
