package com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.EMA;

import java.util.Date;

public interface EmaIndicator {
    void addPoint(double value, Date date);

    double getMultiplier();

    int getElemsSize();

    Date getTime(int index);
    double getValue(int index);
}
