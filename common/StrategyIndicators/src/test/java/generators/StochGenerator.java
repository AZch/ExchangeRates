package generators;

import com.wcreators.strategyindicators.models.Point;
import com.wcreators.strategyindicators.models.CupPoint;

import java.util.ArrayList;
import java.util.List;

public class StochGenerator {

    public static StochData generate(List<CupPoint> cupPoints, int fastKPeriod, int slowKPeriod, int slowDPeriod) {

        List<Point> fastKPoints = new ArrayList<>(cupPoints.size());

        for (int i = 0; i < cupPoints.size(); i++) {
            CupPoint cupPoint = cupPoints.get(i);
            if (i + 1 < fastKPeriod) {
                fastKPoints.add(Point.builder().value(0D).time(cupPoint.getEnd()).build());
            } else {
                int lowerBound = i + 1 - fastKPeriod;
                int upperBound = i + 1;
                double currentLow = cupPoints.stream().limit(upperBound).skip(lowerBound).mapToDouble(CupPoint::getLow).min().orElse(0D);
                double currentHigh = cupPoints.stream().limit(upperBound).skip(lowerBound).mapToDouble(CupPoint::getHigh).max().orElse(0D);
                fastKPoints.add(
                        Point.builder()
                                .value(((cupPoint.getClose() - currentLow) / (currentHigh - currentLow)) * 100)
                                .time(cupPoint.getEnd())
                                .build()
                );
            }
        }

        List<Point> fastK = EmaGenerator.generate(fastKPoints, slowKPeriod);
        List<Point> slowD = EmaGenerator.generate(fastK, slowDPeriod);
        return new StochData(fastK, slowD);
    }

    public static class StochData {

        private final List<Point> fastKPoints;
        private final List<Point> slowDPoints;

        public StochData(List<Point> fastKPoints, List<Point> slowDPoints) {
            this.fastKPoints = fastKPoints;
            this.slowDPoints = slowDPoints;
        }

        public List<Point> getFastKPoints() {
            return fastKPoints;
        }

        public List<Point> getSlowDPoints() {
            return slowDPoints;
        }
    }
}
