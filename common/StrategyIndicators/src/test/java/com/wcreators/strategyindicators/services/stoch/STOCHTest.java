package com.wcreators.strategyindicators.services.stoch;

import com.wcreators.objectmodels.model.Rate;
import com.wcreators.strategyindicators.models.Point;
import com.wcreators.strategyindicators.services.cup.Cup;
import com.wcreators.strategyindicators.models.CupPoint;
import com.wcreators.strategyindicators.services.cup.CupIndicator;
import com.wcreators.strategyindicators.services.ema.Ema;
import com.wcreators.strategyindicators.services.ema.EmaIndicator;
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
    private final CupIndicator cup = new Cup(dateUtils);
    private final EmaIndicator emaFastK = new Ema();
    private final EmaIndicator emaSlowD = new Ema();
    private final Stoch stoch = new Stoch(cup, emaFastK, emaSlowD);

    @Test
    public void loadData() {
        stoch.init();
        int maxMinutes = 50;
        List<CupPoint> cupPoints = CupPointsGenerator.generate(maxMinutes);
        List<Rate> rates = RatesGenerator.generate(cupPoints);
        StochGenerator.StochData stochData = StochGenerator.generate(
                cupPoints, stoch.getUtils().getPeriodFastK(),
                stoch.getUtils().getPeriodSlowD(), stoch.getUtils().getPeriodSlowD()
        );

        for (Rate rate : rates) {
            Optional<CupPoint> optionalCupPoint = cup.addPoint(rate);
            if (optionalCupPoint.isPresent()) {
                stoch.update();
            }
        }

        assertEquals(stochData.getFastKPoints().size(), stoch.getUtils().getFastKElemsSize());
        assertEquals(stochData.getSlowDPoints().size(), stoch.getUtils().getSlowDElemsSize());

        for (int i = 0; i < stochData.getFastKPoints().size(); i++) {
            String indexedErrorMessage = String.format("Incorrect fast k calculated value for %d", i);
            Point elem = stochData.getFastKPoints().get(i);
            assertEquals(elem.getValue(), stoch.getUtils().getFastKValue(i), indexedErrorMessage);
        }

        for (int i = 0; i < stochData.getSlowDPoints().size(); i++) {
            String indexedErrorMessage = String.format("Incorrect slow d calculated value for %d", i);
            Point elem = stochData.getSlowDPoints().get(i);
            assertEquals(elem.getValue(), stoch.getUtils().getSlowDValue(i), indexedErrorMessage);
        }
    }
}