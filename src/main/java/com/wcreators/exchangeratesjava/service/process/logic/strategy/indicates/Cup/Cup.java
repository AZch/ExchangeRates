package com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.Cup;


import com.wcreators.exchangeratesjava.model.Rate;
import com.wcreators.exchangeratesjava.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class Cup {

    private final List<Elem> elems = new LinkedList<>();
    private Elem current;
    private final DateUtils dateUtils;

    public int getElemsSize() {
        return elems.size();
    }

    public double minLastLow(int period) {
        return elems.stream().skip(elems.size() - period).mapToDouble(Elem::getLow).min().getAsDouble();
    }

    public double maxLastHigh(int period) {
        return elems.stream().skip(elems.size() - period).mapToDouble(Elem::getLow).max().getAsDouble();
    }

    public double high(int index) {
        return elems.get(index).getHigh();
    }

    public double low(int index) {
        return elems.get(index).getLow();
    }

    public double close(int index) {
        return elems.get(index).getClose();
    }

    public double open(int index) {
        return elems.get(index).getOpen();
    }

    public Date start(int index) {
        return elems.get(index).getStart();
    }

    public Date end(int index) {
        return elems.get(index).getEnd();
    }

    private final Function<Rate, Elem> elemFromRate = rate -> Elem.builder()
            .start(rate.getCreatedDate())
            .high(rate.getSell())
            .low(rate.getSell())
            .open(rate.getSell())
            .close(rate.getSell())
            .end(rate.getCreatedDate())
            .build();

    public void addRate(Rate rate) {
        if (current == null) {
            current = elemFromRate.apply(rate);
            return;
        }

        if (dateUtils.getMinutes(current.getStart()) == dateUtils.getMinutes(rate.getCreatedDate())) {
            current.addPrice(rate.getSell(), rate.getCreatedDate());
        } else {
            elems.add(current);
            current = elemFromRate.apply(rate);
        }
    }
}
