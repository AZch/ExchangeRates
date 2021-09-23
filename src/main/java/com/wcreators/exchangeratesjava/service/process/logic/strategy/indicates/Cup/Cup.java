package com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.Cup;


import com.wcreators.exchangeratesjava.model.Rate;
import com.wcreators.exchangeratesjava.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class Cup implements CupIndicator {

    private final List<CupPoint> elems = new LinkedList<>();
    private CupPoint current;
    private final DateUtils dateUtils;

    private final Function<Rate, CupPoint> elemFromRate = rate -> CupPoint.builder()
            .start(rate.getCreatedDate())
            .high(rate.getSell())
            .low(rate.getSell())
            .open(rate.getSell())
            .close(rate.getSell())
            .end(rate.getCreatedDate())
            .build();

    @Override
    public Optional<CupPoint> addValue(Rate rate) {
        if (current == null) {
            current = elemFromRate.apply(rate);
            return Optional.empty();
        }

        if (dateUtils.getMinutes(current.getStart()) == dateUtils.getMinutes(rate.getCreatedDate())) {
            current.addPrice(rate.getSell(), rate.getCreatedDate());
            return Optional.empty();
        }

        Optional<CupPoint> addedPoint = Optional.of(current);
        elems.add(current);
        current = elemFromRate.apply(rate);

        return addedPoint;
    }

    @Override
    public int getElemsSize() {
        return elems.size();
    }

    @Override
    public OptionalDouble minLastLow(int lowerBound, int upperBound) {
        return elems.stream().limit(upperBound).skip(lowerBound).mapToDouble(CupPoint::getLow).min();
    }

    @Override
    public OptionalDouble maxLastHigh(int lowerBound, int upperBound) {
        return elems.stream().limit(upperBound).skip(lowerBound).mapToDouble(CupPoint::getHigh).max();
    }

    @Override
    public double getHigh(int index) {
        return elems.get(index).getHigh();
    }

    @Override
    public double getLow(int index) {
        return elems.get(index).getLow();
    }

    @Override
    public double getClose(int index) {
        return elems.get(index).getClose();
    }

    @Override
    public double getOpen(int index) {
        return elems.get(index).getOpen();
    }

    @Override
    public Date getStart(int index) {
        return elems.get(index).getStart();
    }

    @Override
    public Date getEnd(int index) {
        return elems.get(index).getEnd();
    }
}
