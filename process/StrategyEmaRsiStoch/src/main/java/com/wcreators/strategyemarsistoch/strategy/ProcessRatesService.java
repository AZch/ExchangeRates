package com.wcreators.strategyemarsistoch.strategy;

import com.wcreators.objectmodels.model.Rate;
import com.wcreators.objectmodels.model.RateAction;

import java.util.Optional;

public interface ProcessRatesService {
    Optional<RateAction> addRate(Rate rate);

    boolean isRateForStrategy(Rate rate);

    boolean isNotRateForStrategy(Rate rate);
}
