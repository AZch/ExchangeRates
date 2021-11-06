package com.wcreators.strategyemarsistoch.port;

import com.wcreators.objectmodels.model.CupRatePoint;
import com.wcreators.objectmodels.model.Rate;
import com.wcreators.objectmodels.model.RateAction;

public interface PortService {

    void send(RateAction rateAction);

    void receive(CupRatePoint rate);
}
