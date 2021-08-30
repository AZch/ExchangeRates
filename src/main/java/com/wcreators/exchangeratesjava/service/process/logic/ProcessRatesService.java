package com.wcreators.exchangeratesjava.service.process.logic;

import com.wcreators.exchangeratesjava.model.Rate;

import java.util.List;

public interface ProcessRatesService {
    List<Rate> process(List<Rate> rates);
}
