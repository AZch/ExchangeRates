package com.wcreators.exchangeratesjava.service.consume.logic.db.action;

import com.wcreators.exchangeratesjava.model.Rate;

public interface ConsumeActionRateService {
    void consume(Rate rate, String action);
}
