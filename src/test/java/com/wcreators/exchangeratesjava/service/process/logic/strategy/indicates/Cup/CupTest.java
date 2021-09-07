package com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.Cup;

import com.wcreators.exchangeratesjava.model.Rate;
import com.wcreators.exchangeratesjava.util.DateUtils;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CupTest {

    private final Random random = new Random();
    private final Calendar calendar = Calendar.getInstance();
    private final int maxMinutes = 1000;

    private DateUtils dateUtils = new DateUtils();
    private Cup cup = new Cup(dateUtils);

    @Test
    void loadData() {
        calendar.set(Calendar.SECOND, 0);
        List<Elem> elems = new ArrayList<>(maxMinutes);
        while (elems.size() < maxMinutes) {
            elems.add(
                    Elem.builder()
                            .high(elems.size() + 5)
                            .low(elems.size())
                            .open(elems.size() + random.nextDouble())
                            .close(elems.size() + random.nextDouble())
                            .start(calendar.getTime())
                            .end(getNextDate(calendar))
                            .build()
            );
        }
        List<Rate> rates = split(elems);
        rates.forEach(rate -> cup.addRate(rate));

        assertEquals(elems.size(), cup.getElemsSize());
        if (elems.size() != cup.getElemsSize()) {
            return;
        }

        for (int i = 0; i < elems.size(); i++) {
            String indexedErrorMessage = String.format("Incorrect calculated value for %d", i);
            Elem elem = elems.get(i);
            assertEquals(elem.getOpen(), cup.open(i), indexedErrorMessage);
            assertEquals(elem.getHigh(), cup.high(i), indexedErrorMessage);
            assertEquals(elem.getLow(), cup.low(i), indexedErrorMessage);
            assertEquals(elem.getClose(), cup.close(i), indexedErrorMessage);
        }

    }

    private Date getNextDate(Calendar calendar) {
        calendar.add(Calendar.MINUTE, 1);
        return calendar.getTime();
    }

    private Double getNextPrice(Double current) {
        Double bias = random.nextDouble() / 1000;
        Double sign = random.nextBoolean() ? 1d : -1d;
        return current + bias * sign;
    }

    private Double getNextPrice(Double max, Double min) {
        return min + random.nextInt((int) (max - min - 1)) + random.nextDouble();
    }

    private List<Rate> split(List<Elem> elems) {
        List<Rate> rates = new LinkedList<>();
        elems.forEach(elem -> {
           rates.add(
                   Rate.builder()
                           .sell(elem.getOpen())
                           .createdDate(elem.getStart())
                           .build()
           );
            rates.add(
                    Rate.builder()
                            .sell(elem.getHigh())
                            .createdDate(elem.getStart())
                            .build()
            );
            for (int i = 0; i < 10; i++) {
                rates.add(
                        Rate.builder()
                                .sell(getNextPrice(elem.getHigh(), elem.getLow()))
                                .createdDate(elem.getStart())
                                .build()
                );
            }
            rates.add(
                    Rate.builder()
                            .sell(elem.getLow())
                            .createdDate(elem.getStart())
                            .build()
            );
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTimeInMillis(elem.getEnd().getTime());
            calendar.add(Calendar.SECOND, -1);
            rates.add(
                   Rate.builder()
                           .sell(elem.getClose())
                           .createdDate(calendar.getTime())
                           .build()
            );
        });
        rates.add(
                Rate.builder()
                        .sell(0d)
                        .createdDate(elems.get(elems.size() - 1).getEnd())
                        .build()
        );
        return rates;
    }
}