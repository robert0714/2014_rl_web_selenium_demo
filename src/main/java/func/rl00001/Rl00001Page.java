package func.rl00001;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.study.selenium.SRISWebUtils;

import sun.net.util.URLUtil;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;

import func.rl.common.WebUtils;

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

        outer: while (StringUtils.contains(this.driver.getCurrentUrl(), "rl00001/rl00001.xhtml")) {
            //*[@id="txnPersonId"]
            //輸入統號
            this.selenium.type("//input[contains(@id,'txnPersonId')]", getPersonId());

            if (!StringUtils.contains(this.driver.getCurrentUrl(), "rl00001/rl00001.xhtml")) {
                break outer;
            }

            SRISWebUtils.typeAutoComplete(this.selenium, "//td[contains(@id,'currentPersonSiteIdTD')]", getSiteId());

            this.selenium.waitForPageToLoad("30000");
            if (!StringUtils.contains(this.driver.getCurrentUrl(), "rl00001/rl00001.xhtml")) {
                break outer;
            }

            this.selenium.click("//input[@id='applicantSameTxnPerson']");
            this.selenium.waitForPageToLoad("30000");
            this.selenium.waitForPageToLoad("30000");

            final String searchBtnXpath = "//div/div/button";

            if (this.selenium.isElementPresent(searchBtnXpath) && this.selenium.isVisible(searchBtnXpath)
                    && StringUtils.contains(this.driver.getCurrentUrl(), "rl00001/rl00001.xhtml")) {
                boolean giveUpOperation = WebUtils.handleClickBtn(this.selenium, searchBtnXpath);
                if (giveUpOperation) {
                    if (this.selenium.isElementPresent("//*[contains(@id,'alert_flag')]")) {
                        this.selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
                    }
                    this.selenium.refresh();
                }

                int count = 0;
                //由於點擊等待回應真的很花時間
                inner: while (StringUtils.contains(this.driver.getCurrentUrl(), "rl00001/rl00001.xhtml")) {
                    Thread.sleep(5000);//等待5秒
                    this.logger.debug(this.driver.getCurrentUrl());
                    if (StringUtils.contains(this.driver.getCurrentUrl(), "/rl00001/householdMaintain.xhtml")) {
                        break outer;
                    }
                    count++;
                    if (count > 2) {
                        break inner;
                    }
                }

            } else if (!StringUtils.contains(this.driver.getCurrentUrl(), "rl00001/rl00001.xhtml")) {
                break outer;
            }
        }
        System.out.println("Rl00001Page.typingApplication end: "+this.driver.getCurrentUrl());
        
    }

    public String getPersonId() {

        return this.personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getSiteId() {
        return this.siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

}
