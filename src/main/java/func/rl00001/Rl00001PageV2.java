package func.rl00001;

import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.study.selenium.SRISWebUtils;
import com.thoughtworks.selenium.SeleniumException;

import func.rl.common.WebUtils;
import func.rl.common.internal.GrowlMsg;


public class Rl00001PageV2 {
    private  final Logger logger = LoggerFactory.getLogger(getClass());
    private WebDriver driver;
    private String personId;
    private String siteId;
    private final String rl00001Url ="rl00001/rl00001.xhtml";
    private static final String closeBeforeUnloadAlert ="document.getElementsByName('ae_l_leaveCheck')[0].value = null;"; 
    private static final String alertFlagXpath ="//*[contains(@id,'alert_flag')]";
    public Rl00001PageV2(final WebDriver driver) throws UnhandledAlertException,
            SeleniumException {
        super();
        this.driver = driver;
    }
    public void typeTxnPerson(final String personId,final String siteId){
        //輸入當事人統號 
        this.driver.findElement(By.xpath("//input[contains(@id,'txnPersonId')]")).sendKeys(personId);
        //輸入當事人作業點
        SRISWebUtils.typeAutoComplete(this.driver, "//td[contains(@id,'currentPersonSiteIdTD')]" , siteId) ;
    }
    public void typeApplicat1(final String personId,final String siteId,final String relationship){
        //applicant1ApplyRelationshipTD
        //輸入申請人1統號
        this.driver.findElement(By.xpath("//input[contains(@id,'applicant1PersonId')]")).sendKeys(personId); 
        //輸入申請人1作業點 
        SRISWebUtils.typeAutoComplete(this.driver, "//td[contains(@id,'applicant1SiteIdTD')]" , siteId) ;
                
        this.driver.findElement(By.xpath("//td[contains(@id,'applicant1ApplyRelationshipTD')]/span/input")).sendKeys(relationship);
    }
    public void typeApplicat2(final String personId,final String siteId,final String relationship){
        //輸入申請人2統號 
        this.driver.findElement(By.xpath("//input[contains(@id,'applicant2PersonId')]")).sendKeys(personId); 
        //輸入申請人2作業點
        SRISWebUtils.typeAutoComplete(this.driver, "//td[contains(@id,'applicant2SiteIdTD')]", siteId);
        
        this.driver.findElement(By.xpath("//td[contains(@id,'applicant2ApplyRelationshipTD')]/span/input")).sendKeys(relationship); 
    }
    public void typeDelegatedPerson(final String personId,final String siteId ){
        //輸入委託人統號 
        this.driver.findElement(By.xpath("//input[contains(@id,'delegatedPersonId')]")).sendKeys(personId); 
        
        //輸入委託人作業點 
        SRISWebUtils.typeAutoComplete(this.driver, "//td[contains(@id,'delegatedSiteTd')]", siteId);
        
         
    }
    
    public void redirectPage(){
        final WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(alertFlagXpath)));
        ((RemoteWebDriver) driver).executeScript(closeBeforeUnloadAlert, "");
        driver.navigate().refresh();         
    }
    public boolean clickRl1210()throws InterruptedException{
        final String rl01210Xpath = "//a[contains(text(),'出生登記')]";
        this.logger.debug("rl01210click.isVisible()<HouseholdMaintainPage>: " );
//        this.driver.findElement(By.xpath(rl01210Xpath)).click(); 
       final GrowlMsg result = WebUtils.clickBtn(this.driver, rl01210Xpath);
       if(result.isGiveUpOperation()){
	   return false;
       }else{
	   return true ;
       }
     }
    public void clickRl1220()throws InterruptedException{
        final String rl01220Xpath = "//a[contains(text(),'死亡（死亡宣告）登記')]";
        this.logger.debug("rl01220click.isVisible()<HouseholdMaintainPage>: " );
         
        final WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(rl01220Xpath)));
        ((RemoteWebDriver) driver).executeScript(closeBeforeUnloadAlert, "");
        
        this.driver.findElement(By.xpath(rl01220Xpath)).click();      
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
