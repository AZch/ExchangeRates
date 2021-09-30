package com.wcreators.strategyindicators.services.Cup;

import com.wcreators.objectmodels.model.Rate;
import com.wcreators.utils.date.DateUtils;
import com.wcreators.utils.date.DateUtilsUtilDate;
import generators.CupPointsGenerator;
import generators.RatesGenerator;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CupTest {

    private final DateUtils<Date> dateUtils = new DateUtilsUtilDate();
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