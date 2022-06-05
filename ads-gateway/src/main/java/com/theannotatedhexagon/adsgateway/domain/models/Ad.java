package com.theannotatedhexagon.adsgateway.domain.models;

import com.google.common.base.Preconditions;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import org.apache.logging.log4j.util.Strings;

@Value
@EqualsAndHashCode
@ToString
public class Ad {

    String title;
    String body;

    @Builder(builderMethodName = "of", toBuilder = true)
    private Ad(String title, String body) {
        Preconditions.checkArgument(Strings.isNotBlank(title) && title.length() < 80);
        Preconditions.checkArgument(Strings.isBlank(body) || body.length() < 400);
        this.title = title;
        this.body = body;
    }

}
