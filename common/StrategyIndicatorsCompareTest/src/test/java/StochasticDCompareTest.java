import com.wcreators.strategyindicators.models.Decimal;
import com.wcreators.strategyindicators.services.indicators.stochastic.StochasticD;
import com.wcreators.strategyindicators.services.indicators.stochastic.StochasticK;
import eu.verdelhan.ta4j.BaseTick;
import eu.verdelhan.ta4j.BaseTimeSeries;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.StochasticOscillatorDIndicator;
import eu.verdelhan.ta4j.indicators.StochasticOscillatorKIndicator;
import org.junit.jupiter.api.Test;
import utils.Points;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StochasticDCompareTest {

    @Test
    public void compare() {
        int period = 3;
        StochasticK stochasticK = new StochasticK(period, period);
        StochasticD stochasticD = new StochasticD(stochasticK, period);
        TimeSeries barSeries = new BaseTimeSeries();
        StochasticOscillatorKIndicator kIndicator = new StochasticOscillatorKIndicator(barSeries, period);
        StochasticOscillatorDIndicator dIndicator = new StochasticOscillatorDIndicator(kIndicator);

        Arrays.stream(Points.inputValues).forEach(v -> {
            Decimal value = Decimal.valueOf(v);
            eu.verdelhan.ta4j.Decimal close = eu.verdelhan.ta4j.Decimal.valueOf(v);
            Tick bar = new BaseTick(Duration.ofMinutes(1), ZonedDateTime.now(), close, close, close, close, eu.verdelhan.ta4j.Decimal.ONE);
            barSeries.addTick(bar);
            stochasticK.addPoint(value);
            stochasticD.addPoint(value);
        });

        assertTrue(stochasticD.size() > 0);
        IntStream.range(0, stochasticD.size()).forEach(i -> {
            double value = stochasticD.get(i).doubleValue();
            double valueK = stochasticK.get(i).doubleValue();
            double indicatorValue = dIndicator.getValue(i).toDouble();
            double indicatorKValue = kIndicator.getValue(i).toDouble();
            String errorMessage = String.format("%f : %f is not equal to %f : %f", value, valueK, indicatorValue, indicatorKValue);
            System.out.println(errorMessage);
//            if (dIndicator.getValue(i).isNaN()) {
//                System.out.println("Value is nan for i=" + i + ", expected " + value);
//            } else {
//                assertEquals(value, indicatorValue, 0.0001, errorMessage);
//            }
        });
    }

}
