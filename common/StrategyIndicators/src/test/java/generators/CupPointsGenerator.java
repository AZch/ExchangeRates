package generators;

import com.wcreators.strategyindicators.models.CupPoint;

import java.util.*;

public class CupPointsGenerator {

    public static List<CupPoint> generate(int maxMinutes) {
        Random random = new Random();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        List<CupPoint> elems = new ArrayList<>(maxMinutes);
        while (elems.size() < maxMinutes) {
            elems.add(
                    CupPoint.builder()
                            .high(elems.size() + 5 + random.nextDouble())
                            .low(elems.size() + random.nextDouble())
                            .open(elems.size() + 1 + random.nextDouble())
                            .close(elems.size() + 1 + random.nextDouble())
                            .start(calendar.getTime())
                            .end(getNextDate(calendar))
                            .build()
            );
        }
        return elems;
    }

    private static Date getNextDate(Calendar calendar) {
        calendar.add(Calendar.MINUTE, 1);
        return calendar.getTime();
    }
}
