package com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.SMMA;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class Elem {
    private Date time;
    private double sum;
    private int count;

    public double getPoint() {
        return sum / count;
    }

    public void addPrice(double price, Date current) {
        double alpha = getAlpha(count + 1);
        sum = (price * alpha) + sum * (1 - alpha);
        count++;
        time = current;
    }

    private double getAlpha(double N) {
        return 2 / N;
    }
}
