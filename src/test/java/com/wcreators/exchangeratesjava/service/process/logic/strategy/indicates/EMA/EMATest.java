package com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.EMA;

import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.Point;
import common.generators.EmaGenerator;
import common.generators.PointsGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EMATest {

    private final Ema ema = new Ema();

    @Test
    public void loadData() {
        int maxMinutes = 50;
        List<Point> points = PointsGenerator.generate(maxMinutes);
        List<Point> elems = EmaGenerator.generate(points, ema.getPeriod());

        for (Point point : points) {
            ema.addPoint(point.getValue(), point.getTime());
        }

        assertEquals(elems.size(), ema.getElemsSize());

        for (int i = 0; i < elems.size(); i++) {
            String indexedErrorMessage = String.format("Incorrect calculated value for %d", i);
            Point elem = elems.get(i);
            assertEquals(elem.getValue(), ema.getValue(i), indexedErrorMessage);
        }
    }



}