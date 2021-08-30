package com.wcreators.exchangeratesjava.service.supplier.logic.parse.selenium;

import com.wcreators.exchangeratesjava.constant.Resource;
import com.wcreators.exchangeratesjava.model.Rate;
import com.wcreators.exchangeratesjava.service.supplier.logic.parse.ParseRateService;
import com.wcreators.exchangeratesjava.service.supplier.logic.parse.selenium.engine.ParseEngineSeleniumService;
import com.wcreators.exchangeratesjava.util.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfAllElementsLocatedBy;

@Lazy
@RequiredArgsConstructor
@Slf4j
@Service
@Qualifier("parseOandaRateSeleniumService")
public class ParseOandaRateSeleniumService implements ParseRateService {

    private final ParseEngineSeleniumService parseEngine;
    private final DateUtils dateUtils;

    @PostConstruct
    public void init() {
        parseEngine.getDriver().get("https://www1.oanda.com/lang/ru/currency/live-exchange-rates/");
    }

    @Override
    public void reload() {
        parseEngine.reload();
        init();
    }

    @Override
    public List<Rate> parse() throws Exception {
        Date startParsingDate = dateUtils.now();
        log.info("Parsing {} in {}", getResource().getName(), dateUtils.dateToString(startParsingDate));
        // Example of row: "EUR/USD\n1.17369\n1.17355\nUSD/EUR\n0.85212\n0.85201"
        List<Rate> rates = parseEngine.getWait().until(presenceOfAllElementsLocatedBy(By.className("rate_row")))
                .stream()
                .map(WebElement::getText)
                .map(row -> row.split("\n"))
                .map(elems ->
                        Rate.builder()
                                .major(elems[0].split("/")[0])                                      // [EUR]/USD\n1.17369\n1.17355\nUSD/EUR\n0.85212\n0.85201
                                .minor(elems[0].split("/")[1])                                      // EUR/[USD]\n1.17369\n1.17355\nUSD/EUR\n0.85212\n0.85201
                                .sell(Double.parseDouble(elems[1].substring(0, elems[1].length() - 1)))   // EUR/USD\n[1.1736]9\n1.17355\nUSD/EUR\n0.85212\n0.85201
                                .buy(Double.parseDouble(elems[2].substring(0, elems[1].length() - 1)))    // EUR/USD\n1.17369\n[1.1735]5\nUSD/EUR\n0.85212\n0.85201
                                .createdDate(startParsingDate)
                                .build()
                )
                .peek(rate -> log.debug(String.format("Reached %s", rate)))
                .collect(Collectors.toUnmodifiableList());
        log.info("End Parsing {} in {} with {}", getResource().getName(), dateUtils.dateToString(dateUtils.now()), rates.size());
        return rates;
    }

    public Resource getResource() {
        return Resource.OANDA;
    }

}
