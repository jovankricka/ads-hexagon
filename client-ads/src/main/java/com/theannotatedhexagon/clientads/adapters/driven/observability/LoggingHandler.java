package com.theannotatedhexagon.clientads.adapters.driven.observability;

import com.theannotatedhexagon.clientads.domain.events.DomainEvent;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

@Component("ClientAdsLoggingHandler")
@Log4j
public class LoggingHandler implements ObservabilityHandler {

    @Override
    public <T extends DomainEvent> boolean isSubscribedTo(T domainEvent) {
        return true;
    }

    @Override
    public <T extends DomainEvent> void handle(T domainEvent) {
        log.info(domainEvent.getMessage());
    }
}
