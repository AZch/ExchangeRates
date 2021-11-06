package com.wcreators.storeparsedrates.port;

import com.wcreators.databasestarter.service.cup.CupRatePointService;
import com.wcreators.databasestarter.service.rate.RateEntityService;
import com.wcreators.databasestarter.service.rate.action.RateActionService;
import com.wcreators.objectmodels.constant.Resource;
import com.wcreators.objectmodels.model.CupRatePoint;
import com.wcreators.objectmodels.model.Rate;
import com.wcreators.objectmodels.model.RateAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PortRateStoreInDbService {

    private final RateEntityService rateEntityService;
    private final RateActionService rateActionService;
    private final CupRatePointService cupRatePointService;

    @EventListener
    public void storeParsedRate(Rate rate) {
        log.info("Storing parsed rate {}", rate);
        rateEntityService.save(rate, Resource.FOREX);
    }

    @EventListener
    public void storeActionableRate(RateAction rateAction) {
        log.info("Storing action {} for rate {}", rateAction.getAction(), rateAction.getAction());
        rateActionService.save(rateAction);
    }

    @EventListener
    public void storeParsedCupRate(CupRatePoint cupRatePoint) {
        log.info("Storing parsed cup {}", cupRatePoint);
        cupRatePointService.save(cupRatePoint);
    }
}
