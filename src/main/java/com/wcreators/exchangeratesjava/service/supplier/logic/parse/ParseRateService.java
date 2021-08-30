package com.wcreators.exchangeratesjava.service.supplier.logic.parse;

import com.wcreators.exchangeratesjava.constant.Resource;
import com.wcreators.exchangeratesjava.constant.ResourceAction;
import com.wcreators.exchangeratesjava.model.Rate;

import java.util.List;

public interface ParseRateService {
    List<Rate> parse() throws Exception;

    void reload();

    Resource getResource();

    default ResourceAction getResourceAction() {
        return ResourceAction.PARSE;
    }
}
