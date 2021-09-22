package com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.EMA;

import com.wcreators.exchangeratesjava.model.Rate;
import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.Cup.Cup;
import com.wcreators.exchangeratesjava.util.DateUtils;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class EMATest {

    private final int maxMinutes = 5;
    private final int period = 5;
    private final DateUtils dateUtils = new DateUtils();
    private final Cup cup = new Cup(dateUtils);
    private final EMA ema = new EMA(dateUtils, cup);

    @Test
    public void loadData() {
        List<Rate> rates = generateRates();
        List<Elem> elems = generateEMA(rates);

        for (Rate rate : rates) {
            cup.addRate(rate);
            ema.addRate();
        }
        Date lastCreatedDate = rates.get(rates.size() - 1).getCreatedDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lastCreatedDate);
        calendar.add(Calendar.MINUTE, 1);
        Rate rate = Rate.builder()
                .createdDate(calendar.getTime())
                .sell(0D)
                .build();
        cup.addRate(rate);
        ema.addRate();


        assertEquals(elems.size(), ema.getElemsSize());

        for (int i = 0; i < elems.size(); i++) {
            String indexedErrorMessage = String.format("Incorrect calculated value for %d", i);
            Elem elem = elems.get(i);
            assertEquals(elem.getSum(), ema.sum(i), indexedErrorMessage);
        }
    }

    private List<Elem> generateEMA(List<Rate> rates) {
        List<Elem> sma = generateSMA(rates);
        List<Elem> ema = new ArrayList<>(maxMinutes);
        double multiplier = 2D / (period + 1);

        for (int i = 0; i < sma.size(); i++) {
            Elem elem = sma.get(i);
            if (elem.getSum() == 0) {
                ema.add(elem);
            } else {
                Elem prev = ema.get(i - 1);
                if (prev.getSum() == 0) {
                    ema.add(elem);
                    continue;
                }
                ema.add(
                        Elem.builder()
                                .sum((rates.get(i).getSell() - prev.getSum()) * multiplier + prev.getSum())
                                .time(elem.getTime())
                                .build()
                );
            }
        }

        return ema;
    }

    private List<Elem> generateSMA(List<Rate> rates) {
        List<Elem> sma = new ArrayList<>(maxMinutes);

        double interm = 0;
        for (int i = 0; i < rates.size(); i++) {
            Rate rate = rates.get(i);
            interm += rate.getSell();
            if (i + 1 < period) {
                sma.add(
                        Elem.builder()
                                .time(rate.getCreatedDate())
                                .sum(0D)
                                .build()
                );
            } else {
                sma.add(
                        Elem.builder()
                                .time(rate.getCreatedDate())
                                .sum(interm / period)
                                .build()
                );
                interm -= rates.get(i + 1 - period).getSell();
            }
        }

        return sma;
    }

    private List<Rate> generateRates() {
        Random random = new Random();
        List<Rate> rates = new ArrayList<>(maxMinutes * period);

        double[] values = new double[]{2.255971, 9.118996, 2.352030, 8.361017, 3.337696, 7.109888, 1.315648, 6.345684, 5.495644, 3.740019, 1.341857, 8.704543, 7.768567, 8.087856, 4.391464, 3.486373, 6.698083, 6.384132, 8.153968, 6.229387, 8.473008, 0.396446, 5.625874, 4.524369, 9.257231};
        int index = 0;
        Calendar calendar = Calendar.getInstance();
        getNextDate(calendar);
        while (rates.size() < maxMinutes * period) {
            List<Rate> tempRates = new ArrayList<>(period);
            while (tempRates.size() < period) {
                tempRates.add(
                        Rate.builder()
                                .major("")
                                .minor("")
                                .sell(values[index])
//                                .sell(random.nextInt(10) + random.nextDouble())
                                .buy(0D)
                                .createdDate(getNextDate(calendar))
//                                .createdDate(getNextPeriodTime(calendar))
                                .build()
                );
                index++;
            }
            rates.addAll(tempRates);
//            getNextDate(calendar);
        }


        return rates;
    }

    private Date getNextDate(Calendar calendar) {
        calendar.add(Calendar.MINUTE, 1);
        calendar.set(Calendar.SECOND, 1);
//        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    private Date getNextPeriodTime(Calendar calendar) {
        calendar.add(Calendar.SECOND, 1);
        return calendar.getTime();
    }

}