package com.wcreators.exchangeratesjava.service.process.logic.strategy;

import com.wcreators.exchangeratesjava.model.Rate;
import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.Cup.Cup;
import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.EMA.EMA;
import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.RSI.RSI;
import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.STOCH.STOCH;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StrategyOne {

    private final Cup cup;
    private final EMA ema;
    private final RSI rsi;
    private final STOCH stoch;

    public Optional<Rate> addRate(Rate rate) {
        if (isNotRateForStrategy(rate)) {
            return Optional.empty();
        }

        cup.addRate(rate);
        ema.addRate(rate);
        rsi.addRate(rate);
        stoch.addRate(rate);

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
