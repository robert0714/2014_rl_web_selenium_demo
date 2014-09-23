package func.rl00001;

import func.rl.common.WebUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils; 
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

 
/**
 * The Class HouseholdMaintainPageV3.
 */
public class HouseholdMaintainPageV3 extends LoadableComponent<HouseholdMaintainPageV3> {
    
    /** The logger. */
    private  final Logger logger = LoggerFactory.getLogger(getClass());
    
    /** The wait. */
    private  WebDriverWait wait  ;
    
    /** The parent. */
    private final LoadableComponent<?> parent;
    
    /** The load page. */
    private String loadPage; 
    
    /** The driver. */
    private WebDriver driver;
    
    /** The HouseholdMaintain partial url. */
    private final String partialURL = "/rl00001/householdMaintain.xhtml";
    

    /** **** 預覽列印btn ****. */
    @FindBy(how = How.XPATH, using = "//div[contains(@id,'householdbtns')]/button" )
    private WebElement printHouseHoldAppBtn;
    
    /** **** 存檔btn ****. */
    @FindBy(how = How.XPATH, using = "//*[@id='saveBtnId']" )
    private WebElement saveBtn;
    
    
    /**
     * Instantiates a new household maintain page v3.
     *
     * @param driver the driver
     * @param parent the parent
     */
    public HouseholdMaintainPageV3(final WebDriver driver, final LoadableComponent<?> parent) {
        super();
        this.driver = driver;
        this.parent = parent;
        this.wait = new WebDriverWait(driver, 60);
        PageFactory.initElements(driver, this);
    }

    /**
     * Switch tab.
     *
     * @return true, if successful
     * @throws InterruptedException the interrupted exception
     */
    public boolean  switchTab() throws InterruptedException {
	final String currentUrl = driver.getCurrentUrl(); 
	boolean sencondSwitch =false;
        if (StringUtils.contains(currentUrl, partialURL)) {
            final String tabSelected01Xpath = "//a[contains(text(),'戶籍登記作業')]";
            final String tabSelected02Xpath = "//input[@id='更正變更登記categoryRadio']";

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(tabSelected01Xpath)));
            this.driver.findElement(By.xpath(tabSelected01Xpath)).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(tabSelected02Xpath)));
            sencondSwitch = true;
        }
	return sencondSwitch;
    }
    /**
     * Display tx id.
     *
     * @return the string
     */
    public String displayTxId(){
        final String regExpr ="transactionId = ([\\w]*), ";
       
        if(this.driver.getCurrentUrl().contains( partialURL)){
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
    
    
    /**
     * ****
     * 列印申請書測試程序
     * ****.
     *
     * @throws InterruptedException the interrupted exception
     */
    public void processPrintView() throws InterruptedException {
        //戶籍大簿列印頁面應該有交易序號,應該可以使用這交易序號進行申請書頁面畫面進行截圖
        final String txId = displayTxId();
        logger.info("列印申請書測試程序: {}", txId); 
        
	SRISWebUtils.newPdfPreview(driver, printHouseHoldAppBtn);
    }
   
    
    /**
     * ****
     * 存檔測試程序
     * ****.
     *
     * @throws InterruptedException the interrupted exception
     */
    public void processAppyCahange() throws InterruptedException {
 
        //*[@id='saveBtnId']
        //div[contains(@id,'saveBtnId')]/button
        
        wait.until(ExpectedConditions.elementToBeClickable(saveBtn));
 
        WebUtils.clickBtn(driver, saveBtn);
        
    }

    /**
     * Load.
     */
    @Override
    protected void load() {
        final String url ="/rl/faces/pages/func/rl00001/householdMaintain.xhtml";       
        loadPage = AbstractSeleniumV2TestCase.open(url);
        WebUtils.acceptAlertAndGetItsText(this.driver);
        logger.info("open url: {}",this.driver .getCurrentUrl());
    }

    /**
     * Checks if is loaded.
     *
     * @throws Error the error
     */
    @Override
    protected void isLoaded() throws Error {
        final String currentUrl = this.driver.getCurrentUrl();
        //由於頁面網址會帶上windowId,會造成誤判       
        if(! StringUtils.contains(currentUrl, this.partialURL)){
            logger.info("current url: {}" , currentUrl);
            throw new Error(String.format("The wrong page has loaded: %s", currentUrl));
        } 
    }
} 
