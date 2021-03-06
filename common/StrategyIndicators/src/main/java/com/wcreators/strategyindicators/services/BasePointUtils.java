package com.wcreators.strategyindicators.services;

import com.wcreators.strategyindicators.models.Point;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class BasePointUtils {

    private final List<Point> points;

    public double getValue(int index) {
        return points.get(index).getValue();
    }

    public Date getTime(int index) {
        return points.get(index).getTime();
    }

    public int getElemsSize() {
        return points.size();
    }
}
