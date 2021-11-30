package generators;

import com.wcreators.strategyindicators.models.Point;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class PointsGenerator {
    public static List<Point> generate(int maxMinutes) {
//        Random random = new Random();
//        List<Point> points = new ArrayList<>(maxMinutes);
        Calendar calendar = Calendar.getInstance();
//        while (points.size() < maxMinutes) {
//            points.add(
//                    Point.builder()
//                            .time(getNextDate(calendar))
//                            .value(random.nextInt(10) + random.nextDouble())
//                            .build()
//            );
//        }
//
//        return points;

        return Arrays.stream(values)
                .mapToObj(v ->
                        Point.builder()
                                .value(v)
                                .time(getNextDate(calendar))
                                .build())
                .collect(Collectors.toList());


    }

    private static Date getNextDate(Calendar calendar) {
        calendar.add(Calendar.MINUTE, 1);
        return calendar.getTime();
    }

    public static double[] getValues() {
        return values;
    }

    private static final double[] values = new double[] {
            1.15847,
            1.15829,
            1.15844,
            1.15849,
            1.15825,
            1.15767,
            1.15767,
            1.15753,
            1.15759,
            1.15764,
            1.15772,
            1.15739,
            1.15733,
            1.15733,
            1.15746,
            1.15745,
            1.15765,
            1.15758,
            1.15758,
            1.15731,
            1.15724,
            1.15738,
            1.15764,
            1.15772,
            1.15765,
            1.15746,
            1.15746,
            1.15761,
            1.15764,
            1.15769,
            1.15782,
            1.15766,
            1.15761,
            1.1575,
            1.15761,
            1.15762,
            1.15768,
            1.15761,
            1.15775,
            1.15791,
            1.15798,
            1.15815,
            1.15829,
            1.15835,
            1.15839,
            1.15847,
            1.15843,
            1.15834,
            1.15841,
            1.15846,
            1.15845,
            1.15855,
            1.15848,
            1.15866,
            1.15855,
            1.15849,
            1.15863,
            1.15868,
            1.15866,
            1.15876,
            1.15866,
            1.15863,
            1.15882,
            1.15877,
            1.1587,
            1.15876,
            1.15882,
            1.15889,
            1.15883,
            1.15917,
            1.15918,
            1.15907,
            1.159,
            1.15889,
            1.15891,
            1.15907,
            1.15904,
            1.15895,
            1.15899,
            1.15888,
            1.15896,
            1.15906,
            1.15924,
            1.15914,
            1.1591,
            1.15908,
            1.15894,
            1.15905,
            1.1589,
            1.15889,
            1.15886,
            1.15887,
            1.15896,
            1.15905,
            1.15907,
            1.15916,
            1.15915,
            1.15903,
            1.15907,
            1.15821,
            1.15847,
            1.15829,
            1.15844,
            1.15849,
            1.15825,
            1.15767,
            1.15767,
            1.15753,
            1.15759,
            1.15764,
            1.15772,
            1.15739,
            1.15733,
            1.15733,
            1.15746,
    };
}
