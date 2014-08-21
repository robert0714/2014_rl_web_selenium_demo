package func.rl00001;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.study.selenium.SRISWebUtils;
import org.study.selenium.SeleniumConfig;

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
    public void typeTxnPerson(final String personId,final String siteId){
        //輸入當事人統號
        this.selenium.type("//input[contains(@id,'txnPersonId')]", personId);
        
        //輸入當事人作業點
        SRISWebUtils.typeAutoComplete(this.selenium, "//td[contains(@id,'currentPersonSiteIdTD')]", siteId);
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
    }
    public void typeApplicat1(final String personId,final String siteId,final String relationship){
        //applicant1ApplyRelationshipTD
        //輸入申請人1統號
        this.selenium.type("//input[contains(@id,'applicant1PersonId')]", personId);
        //輸入申請人1作業點
        SRISWebUtils.typeAutoComplete(this.selenium, "//td[contains(@id,'applicant1SiteIdTD')]", siteId);
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        this.selenium.type("//td[contains(@id,'applicant1ApplyRelationshipTD')]/span/input", relationship);
    }
    public void typeApplicat2(final String personId,final String siteId,final String relationship){
        //輸入申請人2統號
        this.selenium.type("//input[contains(@id,'applicant2PersonId')]", personId);
        //輸入申請人2作業點
        SRISWebUtils.typeAutoComplete(this.selenium, "//td[contains(@id,'applicant2SiteIdTD')]", siteId);
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        this.selenium.type("//td[contains(@id,'applicant2ApplyRelationshipTD')]/span/input", relationship);
    }
    public void typeDelegatedPerson(final String personId,final String siteId ){
        //輸入委託人統號
        this.selenium.type("//input[contains(@id,'delegatedPersonId')]", personId);
        //輸入委託人作業點
        SRISWebUtils.typeAutoComplete(this.selenium, "//td[contains(@id,'delegatedSiteTd')]", siteId);
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad); 
    }
    
    public void redirectPage(){
        if (this.selenium.isElementPresent("//*[contains(@id,'alert_flag')]")) {
            this.selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
        }
        this.selenium.refresh();
    }
    public void clickRl1210()throws InterruptedException{
        
         
        final String rl01210Xpath = "//a[contains(text(),'出生登記')]";
        this.logger.debug("rl172Bclick.isVisible()<HouseholdMaintainPage>: "+this.selenium.isVisible(rl01210Xpath));
         
        if (this.selenium.isElementPresent(rl01210Xpath)) { 

            this.selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;"); 
            this.selenium.click(rl01210Xpath);
            this.selenium.waitForPageToLoad("300000");
        }
     
     }
    public void clickSearchButton(final String inputCode) throws UnhandledAlertException, SeleniumException, InterruptedException {
        outer: while (StringUtils.contains(this.driver.getCurrentUrl(), "rl00001/rl00001.xhtml")) {
          //*[@id="j_id_8l:j_id_8o"]
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
    }
    public void typingApplication() throws UnhandledAlertException, SeleniumException, InterruptedException {

        outer: while (StringUtils.contains(this.driver.getCurrentUrl(), "rl00001/rl00001.xhtml")) {
            //*[@id="txnPersonId"]
            //輸入當事人統號
            this.selenium.type("//input[contains(@id,'txnPersonId')]", getPersonId());

            if (!StringUtils.contains(this.driver.getCurrentUrl(), "rl00001/rl00001.xhtml")) {
                break outer;
            }
            //輸入當事人
            SRISWebUtils.typeAutoComplete(this.selenium, "//td[contains(@id,'currentPersonSiteIdTD')]", getSiteId());

            this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
            if (!StringUtils.contains(this.driver.getCurrentUrl(), "rl00001/rl00001.xhtml")) {
                break outer;
            }

            //點選當事人同申請人
            this.selenium.click("//input[@id='applicantSameTxnPerson']");
            this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);

            
            //點選查詢按鈕
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