/*
 * Copyright (c) 2010-2020 IISI.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of IISI.
 */
package org.study.selenium;

import com.thoughtworks.selenium.webdriven.WebDriverCommandProcessor;

import func.rl000001.common.RL01210Test001V2;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class RisRemoteWebDriver.
 */
public class RisRemoteWebDriver extends WebDriverCommandProcessor  implements  WrapsDriver ,WebDriver ,JavascriptExecutor{
    
    private  final Logger logger = LoggerFactory.getLogger(RisRemoteWebDriver.class);
    
    /** The base url. */
    private  final URL baseUrl;
    
    /** The wrapped driver. */
    private  final  WebDriver wrappedDriver;
    
    /**
     * Instantiates a new ris remote web driver.
     *
     * @param baseUrl the base url
     * @param driver the driver
     * @throws MalformedURLException 
     */
    public RisRemoteWebDriver(final String baseUrl , final  WebDriver driver) throws MalformedURLException {
        super(baseUrl, driver);
        this.wrappedDriver =driver;
        this.baseUrl =new  URL(baseUrl) ;
        
    }

    /**
     * Gets the base url.
     *
     * @return the base url
     */
    public URL getBaseUrl() {
        return this.baseUrl;
    } 

    /**
     * Gets the wrapped driver.
     *
     * @return the wrapped driver
     */
    @Override
    public WebDriver getWrappedDriver() {
        return this.wrappedDriver;
    }

    /**
     * Open.
     * 相當Selenium 的 open
     *
     * @param url the url
     */
    public void open(String url) {
        //        super.doCommand("open", new String[] {url,});
        
        final String navigateUrl = String.format("%s://%s:%s%s",this.baseUrl.getProtocol(), this.baseUrl.getHost(), this.baseUrl.getPort(), url);
        logger.debug("navigateUrl: {}",navigateUrl);
        this.wrappedDriver.navigate().to(navigateUrl);
    }

    /**
     * Gets the.
     *
     * @param url the url
     */
    @Override
    public void get(String url) {
        this.wrappedDriver.get(url);
        
    }

    /**
     * Gets the current url.
     *
     * @return the current url
     */
    @Override
    public String getCurrentUrl() {
        return this.wrappedDriver.getCurrentUrl();
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    @Override
    public String getTitle() {
        return this.wrappedDriver.getTitle();
    }

    /**
     * Find elements.
     *
     * @param by the by
     * @return the list
     */
    @Override
    public List<WebElement> findElements(By by) {
        return this.wrappedDriver.findElements(by);
    }

    /**
     * Find element.
     *
     * @param by the by
     * @return the web element
     */
    @Override
    public WebElement findElement(By by) {
        return this.wrappedDriver.findElement(by);
    }

    /**
     * Gets the page source.
     *
     * @return the page source
     */
    @Override
    public String getPageSource() {
        return this.wrappedDriver.getPageSource();
    }

    /**
     * Close.
     */
    @Override
    public void close() {
        this.wrappedDriver.close();
    }

    /**
     * Quit.
     */
    @Override
    public void quit() {
        this.wrappedDriver.quit();
    }

    /**
     * Gets the window handles.
     *
     * @return the window handles
     */
    @Override
    public Set<String> getWindowHandles() {
        return  this.wrappedDriver.getWindowHandles();
    }

    /**
     * Gets the window handle.
     *
     * @return the window handle
     */
    @Override
    public String getWindowHandle() {
        // TODO Auto-generated method stub
        return this.wrappedDriver.getWindowHandle();
    }

    /**
     * Switch to.
     *
     * @return the target locator
     */
    @Override
    public TargetLocator switchTo() {
        return this.wrappedDriver.switchTo();
    }

    /**
     * Navigate.
     *
     * @return the navigation
     */
    @Override
    public Navigation navigate() {
        return this.wrappedDriver.navigate();
    }

    /**
     * Manage.
     *
     * @return the options
     */
    @Override
    public Options manage() {
        return this.wrappedDriver.manage();
    }

    /**
     * Execute script.
     *
     * @param script the script
     * @param args the args
     * @return the object
     */
    @Override
    public Object executeScript(String script, Object... args) {
        final JavascriptExecutor js =  (JavascriptExecutor)this.wrappedDriver;
        return js.executeScript(script, args);        
    }

    /**
     * Execute async script.
     *
     * @param script the script
     * @param args the args
     * @return the object
     */
    @Override
    public Object executeAsyncScript(String script, Object... args) {        
        final JavascriptExecutor js =  (JavascriptExecutor)this.wrappedDriver;
        return js.executeAsyncScript(script, args);
    }
}
