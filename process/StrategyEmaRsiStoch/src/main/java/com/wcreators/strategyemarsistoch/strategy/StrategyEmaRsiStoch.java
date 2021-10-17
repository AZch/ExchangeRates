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

    private final CupIndicator cup;
    private final EmaIndicator ema;
    private final RsiIndicator rsi;
    private final StochIndicator stoch;

    private TempStrategyData data;

    public StrategyEmaRsiStoch(
            CupIndicator cup, @Qualifier("EMA") EmaIndicator ema,
            @Qualifier("RSI") RsiIndicator rsi, @Qualifier("STOCH") StochIndicator stoch
    ) {
        this.cup = cup;
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
    public Optional<RateAction> addRate(Rate rate) {

        Optional<CupPoint> optionalCupPoint = cup.addPoint(rate);
        if (optionalCupPoint.isPresent()) {
            CupPoint cupPoint = optionalCupPoint.get();

            Point emaPoint = Point.builder().value(cupPoint.getClose()).time(cupPoint.getEnd()).build();
            ema.addPoint(emaPoint);
            Point rsiPoint = Point.builder().value(cupPoint.getClose()).time(cupPoint.getEnd()).build();
            rsi.addPoint(rsiPoint);
            stoch.update();

            if (testStrategyCall()) {
                log.info("Need try to buy with {}, check this and after 5 min value", rate);
                return Optional.of(
                        RateAction.builder()
                                .rate(rate)
                                .action("try to buy (call)")
                                .build()
                );
            }

            if (testStrategyPut()) {
                log.info("Need try to sell with {}, check this and after 5 min value", rate);
                return Optional.of(
                        RateAction.builder()
                            .rate(rate)
                            .action("try to sell (put)")
                            .build()
                    );
            }
        }

        return Optional.empty();
    }

    public boolean testStrategyCall() {
        if (data == null) {
            data = new TempStrategyData();
            updatePrevValues();
            return false;
        }

        double lastEmaPoint = ema.getUtils().getValue(ema.getUtils().getElemsSize() - 1);
        double prevCupPointHigh = data.getPreviousCupPoint().getHigh();
        double prevCupPointLow = data.getPreviousCupPoint().getLow();
        if (!(prevCupPointHigh > lastEmaPoint && prevCupPointLow < lastEmaPoint)) {
            updatePrevValues();
            return false;
        }
        double lastCupPointLow = cup.getUtils().getLow(cup.getUtils().getElemsSize() - 1);
        if (!(lastCupPointLow > lastEmaPoint)) {
            updatePrevValues();
            return false;
        }

        double lastRsiPoint = rsi.getUtils().getValue(rsi.getUtils().getElemsSize() - 1);
        if (!(lastRsiPoint > 50)) { // 80 is better
            updatePrevValues();
            return false;
        }

        double prevStochFastKPoint = data.getLastStochFastKPoint().getValue();
        double prevStochSlowDPoint = data.getLastStochSlowDPoint().getValue();
        double lastStochFastKPoint = stoch.getUtils().getFastKValue(stoch.getUtils().getFastKElemsSize() - 1);
        double lastStochSlowDPoint = stoch.getUtils().getSlowDValue(stoch.getUtils().getSlowDElemsSize() - 1);
        if (!(lastStochFastKPoint > prevStochFastKPoint && lastStochSlowDPoint > prevStochSlowDPoint)) {
            updatePrevValues();
            return false;
        }
        if (!(prevStochFastKPoint < 30 && prevStochSlowDPoint < 30)) {
            updatePrevValues();
            return false;
        }

        updatePrevValues();
        return true;
    }

    public boolean testStrategyPut() {

        double lastEmaPoint = ema.getUtils().getValue(ema.getUtils().getElemsSize() - 1);
        double prevCupPointHigh = data.getPreviousCupPoint().getHigh();
        double prevCupPointLow = data.getPreviousCupPoint().getLow();
        if (!(prevCupPointHigh > lastEmaPoint && prevCupPointLow < lastEmaPoint)) {
            updatePrevValues();
            return false;
        }
        double lastCupPointHigh = cup.getUtils().getHigh(cup.getUtils().getElemsSize() - 1);
        if (!(lastCupPointHigh < lastEmaPoint)) {
            updatePrevValues();
            return false;
        }

        double lastRsiPoint = rsi.getUtils().getValue(rsi.getUtils().getElemsSize() - 1);
        if (!(lastRsiPoint < 50)) { // better if bottom line was crossed in (20 or 30)
            updatePrevValues();
            return false;
        }

        double prevStochFastKPoint = data.getLastStochFastKPoint().getValue();
        double prevStochSlowDPoint = data.getLastStochSlowDPoint().getValue();
        double lastStochFastKPoint = stoch.getUtils().getFastKValue(stoch.getUtils().getFastKElemsSize() - 1);
        double lastStochSlowDPoint = stoch.getUtils().getSlowDValue(stoch.getUtils().getSlowDElemsSize() - 1);
        if (!(lastStochFastKPoint < prevStochFastKPoint && lastStochSlowDPoint < prevStochSlowDPoint)) {
            updatePrevValues();
            return false;
        }
        if (!(prevStochFastKPoint > 70 && prevStochSlowDPoint > 70)) {
            updatePrevValues();
            return false;
        }

        updatePrevValues();
        return true;
    }

    private void updatePrevValues() {
        data.setPreviousCupPoint(
                CupPoint.builder()
                        .high(cup.getUtils().getHigh(cup.getUtils().getElemsSize() - 1))
                        .low(cup.getUtils().getLow(cup.getUtils().getElemsSize() - 1))
                        .open(cup.getUtils().getOpen(cup.getUtils().getElemsSize() - 1))
                        .close(cup.getUtils().getClose(cup.getUtils().getElemsSize() - 1))
                        .start(cup.getUtils().getStart(cup.getUtils().getElemsSize() - 1))
                        .end(cup.getUtils().getEnd(cup.getUtils().getElemsSize() - 1))
                        .build()
        );
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
