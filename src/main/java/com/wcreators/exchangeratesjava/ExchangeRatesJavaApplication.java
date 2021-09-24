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
//        SpringApplication.run(ExchangeRatesJavaApplication.class, args);
        List<Integer> data = new LinkedList<>();
        data.add(0);
        data.add(1);
        data.add(2);
        data.add(3);
        data.add(4);
        data.add(5);
        data.add(6);
        data.forEach(System.out::println);
        System.out.println();
        System.out.println(data.size());
        if (data.size() > 6) {
            data.subList(0, 3).clear();
        }
        data.forEach(System.out::println);
    }

}
