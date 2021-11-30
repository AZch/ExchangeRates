package com.wcreators.strategyindicators.services.cup;

import com.wcreators.utils.date.DateUtils;
import com.wcreators.utils.date.DateUtilsUtilDate;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CupTest {

    private final DateUtils<Date> dateUtils = new DateUtilsUtilDate();
//    private final CupIndicator cup = new Cup(dateUtils);

    @Test
    void loadData() {
//        int maxMinutes = 50;
//        List<CupPoint> cupPoints = CupPointsGenerator.generate(maxMinutes);
//        List<Rate> rates = RatesGenerator.generate(cupPoints);
//
//        rates.forEach(cup::addPoint);
//
//        assertEquals(cupPoints.size(), cup.getUtils().getElemsSize());
//
//        for (int i = 0; i < cupPoints.size(); i++) {
//            String indexedErrorMessage = String.format("Incorrect calculated value for %d", i);
//            CupPoint cupPoint = cupPoints.get(i);
//            assertEquals(cupPoint.getOpen(), cup.getUtils().getOpen(i), indexedErrorMessage);
//            assertEquals(cupPoint.getHigh(), cup.getUtils().getHigh(i), indexedErrorMessage);
//            assertEquals(cupPoint.getLow(), cup.getUtils().getLow(i), indexedErrorMessage);
//            assertEquals(cupPoint.getClose(), cup.getUtils().getClose(i), indexedErrorMessage);
//        }

    }
}