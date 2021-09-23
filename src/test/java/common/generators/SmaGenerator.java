package common.generators;

import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.Point;

import java.util.ArrayList;
import java.util.List;

public class SmaGenerator {

    public static List<Point> generate(List<Point> points, int period) {
        List<Point> sma = new ArrayList<>(points.size());

        double interm = 0;
        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            interm += point.getValue();
            if (i + 1 < period) {
                sma.add(
                        Point.builder()
                                .time(point.getTime())
                                .value(0D)
                                .build()
                );
            } else {
                sma.add(
                        Point.builder()
                                .time(point.getTime())
                                .value(interm / period)
                                .build()
                );
                interm -= points.get(i + 1 - period).getValue();
            }
        }

        return sma;
    }
}
