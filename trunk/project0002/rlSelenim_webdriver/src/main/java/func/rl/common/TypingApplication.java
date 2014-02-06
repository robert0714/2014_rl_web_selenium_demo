package func.rl.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;

public class TypingApplication {
    protected final  Logger logger = Logger.getLogger(getClass());
    private WebDriver driver;
    private String personId;
    private String siteId;
    private Selenium selenium;

    public TypingApplication(final Selenium selenium, final WebDriver driver) throws UnhandledAlertException, SeleniumException {
	super();
	this.selenium = selenium;
	this.driver = driver;
    }

//    private Map<String, String> getSiteIdMap() {
//	Properties props = new Properties();
//	try {
//	    props.load(TypingApplication.class.getResourceAsStream("RSCD0107.properties"));
//	} catch (Exception e) {
//	    e.printStackTrace();
//	}
//	final Set<Object> keys = props.keySet();
//	final Map<String, String> siteIdMap = new HashMap<String, String>();
//	for (Object key : keys) {
//	    Object value = props.get(key);
//	    siteIdMap.put(key.toString(), value.toString());
//	}
//	return siteIdMap;
//    }

    public void typingApplication() throws UnhandledAlertException, SeleniumException, InterruptedException {
//	String siteLocation = String.format("label=%s：%s", getSiteId(), siteIdMap.get(getSiteId()));

	
	outer: while ( StringUtils.contains(driver.getCurrentUrl(), "rl00001/rl00001.xhtml")) {
	    
	    selenium.type("//td[@id='currentPersonIdTD']/span/input", getPersonId());
	    if (!StringUtils.contains(driver.getCurrentUrl(), "rl00001/rl00001.xhtml")) {
		break outer;
	    }
	    
	    WebElement selectorElement = driver.findElement(By.xpath("//input[contains(@id,'inputValue')]"));
	    selenium.focus("//input[contains(@id,'inputValue')]");
	    // builder.keyDown(Keys.CONTROL).click(selectorElement).keyUp(Keys.CONTROL);
	    selenium.waitForPageToLoad("30000");
	    if (!StringUtils.contains(driver.getCurrentUrl(), "rl00001/rl00001.xhtml")) {
		break outer;
	    }
	    selectorElement.clear();
	    selectorElement.sendKeys(getSiteId());
	    selenium.waitForPageToLoad("360000");
	    if (!StringUtils.contains(driver.getCurrentUrl(), "rl00001/rl00001.xhtml")) {
		break outer;
	    }
	    selenium.click("//input[@id='applicantSameTxnPerson']");
	    selenium.waitForPageToLoad("30000");

//	    selenium.type("//td[@id='applicant1PersonIdTD']/span/input", getPersonId());
	    selenium.waitForPageToLoad("30000");
	    final String searchBtnXpath = "//div/div/button";
	    
	    if (selenium.isElementPresent(searchBtnXpath) && selenium.isVisible(searchBtnXpath)&& StringUtils.contains(driver.getCurrentUrl(), "rl00001/rl00001.xhtml")) {
		boolean giveUpOperation=WebUtils.handleClickBtn(selenium, searchBtnXpath);
		if(giveUpOperation){
		    if (selenium.isElementPresent("//*[contains(@id,'alert_flag')]")) {
			selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
		    }
		    selenium.refresh();
//		    throw new  InterruptedException();
		}
//		selenium.click(searchBtnXpath);
		int count =0;
		//由於點擊等待回應真的很花時間
		inner : while (StringUtils.contains(driver.getCurrentUrl(), "rl00001/rl00001.xhtml")) {
		    Thread.sleep(5000);//等待5秒
		    logger.debug(driver.getCurrentUrl());
		    if (StringUtils.contains(driver.getCurrentUrl(), "/rl00001/householdMaintain.xhtml")) {
			break outer;
		    }
		    count++;
		    if(count>2){
			break inner;
		    }
		}
		
	    } else if (!StringUtils.contains(driver.getCurrentUrl(), "rl00001/rl00001.xhtml")) {
		break outer;
	    }

	}
    }

    public String getPersonId() {
	if (StringUtils.isBlank(personId)) {
//	    personId = "F208368696";
	    personId = "C268351856";
	}
	return personId;
    }

    public void setPersonId(String personId) {
	this.personId = personId;
    }

    public String getSiteId() {
	if (StringUtils.isBlank(siteId)) {
	    siteId = "65000120";
//	    siteId = "10010070";
	}
	return siteId;
    }

    public void setSiteId(String siteId) {
	this.siteId = siteId;
    }

}
