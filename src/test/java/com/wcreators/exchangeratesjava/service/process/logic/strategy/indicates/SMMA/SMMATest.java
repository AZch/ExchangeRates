package com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.SMMA;

import com.wcreators.exchangeratesjava.model.Rate;
import com.wcreators.exchangeratesjava.util.DateUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SMMATest {

    private final int maxMinutes = 1000;
    private final DateUtils dateUtils = new DateUtils();
    private final SMMA smma = new SMMA(dateUtils);

    @Test
    public void loadData() {
        List<Elem> elems = new ArrayList<>(maxMinutes);
        while (elems.size() < maxMinutes) {
            elems.add(
                    Elem.builder()
                            .time(new Date())
                            .sum(1)
                            .count(1)
                            .build()
            );
        }

        List<Rate> rates = split(elems);
        rates.forEach(smma::addRate);


        assertEquals(elems.size(), smma.getElemsSize());

        for (int i = 0; i < elems.size(); i++) {
            String indexedErrorMessage = String.format("Incorrect calculated value for %d", i);
            Elem elem = elems.get(i);
            assertEquals(elem.getSum(), smma.sum(i), indexedErrorMessage);
            assertEquals(elem.getCount(), smma.count(i), indexedErrorMessage);
        }
    }

    private List<Rate> split(List<Elem> elems) {
        return new ArrayList<>();
    }
}