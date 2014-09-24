package func.rl00001;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.study.selenium.AbstractSeleniumV2TestCase;
import org.study.selenium.SRISWebUtils;

import com.gargoylesoftware.htmlunit.util.UrlUtils;

import func.rl.common.WebUtils;
import func.rl.common.internal.GrowlMsg;
import func.rl00001._rl01210.Rl01210PageV3;
import func.rl00001._rl01220.Rl01220PageV3;
import func.rl00001._rl02510.Rl02510PageV3;

/**
 * The Class Rl00001PageV2.
 */
public class Rl00004PageV3 extends LoadableComponent<Rl00004PageV3>{
    
    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /** The driver. */
    private WebDriver driver;
    
    /** The rl00004 url. */
    private final String rl00004Url = "rl00004/rl00004.xhtml";
    
    private String loadPage; 
    
    @FindBy(how = How.LINK_TEXT, using = "戶口名簿")
    private WebElement rl02510Click;
    
    @FindBy(how = How.XPATH, using = "//input[@id='applicantSameTxnPerson']")
    public WebElement applicantSameTxnPerso;
    
    /**
     * Instantiates a new rl00001 page v3.
     *
     * @param driver the driver
     */
    public Rl00004PageV3(final WebDriver driver)  {
        super();
        this.driver = driver;
        PageFactory.initElements(driver, this);
        
    }
    
    
    /**
     * Display tx id.
     *
     * @return the string
     */
    public String displayTxId(){
        final String regExpr ="transactionId = ([\\w]*), ";
       
        if(this.driver.getCurrentUrl().contains( rl00004Url)){
            try {
                final  String txIdInfo = this.driver.findElement(By.xpath("//*[@id='masterForm']/span[1]")).getText();
                final Matcher matcher = Pattern.compile(regExpr).matcher(txIdInfo);
                
                logger.info("Information: {}", txIdInfo);
                while(  matcher.find()){
                   return  matcher.group(matcher.groupCount());
                }
              
                return  txIdInfo;
            } catch (Exception e) {
                logger.error (e .getMessage(), e);
            }
        }
        //
      //*[@id="masterForm"]/span[1]/text()
        return  null;
    }
    @Override
    protected void load() {
        final String url ="/rl/faces/pages/func/rl00004/rl00004.xhtml";       
        loadPage = AbstractSeleniumV2TestCase.open(url);
        WebUtils.acceptAlertAndGetItsText(this.driver);
        logger.info("open url: {}",this.driver .getCurrentUrl());
    }

    @Override
    protected void isLoaded() throws Error {
        final String currentUrl = this.driver.getCurrentUrl();
        if (this.loadPage != null) {
            try {
                URL url1 = new URL(StringUtils.substringBefore(currentUrl, "?"));
                URL url2 = new URL(StringUtils.substringBefore(this.loadPage, "?"));
                if (!url1.equals(url2)) {
                    logger.info("current url: {}", currentUrl);
                    throw new Error(String.format("The wrong page has loaded: ", this.driver.getCurrentUrl()));
                }
            } catch (MalformedURLException e) {
                logger.error(e.getMessage() ,e );
            }
        }
        else if (!StringUtils.contains(currentUrl, this.loadPage)) { //由於頁面網址會帶上windowId,會造成誤判      		
            logger.info("current url: {}", currentUrl);
            throw new Error(String.format("The wrong page has loaded: ", this.driver.getCurrentUrl()));
        }
    }
    /**
     * Type txn person.
     *
     * @param personId the person id
     * @param siteId the site id
     */
    public void typeTxnPerson(final String personId, final String siteId) {
        logger.debug("輸入當事人統號: {} ,當事人作業點: {}", personId, siteId);
        //輸入當事人統號 
        this.driver.findElement(By.xpath("//input[contains(@id,'txnPersonId')]")).sendKeys(personId);
        //輸入當事人作業點
        SRISWebUtils.typeAutoComplete(this.driver, "//td[contains(@id,'currentPersonSiteIdTD')]", siteId);
    }

    /**
     * Type applicat1.
     *
     * @param personId the person id
     * @param siteId the site id
     * @param relationship the relationship
     */
    public void typeApplicat1(final String personId, final String siteId, final String relationship) {
        logger.debug("輸入申請人1統號: {} ,作業點: {},與當事人關係: {}", personId, siteId, relationship);
        //applicant1ApplyRelationshipTD
        //輸入申請人1統號
        this.driver.findElement(By.xpath("//input[contains(@id,'applicant1PersonId')]")).sendKeys(personId);
        //輸入申請人1作業點 
        SRISWebUtils.typeAutoComplete(this.driver, "//td[contains(@id,'applicant1SiteIdTD')]", siteId);

        this.driver.findElement(By.xpath("//td[contains(@id,'applicant1ApplyRelationshipTD')]/span/input"))
                .sendKeys(relationship);
    }

    /**
     * Type applicat2.
     *
     * @param personId the person id
     * @param siteId the site id
     * @param relationship the relationship
     */
    public void typeApplicat2(final String personId, final String siteId, final String relationship) {
        logger.debug("輸入申請人2統號: {} ,作業點: {},與當事人關係: {}", personId, siteId, relationship);
        //輸入申請人2統號 
        this.driver.findElement(By.xpath("//input[contains(@id,'applicant2PersonId')]")).sendKeys(personId);
        //輸入申請人2作業點
        SRISWebUtils.typeAutoComplete(this.driver, "//td[contains(@id,'applicant2SiteIdTD')]", siteId);

        this.driver.findElement(By.xpath("//td[contains(@id,'applicant2ApplyRelationshipTD')]/span/input"))
                .sendKeys(relationship);
    }

    /**
     * Checks if is alert present.
     *
     * @return true, if is alert present
     */
    private boolean isAlertPresent() {
        try {
            Alert alert = driver.switchTo().alert();
            this.logger.info(String.format("\n\r %s", alert.getText()));
            alert.accept();
            this.logger.debug("alert was present");
            return true;
        } catch (NoAlertPresentException e) {
            // Modal dialog showed
            return false;
        } catch (UnhandledAlertException e) {
            // Modal dialog showed
            return false;
        }
    }

    /**
     * Type delegated person.
     *
     * @param personId the person id
     * @param siteId the site id
     */
    public void typeDelegatedPerson(final String personId, final String siteId) {
        logger.debug("輸入委託人1統號: {} ,作業點: {},與當事人關係: {}", personId, siteId);
        //輸入委託人統號 
        this.driver.findElement(By.xpath("//input[contains(@id,'delegatedPersonId')]")).sendKeys(personId);

        //輸入委託人作業點 
        SRISWebUtils.typeAutoComplete(this.driver, "//td[contains(@id,'delegatedSiteTd')]", siteId);
    }


    /**
     * Click rl02510.
     * 點選 戶口名簿 Rl02510
     * @return true, if successful
     * @throws InterruptedException the interrupted exception
     */
    public Rl02510PageV3 clickRl02510() throws InterruptedException {
        logger.info("點選 戶口名簿 Rl02510" );
        
        final GrowlMsg result = WebUtils.clickBtn(this.driver, this.rl02510Click);
        WebUtils.pageLoadTimeout(this.driver);
        
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        isAlertPresent();
        if (result.isGiveUpOperation()) {
            return null;
        } else {
            return new Rl02510PageV3(this.driver,this);
        }
    } 

    
}
