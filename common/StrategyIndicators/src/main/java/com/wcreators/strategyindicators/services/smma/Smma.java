package com.wcreators.strategyindicators.services.smma;

import com.wcreators.strategyindicators.services.ema.Ema;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Qualifier("SMMA")
@Scope("prototype")
public class Smma extends Ema {
    @Override
    public double getMultiplier() {
        return 2D / getPeriod();
    }
}
