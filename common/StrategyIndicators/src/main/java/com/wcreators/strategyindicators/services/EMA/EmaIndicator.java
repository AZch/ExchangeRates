package com.wcreators.strategyindicators.services.EMA;

import java.util.Date;

public interface EmaIndicator {
    void addPoint(double value, Date date);

    double getMultiplier();

    int getElemsSize();

    Date getTime(int index);
    double getValue(int index);
}