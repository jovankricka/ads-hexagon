package com.theannotatedhexagon.adsservice.adapters.driven.observability;

import com.theannotatedhexagon.adsservice.domain.events.DomainEvent;
import com.theannotatedhexagon.adsservice.ports.driven.ObservabilityPort;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

@Component
@Log4j
public class ObservabilityAdapter implements ObservabilityPort {

    @Override
    public void observe(DomainEvent domainEvent) {
        log.info(domainEvent.getMessage());
    }
}