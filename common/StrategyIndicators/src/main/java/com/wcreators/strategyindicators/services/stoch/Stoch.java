package com.wcreators.strategyindicators.services.stoch;

import com.wcreators.strategyindicators.models.Point;
import com.wcreators.strategyindicators.services.cup.Cup;
import com.wcreators.strategyindicators.services.cup.CupIndicator;
import com.wcreators.strategyindicators.services.cup.CupUtils;
import com.wcreators.strategyindicators.services.ema.Ema;
import com.wcreators.strategyindicators.services.ema.EmaIndicator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;

@Service
@Qualifier("STOCH")
@Scope("prototype")
public class Stoch implements StochIndicator {

    private final CupIndicator cup;
    private final EmaIndicator emaFastK;
    private final EmaIndicator emaSlowD;
    private StochUtils utils;
    private int period = 5;

    public Stoch(CupIndicator cup, @Qualifier("EMA") EmaIndicator emaFastK, @Qualifier("EMA") EmaIndicator emaSlowD) {
        this.cup = cup;
        this.emaFastK = emaFastK;
        this.emaSlowD = emaSlowD;
    }

    @PostConstruct
    public void init() {
        utils = new StochUtils(emaFastK, emaSlowD);
    }

    @Override
    public void update() {
        CupUtils cupUtils = cup.getUtils();
        Date date = cupUtils.getEnd(cupUtils.getElemsSize() - 1);
        if (cupUtils.getElemsSize() < period) {
            emaFastK.addPoint(Point.builder().value(0D).time(date).build());
            emaSlowD.addPoint(Point.builder().value(0D).time(date).build());
        } else {
            int lowerBound = cupUtils.getElemsSize() - period;
            int upperBound = cupUtils.getElemsSize();
            double minLastLow = cupUtils.minLastLow(lowerBound, upperBound).orElse(0D);
            double maxLastHigh = cupUtils.maxLastHigh(lowerBound, upperBound).orElse(0D);
            double closeLast = cupUtils.getClose(cupUtils.getElemsSize() - 1);

            double fastK = 100 * ((closeLast - minLastLow) / (maxLastHigh - minLastLow));
            emaFastK.addPoint(Point.builder().time(date).value(fastK).build());

            double slowD = emaFastK.getUtils().getValue(emaFastK.getUtils().getElemsSize() - 1);
            emaSlowD.addPoint(Point.builder().time(date).value(slowD).build());
        }
    }

    @Override
    public StochUtils getUtils() {
        return utils;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }
}
