package func.rl00001._rl01210;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;

import func.rl.common.WebUtils;
import func.rl.common.internal.GrowlMsg;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.study.selenium.SRISWebUtils;
import org.study.selenium.SeleniumConfig;

/**
 * The Class Rl01210Page.
 *  出生登記 頁面
 */
public class Rl01210Page {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(Rl01210Page.class);

    /** The driver. */
    private WebDriver driver;

    /** The selenium. */
    private Selenium selenium;

    /** The rl01210 partial ulr. */
    private final String rl01210PartialURL = "_rl01210/rl01210.xhtml";

    /**
     * Instantiates a new rl01210 page.
     *
     * @param selenium the selenium
     * @param driver the driver
     * @throws UnhandledAlertException the unhandled alert exception
     * @throws SeleniumException the selenium exception
     */
    public Rl01210Page(final Selenium selenium, final WebDriver driver) throws UnhandledAlertException, SeleniumException {
        super();
        this.selenium = selenium;
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
        if (StringUtils.contains(currentUrl, this.rl01210PartialURL)) {
            this.selenium.refresh();
            selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
            this.selenium.focus("//a[contains(text(),'戶籍記事/罰鍰清單')]");
            selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
            this.selenium.click("//a[contains(text(),'戶籍記事/罰鍰清單')]");
            selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
            this.selenium.focus("//a[contains(text(),'全戶基本資料')]");
            selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
            this.selenium.click("//a[contains(text(),'全戶基本資料')]");
            selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
            inputOnTab01();
            selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
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
        this.selenium.click("//a[contains(text(),'戶籍記事/罰鍰清單')]");
        this.selenium.waitForPageToLoad("300000");
        //資料驗證
        final String verifyBtnXpath = "//span[contains(@id,'button')]/button";
        //     this.selenium.click(verifyBtnXpath );        
        //資料驗證
        GrowlMsg verify = WebUtils.clickBtn(selenium, verifyBtnXpath);
        final String errorExtMessage = verify.getExtMessage();
        final String errorMessage = verify.getMessage();
        if (org.apache.commons.lang.StringUtils.isNotBlank(errorMessage)
                || org.apache.commons.lang.StringUtils.isNotBlank(errorExtMessage)) {
            System.out.println(".....");
            while (true) {
                if (StringUtils.equalsIgnoreCase("請輸入發現地點", errorExtMessage)) {
                    //                    Thread.sleep(1000l);
                    this.selenium.click("//a[contains(text(),'全戶基本資料')]");
                    selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
                    typeBirthPlaceAC("63000");
                    this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
                    this.selenium.click("//a[contains(text(),'戶籍記事/罰鍰清單')]");
                    this.selenium.waitForPageToLoad("300000");

                    verify = WebUtils.clickBtn(selenium, verifyBtnXpath);
                    if (!verify.isGiveUpOperation()) {
                        break;
                    }
                }
            }

        }
        //span[@id='j_id_2k:button']/button[3]
        this.selenium.waitForPageToLoad("300000");
        //        Thread.sleep(1000l);
        if (this.selenium.isElementPresent("//div[contains(@id,'growl2']/div/div/div")) {
            final String growl2Content = this.selenium.getText("//div[contains(@id,'growl2']");
            LOGGER.info(growl2Content);
        }

        //暫存
        this.selenium.click("//span[contains(@id,'button')]/button[3]");
        this.selenium.waitForPageToLoad("300000");
    }

    /**
     * 在頁籤01上輸入資料.
     *
     * @throws UnhandledAlertException the unhandled alert exception
     * @throws SeleniumException the selenium exception
     * @throws InterruptedException the interrupted exception
     */
    public void inputOnTab01() throws UnhandledAlertException, SeleniumException, InterruptedException {
        this.selenium.click("//a[contains(text(),'全戶基本資料/出生者、父母資料')]");
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        final String element01 = this.selenium.getText("document.poopupForm.elements[1]");
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        LOGGER.info(element01);
        WebUtils.scroolbarDownUp(this.selenium, this.driver);

        //選擇無依兒童        
        checkBirthKind(BirthKind.INNOCENTI);
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        //自立新戶
        setNewHousehold(true);
        WebUtils.scroolbarDownUp(this.selenium, this.driver);
        //非自立新戶(入他人戶)
        setNewHousehold(false);
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        //輸入戶長統號
        typeHouseholdHeadId("C100202427");
        //輸入戶號
        typeHouseholdId("F5261129");
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        getRLDF001MByClickBtn();
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        WebUtils.scroolbarDown(this.selenium, this.driver);
        typeLastName("無姓");
        typeFirstName("無名");
        typeBirthYyymmdd("1010203");
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        typeBirthOrderSexSelectOneMenu();
        typeRelationShip("稱謂");
        typeBirthPlaceAC("63000");
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);

    }

     
    /**
     * Type birth place ac.
     * 輸入出生地
     * @param birthPlaceAC the birth place ac
     */
    public void typeBirthPlaceAC(final String birthPlaceAC) {
        LOGGER.debug("輸入出生地: {}" ,birthPlaceAC);
        SRISWebUtils.typeAutoCompleteBySpanXpath(this.selenium, "//span[contains(@id,'birthPlaceAC')]", birthPlaceAC);
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
    }

    //
    /**
     * Type relation ship.
     * 輸入稱謂
     * @param relationship the relationship
     */
    public void typeRelationShip(final String relationship) {
        LOGGER.debug("輸入稱: {}" ,relationship);
        //span[@id='j_id_2k:relationship']/span/input
        this.selenium.type("//span[contains(@id,'relationship')]/span/input", relationship);
        selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
    }

    /**
     * 輸入出生別
     * Type birth order sex select one menu.
     */
    public void typeBirthOrderSexSelectOneMenu() {
        this.selenium.click("//label[contains(@id,'birthOrderSexSelectOneMenu')]");
        selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        this.selenium.click("//div[contains(@id,'birthOrderSexSelectOneMenu')]/div/ul/li[2]");
        selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
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
        this.selenium.type("//span[@id='birthYyymmdd__calendar']/input", yyy);
        this.selenium.fireEvent("//span[@id='birthYyymmdd__calendar']/input", "blur");
        selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        this.selenium.type("//span[@id='birthYyymmdd__calendar']/input[2]", mm);
        this.selenium.fireEvent("//span[@id='birthYyymmdd__calendar']/input[2]", "blur");
        selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        this.selenium.type("//span[@id='birthYyymmdd__calendar']/input[3]", dd);
        this.selenium.fireEvent("//span[@id='birthYyymmdd__calendar']/input[3]", "blur");
        selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        this.selenium.click("//span[@id='birthYyymmdd__calendar']/img");
        selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        this.selenium.click("//a[contains(text(),'關閉')]");
        selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);

    }

    /**
     * 輸入出生者資料的姓
     * Type last name.
     *
     * @param lastName the last name
     */
    public void typeLastName(final String lastName) {
        LOGGER.debug("輸入出生者資料的姓: {}" ,lastName);
        ////span[@id='j_id_2k:lastName']/span/input
        this.selenium.type("//span[contains(@id,'lastName')]/span/input", lastName);
    }

    /**
     * 輸入出生者資料的名
     * Type first name.
     *
     * @param firstName the first name
     */
    public void typeFirstName(final String firstName) {
        LOGGER.debug("輸入出生者資料的名: {}" ,firstName);
        ////span[@id='j_id_2k:firstName']/span/input
        this.selenium.type("//span[contains(@id,'firstName')]/span/input", firstName);
    }

    /**
     * 取得全戶基本資料
     * Get the RLDF001M by click btn.
     *
     * @return the RLD f001 m by click btn
     */
    public void getRLDF001MByClickBtn() {
        //span[@id='j_id_2k:household']/table/tbody/tr/td/button
        this.selenium.click("//span[contains(@id,'household')]/table/tbody/tr/td/button");
    }

    /**
     * Type household head id.
     * 輸入戶長統號
     * @param householdHeadId the household head id
     */
    public void typeHouseholdHeadId(final String householdHeadId) {
        LOGGER.debug("輸入戶長統號: {}", householdHeadId);
        this.selenium.type("//input[contains(@id,'oldHouseholdHeadPersonId')]", householdHeadId);
    }

    /**
     * Type household id.
     * 輸入戶號
     * @param householdId the household id
     */
    public void typeHouseholdId(final String householdId) {
        LOGGER.debug("輸入戶號: {}", householdId);
        this.selenium.type("//input[contains(@id,'oldHouseholdId')]", householdId);
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
        this.selenium.click(xpath);
    }

    /**
     * Check birth kind.
     * 選擇出生類別
     * @param type the type
     */
    public void checkBirthKind(final BirthKind type) {
        LOGGER.debug("選擇出生類別: {}", type.toString().intern());
        final String xpath = String.format("//input[contains(@id,'birthKind:%s')]", type.value);
        this.selenium.click(xpath);

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
