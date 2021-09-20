package com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.EMA;

import com.wcreators.exchangeratesjava.model.Rate;
import com.wcreators.exchangeratesjava.util.DateUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EMATest {

    private final int maxMinutes = 1000;
    private final DateUtils dateUtils = new DateUtils();
    private final EMA ema = new EMA(dateUtils);

    @Test
    public void loadData() {
        List<Elem> elems = new ArrayList<>(maxMinutes);
        while (elems.size() < maxMinutes) {
            elems.add(
                    Elem.builder()
                            .count(1)
                            .time(new Date())
                            .sum(1)
                            .build()
            );
        }

        List<Rate> rates = split(elems);
        rates.forEach(ema::addRate);


        assertEquals(elems.size(), ema.getElemsSize());

        for (int i = 0; i < elems.size(); i++) {
            String indexedErrorMessage = String.format("Incorrect calculated value for %d", i);
            Elem elem = elems.get(i);
            assertEquals(elem.getCount(), ema.count(i), indexedErrorMessage);
            assertEquals(elem.getSum(), ema.sum(i), indexedErrorMessage);
        }
    }

    private List<Rate> split(List<Elem> elems) {
        return new ArrayList<>();
    }

}