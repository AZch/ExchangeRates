package com.wcreators.strategyindicators.services.smma;

import com.wcreators.strategyindicators.services.indicators.ma.SMMA;
import generators.PointsGenerator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SMMATest {

//    private final SMMA smma = new SMMA(3);

    @Test
    public void loadData() {
//        double[] values = PointsGenerator.getValues();
//        State prev = new State();
//
//        for (int i = 0; i < values.length; i++) {
//            double expected = smma.addPoint(BigDecimal.valueOf(values[i])).doubleValue();
//            prev.v = Optional.of(expected);
//            double actual = emas[i];
//            String indexedErrorMessage = String.format("Incorrect calculated value for %d, %f : %f", i, expected, actual);
//            assertTrue(Math.abs(expected - actual) < 0.000001, indexedErrorMessage);
//        }
    }



    private static class State {
        Optional<Double> v = Optional.empty();
    }

    double[] emas = new double[] {
            0,
            0.772193333333334,
            1.02969111111111,
            1.11555703703704,
            1.14401901234568,
            1.15311967078189,
            1.15615322359396,
            1.15707107453132,
            1.15741702484377,
            1.15756567494792,
            1.15766855831597,
            1.15748285277199,
            1.157380950924,
            1.15734698364133,
            1.15742232788044,
            1.15744077596015,
            1.15758025865338,
            1.15758008621779,
            1.15758002873926,
            1.15740000957975,
            1.15729333652658,
            1.15735111217553,
            1.15754370405851,
            1.15766123468617,
            1.15765374489539,
            1.1575245816318,
            1.1574815272106,
            1.15756717573687,
            1.15761572524562,
            1.15766524174854,
            1.15776841391618,
            1.15769613797206,
            1.15763871265735,
            1.15754623755245,
            1.15758874585082,
            1.15760958195027,
            1.15765652731676,
            1.15762550910559,
            1.1577085030352,
            1.15784283434507,
            1.15793427811502,
            1.15807809270501,
            1.158219364235,
            1.158306454745,
            1.15836215158167,
            1.15843405052722,
            1.15843135017574,
            1.15837045005858,
            1.15839681668619,
            1.1584389388954,
            1.15844631296513,
            1.15851543765504,
            1.15849181255168,
            1.15860393751723,
            1.15856797917241,
            1.15851599305747,
            1.15859199768582,
            1.15865066589527,
            1.15865688863176,
            1.15872562954392,
            1.15868187651464,
            1.15864729217155,
            1.15876243072385,
            1.15876747690795,
            1.15872249230265,
            1.15874749743422,
            1.15879583247807,
            1.15885861082602,
            1.15883953694201,
            1.15905984564734,
            1.15913994854911,
            1.15909331618304,
            1.15903110539435,
            1.15893703513145,
            1.15891901171048,
            1.15901967057016,
            1.15903322352339,
            1.15897774117446,
            1.15898591372482,
            1.15891530457494,
            1.15894510152498,
            1.15902170050833,
            1.15916723350278,
            1.15914907783426,
            1.15911635927809,
            1.15909211975936,
            1.15899070658645,
            1.15903023552882,
            1.15894341184294,
            1.15890780394765,
            1.15887593464922,
            1.15887197821641,
            1.15893065940547,
            1.15901021980182,
            1.15905007326727,
            1.15912335775576,
            1.15914111925192,
            1.15906703975064,
            1.15906901325021,
            1.15849633775007,
            1.15847877925002,
            1.15835292641667,
            1.15841097547222,
            1.15846365849074,
            1.15832121949691,
            1.15788707316564,
            1.15774235772188,
            1.15760078590729,
            1.15759359530243,
            1.15762453176748,
            1.15768817725583,
            1.15748939241861,
            1.1573831308062,
            1.15734771026873,
            1.15742257008958
    };
}