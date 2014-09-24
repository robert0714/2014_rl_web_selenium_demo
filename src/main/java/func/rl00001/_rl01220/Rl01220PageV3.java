package func.rl00001._rl01220;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.thoughtworks.selenium.SeleniumException;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

import func.rl.common.WebUtils; 

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
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
import org.study.selenium.SRISWebUtils;

/**
 * 
 * The Class Rl01220Page.
 * 死亡登記/死亡宣告登記 頁面
 */
public class Rl01220PageV3 extends LoadableComponent<Rl01220PageV3> {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(Rl01220PageV3.class);

    /** The driver. */
    private WebDriver driver;

    /** The rl01220 partial url. */
    private final String partialURL = "_rl01220/rl01220.xhtml";

    private final LoadableComponent<?> parent;

    /** The wait. */
    private WebDriverWait wait;

    /***
     * 戶籍記事/罰鍰清單 頁籤
     * */
    @FindBy(how = How.XPATH, using = "//a[contains(text(),'戶籍記事/罰鍰清單')]")
    public WebElement tabNotes;

    /***
     * 死亡者基本資料 頁籤
     * */
    @FindBy(how = How.XPATH, using = "//a[contains(text(),'死亡者')]")
    public WebElement tabDeadPersonData;

    /***
     * 關閉視窗按鈕
     * */
    @FindBy(how = How.XPATH, using = "//span[contains(@id,'button')]/button[4]")
    public WebElement closeBtn;

    /***
     * 暫存按鈕
     * */
    @FindBy(how = How.XPATH, using = "//button[contains(@id,'saveBtn')]")
    public WebElement tempSaveBtn;

    /***
     *  死亡原因 inputText
     * */
    @FindBy(how = How.XPATH, using = "//fieldset[@id='tabView:relatedApplyItems']/div/table[2]/tbody/tr/td/input")
    public WebElement deathReason;

    @FindBy(how = How.XPATH, using = "//div[contains(@id,'content')]/button")
    public WebElement verifyBtn;

    /**
     * Instantiates a new rl01220 page.
     *
     * @param driver the driver
     */
    public Rl01220PageV3(final WebDriver driver, final LoadableComponent<?> parent) {
        super();
        this.driver = driver;
        this.parent = parent;
        this.wait = new WebDriverWait(driver, 60);
        PageFactory.initElements(driver, this);
    }

    @Override
    protected void load() {
        final String mainUrl = WebUtils.getMainUrl(this.driver.getCurrentUrl());
        //http://192.168.10.18:6280/rl/faces/pages/func/rl00001/_rl01240/rl01240.xhtml?windowId=846
        final String url = String.format("%s/rl/faces/pages/func/rl00001/%s", mainUrl, partialURL);
        this.driver.get(url);
    }

    @Override
    protected void isLoaded() throws Error {
        final String currentUrl = this.driver.getCurrentUrl();
        //由於頁面網址會帶上windowId,會造成誤判       
        if (!StringUtils.contains(currentUrl, this.partialURL)) {
            throw new Error(String.format("The wrong page has loaded: ", this.driver.getCurrentUrl()));
        }
    }

    /**
     * Gets the today yyy m mdd.
     *
     * @return the today yyy m mdd
     */
    public String getTodayYyyMMdd() {
        final String mmdd = new SimpleDateFormat("MMdd").format(new Date());

        return String.format("103%s", mmdd);
    }

    /**
     * Switch tab.
     *
     * @throws UnhandledAlertException the unhandled alert exception
     * @throws SeleniumException the selenium exception
     * @throws InterruptedException the interrupted exception
     */
    public void switchTab() throws UnhandledAlertException, SeleniumException, InterruptedException {
        final String currentUrl = this.driver.getCurrentUrl();
        if (StringUtils.contains(currentUrl, this.partialURL)) {
            this.driver.navigate().refresh();

            WebUtils.pageLoadTimeout(this.driver);
            this.tabNotes.click();

            WebUtils.pageLoadTimeout(this.driver);
            this.tabDeadPersonData.click();

        }
    }

    /**
     * 死亡原因.
     *
     * @param reason the reason
     */
    public void typeDeathReason(final String reason) {
        // //fieldset[@id='tabView:relatedApplyItems']/div/table[2]/tbody/tr/td/input
        //        selenium.type("//fieldset[@id='tabView:relatedApplyItems']/div/table[2]/tbody/tr/td/input", reason);
        //        selenium.fireEvent("//fieldset[@id='tabView:relatedApplyItems']/div/table[2]/tbody/tr/td/input", "blur");
        this.deathReason.sendKeys(reason);
    }

    /**
     * 死忙地點(國別).
     *
     * @param birthPlaceAC the birth place ac
     */
    public void typeBirthPlaceAC(final String birthPlaceAC) {
        /***
         * 由於發現使用Selenium2 (WebDrvier有異常不能正常操作,所以實作暫時改用Selenium1)
         * ***/
        WebDriverBackedSelenium selenium = new WebDriverBackedSelenium(driver, driver.getCurrentUrl());
        SRISWebUtils.typeAutoCompleteBySpanXpath(selenium, "//span[contains(@id,'deadPlaceNationalityAC')]", birthPlaceAC);
        selenium = null;
        //        SRISWebUtils.typeAutoCompleteBySpanXpath(this.driver, "//span[contains(@id,'deadPlaceNationalityAC')]", birthPlaceAC);
    }

    /**
     * 死亡地點性質.
     *
     * @param item the item
     */
    public void selectDeathPlace(final DeathPlace item) {
        // div[@id='tabView:deathPlaceCode']/div[2]/span
        // div[@id='tabView:deathPlaceCode_panel']/div/ul/li[2]
        final String xpath = String.format("//div[@id='tabView:deathPlaceCode_panel']/div/ul/li[%s]", item.value);
        this.driver.findElement(By.xpath("//div[@id='tabView:deathPlaceCode']/div[2]/span")).click();
        WebUtils.pageLoadTimeout(this.driver);
        this.driver.findElement(By.xpath(xpath)).click();
        WebUtils.pageLoadTimeout(this.driver);
        //        selenium.click("//div[@id='tabView:deathPlaceCode']/div[2]/span");
        //        selenium.fireEvent("//div[@id='tabView:deathPlaceCode']/div[2]/span", "blur");        
        //        selenium.click(xpath);
        WebUtils.pageLoadTimeout(this.driver);
        //        selenium.fireEvent(xpath, "blur");

        //        selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
    }

    /**
     * 死亡日期確定方式.
     *
     * @param item the item
     */
    public void selectDeathWay(final DeathWay item) {
        // input[@id='tabView:deathWay:0']
        final String xpath = String.format("//input[@id='tabView:deathWay:%s']", item.value);
        this.driver.findElement(By.xpath(xpath)).click();
        //        selenium.click(xpath);
        //        selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        //        selenium.fireEvent(xpath, "blur");
        //        selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
    }

    /**
     * 死亡日期.
     *
     * @param birthYyymmdd the birth yyymmdd
     */
    public void typeDeathYyymmdd(final String birthYyymmdd) {
        final WebDriverWait wait = new WebDriverWait(driver, 60);
        // input[@id='j_id_2k:birthYyymmdd:j_id_uj']
        final String yyy = org.apache.commons.lang.StringUtils.substring(birthYyymmdd, 0, 3);
        final String mm = org.apache.commons.lang.StringUtils.substring(birthYyymmdd, 3, 5);
        final String dd = org.apache.commons.lang.StringUtils.substring(birthYyymmdd, 5, 7);
        final String yyyXpath = "//fieldset[@id='tabView:relatedApplyItems']/div/table/tbody/tr/td[3]/span/input";
        final String mmXpath = "//fieldset[@id='tabView:relatedApplyItems']/div/table/tbody/tr/td[3]/span/input[2]";
        final String ddXpath = "//fieldset[@id='tabView:relatedApplyItems']/div/table/tbody/tr/td[3]/span/input[3]";

        //        this. driver.findElement(By.xpath(yyyXpath)).sendKeys(yyy);
        //        this. driver.findElement(By.xpath(mmXpath)).sendKeys(mm);
        //        this. driver.findElement(By.xpath(ddXpath)).sendKeys(dd);

        /***
         * 由於發現使用Selenium2 (WebDrvier有異常不能正常操作,所以實作暫時改用Selenium1)
         * ***/
        WebDriverBackedSelenium selenium = new WebDriverBackedSelenium(driver, driver.getCurrentUrl());

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(yyyXpath)));

        selenium.type(yyyXpath, yyy);
        selenium.fireEvent(yyyXpath, "blur");
        WebUtils.pageLoadTimeout(this.driver);
        //        try {
        //            Thread.sleep(1000l);
        //        } catch (InterruptedException e) {
        //           LOGGER.error(e.getMessage(), e);
        //        }
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(mmXpath)));

        selenium.type(mmXpath, mm);
        selenium.fireEvent(mmXpath, "blur");
        WebUtils.pageLoadTimeout(this.driver);
        //        try {
        //            Thread.sleep(1000l);
        //        } catch (InterruptedException e) {
        //           LOGGER.error(e.getMessage(), e);
        //        }
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ddXpath)));

        selenium.type(ddXpath, dd);
        selenium.fireEvent(ddXpath, "blur");
        WebUtils.pageLoadTimeout(this.driver);
        //        try {
        //            Thread.sleep(1000l);
        //        } catch (InterruptedException e) {
        //           LOGGER.error(e.getMessage(), e);
        //        }

    }

    /**
     * 國民身分證是否繳回.
     *
     * @param item the item
     */
    public void selectIDPolicy(final IDPolicy item) {
        // input[@id='tabView:returnId:0']
        final String xpath = String.format("//input[@id='tabView:returnId:%s']", item.value);

        this.driver.findElement(By.xpath(xpath)).click();

        //        selenium.click(xpath);
        //        selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        //        selenium.fireEvent(xpath, "blur");
        //        selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
    }

    /**
     * 選擇登記項目 如果選擇死忙宣告,還要增加輸入"辦理法院名稱","裁定日期","宣告類別".
     *
     * @param item the item
     */
    public void selectDeathItem(final DeathItem item) {
        // input[@id='tabView:deathItem:1']
        final String xpath = String.format("//input[@id='tabView:deathItem:%s']", item.value);
        this.driver.findElement(By.xpath(xpath)).click();
        //        selenium.click(xpath);
        //        selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        //        selenium.fireEvent(xpath, "blur");
        //        selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
    }

    public String getPartialURL() {
        return this.partialURL;
    }

    /**
     * The Enum IDPolicy.
     */
    public enum IDPolicy {

        /** The return. 國民身分證繳回,是*/
        RETURN(0),
        /** The no. 國民身分證繳回 ,未*/
        NO(1),
        /** The never exist. 未領證*/
        NEVER_EXIST(2),
        /**
        * Instantiates a new ID policy.
        *
        * @param value the value
        */
        ;
        private IDPolicy(int value) {
            this.value = value;
        }

        /** The value. */
        private int value;
    }

    /**
     * The Enum DeathItem.
     */
    public enum DeathItem {

        /** The death. 死忙*/
        DEATH(0),
        /** The death claim. 死亡宣告*/
        DEATH_CLAIM(1),
        /**
        * Instantiates a new death item.
        *
        * @param value the value
        */
        ;
        private DeathItem(int value) {
            this.value = value;
        }

        /** The value. */
        private int value;
    }

    /**
     * The Enum DeathWay.
     */
    public enum DeathWay {

        /** The sure.確定 */
        SURE(0),
        /** The found.發現 */
        FOUND(1),
        /** The assume. 推定*/
        ASSUME(2),
        /**
        * Instantiates a new death way.
        *
        * @param value the value
        */
        ;
        private DeathWay(int value) {
            this.value = value;
        }

        /** The value. */
        private int value;
    }

    /**
     * The Enum DeathPlace.
     */
    public enum DeathPlace {

        /** The hospital.醫院 */
        HOSPITAL(2),
        /** The clinic.診所 */
        CLINIC(3),
        /** The house. 住居所 */
        HOUSE(4),
        /** The other. 其他 */
        OTHER(5),
        /** The foreign.國外 */
        FOREIGN(6),
        /** The org. 長期照護或安養機構*/
        ORG(7),
        
        /**
        * Instantiates a new death place.
        *
        * @param value the value
        */
        ;
        private DeathPlace(int value) {
            this.value = value;
        }

        /** The value. */
        private int value;
    }

}
