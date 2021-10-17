package com.wcreators.strategyindicators.services.rsi;

import com.wcreators.strategyindicators.models.Point;
import com.wcreators.strategyindicators.services.ema.EmaIndicator;
import com.wcreators.strategyindicators.services.smma.Smma;
import generators.PointsGenerator;
import generators.RsiGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RSITest {

    private final EmaIndicator smmaU = new Smma();
    private final EmaIndicator smmaD = new Smma();
    private final RsiIndicator rsi = new Rsi(smmaU, smmaD);

    @Test
    public void loadData() {
        int maxMinutes = 50;
        List<Point> points = PointsGenerator.generate(maxMinutes);
        List<Point> elems = RsiGenerator.generate(points, smmaU.getPeriod());

        points.forEach(rsi::addPoint);

        assertEquals(elems.size(), rsi.getUtils().getElemsSize());

        for (int i = 0; i < elems.size(); i++) {
            String indexedErrorMessage = String.format("Incorrect calculated value for %d", i);
            Point elem = elems.get(i);
            assertEquals(elem.getValue(), rsi.getUtils().getValue(i), indexedErrorMessage);
        }
    }
}