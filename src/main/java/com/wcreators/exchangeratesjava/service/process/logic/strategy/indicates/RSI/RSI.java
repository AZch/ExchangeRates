package com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.RSI;

import com.wcreators.exchangeratesjava.model.Rate;
import com.wcreators.exchangeratesjava.service.process.logic.strategy.indicates.SMMA.SMMA;
import com.wcreators.exchangeratesjava.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static java.lang.Math.abs;

@Service
@RequiredArgsConstructor
@Scope("prototype")
public class RSI {

    private final DateUtils dateUtils;
    private final List<Elem> elems = new LinkedList<>();
    private Elem current;
    private final SMMA smmaU;
    private final SMMA smmaD;
    private final double e = 0.0000001;

    public int getElemsSize() {
        return elems.size();
    }

    private final Function<Rate, Elem> elemFromRate = rate -> Elem.builder()
//            .point()
            .build();

    public void addRate(Rate rate) {
        if (elems.size() == 0 || abs(rate.getSell() - elems.get(elems.size() - 1).getPoint()) < e) {
            smmaU.addRate(Rate.builder().sell(0D).createdDate(rate.getCreatedDate()).build());
            smmaD.addRate(Rate.builder().sell(0D).createdDate(rate.getCreatedDate()).build());
        } else {
            if (rate.getSell() > elems.get(elems.size() - 1).getPoint()) {
                smmaU.addRate(Rate.builder().sell(rate.getSell() - elems.get(elems.size() - 1).getPoint()).createdDate(rate.getCreatedDate()).build());
                smmaD.addRate(Rate.builder().sell(0D).createdDate(rate.getCreatedDate()).build());
            } else if (rate.getSell() < elems.get(elems.size() - 1).getPoint()) {
                smmaU.addRate(Rate.builder().sell(0D).createdDate(rate.getCreatedDate()).build());
                smmaD.addRate(Rate.builder().sell(elems.get(elems.size() - 1).getPoint() - rate.getSell()).createdDate(rate.getCreatedDate()).build());
            }
        }

        if (current == null) {
            current = elemFromRate.apply(rate);
            return;
        }

        if (dateUtils.getMinutes(current.getTime()) == dateUtils.getMinutes(rate.getCreatedDate())) {
            current.addPrice(rate.getSell(), rate.getCreatedDate(), smmaD, smmaU, e, elems);
        } else {
            elems.add(current);
            current = elemFromRate.apply(rate);
        }
    }
}
