import com.wcreators.strategyindicators.models.Decimal;
import com.wcreators.strategyindicators.services.indicators.ma.SMMA;
import eu.verdelhan.ta4j.*;
import eu.verdelhan.ta4j.indicators.EMAIndicator;
import eu.verdelhan.ta4j.indicators.helpers.ClosePriceIndicator;
import org.junit.jupiter.api.Test;
import utils.Points;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SmmaCompareTest {

    @Test
    public void compare() {
        int period = 3;
        SMMA smma = new SMMA(period);
        TimeSeries barSeries = new BaseTimeSeries();
        Indicator<eu.verdelhan.ta4j.Decimal> numIndicator = new ClosePriceIndicator(barSeries);
        EMAIndicator smmaIndicator = new eu.verdelhan.ta4j.indicators.EMAIndicator(numIndicator, period - 1);

        Arrays.stream(Points.inputValues).forEach(v -> {
            Decimal value = Decimal.valueOf(v);
            eu.verdelhan.ta4j.Decimal close = eu.verdelhan.ta4j.Decimal.valueOf(value.doubleValue());
            Tick bar = new BaseTick(Duration.ofMinutes(1), ZonedDateTime.now(), close, close, close, close, eu.verdelhan.ta4j.Decimal.ONE);
            barSeries.addTick(bar);
            smma.addPoint(value);
        });

        assertTrue(smma.size() > 0);
        IntStream.range(0, smma.size()).forEach(i -> {
            double value = smma.get(i).doubleValue();
            double indicatorValue = smmaIndicator.getValue(i).toDouble();
            String errorMessage = String.format("%f is not equal to %f", value, indicatorValue);
            assertEquals(value, indicatorValue, 0.0001, errorMessage);
        });
    }
}
