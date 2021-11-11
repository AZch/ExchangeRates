package com.wcreators.strategyemarsistoch.strategy;

import com.wcreators.objectmodels.constant.Strategy;
import com.wcreators.objectmodels.model.Rate;
import com.wcreators.strategyindicators.models.CupPoint;

import java.util.Optional;

public interface ProcessRatesService {
    Optional<String> addRate(CupPoint rate);

    boolean isRateForStrategy(Rate rate);

    boolean isNotRateForStrategy(Rate rate);

    Strategy getStrategy();
}
