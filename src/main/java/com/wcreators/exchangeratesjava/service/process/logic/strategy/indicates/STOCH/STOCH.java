package com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.STOCH;


import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.Cup.Cup;
import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.EMA.Ema;
import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.Point;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;

import static java.lang.Math.max;

@Service
@RequiredArgsConstructor
@Scope("prototype")
public class STOCH {

    private final Cup cup;
    private final Ema emaFastK;
    private final Ema emaSlowD;
    private final int periodsLow = 5;
    private final int periodsHigh = 5;

    public void update() {
        int maxPeriod = max(periodsHigh, periodsLow);
        Date date = cup.getEnd(cup.getElemsSize() - 1);
        if (cup.getElemsSize() < maxPeriod) {
            emaFastK.addPoint(0D, date);
            emaSlowD.addPoint(0D, date);
        } else {
            int lowerBound = cup.getElemsSize() - maxPeriod;
            int upperBound = cup.getElemsSize();
            double minLastLow = cup.minLastLow(lowerBound, upperBound).orElse(0D);
            double maxLastHigh = cup.maxLastHigh(lowerBound, upperBound).orElse(0D);
            double closeLast = cup.getClose(cup.getElemsSize() - 1);

            double fastK = 100 * ((closeLast - minLastLow) / (maxLastHigh - minLastLow));

            emaFastK.addPoint(fastK, date);
            emaSlowD.addPoint(emaFastK.getValue(emaFastK.getElemsSize() - 1), date);
        }
    }

    public double getFastKValue(int index) {
        return emaFastK.getValue(index);
    }

    public double getSlowDValue(int index) {
        return emaSlowD.getValue(index);
    }

    public int getPeriodsLow() {
        return periodsLow;
    }

    public int getPeriodsHigh() {
        return periodsHigh;
    }

    public int getFastKElemsSize() {
        return emaFastK.getElemsSize();
    }

    public int getSlowDElemsSize() {
        return emaSlowD.getElemsSize();
    }
}
