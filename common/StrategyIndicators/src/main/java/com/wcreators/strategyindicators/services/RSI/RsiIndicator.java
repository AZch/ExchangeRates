package com.wcreators.strategyindicators.services.RSI;

import com.wcreators.strategyindicators.models.Point;

import java.util.Date;

public interface RsiIndicator {
    void addPoint(Point point);

    int getElemsSize();

    Date getTime(int index);
    double getValue(int index);
}
