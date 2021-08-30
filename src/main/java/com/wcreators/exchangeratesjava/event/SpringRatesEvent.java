package com.wcreators.exchangeratesjava.event;

import com.wcreators.exchangeratesjava.constant.Resource;
import com.wcreators.exchangeratesjava.constant.ResourceAction;
import com.wcreators.exchangeratesjava.model.Rate;
import lombok.Builder;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Builder
@Getter
public class SpringRatesEvent {
    private final List<Rate> rates;
    private final Resource resource;
    private final ResourceAction resourceAction;
}
