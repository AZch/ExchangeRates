package generators;

import com.wcreators.strategyindicators.models.Point;

import java.util.*;

public class PointsGenerator {
    public static List<Point> generate(int maxMinutes) {
        Random random = new Random();
        List<Point> points = new ArrayList<>(maxMinutes);
        Calendar calendar = Calendar.getInstance();
        while (points.size() < maxMinutes) {
            points.add(
                    Point.builder()
                            .time(getNextDate(calendar))
                            .value(random.nextInt(10) + random.nextDouble())
                            .build()
            );
        }

        return points;
    }

    private static Date getNextDate(Calendar calendar) {
        calendar.add(Calendar.MINUTE, 1);
        return calendar.getTime();
    }
}
