package com.wcreators.seleniumenginestarter.engine;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public interface ParseEngineSeleniumService {

    WebDriver getDriver();
    WebDriverWait getWait();
    void reload();
}
