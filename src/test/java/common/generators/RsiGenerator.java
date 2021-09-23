package common.generators;

import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.Point;
import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.SMMA.SMMA;

import java.util.ArrayList;
import java.util.List;

public class RsiGenerator {

    public static List<Point> generate(List<Point> points, int period) {
        List<Point> uPoints = new ArrayList<>(points.size());
        List<Point> dPoints = new ArrayList<>(points.size());

        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);

            if (i == 0) {
                uPoints.add(Point.builder().value(0D).time(point.getTime()).build());
                dPoints.add(Point.builder().value(0D).time(point.getTime()).build());
            } else {
                Point prevPoint = points.get(i - 1);
                if (point.getValue() > prevPoint.getValue()) {
                    uPoints.add(Point.builder().value(point.getValue() - prevPoint.getValue()).time(point.getTime()).build());
                    dPoints.add(Point.builder().value(0D).time(point.getTime()).build());
                } else if (point.getValue() < prevPoint.getValue()) {
                    uPoints.add(Point.builder().value(0D).time(point.getTime()).build());
                    dPoints.add(Point.builder().value(prevPoint.getValue() - point.getValue()).time(point.getTime()).build());
                } else {
                    uPoints.add(Point.builder().value(0D).time(point.getTime()).build());
                    dPoints.add(Point.builder().value(0D).time(point.getTime()).build());
                }
            }
        }

        List<Point> smmaUPoints = SmmaGenerator.generate(uPoints, period);
        List<Point> smmaDPoints = SmmaGenerator.generate(dPoints, period);

        List<Point> result = new ArrayList<>(points.size());
        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            if (smmaDPoints.get(i).getValue() == 0) {
                result.add(Point.builder().value(100D).time(point.getTime()).build());
            } else {
                Point smmaUPoint = smmaUPoints.get(i);
                Point smmaDPoint = smmaDPoints.get(i);
                result.add(
                        Point.builder()
                                .value(100 - 100D / (1 + smmaUPoint.getValue() / smmaDPoint.getValue()))
                                .time(point.getTime())
                                .build()
                );
            }
        }
        return result;
    }
}
