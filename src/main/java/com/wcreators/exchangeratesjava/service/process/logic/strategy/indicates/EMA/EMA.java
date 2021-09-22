package com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.EMA;


import com.wcreators.exchangeratesjava.model.Rate;
import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.Cup.Cup;
import com.wcreators.exchangeratesjava.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Scope("prototype")
public class EMA {

    private final DateUtils dateUtils;
    private final List<Elem> elems = new LinkedList<>();
    private final Cup cup;
    private final int period = 5;
    private double interm = 0;
    private final double multiplier = 2D / (period + 1);

    public int getElemsSize() {
        return elems.size();
    }

    private double getCupValue() {
        int last = cup.getElemsSize() - 1;
        return cup.close(last);
    }

    private Date getCupDate() {
        int last = cup.getElemsSize() - 1;
        return cup.end(last);
//            case "high":
//                return cup.end(last) - cup.start(last);
//            case "low":
//                return cup.end(last);
    }

    public void addRate() {
        if (cup.getElemsSize() == elems.size()) {
            return;
        }
        Date date = getCupDate();
        double value = getCupValue();
        if (elems.size() == 0 || elems.get(elems.size() - 1).getSum() == 0) {
            interm += value;
        }
        if (cup.getElemsSize() < period) {
            elems.add(
                    Elem.builder()
                            .sum(0D)
                            .time(date)
                            .build()
            );
        } else {
            Elem prev = elems.get(elems.size() - 1);
            if (prev.getSum() == 0) {
                elems.add(
                        Elem.builder()
                                .time(date)
                                .sum(interm / period)
                                .build()
                );
            } else {
                elems.add(
                        Elem.builder()
                                .time(date)
                                .sum((value - prev.getSum()) * multiplier + prev.getSum())
                                .build()
                );
            }
        }
    }

    private double getAlpha(double N) {
        return 2 / (1 + N);
    }

    public double sum(int index) {
        return elems.get(index).getSum();
    }
}
