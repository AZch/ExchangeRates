package com.wcreators.exchangeratesjava.service.process.logic.curve;

import com.wcreators.exchangeratesjava.model.MACurvePoint;
import com.wcreators.exchangeratesjava.model.Rate;
import com.wcreators.exchangeratesjava.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class MACurveMinuteBuilder implements CurveBuilder<MACurvePoint> {

    private final DateUtils dateUtils;
    private final Map<String, List<MACurvePoint>> curves = Collections.emptyMap();

    @Override
    public Map<String, List<MACurvePoint>> build(List<Rate> rates) {
        return build(curves, rates);
    }

    @Override
    public MACurvePoint createFromRate(Rate rate) {
        return MACurvePoint.builder()
                .sum(rate.getSell())
                .count(1)
                .time(rate.getCreatedDate())
                .build();
    }

    @Override
    public MACurvePoint updateFromRate(MACurvePoint lastPoint, Rate rate) {
        return MACurvePoint.builder()
                .sum(lastPoint.getSum() + rate.getSell())
                .count(lastPoint.getCount() + 1)
                .time(rate.getCreatedDate())
                .build();
    }

    @Override
    public boolean isUpdateCurvePoint(MACurvePoint lastPoint, Rate rate) {
        return dateUtils.getMinutes(lastPoint.getTime()) == dateUtils.getMinutes(rate.getCreatedDate());
    }
}
