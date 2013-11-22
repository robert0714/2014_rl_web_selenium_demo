package org.robert.study.rl.common;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.thoughtworks.selenium.Selenium;

public class HouseholdMaintainPage {
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
//	    String householdMaintainPartialLink = String.format("/rl/faces/pages/func/rl00001/householdMaintain.xhtml?%s", retireveWindowId(currentUrl));
//	    selenium.open(householdMaintainPartialLink);
	    selenium.waitForPageToLoad("300000");
	    System.out.println("tabSelected01.isVisible(): "+selenium.isVisible("//a[contains(text(),'戶籍登記作業')]"));
	    WebElement tabSelected01 = driver.findElement(By.xpath("//a[contains(text(),'戶籍登記作業')]"));
	    selenium.waitForPageToLoad("300000");
	   
	    System.out.println("tabSelected01.isDisplayed(): "+tabSelected01.isDisplayed());
	    System.out.println("tabSelected01.isEnabled(): "+tabSelected01.isEnabled());
	    tabSelected01.click();
	    Thread.sleep(6000l);
	    selenium.waitForPageToLoad("300000");
	    
	    System.out.println("tabSelected02.isVisible(): "+selenium.isVisible("//input[@id='更正變更登記categoryRadio']"));
	    WebElement tabSelected02 = driver.findElement(By.xpath("//input[@id='更正變更登記categoryRadio']"));
	    selenium.waitForPageToLoad("300000");	   
	    System.out.println("tabSelected02.isDisplayed(): "+tabSelected02.isDisplayed());
	    System.out.println("tabSelected02.isEnabled(): "+tabSelected02.isEnabled());
	    tabSelected02.click();
	}
    }
    /** 登記頁面模板. */
    private static final String OPERATION_PAGE_TEMPLATE = "/pages/func/rl00001/%s?%s";
    private final String default0172BURL = "_rl0172b/rl0172b.xhtml";
    private final String default02100URL = "_rl02100/rl02100.xhtml";
    public void clickRl1722B(final String currentUrl){	
	while (true) {
	    System.out.println("rl172Bclick.isVisible(): "+selenium.isVisible("//a[contains(text(),'姓名變更／冠姓／從姓登記')]"));
	    WebElement rl172Bclick = driver.findElement(By.xpath("//a[contains(text(),'姓名變更／冠姓／從姓登記')]"));
	    System.out.println("rl172Bclick.isDisplayed(): "+rl172Bclick.isDisplayed());
	    System.out.println("rl172Bclick.isEnabled(): "+rl172Bclick.isEnabled());
	    selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
	   
	    rl172Bclick.click();	    
	    selenium.waitForPageToLoad("300000");
	    final  String targetUrl = driver.getCurrentUrl();
	    if (StringUtils.contains(targetUrl, "_rl0172b/rl0172b.xhtml")) {
		break;
	    }
	}
    }
    private String retireveWindowId(final String link){
	if(StringUtils.contains(link, "windowId=") && StringUtils.contains(link, "?")){
	    String[] predata = StringUtils.splitPreserveAllTokens(link, "?");
	    if(predata.length>1){
		return predata[1];
	    }
	}
	return null;
    }
}
