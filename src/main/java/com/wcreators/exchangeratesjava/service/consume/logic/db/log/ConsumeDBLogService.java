package com.wcreators.exchangeratesjava.service.consume.logic.db.log;

import com.wcreators.exchangeratesjava.constant.Resource;
import com.wcreators.exchangeratesjava.model.Rate;

import java.util.List;

public interface ConsumeDBLogService {
    void consume(List<Rate> rate, Resource resource);
}
