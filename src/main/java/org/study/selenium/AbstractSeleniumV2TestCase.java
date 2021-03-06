/*
 * Copyright (c) 2010-2020 IISI.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of IISI.
 */
package org.study.selenium;


import func.rl.common.WebUtils;

import java.net.URL; 
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 這是設計給Selenium 2.43.1以後的版本使用 據說Selenium 3 會完全使用Wb driver
 * 只能使用單一的test
 */
public class AbstractSeleniumV2TestCase {
    
    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSeleniumV2TestCase.class);

    /** The base url. */
    private static URL baseUrl;
    
    /** The driver. */
    public static WebDriver driver; 
    
    /**
     * Before class.
     *
     * @throws Exception the exception
     */
    @BeforeClass
    public static void beforeClass() throws Exception {
            driver = WebUtils.getRandomPlatformMachine (); 
//        driver =  WebUtils.localMachine(); 
       final  String url = SeleniumTestHelper.initWebDriverV3(driver);
       baseUrl = new URL(url);
       
    }

    /**
     * Destroy.
     */
    @AfterClass
    public static void destroy() {
        if (driver != null) {
            LOGGER.info("*** Stopping selenium client driver ...");
            driver.close();
            driver.quit();
        }
    }
    /**
     * Open.
     * 相當Selenium 的 open
     *
     * @param url the url
     */
    public static  String open(String url) {        
        final String navigateUrl = String.format("%s://%s:%s%s",baseUrl.getProtocol(), baseUrl.getHost(), baseUrl.getPort(), url);
        LOGGER.debug("navigateUrl: {}",navigateUrl);
        driver.navigate().to(navigateUrl);
        return navigateUrl.intern();
    }

    public boolean isElementPresent(By by) {
	try {
	    driver.findElement(by);
	    return true;
	} catch (NoSuchElementException e) {
	    return false;
	}
    }

    public boolean isAlertPresent() {
	try {
	    driver.switchTo().alert();
	    return true;
	} catch (NoAlertPresentException e) {
	    return false;
	}
    }

   

    public static String acceptAlertAndGetItsText(final WebDriver driver) {
        try {
            final Alert alert = driver.switchTo().alert();
            final String alertText = alert.getText();
            alert.accept();
            return alertText;
        }catch (NoAlertPresentException e) {
            // Modal dialog showed
            return null;
        }catch (UnhandledAlertException e) {
            // Modal dialog showed
            return null;
        }
    }
}
