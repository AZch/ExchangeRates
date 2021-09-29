package com.wcreators.forexparser.port;

import com.wcreators.objectmodels.model.Rate;

public interface PortService {
    void sendParsedRate(Rate rate);
}
