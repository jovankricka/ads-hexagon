package com.theannotatedhexagon.clientads.domain.models;

import com.google.common.base.Preconditions;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import org.apache.logging.log4j.util.Strings;

import java.util.UUID;

@Value
@EqualsAndHashCode
@ToString
public class AdId {

    String value;

    @Builder(builderMethodName = "of")
    private AdId(String value) {
        Preconditions.checkArgument(Strings.isNotBlank(value));
        this.value = value;
    }

    public static AdId generate() {
        return AdId.of().value(UUID.randomUUID().toString()).build();
    }
}
