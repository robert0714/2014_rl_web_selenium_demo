package func.rl00001._rl01210;
 

import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

import func.rl.common.WebUtils;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions; 
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.study.selenium.SRISWebUtils;
import org.study.selenium.SeleniumConfig;

/**
 * The Class Rl01210Page.
 *  出生登記 頁面
 */
public class Rl01210PageV3 extends LoadableComponent<Rl01210PageV3>{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(Rl01210PageV3.class);

    /** The driver. */
    private WebDriver driver;
 
    
    /** The rl01210 partial ulr. */
    private final String partialURL = "_rl01210/rl01210.xhtml";

    private final LoadableComponent<?> parent;
    
    /***
     * 戶籍記事/罰鍰清單 頁籤
     * */
    @FindBy(how = How.XPATH, using = "//a[contains(text(),'戶籍記事/罰鍰清單')]")
    public WebElement tabNotes;
    
    /***
     * 全戶基本資料 頁籤
     * */
    @FindBy(how = How.XPATH, using = "//a[contains(text(),'全戶基本資料')]")
    public  WebElement tabBasicHouseholdData;
    
    /***
     * 資料驗證按鈕
     * */
    @FindBy(how = How.XPATH, using = "//span[contains(@id,'button')]/button")
    public  WebElement verifyBtn;
    
    /***
     * 暫存按鈕
     * */
    @FindBy(how = How.XPATH, using = "//span[contains(@id,'button')]/button[3]")
    public   WebElement tempSaveBtn;
    
    /***
     * 關閉視窗按鈕
     * */
    @FindBy(how = How.XPATH, using = "//span[contains(@id,'button')]/button[4]")
    public   WebElement closeBtn;
    
    /***
     * 稱謂input text
     * */
    @FindBy(how = How.XPATH, using = "//span[contains(@id,'relationship')]/span/input")
    public   WebElement inputRelationship  ;
    
    
    
    /**
     * Instantiates a new rl01210 page.
     *
     * @param driver the driver
     */
    public Rl01210PageV3(final WebDriver driver ,final LoadableComponent<?> parent)   {
        super();
        this.driver = driver;
        this.parent = parent;
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
	WebDriverBackedSelenium selenium = new WebDriverBackedSelenium(driver, driver.getCurrentUrl());
//        SRISWebUtils.typeAutoCompleteBySpanXpath(this.driver, typeXpath, birthPlaceAC);
        SRISWebUtils.typeAutoCompleteBySpanXpath(selenium, typeXpath, birthPlaceAC);
    }

     
    /**
     * Type relation ship.
     * 輸入稱謂
     * @param relationship the relationship
     */
    public void typeRelationShip(final String relationship) {
        LOGGER.debug("輸入稱謂: {}" ,relationship); 
        
        final Actions oAction = new Actions(driver);
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
        this.driver.findElement(By.xpath("//label[contains(@id,'birthOrderSexSelectOneMenu')]")).click();
        WebUtils.pageLoadTimeout(this.driver);
        this.driver.findElement(By.xpath("//div[contains(@id,'birthOrderSexSelectOneMenu')]/div/ul/li[2]")).click(); 
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
        
        //input[@id='j_id_2k:birthYyymmdd:j_id_uj']
        final String yyy = org.apache.commons.lang.StringUtils.substring(birthYyymmdd, 0, 3);
        final String mm = org.apache.commons.lang.StringUtils.substring(birthYyymmdd, 3, 5);
        final String dd = org.apache.commons.lang.StringUtils.substring(birthYyymmdd, 5, 7);
       
//        this.driver.findElement(By.xpath("//span[contains(@id,'birthYyymmdd')]/input")).sendKeys(yyy); 
//        
//	WebUtils.pageLoadTimeout(this.driver);
//        
//        this. driver.findElement(By.xpath("//span[@id='birthYyymmdd__calendar']/input[2]")).sendKeys(mm); 
//        
//        WebUtils.pageLoadTimeout(this.driver);
//        
//        this.driver.findElement(By.xpath("//span[@id='birthYyymmdd__calendar']/input[3]")).sendKeys(dd);
//        
//        WebUtils.pageLoadTimeout(this.driver);
        
        
        /***
         * 由於發現使用Selenium2 (WebDrvier有異常不能正常操作,所以實作暫時改用Selenium1)
         * ***/
        WebDriverBackedSelenium selenium = new WebDriverBackedSelenium(driver, driver.getCurrentUrl());
        selenium.type("//span[@id='birthYyymmdd__calendar']/input", yyy);
        selenium.fireEvent("//span[@id='birthYyymmdd__calendar']/input", "blur");
        
        selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        selenium.type("//span[@id='birthYyymmdd__calendar']/input[2]", mm);
        selenium.fireEvent("//span[@id='birthYyymmdd__calendar']/input[2]", "blur");
        WebUtils.pageLoadTimeout(this.driver);
        selenium.type("//span[@id='birthYyymmdd__calendar']/input[3]", dd);
        selenium.fireEvent("//span[@id='birthYyymmdd__calendar']/input[3]", "blur");
        WebUtils.pageLoadTimeout(this.driver);
        selenium.click("//span[@id='birthYyymmdd__calendar']/img");
        WebUtils.pageLoadTimeout(this.driver);
         
        selenium=null;
    }

    /**
     * 輸入出生者資料的姓
     * Type last name.
     *
     * @param lastName the last name
     */
    public void typeLastName(final String lastName) {
        ////span[@id='j_id_2k:lastName']/span/input   
	
//        this. driver.findElement(By.xpath("//span[contains(@id,'lastName')]/span/input")).sendKeys(lastName);  
//        WebUtils.pageLoadTimeout(this.driver);
	 /***
         * 由於發現使用Selenium2 (WebDrvier在firefox 17下 有異常不能正常操作,所以實作暫時改用Selenium1)
         * ***/
	WebDriverBackedSelenium selenium = new WebDriverBackedSelenium(driver, driver.getCurrentUrl());
        selenium.type("//span[contains(@id,'lastName')]/span/input", lastName);
        selenium = null;
        
        
    }

    /**
     * 輸入出生者資料的名
     * Type first name.
     *
     * @param firstName the first name
     */
    public void typeFirstName(final String firstName) {
        ////span[@id='j_id_2k:firstName']/span/input
//        this. driver.findElement(By.xpath("//span[contains(@id,'firstName')]/span/input")).sendKeys(firstName);  
//        WebUtils.pageLoadTimeout(this.driver);
	
	 /***
         * 由於發現使用Selenium2 (WebDrvier有異常不能正常操作,所以實作暫時改用Selenium1)
         * ***/
        WebDriverBackedSelenium selenium = new WebDriverBackedSelenium(driver, driver.getCurrentUrl());
        selenium.type("//span[contains(@id,'firstName')]/span/input", firstName);
        selenium = null;
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
        this.driver.findElement(By.xpath("//span[contains(@id,'household')]/table/tbody/tr/td/button")).click();
        WebUtils.pageLoadTimeout(this.driver);
    }

    /**
     * Type household head id.
     *  輸入戶長統號
     * @param householdHeadId the household head id
     */
    public void typeHouseholdHeadId(final String householdHeadId) {
        LOGGER.debug("輸入戶長統號: {}", householdHeadId);
        this. driver.findElement(By.xpath("//input[contains(@id,'oldHouseholdHeadPersonId')]")).sendKeys(householdHeadId); 
        WebUtils.pageLoadTimeout(this.driver);
    }

    /**
     * Type household id.
     * 輸入戶號
     * @param householdId the household id
     */
    public void typeHouseholdId(final String householdId) {
        LOGGER.debug("輸入戶號: {}", householdId);
        this.driver.findElement(By.xpath("//input[contains(@id,'oldHouseholdId')]")).sendKeys(householdId);  
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
        
        final  WebElement element = this.driver.findElement(By.xpath(xpath));
        
        final Actions oAction = new Actions(driver);
        oAction.moveToElement(element);
                
        oAction.doubleClick(element).build().perform();
        
        oAction.click(element).build().perform();  
        
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
        this.driver.findElement(By.xpath(xpath)).click();  
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
