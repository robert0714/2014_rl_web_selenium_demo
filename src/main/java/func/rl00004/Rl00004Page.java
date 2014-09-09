package func.rl00004;

import org.apache.commons.lang3.StringUtils; 
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;

import func.rl.common.WebUtils;

public class Rl00004Page {
	private static final Logger logger = LoggerFactory.getLogger(Rl00004Page.class);
    private WebDriver driver;
    private Selenium selenium;
    private String personId;
    private String siteId;
    public String getPersonId() {
   	if (StringUtils.isBlank(personId)) {
//   	    personId = "F208368696";
   	    personId = "C220748971";
   	}
   	return personId;
       }

       public void setPersonId(String personId) {
   	this.personId = personId;
       }

       public String getSiteId() {
   	if (StringUtils.isBlank(siteId)) {
   	    siteId = "65000120";
//   	    siteId = "10010070";
   	}
   	return siteId;
       }

       public void setSiteId(String siteId) {
   	this.siteId = siteId;
       }
    
    public Rl00004Page(final Selenium selenium, final WebDriver driver)throws  UnhandledAlertException,SeleniumException  {
	super();
	this.selenium = selenium;
	this.driver = driver;
    }
    
    private final String rl02d00PartialUlr ="_rl02d00/rl02d00.xhtml";
    
    public void typingApplication() throws UnhandledAlertException, SeleniumException, InterruptedException {
	outer: while (StringUtils.contains(driver.getCurrentUrl(), "rl00004/rl00004.xhtml")) {
	    selenium.focus("//td[@id='currentPersonIdTD']/span/input");
	    selenium.type("//td[@id='currentPersonIdTD']/span/input", getPersonId());
	    WebElement selectorElement = driver.findElement(By.xpath("//input[contains(@id,'inputValue')]"));
	    selenium.focus("//input[contains(@id,'inputValue')]");
	    // builder.keyDown(Keys.CONTROL).click(selectorElement).keyUp(Keys.CONTROL);
	    selenium.waitForPageToLoad("30000");
	    if (!StringUtils.contains(driver.getCurrentUrl(), "rl00004/rl00004.xhtml")) {
		 break outer;
	    }
	    selectorElement.clear();
	    selectorElement.sendKeys(getSiteId());
	    selenium.waitForPageToLoad("360000");
	    if (!StringUtils.contains(driver.getCurrentUrl(), "rl00004/rl00004.xhtml")) {
		 break outer;
	    }
	    selenium.click("//input[@id='applicantSameTxnPerson']");
	    selenium.waitForPageToLoad("30000");

	    // selenium.type("//td[@id='applicant1PersonIdTD']/span/input", getPersonId());
	    selenium.waitForPageToLoad("30000");
	    final String searchBtnXpath = "//a[contains(text(),'結(離)婚證明書')]";
	  //a[contains(text(),'結(離)婚證明書')]
	    if (selenium.isElementPresent(searchBtnXpath) && selenium.isVisible(searchBtnXpath)
		    && StringUtils.contains(driver.getCurrentUrl(), "rl00004/rl00004.xhtml")) {
		if (selenium.isElementPresent("//*[contains(@id,'alert_flag')]")) {
			selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
		    }
		boolean giveUpOperation = WebUtils.handleClickBtn(selenium, searchBtnXpath);
		selenium.waitForPageToLoad("30000");
		if (giveUpOperation) {
		    if (selenium.isElementPresent("//*[contains(@id,'alert_flag')]")) {
			selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
		    }
		    selenium.refresh();
		    throw new InterruptedException();
		}
		// selenium.click(searchBtnXpath);
		int count = 0;
		// 由於點擊等待回應真的很花時間
		inner: while (StringUtils.contains(driver.getCurrentUrl(), "rl00004/rl00004.xhtml")) {
		    Thread.sleep(2000);// 等待2秒
		    logger.debug(driver.getCurrentUrl());
		    if (StringUtils.contains(driver.getCurrentUrl(), rl02d00PartialUlr)) {
			break outer;
		    }
		    count++;
		    if (count > 2) {
			break inner;
		    }
		}

	    } else if (!StringUtils.contains(driver.getCurrentUrl(), "rl00004/rl00004.xhtml")) {
		break outer;
	    }
	}
	
    }
}
