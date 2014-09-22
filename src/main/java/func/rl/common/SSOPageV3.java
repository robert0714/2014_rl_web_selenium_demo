/*
 * Copyright (c) 2010-2020 IISI.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of IISI.
 */
package func.rl.common;

import com.thoughtworks.selenium.SeleniumException;

import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
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
    public SSOPageV3(final WebDriver driver) throws UnhandledAlertException, SeleniumException {
        super();
        this.driver = driver;
        
    } 
    @FindBy(how = How.XPATH, using = "//input[@name='Ecom_User_ID']" )
    WebElement usernameInput;
    
    @FindBy(how = How.XPATH, using = "//input[@name='Ecom_Password']")
    WebElement passwordInput;
    
    @FindBy(how = How.XPATH, using = "//input[@name='loginButton2']")
    WebElement logonSubmit;
    @Override
    protected void load() {
      //得到https://idpfl.ris.gov.tw:8443
        final String openAuthorizationUrl =  "/nidp/idff/sso?id=1&sid=1&option=credential&sid=1";
        //https://idpfl.ris.gov.tw:8443/nidp/idff/sso?id=1&sid=1&option=credential&sid=1
        loadPage =  AbstractSeleniumV2TestCase.open(openAuthorizationUrl);
//      this.driver.get("https://idpfl.ris.gov.tw:8443/nidp/idff/sso?id=1&sid=1&option=credential&sid=1");
        
    }

    @Override
    protected void isLoaded() throws Error {
        if(!this.driver.getCurrentUrl().equals(this.loadPage)){
            throw new Error(String.format("The wrong page has loaded: ", this.driver.getCurrentUrl()));
        } 
    }
    
}
