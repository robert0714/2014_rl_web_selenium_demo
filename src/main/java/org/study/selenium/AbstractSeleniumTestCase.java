/*
 * Copyright (c) 2010-2020 IISI.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of IISI.
 */
package org.study.selenium;

import com.thoughtworks.selenium.Selenium;

import func.rl.common.WebUtils; 

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils; 
import org.junit.AfterClass;
import org.junit.BeforeClass; 
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public abstract class AbstractSeleniumTestCase {
	private static final Logger logger = LoggerFactory.getLogger(AbstractSeleniumTestCase.class);

    protected static Selenium selenium;
    public static WebDriver driver;
    
    @BeforeClass
    public static void beforeClass() throws Exception {
        driver  = WebUtils.windowsMachine();
//        driver  = WebUtils.localMachine();
        selenium = SeleniumTestHelper.initWebDriver(driver);
    }

    @AfterClass
    public static void destroy() {
        
        SeleniumTestHelper.destroy(selenium);
    }
    public String getMainUrl(final String src) {
        final String expr = "([a-z][a-z0-9+\\-.]*:(//[^/?#]+)?)";
        final Collection<String> intData = WebUtils.extract(expr, src);
        return (String) CollectionUtils.get(intData, 0);
    }

}
