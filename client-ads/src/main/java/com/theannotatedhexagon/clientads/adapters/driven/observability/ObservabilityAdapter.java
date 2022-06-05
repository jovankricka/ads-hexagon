package com.theannotatedhexagon.clientads.adapters.driven.observability;

import com.theannotatedhexagon.clientads.domain.events.DomainEvent;
import com.theannotatedhexagon.clientads.ports.driven.ObservabilityPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("ClientAdsObservabilityAdapter")
@AllArgsConstructor
public class ObservabilityAdapter implements ObservabilityPort {

    private final List<ObservabilityHandler> observabilityHandlers;

    @Override
    public void observe(DomainEvent domainEvent) {
        observabilityHandlers.stream()
                .filter(observabilityHandler -> observabilityHandler.isSubscribedTo(domainEvent))
                .forEach(observabilityHandler -> observabilityHandler.handle(domainEvent));
    }
}
