package com.wcreators.strategyindicators.services.indicators.ma;

public class SMMA extends EMA {
    public SMMA(int period) {
        super(period - 1);
    }
}
