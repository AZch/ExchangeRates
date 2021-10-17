package com.wcreators.strategyindicators.services.stoch;

import com.wcreators.strategyindicators.services.ema.EmaIndicator;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
public class StochUtils {

    private final EmaIndicator emaFastK;
    private final EmaIndicator emaSlowD;

    public double getFastKValue(int index) {
        return emaFastK.getUtils().getValue(index);
    }

    public double getSlowDValue(int index) {
        return emaSlowD.getUtils().getValue(index);
    }

    public Date getFastKTime(int index) {
        return emaFastK.getUtils().getTime(index);
    }

    public Date getSlowDTime(int index) {
        return emaSlowD.getUtils().getTime(index);
    }

    public int getPeriodSlowD() {
        return emaSlowD.getPeriod();
    }

    public int getPeriodFastK() {
        return emaFastK.getPeriod();
    }

    public void setPeriodSlowD(int period) {
        emaSlowD.setPeriod(period);
    }

    public void setPeriodFastK(int period) {
        emaFastK.setPeriod(period);
    }

    public int getFastKElemsSize() {
        return emaFastK.getUtils().getElemsSize();
    }

    public int getSlowDElemsSize() {
        return emaSlowD.getUtils().getElemsSize();
    }
}
