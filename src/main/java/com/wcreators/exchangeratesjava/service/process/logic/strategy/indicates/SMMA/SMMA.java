package com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.SMMA;

import com.wcreators.exchangeratesjava.model.Rate;
import com.wcreators.exchangeratesjava.util.DateUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Scope("prototype")
public class SMMA {

    private final DateUtils dateUtils;
    private final List<Elem> elems = new LinkedList<>();
    private Elem current;

    public int getElemsSize() {
        return elems.size();
    }

    private final Function<Rate, Elem> elemFromRate = rate -> Elem.builder()
            .sum(rate.getSell())
            .count(1)
            .time(rate.getCreatedDate())
            .build();

    public void addRate(Rate rate) {
        if (current == null) {
            current = elemFromRate.apply(rate);
            return;
        }

        if (dateUtils.getMinutes(current.getTime()) == dateUtils.getMinutes(rate.getCreatedDate())) {
            current.addPrice(rate.getSell(), rate.getCreatedDate());
        } else {
            elems.add(current);
            current = elemFromRate.apply(rate);
        }
    }

    public double getPoint(int index) {
        return elems.get(index).getPoint();
    }


}
