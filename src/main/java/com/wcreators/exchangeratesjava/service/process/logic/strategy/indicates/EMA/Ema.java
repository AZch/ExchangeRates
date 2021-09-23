package com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.EMA;


import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.Point;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Scope("prototype")
public class Ema implements EmaIndicator {

    private final List<Point> elems = new LinkedList<>();
    private int period = 5;
    private double firstSum = 0;

    @Override
    public void addPoint(double value, Date date) {
        if (elems.size() == 0 || elems.get(elems.size() - 1).getValue() == 0) {
            firstSum += value;
        }
        if (elems.size() < period - 1) {
            elems.add(
                    Point.builder()
                            .value(0D)
                            .time(date)
                            .build()
            );
        } else {
            Point prev = elems.get(elems.size() - 1);
            if (prev.getValue() == 0) {
                elems.add(
                        Point.builder()
                                .time(date)
                                .value(firstSum / period)
                                .build()
                );
            } else {
                elems.add(
                        Point.builder()
                                .time(date)
                                .value((value - prev.getValue()) * getMultiplier() + prev.getValue())
                                .build()
                );
            }
        }
    }

    @Override
    public double getMultiplier() {
        return 2D / (period + 1);
    }

    @Override
    public double getValue(int index) {
        return elems.get(index).getValue();
    }

    @Override
    public Date getTime(int index) {
        return elems.get(index).getTime();
    }

    public int getElemsSize() {
        return elems.size();
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }
}
