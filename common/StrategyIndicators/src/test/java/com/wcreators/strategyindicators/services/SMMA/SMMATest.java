package com.wcreators.strategyindicators.services.SMMA;

import com.wcreators.strategyindicators.models.Point;
import generators.PointsGenerator;
import generators.SmmaGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SMMATest {

    private final SMMA smma = new SMMA();

    @Test
    public void loadData() {
        int maxMinutes = 50;
        List<Point> points = PointsGenerator.generate(maxMinutes);
        List<Point> elems = SmmaGenerator.generate(points, smma.getPeriod());

        for (Point point : points) {
            smma.addPoint(point.getValue(), point.getTime());
        }

        assertEquals(elems.size(), smma.getElemsSize());

        for (int i = 0; i < elems.size(); i++) {
            String indexedErrorMessage = String.format("Incorrect calculated value for %d", i);
            Point elem = elems.get(i);
            assertEquals(elem.getValue(), smma.getValue(i), indexedErrorMessage);
        }
    }
}