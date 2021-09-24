package com.wcreators.exchangeratesjava.service.database.rate.action;

import com.wcreators.exchangeratesjava.entity.RateActionEntity;
import com.wcreators.exchangeratesjava.model.Rate;
import com.wcreators.exchangeratesjava.model.RateAction;

public interface RateActionService {
    RateActionEntity save(RateAction action);
}
