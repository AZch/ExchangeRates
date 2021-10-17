package com.wcreators.strategyindicators.services.stoch;

public interface StochIndicator {
    void update();

    StochUtils getUtils();

    int getPeriod();

    void setPeriod(int period);
}
