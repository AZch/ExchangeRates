package com.wcreators.exchangeratesjava.service.supplier.port;

import com.wcreators.exchangeratesjava.constant.Resource;
import com.wcreators.exchangeratesjava.constant.ResourceAction;
import com.wcreators.exchangeratesjava.model.Rate;

import java.util.List;

public interface SupplierPortService {
    void sendParsedRates(List<Rate> rates, Resource resource, ResourceAction resourceAction);
}
