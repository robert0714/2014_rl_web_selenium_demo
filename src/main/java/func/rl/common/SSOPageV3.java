/*
 * Copyright (c) 2010-2020 IISI.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of IISI.
 */
package func.rl.common;

import com.thoughtworks.selenium.SeleniumException;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.study.selenium.AbstractSeleniumV2TestCase;

/**
 *
 */
public class SSOPageV3 extends LoadableComponent<SSOPageV3>{ 
    private WebDriver driver;
    private  final Logger logger = LoggerFactory.getLogger(getClass());
    private String loadPage; 
    private String loadPage2; 
    public SSOPageV3(final WebDriver driver)   {
        super();
        this.driver = driver;
        load();
        PageFactory.initElements(driver, this);
    } 
    @FindBy(how = How.XPATH, using = "//input[@name='Ecom_User_ID']" )
    WebElement usernameInput;
    
    @FindBy(how = How.XPATH, using = "//input[@name='Ecom_Password']")
    WebElement passwordInput;
    
    @FindBy(how = How.XPATH, using = "//input[@name='loginButton2']")
    WebElement logonSubmit;
    
    public void login(final WebDriver driver , final String user, final String passwd) {
        usernameInput.sendKeys(user);
        passwordInput.sendKeys(passwd);
        logonSubmit.click();
        AbstractSeleniumV2TestCase.open("/rl/");
    }
    
    @Override
    protected void load() {
      //得到https://idpfl.ris.gov.tw:8443
    	final String mainUrl = getMainUrl(this.driver.getCurrentUrl());
        //https://idpfl.ris.gov.tw:8443/nidp/idff/sso?id=1&sid=1&option=credential&sid=1
        loadPage = mainUrl + "/nidp/idff/sso?id=1&sid=1&option=credential";
         this.driver.get(loadPage);
         
        //得到https://idpfl.ris.gov.tw:8443
           
//      this.driver.get("https://idpfl.ris.gov.tw:8443/nidp/idff/sso?id=1&sid=1&option=credential&sid=1");
       
    }

    @Override
    protected void isLoaded() throws Error {
    	final String currentUrl = this.driver.getCurrentUrl();
    	boolean  jugement =  !currentUrl.equals(this.loadPage)   ; 
        if(jugement){
            throw new Error(String.format("The wrong page has loaded: ", this.driver.getCurrentUrl()));
        } 
    }
    private String getMainUrl(final String src) {
        final String expr = "([a-z][a-z0-9+\\-.]*:(//[^/?#]+)?)";
        Collection<String> intData = WebUtils.extract(expr, src);
        return (String) CollectionUtils.get(intData, 0);
    }
}
