package com.wcreators.strategyemarsistoch.port;

import com.wcreators.objectmodels.model.Rate;
import com.wcreators.objectmodels.model.RateAction;

public interface PortService {

    void send(RateAction rateAction);

    void receive(Rate rate);
}
