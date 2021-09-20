package com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.RSI;

import com.wcreators.exchangeratesjava.model.Rate;
import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.SMMA.SMMA;
import com.wcreators.exchangeratesjava.util.DateUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RSITest {

    private final int maxMinutes = 1000;
    private final DateUtils dateUtils = new DateUtils();
    private final SMMA smmaU = new SMMA(dateUtils);
    private final SMMA smmaD = new SMMA(dateUtils);
    private final RSI rsi = new RSI(dateUtils, smmaU, smmaD);

    @Test
    public void loadData() {
        List<Elem> elems = new ArrayList<>(maxMinutes);
        while (elems.size() < maxMinutes) {
            elems.add(
                    Elem.builder()
                            .point(1)
                            .build()
            );
        }

        List<Rate> rates = split(elems);
        rates.forEach(rsi::addRate);


        assertEquals(elems.size(), rsi.getElemsSize());

        for (int i = 0; i < elems.size(); i++) {
            String indexedErrorMessage = String.format("Incorrect calculated value for %d", i);
            Elem elem = elems.get(i);
            assertEquals(elem.getPoint(), rsi.point(i), indexedErrorMessage);
        }
    }

    private List<Rate> split(List<Elem> elems) {
        return new ArrayList<>();
    }

}