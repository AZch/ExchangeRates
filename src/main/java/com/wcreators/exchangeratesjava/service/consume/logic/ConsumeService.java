package com.wcreators.exchangeratesjava.service.consume.logic;

import com.wcreators.exchangeratesjava.constant.Resource;
import com.wcreators.exchangeratesjava.model.Rate;

import java.util.List;

public interface ConsumeService {
    void consume(List<Rate> rate, Resource resource);
}
