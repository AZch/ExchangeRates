package com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.Cup;

import com.wcreators.exchangeratesjava.model.Rate;
import com.wcreators.exchangeratesjava.util.DateUtils;
import common.generators.CupPointsGenerator;
import common.generators.RatesGenerator;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CupTest {

    private final DateUtils dateUtils = new DateUtils();
    private final Cup cup = new Cup(dateUtils);

    @Test
    void loadData() {
        int maxMinutes = 50;
        List<CupPoint> cupPoints = CupPointsGenerator.generate(maxMinutes);
        List<Rate> rates = RatesGenerator.generate(cupPoints);

        rates.forEach(cup::addValue);

        assertEquals(cupPoints.size(), cup.getElemsSize());

        for (int i = 0; i < cupPoints.size(); i++) {
            String indexedErrorMessage = String.format("Incorrect calculated value for %d", i);
            CupPoint cupPoint = cupPoints.get(i);
            assertEquals(cupPoint.getOpen(), cup.getOpen(i), indexedErrorMessage);
            assertEquals(cupPoint.getHigh(), cup.getHigh(i), indexedErrorMessage);
            assertEquals(cupPoint.getLow(), cup.getLow(i), indexedErrorMessage);
            assertEquals(cupPoint.getClose(), cup.getClose(i), indexedErrorMessage);
        }

    }
}