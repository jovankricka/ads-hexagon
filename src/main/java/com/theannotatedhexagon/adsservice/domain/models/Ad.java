package com.theannotatedhexagon.adsservice.domain.models;

import com.google.common.base.Preconditions;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import org.apache.logging.log4j.util.Strings;

import java.util.Objects;

@Value
@EqualsAndHashCode
@ToString
public class Ad {

    AdId id;
    String title;
    String description;
    boolean active;

    @Builder(builderMethodName = "of", toBuilder = true)
    public Ad(AdId id, String title, String description, boolean active) {
        Objects.requireNonNull(id);
        Preconditions.checkArgument(Strings.isNotBlank(title));
        Preconditions.checkArgument(Strings.isNotBlank(description));
        this.id = id;
        this.title = title;
        this.description = description;
        this.active = active;
    }

    public Ad deactivate() {
        return this.toBuilder().active(false).build();
    }

}
