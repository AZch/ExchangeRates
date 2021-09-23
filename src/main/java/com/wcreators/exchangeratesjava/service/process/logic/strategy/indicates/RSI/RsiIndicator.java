package com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.RSI;

import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.Point;

import java.util.Date;

public interface RsiIndicator {
    void addPoint(Point point);

    int getElemsSize();

    Date getTime(int index);
    double getValue(int index);
}
