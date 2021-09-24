package com.wcreators.exchangeratesjava.service.process.logic.strategy;

import com.wcreators.exchangeratesjava.model.Rate;
import com.wcreators.exchangeratesjava.model.RateAction;
import com.wcreators.exchangeratesjava.service.process.logic.ProcessRatesService;
import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.Cup.Cup;
import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.Cup.CupPoint;
import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.EMA.Ema;
import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.Point;
import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.RSI.RSI;
import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.STOCH.STOCH;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.swing.text.html.Option;
import java.util.Optional;

// https://www.bestbinar.ru/strategiya-skalpinga-na-1-minute/
@Service
@Qualifier("strategyOne")
@RequiredArgsConstructor
@Slf4j
public class StrategyOne implements ProcessRatesService {

    private final Cup cup;
    @Qualifier("EMA")
    private final Ema ema;
    private final RSI rsi;
    private final STOCH stoch;

    private TempStrategyData data;

    @PostConstruct
    public void init() {
        ema.setPeriod(7);
        rsi.setPeriod(3);
        stoch.setPeriod(6);
        stoch.setPeriodFastK(3);
        stoch.setPeriodSlowD(3);
    }

    @Override
    public Optional<RateAction> addRate(Rate rate) {

        Optional<CupPoint> optionalCupPoint = cup.addValue(rate);
        if (optionalCupPoint.isPresent()) {
            CupPoint cupPoint = optionalCupPoint.get();

            ema.addPoint(cupPoint.getClose(), cupPoint.getEnd());
            rsi.addPoint(Point.builder().value(cupPoint.getClose()).time(cupPoint.getEnd()).build());
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

        double lastEmaPoint = ema.getValue(ema.getElemsSize() - 1);
        double prevCupPointHigh = data.getPreviousCupPoint().getHigh();
        double prevCupPointLow = data.getPreviousCupPoint().getLow();
        if (!(prevCupPointHigh > lastEmaPoint && prevCupPointLow < lastEmaPoint)) {
            updatePrevValues();
            return false;
        }
        double lastCupPointLow = cup.getLow(cup.getElemsSize() - 1);
        if (!(lastCupPointLow > lastEmaPoint)) {
            updatePrevValues();
            return false;
        }

        double lastRsiPoint = rsi.getValue(rsi.getElemsSize() - 1);
        if (!(lastRsiPoint > 50)) { // 80 is better
            updatePrevValues();
            return false;
        }

        double prevStochFastKPoint = data.getLastStochFastKPoint().getValue();
        double prevStochSlowDPoint = data.getLastStochSlowDPoint().getValue();
        double lastStochFastKPoint = stoch.getFastKValue(stoch.getFastKElemsSize() - 1);
        double lastStochSlowDPoint = stoch.getSlowDValue(stoch.getSlowDElemsSize() - 1);
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

        double lastEmaPoint = ema.getValue(ema.getElemsSize() - 1);
        double prevCupPointHigh = data.getPreviousCupPoint().getHigh();
        double prevCupPointLow = data.getPreviousCupPoint().getLow();
        if (!(prevCupPointHigh > lastEmaPoint && prevCupPointLow < lastEmaPoint)) {
            updatePrevValues();
            return false;
        }
        double lastCupPointHigh = cup.getHigh(cup.getElemsSize() - 1);
        if (!(lastCupPointHigh < lastEmaPoint)) {
            updatePrevValues();
            return false;
        }

        double lastRsiPoint = rsi.getValue(rsi.getElemsSize() - 1);
        if (!(lastRsiPoint < 50)) { // better if bottom line was crossed in (20 or 30)
            updatePrevValues();
            return false;
        }

        double prevStochFastKPoint = data.getLastStochFastKPoint().getValue();
        double prevStochSlowDPoint = data.getLastStochSlowDPoint().getValue();
        double lastStochFastKPoint = stoch.getFastKValue(stoch.getFastKElemsSize() - 1);
        double lastStochSlowDPoint = stoch.getSlowDValue(stoch.getSlowDElemsSize() - 1);
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
                        .high(cup.getHigh(cup.getElemsSize() - 1))
                        .low(cup.getLow(cup.getElemsSize() - 1))
                        .open(cup.getOpen(cup.getElemsSize() - 1))
                        .close(cup.getClose(cup.getElemsSize() - 1))
                        .start(cup.getStart(cup.getElemsSize() - 1))
                        .end(cup.getEnd(cup.getElemsSize() - 1))
                        .build()
        );
        data.setLastStochFastKPoint(
                Point.builder()
                        .value(stoch.getFastKValue(stoch.getFastKElemsSize() - 1))
                        .time(stoch.getFastKTime(stoch.getFastKElemsSize() - 1))
                        .build()
        );
        data.setLastStochSlowDPoint(
                Point.builder()
                        .value(stoch.getSlowDValue(stoch.getSlowDElemsSize() - 1))
                        .time(stoch.getSlowDTime(stoch.getSlowDElemsSize() - 1))
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
