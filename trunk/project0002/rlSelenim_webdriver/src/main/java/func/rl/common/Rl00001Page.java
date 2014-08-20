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

public class Rl00001Page {
    protected final Logger logger = Logger.getLogger(getClass());
    private WebDriver driver;
    private String personId;
    private String siteId;
    private Selenium selenium;

    public Rl00001Page(final Selenium selenium, final WebDriver driver) throws UnhandledAlertException,
            SeleniumException {
        super();
        this.selenium = selenium;
        this.driver = driver;
    }

    public void typingApplication() throws UnhandledAlertException, SeleniumException, InterruptedException {
        //	String siteLocation = String.format("label=%s：%s", getSiteId(), siteIdMap.get(getSiteId()));

        outer: while (StringUtils.contains(driver.getCurrentUrl(), "rl00001/rl00001.xhtml")) {
            //*[@id="txnPersonId"]
            //輸入統號
            this.selenium.type("//input[contains(@id,'txnPersonId')]", getPersonId());
            
            if (!StringUtils.contains(this.driver.getCurrentUrl(), "rl00001/rl00001.xhtml")) {
                break outer;
            }
//            selenium.type("//td[@id='currentPersonIdTD']/span/input", getPersonId());
          //*[@id="j_id_48:j_id_4a"]
          //*[@id="currentPersonSiteIdTD"]
            
            this.selenium.focus("//td[@id='currentPersonSiteIdTD']");
           
            this.selenium.type("//td[@id='currentPersonSiteIdTD']/span/input", "");
            this.selenium.click("//td[@id='currentPersonSiteIdTD']/span/input");
            this.selenium.click("//td[contains(@id,'currentPersonSiteIdTD')]/span/input");
            this.selenium.click("//span/div/ul/li");
         
            // builder.keyDown(Keys.CONTROL).click(selectorElement).keyUp(Keys.CONTROL);
            this.selenium.waitForPageToLoad("30000");
            if (!StringUtils.contains(this.driver.getCurrentUrl(), "rl00001/rl00001.xhtml")) {
                break outer;
            }
            this.selenium.click("//td[@id='currentPersonSiteIdTD']");
            final WebElement selectorElement = this.driver.findElement(By.xpath("//td[@id='currentPersonSiteIdTD']"));
            selectorElement.clear();
            selectorElement.sendKeys(getSiteId());
            this.selenium.waitForPageToLoad("360000");
            if (!StringUtils.contains(this.driver.getCurrentUrl(), "rl00001/rl00001.xhtml")) {
                break outer;
            }
            this.selenium.click("//input[@id='applicantSameTxnPerson']");
            this.selenium.waitForPageToLoad("30000");

            //	    selenium.type("//td[@id='applicant1PersonIdTD']/span/input", getPersonId());
            this.selenium.waitForPageToLoad("30000");
            final String searchBtnXpath = "//div/div/button";

            if (selenium.isElementPresent(searchBtnXpath) && this.selenium.isVisible(searchBtnXpath)
                    && StringUtils.contains(this.driver.getCurrentUrl(), "rl00001/rl00001.xhtml")) {
                boolean giveUpOperation = WebUtils.handleClickBtn(this.selenium, searchBtnXpath);
                if (giveUpOperation) {
                    if (this.selenium.isElementPresent("//*[contains(@id,'alert_flag')]")) {
                        this.selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
                    }
                    this.selenium.refresh();
                    //		    throw new  InterruptedException();
                }
                //		selenium.click(searchBtnXpath);
                int count = 0;
                //由於點擊等待回應真的很花時間
                inner: while (StringUtils.contains(driver.getCurrentUrl(), "rl00001/rl00001.xhtml")) {
                    Thread.sleep(5000);//等待5秒
                    this.logger.debug(this.driver.getCurrentUrl());
                    if (StringUtils.contains(driver.getCurrentUrl(), "/rl00001/householdMaintain.xhtml")) {
                        break outer;
                    }
                    count++;
                    if (count > 2) {
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
