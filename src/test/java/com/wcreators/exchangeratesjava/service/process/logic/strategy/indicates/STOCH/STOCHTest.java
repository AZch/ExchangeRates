package com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.STOCH;

import com.wcreators.exchangeratesjava.model.Rate;
import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.Cup.Cup;
import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.EMA.EMA;
import com.wcreators.exchangeratesjava.util.DateUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class STOCHTest {

    private final int maxMinutes = 1000;
    private final DateUtils dateUtils = new DateUtils();
    private final Cup cup = new Cup(dateUtils);
    private final EMA emaFastK = new EMA(dateUtils, cup);
    private final EMA emaSlowD = new EMA(dateUtils, cup);
    private final STOCH stoch = new STOCH(cup, emaFastK, emaSlowD);

    @Test
    public void loadData() {
        List<Elem> elems = new ArrayList<>(maxMinutes);
        while (elems.size() < maxMinutes) {
            elems.add(
                    Elem.builder()
                            .time(new Date())
                            .point(1)
                            .build()
            );
        }

        List<Rate> rates = split(elems);
        rates.forEach(stoch::addRate);


        assertEquals(elems.size(), stoch.getElemsSize());

        for (int i = 0; i < elems.size(); i++) {
            String indexedErrorMessage = String.format("Incorrect calculated value for %d", i);
            Elem elem = elems.get(i);
            assertEquals(elem.getPoint(), stoch.point(i), indexedErrorMessage);
        }
    }

    private List<Rate> split(List<Elem> elems) {
        return new ArrayList<>();
    }
}