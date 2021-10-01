package com.wcreators.objectmodels.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class RateAction {
    private Rate rate;
    private String action;
}
