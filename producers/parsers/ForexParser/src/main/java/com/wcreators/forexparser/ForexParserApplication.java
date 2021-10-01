package com.wcreators.forexparser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(exclude = KafkaAutoConfiguration.class)
public class ForexParserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForexParserApplication.class, args);
    }

}
