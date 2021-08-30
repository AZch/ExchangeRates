package com.wcreators.exchangeratesjava.service.process.logic.curve;

import com.wcreators.exchangeratesjava.model.Rate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface CurveBuilder<T> {

    Map<String, List<T>> build(List<Rate> rates);
    T createFromRate(Rate rate);
    T updateFromRate(T lastPoint, Rate rate);
    boolean isUpdateCurvePoint(T lastPoint, Rate rate);

    default Map<String, List<T>> build(Map<String, List<T>> curves, List<Rate> rates) {
        rates.forEach(rate -> {
            List<T> curve = curves.getOrDefault(rate.getName(), Collections.emptyList());

            if (curve.isEmpty()) {
                curve.add(createFromRate(rate));
                return;
            }

            T lastPoint = curve.get(curve.size() - 1);
            if (isUpdateCurvePoint(lastPoint, rate)) {
                curve.set(curve.size() - 1, updateFromRate(lastPoint, rate));
                return;
            }

            curve.add(createFromRate(rate));
        });
        return curves;
    }
}
