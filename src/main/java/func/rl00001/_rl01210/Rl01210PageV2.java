package func.rl00001._rl01210;
 
import com.thoughtworks.selenium.SeleniumException;

import func.rl.common.WebUtils;
import func.rl.common.internal.GrowlMsg;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.study.selenium.SRISWebUtils;
import org.study.selenium.SeleniumConfig;

/**
 * The Class Rl01210Page.
 *  出生登記 頁面
 */
public class Rl01210PageV2 {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(Rl01210PageV2.class);

    /** The driver. */
    private WebDriver driver;
 

    /** The rl01210 partial ulr. */
    private final String rl01210PartialUlr = "_rl01210/rl01210.xhtml";

    /**
     * Instantiates a new rl01210 page.
     *
     * @param selenium the selenium
     * @param driver the driver
     * @throws UnhandledAlertException the unhandled alert exception
     * @throws SeleniumException the selenium exception
     */
    public Rl01210PageV2(final WebDriver driver) throws UnhandledAlertException, SeleniumException {
        super();
        this.driver = driver;
    }

    
    /**
     * Switch tab.
     * 進行頁籤轉換
     * @throws UnhandledAlertException the unhandled alert exception
     * @throws SeleniumException the selenium exception
     * @throws InterruptedException the interrupted exception
     */
    public void switchTab() throws UnhandledAlertException, SeleniumException, InterruptedException {
        final String currentUrl = this.driver.getCurrentUrl();
        if (StringUtils.contains(currentUrl, this.rl01210PartialUlr)) {
            this.driver.navigate().refresh();
            WebUtils.waitPageLoadByLinkText(this.driver,SeleniumConfig.waitForPageToLoadS , "//a[contains(text(),'戶籍記事/罰鍰清單')]");
            this.driver.findElement(By.linkText("//a[contains(text(),'戶籍記事/罰鍰清單')]")).click();
           
            WebUtils.waitPageLoadByLinkText(this.driver,SeleniumConfig.waitForPageToLoadS , "//a[contains(text(),'全戶基本資料')]");
            this.driver.findElement(By.linkText("//a[contains(text(),'全戶基本資料')]")).click();
            
            inputOnTab01();
            WebUtils.waitPageLoadByLinkText(this.driver,SeleniumConfig.waitForPageToLoadS , "//a[contains(text(),'全戶基本資料')]");
            inputOnTab02();

        }
    }

    /**
     * Input data02.
     *
     * @throws UnhandledAlertException the unhandled alert exception
     * @throws SeleniumException the selenium exception
     * @throws InterruptedException the interrupted exception
     */
    public void inputOnTab02() throws UnhandledAlertException, SeleniumException, InterruptedException {
        WebUtils.waitPageLoadByLinkText(this.driver,SeleniumConfig.waitForPageToLoadS , "//a[contains(text(),'戶籍記事/罰鍰清單')]");
        this.driver.findElement(By.linkText("//a[contains(text(),'戶籍記事/罰鍰清單')]")).click();
        
        WebUtils.waitPageLoadByLinkText(this.driver,SeleniumConfig.waitForPageToLoadS , "//a[contains(text(),'戶籍記事/罰鍰清單')]");
        
        //資料驗證
        final String verifyBtnXpath = "//span[contains(@id,'button')]/button";
        //     this.selenium.click(verifyBtnXpath );        
        //資料驗證
        GrowlMsg verify = WebUtils.clickBtn(this.driver, verifyBtnXpath);
        final String errorExtMessage = verify.getErrorExtMessage();
        final String errorMessage = verify.getErrorMessage();
        if (org.apache.commons.lang.StringUtils.isNotBlank(errorMessage)
                || org.apache.commons.lang.StringUtils.isNotBlank(errorExtMessage)) {
            LOGGER.info(".....");
            while (true) {
                if (StringUtils.equalsIgnoreCase("請輸入發現地點", errorExtMessage)) {
                    
                    this.driver.findElement(By.linkText("//a[contains(text(),'全戶基本資料')]")).click();
                    WebUtils.waitPageLoadByLinkText(this.driver,SeleniumConfig.waitForPageToLoadS , "//span[contains(@id,'birthPlaceAC')]");
                    
                    typeBirthPlaceAC("63000");
                    
                    WebUtils.waitPageLoadByLinkText(this.driver,SeleniumConfig.waitForPageToLoadS , "//a[contains(text(),'戶籍記事/罰鍰清單')]");
                    this.driver.findElement(By.linkText("//a[contains(text(),'戶籍記事/罰鍰清單')]")).click();

                    verify = WebUtils.clickBtn(this.driver, verifyBtnXpath);
                    if (!verify.isGiveUpOperation()) {
                        break;
                    }
                }
            }
        } 
        WebUtils.waitPageLoadByXpath(this.driver,SeleniumConfig.waitForPageToLoadS , "//div[contains(@id,'growl2']/div/div/div");
        boolean present = driver.findElements(By.xpath("//div[contains(@id,'growl2']/div/div/div")).size() != 0  ;
        
        if (present) {
            final String growl2Content = driver.findElement(By.xpath("//div[contains(@id,'growl2']")).getText(); 
            LOGGER.info(growl2Content);
        }

        //暫存
        WebUtils.waitPageLoadByXpath(this.driver,SeleniumConfig.waitForPageToLoadS , "//span[contains(@id,'button')]/button[3]");
        this.driver.findElement(By.xpath("//span[contains(@id,'button')]/button[3]")).click(); 
    }

    /**
     * 在頁籤01上輸入資料.
     *
     * @throws UnhandledAlertException the unhandled alert exception
     * @throws SeleniumException the selenium exception
     * @throws InterruptedException the interrupted exception
     */
    public void inputOnTab01() throws UnhandledAlertException, SeleniumException, InterruptedException {
        WebUtils.waitPageLoadByLinkText(this.driver,SeleniumConfig.waitForPageToLoadS , "//a[contains(text(),'全戶基本資料/出生者、父母資料')]");
        this.driver.findElement(By.linkText("//a[contains(text(),'全戶基本資料/出生者、父母資料')]")).click();
        
        final String element01 = this.driver.findElement(By.name("document.poopupForm.elements[1]")).getText();;
        
        LOGGER.info(element01);
        WebUtils.scroolbarDownUp(this.driver);

        //選擇無依兒童        
        checkBirthKind(BirthKind.INNOCENTI);
        //自立新戶
        setNewHousehold(true);
        WebUtils.scroolbarDownUp(this.driver);
        //非自立新戶(入他人戶)
        setNewHousehold(false);
        
        //輸入戶長統號
        typeHouseholdHeadId("C100202427");
        //輸入戶號
        typeHouseholdId("F5261129");
        getRLDF001MByClickBtn();
        WebUtils.scroolbarDown(this.driver);
        typeLastName("無姓");
        typeFirstName("無名");
        typeBirthYyymmdd("1010203");
        typeBirthOrderSexSelectOneMenu();
        typeRelationShip("稱謂");
        typeBirthPlaceAC("63000");

    }

    //輸入出生地
    /**
     * Type birth place ac.
     *
     * @param birthPlaceAC the birth place ac
     */
    public void typeBirthPlaceAC(final String birthPlaceAC) {
        SRISWebUtils.typeAutoCompleteBySpanXpath(this.driver, "//span[contains(@id,'birthPlaceAC')]", birthPlaceAC);
    }

    //
    /**
     * Type relation ship.
     * 輸入稱謂
     * @param relationship the relationship
     */
    public void typeRelationShip(final String relationship) {
        //span[@id='j_id_2k:relationship']/span/input
        final WebElement oWE = driver.findElement(By.xpath("//span[contains(@id,'relationship')]/span/input"));
        Actions oAction = new Actions(driver);
        oAction.moveToElement(oWE);
        oAction.doubleClick(oWE).build().perform();
        oWE.sendKeys(relationship); 
    }

    /**
     * 輸入出生別
     * Type birth order sex select one menu.
     */
    public void typeBirthOrderSexSelectOneMenu() {
        this.driver.findElement(By.xpath("//label[contains(@id,'birthOrderSexSelectOneMenu')]")).click();
        this.driver.findElement(By.xpath("//div[contains(@id,'birthOrderSexSelectOneMenu')]/div/ul/li[2]")).click(); 
    }

    /**
     * 輸入出生日期
     * Type birth yyymmdd.
     *
     * @param birthYyymmdd the birth yyymmdd
     */
    public void typeBirthYyymmdd(final String birthYyymmdd) {
        //input[@id='j_id_2k:birthYyymmdd:j_id_uj']
        final String yyy = org.apache.commons.lang.StringUtils.substring(birthYyymmdd, 0, 3);
        final String mm = org.apache.commons.lang.StringUtils.substring(birthYyymmdd, 3, 5);
        final String dd = org.apache.commons.lang.StringUtils.substring(birthYyymmdd, 5, 7);
        
        this.driver.findElement(By.xpath("//span[@id='birthYyymmdd__calendar']/input")).sendKeys(yyy);        
//        this.selenium.fireEvent("//span[@id='birthYyymmdd__calendar']/input", "blur");
        
        this. driver.findElement(By.xpath("//span[@id='birthYyymmdd__calendar']/input[2]")).sendKeys(mm); 
//        this.selenium.fireEvent("//span[@id='birthYyymmdd__calendar']/input[2]", "blur");
        
        this.driver.findElement(By.xpath("//span[@id='birthYyymmdd__calendar']/input[3]")).sendKeys(dd);
//        this.selenium.fireEvent("//span[@id='birthYyymmdd__calendar']/input[3]", "blur");
        
        this. driver.findElement(By.xpath("//span[@id='birthYyymmdd__calendar']/img")).click();
        
        this.driver.findElement(By.linkText("//a[contains(text(),'關閉')]")).click();
         
        
    }

    /**
     * 輸入出生者資料的姓
     * Type last name.
     *
     * @param lastName the last name
     */
    public void typeLastName(final String lastName) {
        ////span[@id='j_id_2k:lastName']/span/input
        
        this. driver.findElement(By.xpath("//span[contains(@id,'lastName')]/span/input")).sendKeys(lastName);  
    }

    /**
     * 輸入出生者資料的名
     * Type first name.
     *
     * @param firstName the first name
     */
    public void typeFirstName(final String firstName) {
        ////span[@id='j_id_2k:firstName']/span/input
        this. driver.findElement(By.xpath("//span[contains(@id,'firstName')]/span/input")).sendKeys(firstName);  
    }

    /**
     * 取得全戶基本資料
     * Get the RLDF001M by click btn.
     *
     * @return the RLD f001 m by click btn
     */
    public void getRLDF001MByClickBtn() {
        //span[@id='j_id_2k:household']/table/tbody/tr/td/button
        this.driver.findElement(By.xpath("//span[contains(@id,'household')]/table/tbody/tr/td/button")).click();
    }

    /**
     * Type household head id.
     *
     * @param householdHeadId the household head id
     */
    public void typeHouseholdHeadId(final String householdHeadId) {
        this. driver.findElement(By.xpath("//input[contains(@id,'oldHouseholdHeadPersonId')]")).sendKeys(householdHeadId); 
    }

    /**
     * Type household id.
     *
     * @param householdId the household id
     */
    public void typeHouseholdId(final String householdId) {
        this.driver.findElement(By.xpath("//input[contains(@id,'oldHouseholdId')]")).sendKeys(householdId);  
    }

    /**
     * Sets the new household.
     *
     * @param isNewHousehold the new new household
     */
    public void setNewHousehold(final boolean isNewHousehold) {
        //input[@id='j_id_2k:isNewHousehold:0']
        final String xpath = String.format("//input[contains(@id,'isNewHousehold:%s')]", isNewHousehold ? 0 : 1); 
        this.driver.findElement(By.xpath(xpath)).click();  
    }

    /**
     * Check birth kind.
     *
     * @param type the type
     */
    public void checkBirthKind(final BirthKind type) {
        final String xpath = String.format("//input[contains(@id,'birthKind:%s')]", type.value);
        this.driver.findElement(By.xpath(xpath)).click();  

    }

    /**
     * The Enum BirthKind.
     */
    enum BirthKind {

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
