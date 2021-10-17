package com.wcreators.strategyindicators.services.cup;

import com.wcreators.objectmodels.model.Rate;
import com.wcreators.strategyindicators.models.CupPoint;
import com.wcreators.strategyindicators.services.Indicator;

import java.util.Optional;

public interface CupIndicator extends Indicator<Rate, Optional<CupPoint>, CupUtils> {
}
