package common.generators;

import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.Point;

import java.util.ArrayList;
import java.util.List;

public class EmaGenerator {

    public static List<Point> generate(List<Point> points, int period) {
        return basicGenerate(points, period, 2D / (period + 1));
    }

    public static List<Point> basicGenerate(List<Point> points, int period, double multiplier) {
        List<Point> sma = SmaGenerator.generate(points, period);
        List<Point> ema = new ArrayList<>(points.size());

        for (int i = 0; i < sma.size(); i++) {
            Point elem = sma.get(i);
            if (elem.getValue() == 0) {
                ema.add(elem);
            } else {
                Point prev = ema.get(i - 1);
                if (prev.getValue() == 0) {
                    ema.add(elem);
                    continue;
                }
                ema.add(
                        Point.builder()
                                .value((points.get(i).getValue() - prev.getValue()) * multiplier + prev.getValue())
                                .time(elem.getTime())
                                .build()
                );
            }
        }

        return ema;
    }
}
