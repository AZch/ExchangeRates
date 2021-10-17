package com.wcreators.strategyindicators.services.cup;

import com.wcreators.objectmodels.model.Rate;
import com.wcreators.strategyindicators.models.CupPoint;
import com.wcreators.strategyindicators.services.Indicator;
import com.wcreators.utils.date.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class Cup implements CupIndicator {

    private final List<CupPoint> elems = new ArrayList<>();
    private CupPoint current;
    private final DateUtils<Date> dateUtils;
    private final CupUtils utils = new CupUtils(elems);

    private final Function<Rate, CupPoint> elemFromRate = rate -> CupPoint.builder()
            .start(rate.getCreatedDate())
            .high(rate.getSell())
            .low(rate.getSell())
            .open(rate.getSell())
            .close(rate.getSell())
            .end(rate.getCreatedDate())
            .build();

    @Override
    public Optional<CupPoint> addPoint(Rate rate) {
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
        if (elems.size() > 60) {
            elems.subList(0, 30).clear();
        }

        return addedPoint;
    }

    @Override
    public CupUtils getUtils() {
        return utils;
    }
}
