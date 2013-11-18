package org.robert.study.rl.common;

import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;

public class RlHompage {
    WebDriver driver;
    private Selenium selenium;

    public RlHompage(final WebDriver driver) throws  UnhandledAlertException,SeleniumException  {
	super();
	this.driver = driver;
	final String baseUrl = "http://192.168.9.94:6280/rl/";
	selenium = new WebDriverBackedSelenium(driver, baseUrl);
	login();
    }

    public RlHompage(final Selenium selenium ,final WebDriver driver)throws  UnhandledAlertException,SeleniumException  {
	super();
	this.driver = driver;
	this.selenium = selenium;
	login();
    }

    private void login()throws  UnhandledAlertException,SeleniumException  {
	selenium.open("/rl/pages/common/login.jsp");
	selenium.type("name=j_username", "RF1200123");
	selenium.type("name=j_password", "RF1200123");
	selenium.click("css=input[type=\"submit\"]");
	
	enterRl00001();
	
    }

    public TypingApplication typingApplication()throws  UnhandledAlertException,SeleniumException   {
	return new TypingApplication(selenium,driver);
    }
    protected void replacePageTest(){
	enterRl00001();
	selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;"); 
	enterRl00001();
	selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;"); 
	enterRl00001();
	selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;"); 
	enterRl00001();
	selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;"); 
	enterRl00001();
	selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;"); 
	enterRl00001();
	selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;"); 
	enterRl00001();
    }
    /**
     * 進入現戶簿頁
     * ***/
    protected void enterRl00001() {
	selenium.waitForPageToLoad("30000");
	// selenium.click("//ul[@id='navmenu-v']/li/ul/li/a");
	selenium.click("//ul[@id='navmenu-v']/li");// 進入登記作業,
	selenium.waitForPageToLoad("30000");
	selenium.click("//a[contains(@href, '/rl/faces/pages/func/rl00001/rl00001.xhtml')]");// 進入現戶簿頁
//	selenium.open("/rl/faces/pages/func/rl00001/rl00001.xhtml");
	selenium.waitForPageToLoad("30000");
    }

    /**
     * 進入解鎖作業
     * ***/
    protected void enterRl00003() {
	selenium.waitForPageToLoad("30000");
	// selenium.click("//ul[@id='navmenu-v']/li[12]/ul/li[7]/a");
	selenium.click("//ul[@id='navmenu-v']/li[12]");// 進入解鎖作業,
	selenium.waitForPageToLoad("30000");
	selenium.open("/rl/faces/pages/func/rl00003/rl00003.xhtml");
	selenium.waitForPageToLoad("30000");
	////td/button
	selenium.click("//td/button");// 點選按鈕,
	selenium.waitForPageToLoad("30000");
    }
}
