package com.wcreators.strategyindicators.services.ema;

import com.wcreators.strategyindicators.models.Point;
import com.wcreators.strategyindicators.services.BasePointUtils;
import com.wcreators.strategyindicators.services.Indicator;

public interface EmaIndicator extends Indicator<Point, Void, BasePointUtils> {
    double getMultiplier();

    void setPeriod(int period);

    int getPeriod();
}
