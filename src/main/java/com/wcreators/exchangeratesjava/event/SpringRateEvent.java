package com.wcreators.exchangeratesjava.event;

import com.wcreators.exchangeratesjava.constant.Resource;
import com.wcreators.exchangeratesjava.model.Rate;
import lombok.Builder;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Builder
@Getter
public class SpringRateEvent {
    private final List<Rate> rate;
    private final Resource resource;
}
