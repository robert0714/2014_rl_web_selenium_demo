package func.rl00001;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils; 
import org.openqa.selenium.By; 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.study.selenium.AbstractSeleniumV2TestCase;
import org.study.selenium.SRISWebUtils; 
 
import func.rl.common.WebUtils;
import func.rl.common.internal.GrowlMsg;
import func.rl00001._rl01210.Rl01210PageV3;
import func.rl00001._rl01220.Rl01220PageV3;

/**
 * The Class Rl00001PageV2.
 */
public class Rl00001PageV3 extends LoadableComponent<Rl00001PageV3>{
    
    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /** The driver. */
    private WebDriver driver;
    
    /** The wait. */
    private WebDriverWait wait;
    
    private final Actions oAction ;
    
    /** The rl00001 url. */
    private final String rl00001Url = "rl00001/rl00001.xhtml";
    
    private String loadPage; 
    
    @FindBy(how = How.XPATH, using = "//a[contains(text(),'出生登記')]")
    private WebElement rl01210Click;
    
    
    @FindBy(how = How.XPATH, using = "//a[contains(text(),'死亡（死亡宣告）登記')]" )
    private WebElement rl01220Click;
    
    /**
     * Instantiates a new rl00001 page v3.
     *
     * @param driver the driver
     */
    public Rl00001PageV3(final WebDriver driver)  {
        super();
        this.driver = driver;        
        this.wait = new WebDriverWait(driver, 60);
        this.oAction = new Actions(this.driver);
        
        PageFactory.initElements(driver, this);
    }
    
    
    /**
     * Display tx id.
     *
     * @return the string
     */
    public String displayTxId(){
        final String regExpr ="transactionId = ([\\w]*), ";
       
        if(this.driver.getCurrentUrl().contains( rl00001Url)){
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
        WebUtils.acceptAlertAndGetItsText(this.driver);
        
        final String currentUrl =  enterRl00001Action();
        if(StringUtils.isBlank(currentUrl)){
            final String url ="/rl/faces/pages/func/rl00001/rl00001.xhtml";            
            this.loadPage = AbstractSeleniumV2TestCase.open(url);        
            WebUtils.acceptAlertAndGetItsText(this.driver);
        }else{
            this.loadPage = currentUrl;
        }
        
        logger.info("open url: {}",this.driver .getCurrentUrl());
    }

    /**
     * 進入現戶簿頁左邊選單動作
     * ***/
    public String enterRl00001Action() { 
        try {
            final WebElement mainMeunElement = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='navmenu-v']/li"))); 

            logger.info("進入左邊選單: 登記作業");
            // 進入登記作業,
            
            oAction.moveToElement(mainMeunElement);
            
            oAction.click(mainMeunElement).build().perform();
            

            final String rl00001Xpath = "//a[contains(@href, '/rl/faces/pages/func/rl00001/rl00001.xhtml')]";

            final WebElement rl00001ClickLink = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(rl00001Xpath)));
   
            oAction.moveToElement(rl00001ClickLink);        
            
            logger.info("進入左邊選單: 點選現戶簿頁");

            oAction.click(rl00001ClickLink).build().perform();

            WebUtils.acceptAlertAndGetItsText(this.driver);
            
         
        } catch (Exception e) { 
            logger.error(e.getMessage(), e);
            return null;
        }
        String currentUrl = this.driver.getCurrentUrl();

        this.logger.debug(currentUrl);
        return currentUrl;
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
     * Click rl01210.
     * 點選 出生登記 Rl01210
     * @return true, if successful
     * @throws InterruptedException the interrupted exception
     */
    public Rl01210PageV3 clickRl01210() throws InterruptedException {
        logger.info("點選 出生登記 Rl01210" );
        
        final GrowlMsg result = WebUtils.clickBtn(this.driver, this.rl01210Click);
        WebUtils.pageLoadTimeout(this.driver);
        
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebUtils.acceptAlertAndGetItsText(this.driver);;
        if (result.isGiveUpOperation()) {
            return null;
        } else {
            return PageFactory.initElements(this.driver, Rl01210PageV3.class); 
        }
    }

    /**
     * Click rl01220.
     * 點選 死亡（死亡宣告）登記 Rl01220
     * @return true, if successful
     * @throws InterruptedException the interrupted exception
     */
    public Rl01220PageV3 clickRl01220() throws InterruptedException {
        logger.info("點選 死亡（死亡宣告）登記 Rl01220" );
        
        final GrowlMsg result = WebUtils.clickBtn(this.driver, this.rl01220Click);
        WebUtils.pageLoadTimeout(this.driver);
        
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebUtils.acceptAlertAndGetItsText(this.driver);
        
        if (result.isGiveUpOperation()) {
            return null;
        } else {
            return PageFactory.initElements(this.driver, Rl01220PageV3.class); 
        }
    }    
}
