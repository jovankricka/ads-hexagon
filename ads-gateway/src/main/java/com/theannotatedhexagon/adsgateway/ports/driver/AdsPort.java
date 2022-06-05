package com.theannotatedhexagon.adsgateway.ports.driver;

import com.theannotatedhexagon.adsgateway.domain.errors.DomainError;
import com.theannotatedhexagon.adsgateway.domain.models.Ad;
import io.vavr.collection.Seq;
import io.vavr.control.Either;

public interface AdsPort {

    Either<Seq<DomainError>, Void> publishAd(Ad ad);

}
