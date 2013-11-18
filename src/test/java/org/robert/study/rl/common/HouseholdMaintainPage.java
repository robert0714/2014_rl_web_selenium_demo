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

    public void switchTab() {
	final String currentUrl = driver.getCurrentUrl();
	// http://192.168.10.18:6280/rl/faces/pages/func/rl00001/householdMaintain.xhtml?windowId=5ae
	System.out.println(currentUrl);
	if (StringUtils.contains(currentUrl, "/rl00001/householdMaintain.xhtml")) {
	    String householdMaintainPartialLink = String.format("/rl/faces/pages/func/rl00001/householdMaintain.xhtml?%s", retireveWindowId(currentUrl));
	    selenium.open(householdMaintainPartialLink);
	    selenium.waitForPageToLoad("300000");
	    Actions builder = new Actions(driver);
	    // driver.findElement(By.xpath("//input[@id='j_id37_j_id_4e:inputValue']")).sendKeys("444");
	    WebElement tabSelected01 = driver.findElement(By.xpath("//a[contains(text(),'戶籍登記作業')]"));
	    builder.keyDown(Keys.CONTROL).click(tabSelected01).keyUp(Keys.CONTROL);	   
	    selenium.waitForPageToLoad("300000");
	    tabSelected01.click();
	    selenium.waitForPageToLoad("300000");
	    WebElement tabSelected02 = driver.findElement(By.xpath("//input[@id='更正變更登記categoryRadio']"));
	    builder.keyDown(Keys.CONTROL).click(tabSelected02).keyUp(Keys.CONTROL);	   
	    selenium.waitForPageToLoad("300000");
	    tabSelected02.click();
	}
    }
    private String retireveWindowId(final String link){
	if(StringUtils.contains(link, "windowId=") && StringUtils.contains(link, "?")){
	    String[] predata = StringUtils.splitPreserveAllTokens(link, "?");
	    if(predata.length>1){
		return predata[0];
	    }
	}
	return null;
    }
}
