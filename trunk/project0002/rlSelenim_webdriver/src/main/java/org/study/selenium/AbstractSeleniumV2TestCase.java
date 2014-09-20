/*
 * Copyright (c) 2010-2020 IISI.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of IISI.
 */
package org.study.selenium;


import func.rl.common.WebUtils;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 這是設計給Selenium 2.43.1以後的版本使用 據說Selenium 3 會完全使用Wb driver
 */
public class AbstractSeleniumV2TestCase {
    
    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSeleniumV2TestCase.class);

    
    /** The driver. */
    public static RisRemoteWebDriver driver;

    /**
     * Before class.
     *
     * @throws Exception the exception
     */
    @BeforeClass
    public static void beforeClass() throws Exception {
//        final   WebDriver initDriver = WebUtils.windowsMachine();
      final   WebDriver initDriver = WebUtils.localMachine();
      driver =  SeleniumTestHelper.initWebDriverV2( initDriver);
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
     * Gets the main url.
     *
     * @param src the src
     * @return the main url
     */
    public String getMainUrl(final String src) {
        final String expr = "([a-z][a-z0-9+\\-.]*:(//[^/?#]+)?)";
        final Collection<String> intData = WebUtils.extract(expr, src);
        return (String) CollectionUtils.get(intData, 0);
    }
}
