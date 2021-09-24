package com.wcreators.exchangeratesjava.event;

import com.wcreators.exchangeratesjava.model.Rate;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SpringActionableRateEvent {
    private final Rate rate;
    private final String action;
}
