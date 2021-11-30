import com.wcreators.strategyindicators.models.Decimal;
import com.wcreators.strategyindicators.services.indicators.ma.SMA;
import com.wcreators.strategyindicators.services.indicators.rsi.RSI;
import eu.verdelhan.ta4j.*;
import eu.verdelhan.ta4j.indicators.RSIIndicator;
import eu.verdelhan.ta4j.indicators.helpers.ClosePriceIndicator;
import org.junit.jupiter.api.Test;
import utils.Points;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RsiCompareTest {

    @Test
    public void compare() {
        int period = 3;
        RSI rsi = new RSI(new SMA(3), new SMA(3));

        TimeSeries barSeries = new BaseTimeSeries();
        Indicator<eu.verdelhan.ta4j.Decimal> numIndicator = new ClosePriceIndicator(barSeries);
        RSIIndicator rsiIndicator = new eu.verdelhan.ta4j.indicators.RSIIndicator(numIndicator, period);

        Arrays.stream(Points.inputValues).forEach(v -> {
            Decimal value = Decimal.valueOf(v);
            eu.verdelhan.ta4j.Decimal close = eu.verdelhan.ta4j.Decimal.valueOf(v);
            Tick bar = new BaseTick(Duration.ofMinutes(1), ZonedDateTime.now(), close, close, close, close, eu.verdelhan.ta4j.Decimal.ONE);
            barSeries.addTick(bar);
            rsi.addPoint(value);
        });

        assertTrue(rsi.size() > 0);
        IntStream.range(0, rsi.size()).forEach(i -> {
            double value = rsi.get(i).doubleValue();
            double indicatorValue = rsiIndicator.getValue(i).toDouble();
            String errorMessage = String.format("%f is not equal to %f", value, indicatorValue);
            assertEquals(value, indicatorValue, 0.001, errorMessage);
        });
    }
}
