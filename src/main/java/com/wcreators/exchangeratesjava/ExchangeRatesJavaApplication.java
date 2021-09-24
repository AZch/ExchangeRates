package com.wcreators.exchangeratesjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.LinkedList;
import java.util.List;

@EnableScheduling
@EnableAsync
@SpringBootApplication
public class ExchangeRatesJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExchangeRatesJavaApplication.class, args);
    }

}
