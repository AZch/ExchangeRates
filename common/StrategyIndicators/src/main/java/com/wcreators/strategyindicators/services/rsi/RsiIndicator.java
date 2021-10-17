package com.wcreators.strategyindicators.services.rsi;

import com.wcreators.strategyindicators.models.Point;
import com.wcreators.strategyindicators.services.BasePointUtils;
import com.wcreators.strategyindicators.services.Indicator;

public interface RsiIndicator extends Indicator<Point, Void, BasePointUtils> {
    void setPeriod(int period);
}
