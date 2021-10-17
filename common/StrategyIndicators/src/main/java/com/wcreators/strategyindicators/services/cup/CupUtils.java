package com.wcreators.strategyindicators.services.cup;

import com.wcreators.strategyindicators.models.CupPoint;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.OptionalDouble;

@RequiredArgsConstructor
public class CupUtils {

    private final List<CupPoint> cupPoints;

    public OptionalDouble minLastLow(int lowerBound, int upperBound) {
        return cupPoints.stream().limit(upperBound).skip(lowerBound).mapToDouble(CupPoint::getLow).min();
    }

    public OptionalDouble maxLastHigh(int lowerBound, int upperBound) {
        return cupPoints.stream().limit(upperBound).skip(lowerBound).mapToDouble(CupPoint::getHigh).max();
    }

    public double getHigh(int index) {
        return cupPoints.get(index).getHigh();
    }

    public double getLow(int index) {
        return cupPoints.get(index).getLow();
    }

    public double getClose(int index) {
        return cupPoints.get(index).getClose();
    }

    public double getOpen(int index) {
        return cupPoints.get(index).getOpen();
    }

    public Date getStart(int index) {
        return cupPoints.get(index).getStart();
    }

    public Date getEnd(int index) {
        return cupPoints.get(index).getEnd();
    }

    public int getElemsSize() {
        return cupPoints.size();
    }
}
