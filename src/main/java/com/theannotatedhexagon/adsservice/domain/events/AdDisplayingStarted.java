package com.theannotatedhexagon.adsservice.domain.events;

import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder(builderMethodName = "of")
@NoArgsConstructor
public class AdDisplayingStarted implements DomainEvent {
}