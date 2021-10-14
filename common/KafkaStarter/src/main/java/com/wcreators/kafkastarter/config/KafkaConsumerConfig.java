package com.wcreators.kafkastarter.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KafkaConsumerConfig {
    private String autoOffsetReset;
    private String groupId;
    private String clientId;
}
