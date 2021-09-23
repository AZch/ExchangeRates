package com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.SMMA;

import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.EMA.Ema;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Scope("prototype")
public class SMMA extends Ema {
    @Override
    public double getMultiplier() {
        return 2D / getPeriod();
    }
}
