package com.wcreators.strategyindicators.services.smma;

import com.wcreators.strategyindicators.models.Point;
import generators.PointsGenerator;
import generators.SmmaGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SMMATest {

    private final Smma smma = new Smma();

    @Test
    public void loadData() {
        int maxMinutes = 50;
        List<Point> points = PointsGenerator.generate(maxMinutes);
        List<Point> elems = SmmaGenerator.generate(points, smma.getPeriod());

        points.forEach(smma::addPoint);

        assertEquals(elems.size(), smma.getUtils().getElemsSize());

        for (int i = 0; i < elems.size(); i++) {
            String indexedErrorMessage = String.format("Incorrect calculated value for %d", i);
            Point elem = elems.get(i);
            assertEquals(elem.getValue(), smma.getUtils().getValue(i), indexedErrorMessage);
        }
    }
}