package com.theannotatedhexagon.adsgateway.adapters.driver.grpc;

import com.theannotatedhexagon.adsgateway.grpc.GrpcApi;
import io.vavr.control.Either;
import lombok.Builder;
import lombok.Value;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("AdsGatewayGrpcValidator")
public class GrpcValidator {

    private static final int TITLE_LENGTH_LIMIT = 80;
    private static final int DESCRIPTION_LENGTH_LIMIT = 400;

    public Either<List<ValidationError>, Void> validate(GrpcApi.Ad emailAd) {

        var errors = new ArrayList<ValidationError>();
        if (!hasValidTitle(emailAd)) {
            errors.add(ValidationError.of().message(
                    "Title must be present and less than " + TITLE_LENGTH_LIMIT + " characters.").build());
        }
        if (!hasValidDescription(emailAd)) {
            errors.add(ValidationError.of().message(
                    "Description must be less than " + DESCRIPTION_LENGTH_LIMIT + " characters.").build());
        }
        if (errors.isEmpty()) {
            return Either.right(null);
        } else {
            return Either.left(errors);
        }
    }

    private boolean hasValidTitle(GrpcApi.Ad ad) {
        return !Strings.isBlank(ad.getTitle()) &&
                ad.getTitle().length() < TITLE_LENGTH_LIMIT;
    }

    private boolean hasValidDescription(GrpcApi.Ad ad) {
        return Strings.isBlank(ad.getDescription()) ||
                ad.getDescription().length() < DESCRIPTION_LENGTH_LIMIT;
    }

    @Value
    @Builder(builderMethodName = "of")
    public static class ValidationError {
        String message;
    }
}
