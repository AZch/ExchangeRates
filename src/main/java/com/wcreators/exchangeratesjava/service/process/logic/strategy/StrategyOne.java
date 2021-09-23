package com.wcreators.exchangeratesjava.service.process.logic.strategy;

import com.wcreators.exchangeratesjava.model.Rate;
import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.Cup.Cup;
import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.Cup.CupPoint;
import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.EMA.Ema;
import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.Point;
import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.RSI.RSI;
import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.STOCH.STOCH;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StrategyOne {

    private final Cup cup;
    private final Ema ema;
    private final RSI rsi;
    private final STOCH stoch;

    public Optional<Rate> addRate(Rate rate) {
        if (isNotRateForStrategy(rate)) {
            return Optional.empty();
        }

        Optional<CupPoint> optionalCupPoint = cup.addValue(rate);
        if (optionalCupPoint.isPresent()) {
            CupPoint cupPoint = optionalCupPoint.get();
            ema.addPoint(cupPoint.getClose(), cupPoint.getEnd());
            rsi.addPoint(Point.builder().value(cupPoint.getClose()).time(cupPoint.getEnd()).build());
            stoch.update();
        }

        return Optional.of(rate);
    }

    private boolean isRateForStrategy(Rate rate) {
        return (rate.getMajor().equals("USD") && rate.getMinor().equals("EUR"))
                || (rate.getMajor().equals("EUR") && rate.getMinor().equals("USD"));
    }

    private boolean isNotRateForStrategy(Rate rate) {
        return !isRateForStrategy(rate);
    }

}
