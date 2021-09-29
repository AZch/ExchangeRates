package com.wcreators.strategyindicators.services.Cup;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Builder
@Getter
@ToString
public class CupPoint {

    private double high;
    private double low;
    private double open;
    private double close;
    private Date start;
    private Date end;

    public void addPrice(double price, Date current) {
        close = price;
        end = current;
        high = max(high, close);
        low = min(low, close);
    }
}
