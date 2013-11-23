package org.robert.study.rl.common;


import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.thoughtworks.selenium.Selenium;

public class HouseholdMaintainPage {
    protected final  Logger logger = Logger.getLogger(getClass());
    private WebDriver driver;
    private Selenium selenium;

    public HouseholdMaintainPage(final Selenium selenium, final WebDriver driver) {
	super();
	this.selenium = selenium;
	this.driver = driver;
    }

    public void switchTab() throws InterruptedException {
	final String currentUrl = driver.getCurrentUrl();
	if (StringUtils.contains(currentUrl, "/rl00001/householdMaintain.xhtml")) {
	    
	    if(selenium.isElementPresent("//a[contains(text(),'戶籍登記作業')]")){
		WebElement tabSelected01 = driver.findElement(By.xpath("//a[contains(text(),'戶籍登記作業')]"));
		tabSelected01.click();
		selenium.waitForPageToLoad("300000");
	    }
//	    Thread.sleep(6000l);
	    
	   logger.debug("tabSelected02.isVisible()<>: "+selenium.isVisible("//input[@id='更正變更登記categoryRadio']"));
	    if(selenium.isElementPresent("//input[@id='更正變更登記categoryRadio']")){
		WebElement tabSelected02 = driver.findElement(By.xpath("//input[@id='更正變更登記categoryRadio']"));
		
		tabSelected02.click();
		selenium.waitForPageToLoad("300000");
	    }
	   
	}
    }
    /** 登記頁面模板. */
    private static final String OPERATION_PAGE_TEMPLATE = "/pages/func/rl00001/%s?%s";
    private final String default0172BURL = "_rl0172b/rl0172b.xhtml";
    private final String default02100URL = "_rl02100/rl02100.xhtml";
    public void clickRl1722B(){
	    if(selenium.isElementPresent("//a[contains(text(),'戶籍登記作業')]")){
		WebElement tabSelected01 = driver.findElement(By.xpath("//a[contains(text(),'戶籍登記作業')]"));
		tabSelected01.click();
		selenium.waitForPageToLoad("300000");
	    }
	   
//	    Thread.sleep(6000l);
	    
	   logger.debug("tabSelected02.isVisible()<>: "+selenium.isVisible("//input[@id='更正變更登記categoryRadio']"));
	    if(selenium.isElementPresent("//input[@id='更正變更登記categoryRadio']")){
		WebElement tabSelected02 = driver.findElement(By.xpath("//input[@id='更正變更登記categoryRadio']"));
		
		tabSelected02.click();
		selenium.waitForPageToLoad("300000");
	    }
	    
	while (true) {
	   logger.debug("rl172Bclick.isVisible()<HouseholdMaintainPage>: "+selenium.isVisible("//a[contains(text(),'姓名變更／冠姓／從姓登記')]"));
	    
	    if(selenium.isElementPresent("//a[contains(text(),'姓名變更／冠姓／從姓登記')]")){
		WebElement rl172Bclick = driver.findElement(By.xpath("//a[contains(text(),'姓名變更／冠姓／從姓登記')]"));
		logger.debug("rl172Bclick.isDisplayed(): " + rl172Bclick.isDisplayed());
		logger.debug("rl172Bclick.isEnabled(): " + rl172Bclick.isEnabled());
		selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
		rl172Bclick.click();
		selenium.waitForPageToLoad("300000");
	    }
	    final  String targetUrl = driver.getCurrentUrl();
	    if (StringUtils.contains(targetUrl, "_rl0172b/rl0172b.xhtml")) {
		break;
	    }
	}
    }
    
}
