package com.wcreators.strategyindicators.services.STOCH;

import com.wcreators.objectmodels.model.Rate;
import com.wcreators.strategyindicators.models.Point;
import com.wcreators.strategyindicators.services.Cup.Cup;
import com.wcreators.strategyindicators.services.Cup.CupPoint;
import com.wcreators.strategyindicators.services.EMA.Ema;
import com.wcreators.utils.date.DateUtils;
import com.wcreators.utils.date.DateUtilsUtilDate;
import generators.CupPointsGenerator;
import generators.RatesGenerator;
import generators.StochGenerator;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class STOCHTest {

    private final DateUtils<Date> dateUtils = new DateUtilsUtilDate();
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