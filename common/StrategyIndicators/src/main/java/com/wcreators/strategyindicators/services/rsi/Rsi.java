package com.wcreators.strategyindicators.services.rsi;

import com.wcreators.strategyindicators.models.Point;
import com.wcreators.strategyindicators.services.BasePointUtils;
import com.wcreators.strategyindicators.services.ema.EmaIndicator;
import com.wcreators.strategyindicators.services.smma.Smma;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Qualifier("RSI")
@Scope("prototype")
public class Rsi implements RsiIndicator {

    private final EmaIndicator smmaU;
    private final EmaIndicator smmaD;
    private final List<Point> elems = new ArrayList<>();
    private final BasePointUtils utils = new BasePointUtils(elems);
    private double previousValue = 0D;

    public Rsi(@Qualifier("SMMA")  EmaIndicator smmaU, @Qualifier("SMMA")  EmaIndicator smmaD) {
        this.smmaU = smmaU;
        this.smmaD = smmaD;
    }

    @Override
    public Void addPoint(Point point) {
        Date date = point.getTime();
        double value = point.getValue();
        Point zero = Point.builder().value(0D).time(date).build();
        if (smmaU.getUtils().getElemsSize() == 0) {
            smmaU.addPoint(zero);
            smmaD.addPoint(zero);
        } else {
            if (value > previousValue) {
                smmaU.addPoint(Point.builder().value(value - previousValue).time(date).build());
                smmaD.addPoint(zero);
            } else if (value < previousValue) {
                smmaU.addPoint(zero);
                smmaD.addPoint(Point.builder().value(previousValue - value).time(date).build());
            } else {
                smmaU.addPoint(zero);
                smmaD.addPoint(zero);
            }
        }
        previousValue = value;

        double lastSmmaDValue = smmaD.getUtils().getValue(smmaD.getUtils().getElemsSize() - 1);
        double lastSmmaUValue = smmaU.getUtils().getValue(smmaU.getUtils().getElemsSize() - 1);
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
        if (elems.size() > 60) {
            elems.subList(0, 30).clear();
        }
        return null;
    }

    @Override
    public BasePointUtils getUtils() {
        return utils;
    }

    public void setPeriod(int period) {
        this.smmaU.setPeriod(period);
        this.smmaD.setPeriod(period);
    }
}
