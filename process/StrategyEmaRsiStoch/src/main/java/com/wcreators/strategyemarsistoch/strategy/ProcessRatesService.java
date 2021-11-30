package com.wcreators.strategyemarsistoch.strategy;

import com.wcreators.objectmodels.constant.Strategy;
import com.wcreators.objectmodels.model.Rate;
import com.wcreators.strategyindicators.models.CupPoint;

import java.util.Optional;

public interface ProcessRatesService {
    Optional<String> addRate(CupPoint rate);

    Strategy getStrategy();
}
