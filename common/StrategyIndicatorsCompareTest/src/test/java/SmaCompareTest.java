import com.wcreators.strategyindicators.models.Decimal;
import com.wcreators.strategyindicators.services.indicators.ma.EMA;
import com.wcreators.strategyindicators.services.indicators.ma.SMA;
import eu.verdelhan.ta4j.*;
import eu.verdelhan.ta4j.indicators.EMAIndicator;
import eu.verdelhan.ta4j.indicators.SMAIndicator;
import eu.verdelhan.ta4j.indicators.helpers.ClosePriceIndicator;
import org.junit.jupiter.api.Test;
import utils.Points;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SmaCompareTest {

    @Test
    public void compare() {
        int period = 3;
        SMA sma = new SMA(period);
        TimeSeries barSeries = new BaseTimeSeries();
        Indicator<eu.verdelhan.ta4j.Decimal> numIndicator = new ClosePriceIndicator(barSeries);
        SMAIndicator emaIndicator = new SMAIndicator(numIndicator, period);

        Arrays.stream(Points.inputValues).forEach(v -> {
            Decimal value = Decimal.valueOf(v);
            eu.verdelhan.ta4j.Decimal close = eu.verdelhan.ta4j.Decimal.valueOf(v);
            Tick bar = new BaseTick(Duration.ofMinutes(1), ZonedDateTime.now(), close, close, close, close, eu.verdelhan.ta4j.Decimal.ONE);
            barSeries.addTick(bar);
            sma.addPoint(value);
        });

        assertTrue(sma.size() > 0);
        IntStream.range(0, sma.size()).forEach(i -> {
            double emaValue = sma.get(i).doubleValue();
            double indicatorValue = emaIndicator.getValue(i).toDouble();
            String errorMessage = String.format("%f is not equal to %f", emaValue, indicatorValue);
            assertEquals(emaValue, indicatorValue, 0.001, errorMessage);
        });
    }

}
