package com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.STOCH;

import com.wcreators.exchangeratesjava.model.Rate;
import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.Cup.Cup;
import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.Cup.CupPoint;
import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.EMA.Ema;
import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.Point;
import com.wcreators.exchangeratesjava.util.DateUtils;
import common.generators.CupPointsGenerator;
import common.generators.RatesGenerator;
import common.generators.StochGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class STOCHTest {

    private final DateUtils dateUtils = new DateUtils();
    private final Cup cup = new Cup(dateUtils);
    private final Ema emaFastK = new Ema();
    private final Ema emaSlowD = new Ema();
    private final STOCH stoch = new STOCH(cup, emaFastK, emaSlowD);

    @Test
    public void loadData() {
        int maxMinutes = 50;
        List<CupPoint> cupPoints = CupPointsGenerator.generate(maxMinutes);
        List<Rate> rates = RatesGenerator.generate(cupPoints);
        StochGenerator.StochData stochData = StochGenerator.generate(cupPoints, stoch.getPeriodFastK(), stoch.getPeriodSlowD(), stoch.getPeriodSlowD());

        for (Rate rate : rates) {
            Optional<CupPoint> optionalCupPoint = cup.addValue(rate);
            if (optionalCupPoint.isPresent()) {
                stoch.update();
            }
        }

        assertEquals(stochData.getFastKPoints().size(), stoch.getFastKElemsSize());
        assertEquals(stochData.getSlowDPoints().size(), stoch.getSlowDElemsSize());

        for (int i = 0; i < stochData.getFastKPoints().size(); i++) {
            String indexedErrorMessage = String.format("Incorrect fast k calculated value for %d", i);
            Point elem = stochData.getFastKPoints().get(i);
            assertEquals(elem.getValue(), stoch.getFastKValue(i), indexedErrorMessage);
        }

        for (int i = 0; i < stochData.getSlowDPoints().size(); i++) {
            String indexedErrorMessage = String.format("Incorrect slow d calculated value for %d", i);
            Point elem = stochData.getSlowDPoints().get(i);
            assertEquals(elem.getValue(), stoch.getSlowDValue(i), indexedErrorMessage);
        }
    }
}