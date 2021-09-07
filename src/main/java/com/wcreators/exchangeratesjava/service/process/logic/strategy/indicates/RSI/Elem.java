package com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.RSI;

import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.SMMA.SMMA;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Builder
@Getter
public class Elem {

    private Date time;
    private double point;

    public void addPrice(double price, Date current, SMMA smmaD, SMMA smmaU, double e, List<Elem> elems) {
        if (smmaD.getPoint(elems.size()) < e) {
            point = 100;
            return;
        }
        point = 100 - (100 / (1 + smmaU.getPoint(elems.size()) / smmaD.getPoint(elems.size())));
    }
}
