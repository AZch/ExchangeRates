package com.wcreators.strategyindicators.services.ema;

import com.wcreators.strategyindicators.models.Point;
import generators.EmaGenerator;
import generators.PointsGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EMATest {

    private final EmaIndicator ema = new Ema();

    @Test
    public void loadData() {
        int maxMinutes = 50;
        List<Point> points = PointsGenerator.generate(maxMinutes);
        List<Point> elems = EmaGenerator.generate(points, ema.getPeriod());

        points.forEach(ema::addPoint);

        assertEquals(elems.size(), ema.getUtils().getElemsSize());

        for (int i = 0; i < elems.size(); i++) {
            String indexedErrorMessage = String.format("Incorrect calculated value for %d", i);
            Point elem = elems.get(i);
            assertEquals(elem.getValue(), ema.getUtils().getValue(i), indexedErrorMessage);
        }
    }



}