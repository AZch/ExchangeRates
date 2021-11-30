import com.wcreators.strategyindicators.models.CupPoint;
import com.wcreators.strategyindicators.models.Decimal;
import com.wcreators.strategyindicators.services.cup.Cup;
import com.wcreators.strategyindicators.services.indicators.stochastic.StochasticK;
import com.wcreators.utils.date.DateUtilsUtilDate;
import eu.verdelhan.ta4j.BaseTick;
import eu.verdelhan.ta4j.BaseTimeSeries;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.StochasticOscillatorKIndicator;
import org.junit.jupiter.api.Test;
import utils.Points;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StochasticKCompareTest {

    @Test
    public void compare() {
        int period = 3;
        Cup cup = new Cup(new DateUtilsUtilDate());
        StochasticK stochasticK = new StochasticK(period, period);
        TimeSeries barSeries = new BaseTimeSeries();
        StochasticOscillatorKIndicator kIndicator = new StochasticOscillatorKIndicator(barSeries, period);

        Arrays.stream(Points.inputValues).forEach(v -> {
            Decimal value = Decimal.valueOf(v);
            eu.verdelhan.ta4j.Decimal close = eu.verdelhan.ta4j.Decimal.valueOf(v);
            Tick bar = new BaseTick(Duration.ofMinutes(1), ZonedDateTime.now(), close, close, close, close, eu.verdelhan.ta4j.Decimal.ONE);
            barSeries.addTick(bar);
            cup.addPoint(
                    CupPoint.builder()
                            .close(v)
                            .build()
            );
            stochasticK.addPoint(value);
        });

        assertTrue(stochasticK.size() > 0);
        IntStream.range(0, stochasticK.size()).forEach(i -> {
            double value = stochasticK.get(i).doubleValue();
            double indicatorValue = kIndicator.getValue(i).toDouble();
            String errorMessage = String.format("%f is not equal to %f", value, indicatorValue);
            if (kIndicator.getValue(i).isNaN()) {
                System.out.println("Value is nan for i=" + i + ", expected " + value);
            } else {
                assertEquals(value, indicatorValue, 0.01, errorMessage);
            }
        });
    }

}
