package com.wcreators.strategyindicators.services.Cup;

import com.wcreators.objectmodels.model.Rate;

import java.util.Date;
import java.util.Optional;
import java.util.OptionalDouble;

public interface CupIndicator {
    Optional<CupPoint> addValue(Rate rate);

    int getElemsSize();

    OptionalDouble minLastLow(int lowerBound, int upperBound);
    OptionalDouble maxLastHigh(int lowerBound, int upperBound);

    double getHigh(int index);
    double getLow(int index);
    double getClose(int index);
    double getOpen(int index);
    Date getStart(int index);
    Date getEnd(int index);
}
