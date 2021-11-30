package com.wcreators.strategyemarsistoch.strategy;

import com.wcreators.objectmodels.model.Rate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties="spring.main.lazy-initialization=true")
class StrategyEmaRsiStochTest {

    @Autowired
    private StrategyEmaRsiStochastic strategy;

    @ParameterizedTest
    @MethodSource("generateRatesForTesting")
    public void idealStrategyCase(List<Rate> rates) {
//        List<RateAction> actions = rates.stream()
//                .map(strategy::addRate)
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .collect(Collectors.toList());
//        assertTrue(actions.size() > 2);
//        assertTrue(actions.stream().map(RateAction::getAction).anyMatch(Predicate.isEqual("try to buy (call)")));
//        assertTrue(actions.stream().map(RateAction::getAction).anyMatch(Predicate.isEqual("try to sell (put)")));
    }

    private static Stream<List<Rate>> generateRatesForTesting() {
        return Stream.of(
                getIdealRates(getCalendar())
        );
    }

    private static List<Rate> getIdealRates(Calendar calendar) {
        return Stream.of(
                getOneMinuteRateValues(calendar, 15, 6.5, 9, 14),
                getOneMinuteRateValues(calendar, 15, 6.5, 14, 7),
                getOneMinuteRateValues(calendar, 11.5, 7, 7, 11),
                getOneMinuteRateValues(calendar, 14, 11, 11, 13),
                getOneMinuteRateValues(calendar, 15, 12, 13, 14),
                getOneMinuteRateValues(calendar, 17, 12, 14, 14),
                getOneMinuteRateValues(calendar, 22, 13, 14, 19),
                getOneMinuteRateValues(calendar, 19, 16.5, 19, 17),
                getOneMinuteRateValues(calendar, 19, 17, 17, 18),
                getOneMinuteRateValues(calendar, 21, 18, 18, 18),
                getOneMinuteRateValues(calendar, 18, 8, 18, 11),
                getOneMinuteRateValues(calendar, 14, 9, 11, 13),
                getOneMinuteRateValues(calendar, 13, 8.5, 13, 8.5),
                getOneMinuteRateValues(calendar, 9, 8, 9, 8),
                getOneMinuteRateValues(calendar, 9, 4.5, 8, 5),
                getOneMinuteRateValues(calendar, 5, 2, 5, 3),
                getOneMinuteRateValues(calendar, 4, 3, 3, 4),
                getOneMinuteRateValues(calendar, 4, 3, 3, 4),
                getOneMinuteRateValues(calendar, 4, 1.5, 4, 2),
                getOneMinuteRateValues(calendar, 4, 1, 2, 1.5),
                getOneMinuteRateValues(calendar, 3, 1.5, 1.5, 2),
                getOneMinuteRateValues(calendar, 3.5, 1.5, 2, 3),
                getOneMinuteRateValues(calendar, 3, 1.5, 3, 1.5),
                getOneMinuteRateValues(calendar, 8, 1.5, 1.5, 8),
                getOneMinuteRateValues(calendar, 8, 7.5, 8, 8),
                getOneMinuteRateValues(calendar, 8, 7.5, 8, 7.5),
                getOneMinuteRateValues(calendar, 9, 7.5, 7.5, 9),
                getOneMinuteRateValues(calendar, 12, 7.5, 7.5, 12),
                getOneMinuteRateValues(calendar, 0, 0, 0, 0)
        ).flatMap(List::stream)
        .collect(Collectors.toList());
    }

    private static List<Rate> getOneMinuteRateValues(Calendar calendar, double high, double low, double start, double end) {
        List<Rate> rates = List.of(
                Rate.builder().sell(start).createdDate(getNextRateDateValue(calendar, 1)).build(),
                Rate.builder().sell(high).createdDate(getNextRateDateValue(calendar, 19)).build(),
                Rate.builder().sell(low).createdDate(getNextRateDateValue(calendar, 20)).build(),
                Rate.builder().sell(end).createdDate(getNextRateDateValue(calendar, 19)).build()
        );
        getNextRateDateValue(calendar, 1);
        return rates;
    }

    private static Date getNextRateDateValue(Calendar calendar, int seconds) {
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    private static Calendar getCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }
}