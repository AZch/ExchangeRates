package com.wcreators.strategyindicators.services.cup;

import com.wcreators.objectmodels.model.Rate;
import com.wcreators.strategyindicators.models.CupPoint;
import com.wcreators.strategyindicators.models.Decimal;
import com.wcreators.strategyindicators.services.storage.StorageIndicator;
import com.wcreators.utils.date.DateUtils;
import com.wcreators.utils.date.DateUtilsUtilDate;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.function.Function;

@RequiredArgsConstructor
public class Cup extends StorageIndicator<Rate, Optional<CupPoint>> {
    private CupPoint current;
    private final DateUtils<Date> dateUtils = new DateUtilsUtilDate();

    private final Function<Rate, CupPoint> elemFromRate = rate -> CupPoint.builder()
            .start(rate.getCreatedDate())
            .high(rate.getSell())
            .low(rate.getSell())
            .open(rate.getSell())
            .close(rate.getSell())
            .end(rate.getCreatedDate())
            .build();

    @Override
    protected Optional<CupPoint> calculate(Rate value) {
        if (current == null) {
            current = elemFromRate.apply(value);
            return Optional.empty();
        }

        if (dateUtils.getMinutes(current.getStart()) == dateUtils.getMinutes(value.getCreatedDate())) {
            current.addPrice(value.getSell(), value.getCreatedDate());
            return Optional.empty();
        }

        CupPoint addedPoint = current;
        current = elemFromRate.apply(value);

        return Optional.of(addedPoint);
    }

    @Override
    public Optional<CupPoint> addPoint(Rate value) {
        Optional<CupPoint> point = calculate(value);
        if (point.isPresent()) {
            points.add(point);
        }
        return point;
    }

    public Optional<CupPoint> addPoint(CupPoint value) {
        points.add(Optional.of(value));
        return Optional.of(value);
    }

    public Optional<Decimal> getMax(int start, int end) {
        return points.subList(start, end).stream().map(Optional::get).map(CupPoint::getDecimalClose).max(Decimal::compareTo);
    }

    public Optional<Decimal> getMin(int start, int end) {
        return points.subList(start, end).stream().map(Optional::get).map(CupPoint::getDecimalClose).min(Decimal::compareTo);
    }
}
