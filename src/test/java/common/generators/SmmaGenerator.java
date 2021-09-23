package common.generators;

import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.Point;

import java.util.List;

public class SmmaGenerator {

    public static List<Point> generate(List<Point> points, int period) {
        return EmaGenerator.basicGenerate(points, period, 2D / period);
    }
}
