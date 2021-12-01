package com.wcreators.strategyemarsistoch.strategy;

import com.wcreators.objectmodels.constant.Strategy;
import com.wcreators.strategyindicators.models.CupPoint;
import com.wcreators.strategyindicators.models.Decimal;
import com.wcreators.strategyindicators.services.cup.Cup;
import com.wcreators.strategyindicators.services.indicators.ma.EMA;
import com.wcreators.strategyindicators.services.indicators.rsi.RSI;
import com.wcreators.strategyindicators.services.indicators.stochastic.StochasticD;
import com.wcreators.strategyindicators.services.indicators.stochastic.StochasticK;
import com.wcreators.utils.date.DateUtilsUtilDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

// https://www.bestbinar.ru/strategiya-skalpinga-na-1-minute/
@Service
@Slf4j
public class StrategyEmaRsiStochastic implements ProcessRatesService {

    private final Cup cup;
    private final EMA ema;
    private final RSI rsi;
    private final StochasticK stochasticK;
    private final StochasticD stochasticD;

    public StrategyEmaRsiStochastic() {
        this.cup = new Cup();
        this.ema = new EMA(7);
        this.rsi = new RSI(3);
        this.stochasticK = new StochasticK(6, 3);
        this.stochasticD = new StochasticD(stochasticK, 3);
    }

    @Override
    public Optional<String> addRate(CupPoint cupPoint) {

        cup.addPoint(cupPoint);
        Decimal point = cupPoint.getDecimalClose();
        ema.addPoint(point);
        rsi.addPoint(point);
        stochasticK.addPoint(point);
        stochasticD.addPoint(point);

        if (cup.size() > 1 && testStrategyCall(cupPoint)) {
            log.info("Need try to buy, check this and after 5 min value");
            return Optional.of("try to buy (call)");
        }

        if (cup.size() > 1 && testStrategyPut(cupPoint)) {
            log.info("Need try to sell, check this and after 5 min value");
            return Optional.of("try to sell (put)");
        }

        return Optional.empty();
    }

    public boolean testStrategyCall(CupPoint lastCupPoint) {
        Decimal lastEmaPoint = ema.lastAdded();
        Decimal prevCupHigh = cup.get(cup.size() - 2).get().getDecimalHigh();
        Decimal prevCupLow = cup.get(cup.size() - 2).get().getDecimalLow();
        if (!(prevCupHigh.compareTo(lastEmaPoint) > 0 && prevCupLow.compareTo(lastEmaPoint) < 0)) {
            log.warn("Call 1 high: {}, low: {}, ema: {}", prevCupHigh, prevCupLow, lastEmaPoint);
            return false;
        }
        Decimal lastCupLow = lastCupPoint.getDecimalLow();
        if (!(lastCupLow.compareTo(lastEmaPoint) > 0)) {
            log.warn("Call 2 low: {}, ema: {}", lastCupLow, lastEmaPoint);
            return false;
        }

        Decimal lastRsiPoint = rsi.lastAdded();
        if (!(lastRsiPoint.compareTo(Decimal.valueOf(50)) > 0)) { // 80 is better
            log.warn("Call 3 rsi: {}", lastRsiPoint);
            return false;
        }

        Decimal prevStochasticK = stochasticK.get(stochasticK.size() - 2);
        Decimal prevStochasticD = stochasticD.get(stochasticD.size() - 2);
        Decimal lastStochasticK = stochasticK.lastAdded();
        Decimal lastStochasticD = stochasticD.lastAdded();
        if (!(lastStochasticK.compareTo(prevStochasticK) > 0 && lastStochasticD.compareTo(prevStochasticD) > 0)) {
            log.warn("Call 4.1 lastK: {}, prevK: {}", lastStochasticK, prevStochasticK);
            log.warn("Call 4.2 lastD: {}, prevD: {}", lastStochasticD, prevStochasticD);
            return false;
        }
        if (!(prevStochasticK.compareTo(Decimal.valueOf(30)) < 0 && prevStochasticD.compareTo(Decimal.valueOf(30)) < 0)) {
            log.warn("Call 5 prevK: {}, prevD: {}", prevStochasticK, prevStochasticD);
            return false;
        }

        return true;
    }

    public boolean testStrategyPut(CupPoint lastCupPoint) {
        Decimal lastEmaPoint = ema.lastAdded();
        Decimal prevCupHigh = cup.get(cup.size() - 2).get().getDecimalHigh();
        Decimal prevCupLow = cup.get(cup.size() - 2).get().getDecimalLow();
        if (!(prevCupHigh.compareTo(lastEmaPoint) > 0 && prevCupLow.compareTo(lastEmaPoint) < 0)) {
            log.warn("Put 1 high: {}, low: {}, ema: {}", prevCupHigh, prevCupLow, lastEmaPoint);
            return false;
        }
        Decimal lastCupHigh = lastCupPoint.getDecimalHigh();
        if (!(lastCupHigh.compareTo(lastEmaPoint) < 0)) {
            log.warn("Put 2 low: {}, ema: {}", lastCupHigh, lastEmaPoint);
            return false;
        }

        Decimal lastRsiPoint = rsi.lastAdded();
        if (!(lastRsiPoint.compareTo(Decimal.valueOf(50)) < 0)) { // better if bottom line was crossed in (20 or 30)
            log.warn("Put 3 rsi: {}", lastRsiPoint);
            return false;
        }

        Decimal prevStochasticK = stochasticK.get(stochasticK.size() - 2);
        Decimal prevStochasticD = stochasticD.get(stochasticD.size() - 2);
        Decimal lastStochasticK = stochasticK.lastAdded();
        Decimal lastStochasticD = stochasticD.lastAdded();
        if (!(lastStochasticK.compareTo(prevStochasticK) < 0 && lastStochasticD.compareTo(prevStochasticD) < 0)) {
            log.warn("Call 4.1 lastK: {}, prevK: {}", lastStochasticK, prevStochasticK);
            log.warn("Call 4.2 lastD: {}, prevD: {}", lastStochasticD, prevStochasticD);
            return false;
        }
        if (!(prevStochasticK.compareTo(Decimal.valueOf(70)) > 0 && prevStochasticD.compareTo(Decimal.valueOf(70)) > 0)) {
            log.warn("Call 5 prevK: {}, prevD: {}", prevStochasticK, prevStochasticD);
            return false;
        }

        return true;
    }

    @Override
    public Strategy getStrategy() {
        return Strategy.EMA_RSI_STOCH;
    }
}
