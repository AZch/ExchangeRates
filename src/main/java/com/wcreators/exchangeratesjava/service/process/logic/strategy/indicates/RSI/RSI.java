package com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.RSI;

import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.Point;
import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.SMMA.SMMA;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Scope("prototype")
public class RSI implements RsiIndicator {

    private final SMMA smmaU;
    private final SMMA smmaD;

    private final List<Point> elems = new LinkedList<>();
    private double previousValue = 0D;

    @Override
    public void addPoint(Point point) {
        Date date = point.getTime();
        double value = point.getValue();
        if (smmaU.getElemsSize() == 0) {
            smmaU.addPoint(0D, date);
            smmaD.addPoint(0D, date);
        } else {
            if (value > previousValue) {
                smmaU.addPoint(value - previousValue, date);
                smmaD.addPoint(0D, date);
            } else if (value < previousValue) {
                smmaU.addPoint(0D, date);
                smmaD.addPoint(previousValue - value, date);
            } else {
                smmaU.addPoint(0D, date);
                smmaD.addPoint(0D, date);
            }
        }
        previousValue = value;

        double lastSmmaDValue = smmaD.getValue(smmaD.getElemsSize() - 1);
        double lastSmmaUValue = smmaU.getValue(smmaU.getElemsSize() - 1);
        if (lastSmmaDValue == 0) {
            elems.add(
                    Point.builder()
                            .time(date)
                            .value(100D)
                            .build()
            );
        } else {
            elems.add(
                    Point.builder()
                            .time(date)
                            .value(100 - 100D / (1 + lastSmmaUValue / lastSmmaDValue))
                            .build()
            );
        }
    }

    @Override
    public int getElemsSize() {
        return elems.size();
    }

    @Override
    public double getValue(int index) {
        return elems.get(index).getValue();
    }

    @Override
    public Date getTime(int index) {
        return elems.get(index).getTime();
    }
}
