package generators;

import com.wcreators.strategyindicators.models.Point;

import java.util.List;

public class SmmaGenerator {

    public static List<Point> generate(List<Point> points, int period) {
        return EmaGenerator.basicGenerate(points, period, 2D / period);
    }
}
