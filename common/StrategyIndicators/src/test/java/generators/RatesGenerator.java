package generators;

import com.wcreators.objectmodels.model.Rate;
import com.wcreators.strategyindicators.services.Cup.CupPoint;

import java.util.*;

public class RatesGenerator {

    public static List<Rate> generate(List<CupPoint> cupPoints) {
        return split(cupPoints);
    }

    private static Double getNextPrice(Double max, Double min, Random random) {
        return min + random.nextInt((int) (max - min - 1)) + random.nextDouble();
    }

    private static List<Rate> split(List<CupPoint> elems) {
        Random random = new Random();
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
                                .sell(getNextPrice(elem.getHigh(), elem.getLow(), random))
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
