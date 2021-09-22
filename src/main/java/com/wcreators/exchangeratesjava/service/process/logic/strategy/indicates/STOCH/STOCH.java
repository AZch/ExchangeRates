package com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.STOCH;


import com.wcreators.exchangeratesjava.model.Rate;
import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.Cup.Cup;
import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.EMA.EMA;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.max;

@Service
@RequiredArgsConstructor
@Scope("prototype")
public class STOCH {

    private final Cup cup;
    private final List<Elem> elems = new LinkedList<>();
    private final EMA emaFastK;
    private final EMA emaSlowD;
    int periodsLow = 3;
    int periodsHigh = 3;

    public int getElemsSize() {
        return elems.size();
    }

    public void addRate(Rate rate) {
        cup.addRate(rate);

        int maxPeriod = max(periodsHigh, periodsLow);
        if (cup.getElemsSize() - maxPeriod > elems.size()) {
            double minLastLow = cup.minLastLow(periodsLow);
            double maxLastHigh = cup.maxLastHigh(periodsHigh);
            double closeLast = cup.close(cup.getElemsSize() - 1);
            double fastK = 100 * ((closeLast - minLastLow) / (maxLastHigh - minLastLow));
            elems.add(Elem.builder().point(fastK).time(rate.getCreatedDate()).build());
            Rate kRate = Rate.builder().sell(fastK).createdDate(rate.getCreatedDate()).build();
            emaSlowD.addRate();
            emaFastK.addRate();
        }
    }

    public double point(int index) {
        return elems.get(index).getPoint();
    }
}
