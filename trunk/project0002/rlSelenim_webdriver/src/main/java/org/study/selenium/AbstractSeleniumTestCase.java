/*
 * Copyright (c) 2010-2020 IISI.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of IISI.
 */
package org.study.selenium;

import com.thoughtworks.selenium.DefaultSelenium;
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
 *這是設計給向下相容(可以使用selenium 1 的擇衷作法 ,
 *selenium 2.43.1己經標記deprecated 表示selenium 3 會移除它)
 */
public abstract class AbstractSeleniumTestCase {
    
    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(AbstractSeleniumTestCase.class);

    /** The selenium. */
    protected static Selenium selenium;
    
    /** The driver. */
    public static WebDriver driver;

    /**
     * Before class.
     *
     * @throws Exception the exception
     */
    @BeforeClass
    public static void beforeClass() throws Exception {
        driver = WebUtils.windowsMachine();
        //        driver  = WebUtils.localMachine();
        selenium = SeleniumTestHelper.initWebDriver(driver);
    }

    /**
     * Destroy.
     */
    @AfterClass
    public static void destroy() {

        SeleniumTestHelper.destroy((DefaultSelenium) selenium);
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
