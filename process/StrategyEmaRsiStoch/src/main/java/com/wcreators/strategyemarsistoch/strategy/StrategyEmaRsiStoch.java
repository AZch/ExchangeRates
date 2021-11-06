package com.wcreators.strategyemarsistoch.strategy;

import com.wcreators.objectmodels.model.Rate;
import com.wcreators.objectmodels.model.RateAction;
import com.wcreators.strategyindicators.models.Point;
import com.wcreators.strategyindicators.models.CupPoint;
import com.wcreators.strategyindicators.services.cup.CupIndicator;
import com.wcreators.strategyindicators.services.ema.EmaIndicator;
import com.wcreators.strategyindicators.services.rsi.RsiIndicator;
import com.wcreators.strategyindicators.services.stoch.StochIndicator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

// https://www.bestbinar.ru/strategiya-skalpinga-na-1-minute/
@Service
@Slf4j
public class StrategyEmaRsiStoch implements ProcessRatesService {

    private final EmaIndicator ema;
    private final RsiIndicator rsi;
    private final StochIndicator stoch;

    private TempStrategyData data;

    public StrategyEmaRsiStoch(
            @Qualifier("EMA") EmaIndicator ema,
            @Qualifier("RSI") RsiIndicator rsi, @Qualifier("STOCH") StochIndicator stoch
    ) {
        this.ema = ema;
        this.rsi = rsi;
        this.stoch = stoch;
    }

    @PostConstruct
    public void init() {
        ema.setPeriod(7);
        rsi.setPeriod(3);
        stoch.setPeriod(6);
        stoch.getUtils().setPeriodFastK(3);
        stoch.getUtils().setPeriodSlowD(3);
    }

    @Override
    public Optional<String> addRate(CupPoint cupPoint) {

        Point emaPoint = Point.builder().value(cupPoint.getClose()).time(cupPoint.getEnd()).build();
        ema.addPoint(emaPoint);
        Point rsiPoint = Point.builder().value(cupPoint.getClose()).time(cupPoint.getEnd()).build();
        rsi.addPoint(rsiPoint);
        stoch.update();

        if (data != null && testStrategyCall(cupPoint)) {
            log.info("Need try to buy, check this and after 5 min value");
            return Optional.of("try to buy (call)");
        }

        if (data != null && testStrategyPut(cupPoint)) {
            log.info("Need try to sell, check this and after 5 min value");
            return Optional.of("try to sell (put)");
        }

        data = new TempStrategyData();
        updatePrevValues(cupPoint);

        return Optional.empty();
    }

    public boolean testStrategyCall(CupPoint lastCupPoint) {
        double lastEmaPoint = ema.getUtils().getValue(ema.getUtils().getElemsSize() - 1);
        double prevCupPointHigh = data.getPreviousCupPoint().getHigh();
        double prevCupPointLow = data.getPreviousCupPoint().getLow();
        if (!(prevCupPointHigh > lastEmaPoint && prevCupPointLow < lastEmaPoint)) {
            return false;
        }
        double lastCupPointLow = lastCupPoint.getLow();
        if (!(lastCupPointLow > lastEmaPoint)) {
            return false;
        }

        double lastRsiPoint = rsi.getUtils().getValue(rsi.getUtils().getElemsSize() - 1);
        if (!(lastRsiPoint > 50)) { // 80 is better
            return false;
        }

//        double prevStochFastKPoint = data.getLastStochFastKPoint().getValue();
//        double prevStochSlowDPoint = data.getLastStochSlowDPoint().getValue();
//        double lastStochFastKPoint = stoch.getUtils().getFastKValue(stoch.getUtils().getFastKElemsSize() - 1);
//        double lastStochSlowDPoint = stoch.getUtils().getSlowDValue(stoch.getUtils().getSlowDElemsSize() - 1);
//        if (!(lastStochFastKPoint > prevStochFastKPoint && lastStochSlowDPoint > prevStochSlowDPoint)) {
//            return false;
//        }
//        if (!(prevStochFastKPoint < 30 && prevStochSlowDPoint < 30)) {
//            return false;
//        }

        return true;
    }

    public boolean testStrategyPut(CupPoint lastCupPoint) {
        double lastEmaPoint = ema.getUtils().getValue(ema.getUtils().getElemsSize() - 1);
        double prevCupPointHigh = data.getPreviousCupPoint().getHigh();
        double prevCupPointLow = data.getPreviousCupPoint().getLow();
        if (!(prevCupPointHigh > lastEmaPoint && prevCupPointLow < lastEmaPoint)) {
            return false;
        }
        double lastCupPointHigh = lastCupPoint.getHigh();
        if (!(lastCupPointHigh < lastEmaPoint)) {
            return false;
        }

        double lastRsiPoint = rsi.getUtils().getValue(rsi.getUtils().getElemsSize() - 1);
        if (!(lastRsiPoint < 50)) { // better if bottom line was crossed in (20 or 30)
            return false;
        }

//        double prevStochFastKPoint = data.getLastStochFastKPoint().getValue();
//        double prevStochSlowDPoint = data.getLastStochSlowDPoint().getValue();
//        double lastStochFastKPoint = stoch.getUtils().getFastKValue(stoch.getUtils().getFastKElemsSize() - 1);
//        double lastStochSlowDPoint = stoch.getUtils().getSlowDValue(stoch.getUtils().getSlowDElemsSize() - 1);
//        if (!(lastStochFastKPoint < prevStochFastKPoint && lastStochSlowDPoint < prevStochSlowDPoint)) {
//            return false;
//        }
//        if (!(prevStochFastKPoint > 70 && prevStochSlowDPoint > 70)) {
//            return false;
//        }

        return true;
    }

    private void updatePrevValues(CupPoint lastCupPoint) {
        data.setPreviousCupPoint(lastCupPoint);
        data.setLastStochFastKPoint(
                Point.builder()
                        .value(stoch.getUtils().getFastKValue(stoch.getUtils().getFastKElemsSize() - 1))
                        .time(stoch.getUtils().getFastKTime(stoch.getUtils().getFastKElemsSize() - 1))
                        .build()
        );
        data.setLastStochSlowDPoint(
                Point.builder()
                        .value(stoch.getUtils().getSlowDValue(stoch.getUtils().getSlowDElemsSize() - 1))
                        .time(stoch.getUtils().getSlowDTime(stoch.getUtils().getSlowDElemsSize() - 1))
                        .build()
        );
    }

    @Override
    public boolean isRateForStrategy(Rate rate) {
        return (rate.getMajor().equals("USD") && rate.getMinor().equals("EUR"))
                || (rate.getMajor().equals("EUR") && rate.getMinor().equals("USD"));
    }

    @Override
    public boolean isNotRateForStrategy(Rate rate) {
        return !isRateForStrategy(rate);
    }

    private static class TempStrategyData {
        private CupPoint previousCupPoint;
        private Point lastStochFastKPoint;
        private Point lastStochSlowDPoint;

        public CupPoint getPreviousCupPoint() {
            return previousCupPoint;
        }

        public void setPreviousCupPoint(CupPoint previousCupPoint) {
            this.previousCupPoint = previousCupPoint;
        }

        public Point getLastStochFastKPoint() {
            return lastStochFastKPoint;
        }

        public void setLastStochFastKPoint(Point lastStochFastKPoint) {
            this.lastStochFastKPoint = lastStochFastKPoint;
        }

        public Point getLastStochSlowDPoint() {
            return lastStochSlowDPoint;
        }

        public void setLastStochSlowDPoint(Point lastStochSlowDPoint) {
            this.lastStochSlowDPoint = lastStochSlowDPoint;
        }
    }
}
