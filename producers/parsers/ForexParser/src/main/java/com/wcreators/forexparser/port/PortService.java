package com.wcreators.forexparser.port;

import com.wcreators.objectmodels.constant.Resource;
import com.wcreators.objectmodels.constant.ResourceAction;
import com.wcreators.objectmodels.model.Rate;

import java.util.List;

public interface PortService {
    void sendParsedRates(List<Rate> rates, Resource resource, ResourceAction resourceAction);
}
