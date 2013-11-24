package func.rl.common;


import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

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
	    final String tabSelected01Xpath = "//a[contains(text(),'戶籍登記作業')]";
	    final String tabSelected02Xpath = "//input[@id='更正變更登記categoryRadio']";
	    if(selenium.isElementPresent(tabSelected01Xpath)){
		selenium.click(tabSelected01Xpath);
		selenium.waitForPageToLoad("300000");
	    }
	   logger.debug("tabSelected02.isVisible()<>: "+selenium.isVisible(tabSelected02Xpath));
	    if(selenium.isElementPresent(tabSelected02Xpath)){
		selenium.click(tabSelected02Xpath);
		selenium.waitForPageToLoad("300000");
	    }	   
	}
    }

    public void clickRl1722B()throws InterruptedException{
	   switchTab(); 
	    
	   final String rl0172BXpath = "//a[contains(text(),'姓名變更／冠姓／從姓登記')]";
	   logger.debug("rl172Bclick.isVisible()<HouseholdMaintainPage>: "+selenium.isVisible(rl0172BXpath));
	    
	    if(selenium.isElementPresent(rl0172BXpath)){
//		WebElement rl172Bclick = driver.findElement(By.xpath(rl0172BXpath));
//		logger.debug("rl172Bclick.isDisplayed(): " + rl172Bclick.isDisplayed());
//		logger.debug("rl172Bclick.isEnabled(): " + rl172Bclick.isEnabled());
		
		selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
//		rl172Bclick.click();
		selenium.click(rl0172BXpath);
		selenium.waitForPageToLoad("300000");
	    }
	
    }
    
}
