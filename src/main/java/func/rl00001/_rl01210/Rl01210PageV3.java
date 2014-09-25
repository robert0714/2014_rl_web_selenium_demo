package func.rl00001._rl01210;
 

import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

import func.rl.common.WebUtils;
import func.rl.common.internal.GrowlMsg;

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
import org.study.selenium.SRISWebUtils; 

/**
 * The Class Rl01210Page.
 *  出生登記 頁面
 */
public class Rl01210PageV3 extends LoadableComponent<Rl01210PageV3>{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(Rl01210PageV3.class);

    /** The driver. */
    private final WebDriver driver;
 
    private final Actions oAction ;
    
    /** The wait. */
    private  final WebDriverWait wait  ;
    
    /** The rl01210 partial ulr. */
    private final String partialURL = "_rl01210/rl01210.xhtml";

    private final LoadableComponent<?> parent;
    
    /***
     * 戶籍記事/罰鍰清單 頁籤
     * */
    @FindBy(how = How.XPATH, using = "//a[contains(text(),'戶籍記事/罰鍰清單')]")
    private WebElement tabNotes;
    
    /***
     * 全戶基本資料 頁籤
     * */
    @FindBy(how = How.XPATH, using = "//a[contains(text(),'全戶基本資料')]")
    private  WebElement tabBasicHouseholdData;
    
    /***
     * 資料驗證按鈕
     * */
    @FindBy(how = How.XPATH, using = "//span[contains(@id,'button')]/button")
    private  WebElement verifyBtn;
    
    /***
     * 暫存按鈕
     * */
    @FindBy(how = How.XPATH, using = "//span[contains(@id,'button')]/button[3]")
    private   WebElement tempSaveBtn;
    
    /***
     * 關閉視窗按鈕
     * */
    @FindBy(how = How.XPATH, using = "//span[contains(@id,'button')]/button[4]")
    private   WebElement closeBtn;
    
    /***
     * 稱謂input text
     * */
    @FindBy(how = How.XPATH, using = "//span[contains(@id,'relationship')]/span/input")
    private   WebElement inputRelationship  ;
    
    
    /**
     * Instantiates a new rl01210 page.
     *
     * @param driver the driver
     */
    public Rl01210PageV3(final WebDriver driver ,final LoadableComponent<?> parent)   {
        super();
        this.driver = driver;
        this.parent = parent;
        this.wait = new WebDriverWait(driver, 60);
        this.oAction = new Actions(this.driver);
        PageFactory.initElements(driver, this);
    }
    /**
     * Instantiates a new rl01210 page.
     * 為了使 PageFactory.initElements(this.driver, Rl01210PageV3.class)可以正常使用
     * @param driver the driver
     */
    public Rl01210PageV3(final WebDriver driver )   {
        super();
        this.driver = driver;
        this.parent = null;
        this.wait = new WebDriverWait(driver, 60);
        this.oAction = new Actions(this.driver);
        PageFactory.initElements(driver, this);
    }
    @Override
    protected void load() {
        final String mainUrl = WebUtils .getMainUrl( this.driver.getCurrentUrl());
        //http://192.168.10.18:6280/rl/faces/pages/func/rl00001/_rl01240/rl01240.xhtml?windowId=846
        final String url = String.format("%s/rl/faces/pages/func/rl00001/%s", mainUrl,partialURL);
        this.driver.get(url);
    }
    @Override
    protected void isLoaded() throws Error {
        final String currentUrl = this.driver.getCurrentUrl();
        //由於頁面網址會帶上windowId,會造成誤判       
        if(! StringUtils.contains(currentUrl, this.partialURL)){
            throw new Error(String.format("The wrong page has loaded: ", this.driver.getCurrentUrl()));
        } 
    }
    public void clickTabBasicHouseholdData(){
        oAction.moveToElement(this.tabBasicHouseholdData);
        oAction.click(this.tabBasicHouseholdData).build().perform();
    }
    public void clickTabNotes(){
        oAction.moveToElement(this.tabNotes);
        oAction.click(this.tabNotes).build().perform();
    }
    
    public GrowlMsg clickVerifyBtn(){
        oAction.moveToElement(this.verifyBtn);
        final GrowlMsg verification = WebUtils.clickBtn(this.driver, this.verifyBtn);
        return verification;
    }
    public GrowlMsg clickTempSaveBtn(){
        oAction.moveToElement(this.tempSaveBtn);
        final GrowlMsg verification = WebUtils.clickBtn(this.driver, this.tempSaveBtn);
        return verification;
    }
    public GrowlMsg clickCloseBtn(){
        oAction.moveToElement(this.closeBtn);
        final GrowlMsg verification = WebUtils.clickBtn(this.driver, this.closeBtn);
        return verification;
    }
    /**
     * Switch tab.
     * 進行頁籤轉換
     */
    public void switchTab()  {
        final String currentUrl = this.driver.getCurrentUrl();
        if (StringUtils.contains(currentUrl, this.partialURL)) {
            this.driver.navigate().refresh();
            
            WebUtils.pageLoadTimeout(this.driver);
            this.tabNotes.click();
           
            WebUtils.pageLoadTimeout(this.driver);
            this.tabBasicHouseholdData.click();

        }
    }
    
     
    /**
     * Type birth place ac.
     * 輸入出生地
     * @param birthPlaceAC the birth place ac
     */
    public void typeBirthPlaceAC(final String birthPlaceAC) {
        LOGGER.debug("輸入出生地: {}" ,birthPlaceAC);
	final String typeXpath = "//span[contains(@id,'birthPlaceAC')]";
	/***
         * 由於發現使用Selenium2 (WebDrvier在firefox 17下 有異常不能正常操作,所以實作暫時改用Selenium1)
         * ***/
//	WebDriverBackedSelenium selenium = new WebDriverBackedSelenium(driver, driver.getCurrentUrl());
        SRISWebUtils.typeAutoCompleteBySpanXpath(this.driver, typeXpath, birthPlaceAC);
//        SRISWebUtils.typeAutoCompleteBySpanXpath(selenium, typeXpath, birthPlaceAC);
    }

     
    /**
     * Type relation ship.
     * 輸入稱謂
     * @param relationship the relationship
     */
    public void typeRelationShip(final String relationship) {
        LOGGER.debug("輸入稱謂: {}" ,relationship); 
        
        oAction.moveToElement(this.inputRelationship);
        oAction.doubleClick(this.inputRelationship).build().perform();
        WebUtils.pageLoadTimeout(this.driver);
        this.inputRelationship.sendKeys(relationship); 
        WebUtils.pageLoadTimeout(this.driver);
    }

    /**
     * 輸入出生別
     * Type birth order sex select one menu.
     */
    public void typeBirthOrderSexSelectOneMenu() {
        LOGGER.debug("輸入出生別" ); 
        
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[contains(@id,'birthOrderSexSelectOneMenu')]"))).click();
        WebUtils.pageLoadTimeout(this.driver); 
        
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@id,'birthOrderSexSelectOneMenu')]/div/ul/li[2]"))).click();
        
        WebUtils.pageLoadTimeout(this.driver);
    }

    /**
     * 輸入出生日期
     * Type birth yyymmdd.
     *
     * @param birthYyymmdd the birth yyymmdd
     */
    public void typeBirthYyymmdd(final String birthYyymmdd) {
        LOGGER.debug("輸入出生日期: {}", birthYyymmdd);
        
        SRISWebUtils.typeYyymmdd(birthYyymmdd, driver, "//span[contains(@id,'birthYyymmdd')]");
       
    }

    /**
     * 輸入出生者資料的姓
     * Type last name.
     *
     * @param lastName the last name
     */
    public void typeLastName(final String lastName) {
        LOGGER.debug("輸入出生者資料的姓: {}" ,lastName);
        final String xpath = "//span[contains(@id,'lastName')]/span/input";
        ////span[@id='j_id_2k:lastName']/span/input   
        
        final WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
       
        oAction.moveToElement(element).build().perform();
        WebUtils.pageLoadTimeout(this.driver);
        
        oAction.doubleClick(element).build().perform();        
        WebUtils.pageLoadTimeout(this.driver);
        
        oAction.sendKeys(element,lastName).build().perform();  
        WebUtils.pageLoadTimeout(this.driver);
    }

    /**
     * 輸入出生者資料的名
     * Type first name.
     *
     * @param firstName the first name
     */
    public void typeFirstName(final String firstName) {
        LOGGER.debug("輸入出生者資料的名: {}" ,firstName);
        final String xpath = "//span[contains(@id,'firstName')]/span/input";
        ////span[@id='j_id_2k:firstName']/span/input 
        
        final WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        
        oAction.moveToElement(element).build().perform();
        WebUtils.pageLoadTimeout(this.driver);
        oAction.sendKeys(element,firstName).build().perform();  
        WebUtils.pageLoadTimeout(this.driver);
    }

    /**
     * 取得全戶基本資料
     * Get the RLDF001M by click btn.
     *
     * @return the RLD f001 m by click btn
     */
    public void getRLDF001MByClickBtn() {
        LOGGER.debug("取得全戶基本資料" );
        //span[@id='j_id_2k:household']/table/tbody/tr/td/button 
        final WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(@id,'household')]/table/tbody/tr/td/button")));
        element.click();
        WebUtils.pageLoadTimeout(this.driver);
    }

    /**
     * Type household head id.
     *  輸入戶長統號
     * @param householdHeadId the household head id
     */
    public void typeHouseholdHeadId(final String householdHeadId) {
        LOGGER.debug("輸入戶長統號: {}", householdHeadId); 
        final WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[contains(@id,'oldHouseholdHeadPersonId')]")));
        element.sendKeys(householdHeadId);
        WebUtils.pageLoadTimeout(this.driver);
    }

    /**
     * Type household id.
     * 輸入戶號
     * @param householdId the household id
     */
    public void typeHouseholdId(final String householdId) {
        LOGGER.debug("輸入戶號: {}", householdId);
//        this.driver.findElement(By.xpath("//input[contains(@id,'oldHouseholdId')]")).sendKeys(householdId);  
        final WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[contains(@id,'oldHouseholdId')]")));
        element.sendKeys(householdId);
        WebUtils.pageLoadTimeout(this.driver);
    }

    /**
     * Sets the new household.
     * 自立新戶 true
     * 非自立新戶(入他人戶) false
     * @param isNewHousehold the new new household
     */
    public void setNewHousehold(final boolean isNewHousehold) {
        LOGGER.debug("自立新戶: {}", isNewHousehold);
        //input[@id='j_id_2k:isNewHousehold:0']
        final String xpath = String.format("//input[contains(@id,'isNewHousehold:%s')]", isNewHousehold ? 0 : 1);
        
        final WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
 
         
        oAction.moveToElement(element).build().perform();
        WebUtils.pageLoadTimeout(this.driver);
        oAction.click().build().perform();  
        
        WebUtils.pageLoadTimeout(this.driver);
    }

    /**
     * Check birth kind.
     * 選擇出生類別
     * @param type the type
     */
    public void checkBirthKind(final BirthKind type) {
        
        LOGGER.debug("選擇出生類別: {}", type.toString().intern());
        
        final String xpath = String.format("//input[contains(@id,'birthKind:%s')]", type.value);
        final WebElement checkBirthKindRadioBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));

        checkBirthKindRadioBtn.click();
        WebUtils.pageLoadTimeout(this.driver);
    }

    
    /**
     * Gets the partial url.
     *
     * @return the partial url
     */
    public String getPartialURL() {
        return this.partialURL;
    }


    /**
     * The Enum BirthKind.
     */
   public enum BirthKind {

        /** The wedlock.婚生 */
        WEDLOCK(0),
        /** The posthumous.遺腹子 */
        POSTHUMOUS(1),
        /** The OUTOFWEDLOC k1. 非婚生（續辦認領）*/
        OUTOFWEDLOCK1(2),
        /** The OUTOFWEDLOC k2. 非婚生（不續辦認領）*/
        OUTOFWEDLOCK2(3),
        /** The innocenti.無依兒童 */
        INNOCENTI(4),
        /**
        * Instantiates a new birth kind.
        *
        * @param value the value
        */
        ;
        private BirthKind(int value) {
            this.value = value;
        }

        /** The value. */
        private int value;
    }
}
