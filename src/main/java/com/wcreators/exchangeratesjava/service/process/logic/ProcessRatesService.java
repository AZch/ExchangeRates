package com.wcreators.exchangeratesjava.service.process.logic;

import com.wcreators.exchangeratesjava.model.Rate;

import java.util.List;
import java.util.Optional;

public interface ProcessRatesService {
    Optional<Rate> addRate(Rate rate);

    boolean isRateForStrategy(Rate rate);

    boolean isNotRateForStrategy(Rate rate);
}
