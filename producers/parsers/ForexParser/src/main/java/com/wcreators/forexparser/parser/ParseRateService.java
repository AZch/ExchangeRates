package com.wcreators.forexparser.parser;

import com.wcreators.objectmodels.constant.Resource;
import com.wcreators.objectmodels.constant.ResourceAction;
import com.wcreators.objectmodels.model.Rate;

import java.util.List;

public interface ParseRateService {
    List<Rate> parse() throws Exception;

    void reload();

    Resource getResource();

    default ResourceAction getResourceAction() {
        return ResourceAction.PARSE;
    }
}
