package com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.RSI;

import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.Point;
import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.SMMA.SMMA;
import common.generators.PointsGenerator;
import common.generators.RsiGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RSITest {

    private final SMMA smmaU = new SMMA();
    private final SMMA smmaD = new SMMA();
    private final RSI rsi = new RSI(smmaU, smmaD);

    @Test
    public void loadData() {
        int maxMinutes = 50;
        List<Point> points = PointsGenerator.generate(maxMinutes);
        List<Point> elems = RsiGenerator.generate(points, smmaU.getPeriod());

        points.forEach(rsi::addPoint);

        assertEquals(elems.size(), rsi.getElemsSize());

        for (int i = 0; i < elems.size(); i++) {
            String indexedErrorMessage = String.format("Incorrect calculated value for %d", i);
            Point elem = elems.get(i);
            assertEquals(elem.getValue(), rsi.getValue(i), indexedErrorMessage);
        }
    }
}