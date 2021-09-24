package com.wcreators.exchangeratesjava.service.process.port;

import com.wcreators.exchangeratesjava.model.Rate;

import java.util.List;

public interface ProcessPortService<T, U> {

    List<Rate> receiveRates(T event);

    void sendAction(U action);

}
