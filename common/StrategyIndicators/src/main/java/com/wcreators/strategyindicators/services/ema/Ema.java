package com.wcreators.strategyindicators.services.ema;


import com.wcreators.strategyindicators.models.Point;
import com.wcreators.strategyindicators.services.BasePointUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Qualifier("EMA")
@Scope("prototype")
public class Ema implements EmaIndicator {

    private final List<Point> elems = new ArrayList<>();
    private final BasePointUtils utils = new BasePointUtils(elems);
    private int period = 5;
    private double firstSum = 0;

    @Override
    public Void addPoint(Point point) {
        if (elems.size() == 0 || elems.get(elems.size() - 1).getValue() == 0) {
            firstSum += point.getValue();
        }
        if (elems.size() < period - 1) {
            elems.add(
                    Point.builder()
                            .value(0D)
                            .time(point.getTime())
                            .build()
            );
        } else {
            Point prev = elems.get(elems.size() - 1);
            if (prev.getValue() == 0) {
                elems.add(
                        Point.builder()
                                .time(point.getTime())
                                .value(firstSum / period)
                                .build()
                );
            } else {
                elems.add(
                        Point.builder()
                                .time(point.getTime())
                                .value((point.getValue() - prev.getValue()) * getMultiplier() + prev.getValue())
                                .build()
                );
            }
        }
        if (elems.size() > 60) {
            elems.subList(0, 30).clear();
        }
        return null;
    }

    @Override
    public double getMultiplier() {
        return 2D / (period + 1);
    }

    @Override
    public void setPeriod(int period) {
        this.period = period;
    }

    @Override
    public int getPeriod() {
        return period;
    }

    @Override
    public BasePointUtils getUtils() {
        return utils;
    }
}
