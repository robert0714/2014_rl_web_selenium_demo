package func.rl00001._rl01220;

import java.text.SimpleDateFormat;
import java.util.Date;

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
 * 
 * The Class Rl01220Page.
 * 死亡登記/死亡宣告登記 頁面
 */
public class Rl01220Page {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(Rl01220Page.class);

    /** The driver. */
    private WebDriver driver;

    /** The selenium. */
    private Selenium selenium;

    /** The rl01220 partial url. */
    private final String rl01220PartialURL = "_rl01220/rl01220.xhtml";

    /**
     * Instantiates a new rl01220 page.
     *
     * @param selenium the selenium
     * @param driver the driver
     * @throws UnhandledAlertException the unhandled alert exception
     * @throws SeleniumException the selenium exception
     */
    public Rl01220Page(final Selenium selenium, final WebDriver driver) throws UnhandledAlertException, SeleniumException {
        super();
        this.selenium = selenium;
        this.driver = driver;
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
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        System.out.println(String.format("%1$,03d", 31));
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
        if (StringUtils.contains(currentUrl, this.rl01220PartialURL)) {
            this.selenium.refresh();
            this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
            this.selenium.focus("//a[contains(text(),'戶籍記事/罰鍰清單')]");
            this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
            this.selenium.click("//a[contains(text(),'戶籍記事/罰鍰清單')]");
            this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
            this.selenium.focus("//a[contains(text(),'死亡者')]");
            this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
            this.selenium.click("//a[contains(text(),'死亡者')]");
            this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
            inputData01();
            this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
            inputData02();

        }
    }

    /**
     * Input data02.
     *
     * @throws UnhandledAlertException the unhandled alert exception
     * @throws SeleniumException the selenium exception
     * @throws InterruptedException the interrupted exception
     */
    public void inputData02() throws UnhandledAlertException, SeleniumException, InterruptedException {
        this.selenium.click("//a[contains(text(),'戶籍記事/罰鍰清單')]");
        this.selenium.waitForPageToLoad("300000");
        // 資料驗證 
        final String verifyBtnXpath = "//div[contains(@id,'content')]/button";
        // this.selenium.click(verifyBtnXpath );
        // 資料驗證
        GrowlMsg verify = WebUtils.clickBtn(this.selenium, verifyBtnXpath);
        final String errorExtMessage = verify.getExtMessage();
        final String errorMessage = verify.getMessage();
        if (org.apache.commons.lang.StringUtils.isNotBlank(errorMessage)
                || org.apache.commons.lang.StringUtils.isNotBlank(errorExtMessage)) {
            System.out.println(".....");
            while (true) {
                if (StringUtils.equalsIgnoreCase("請輸入死亡原因", errorExtMessage)) {
                    // Thread.sleep(1000l);
                    this.selenium.click("//a[contains(text(),'死亡者')]");
                    this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
                    typeDeathReason("死亡原因");
                    this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
                    this.selenium.click("//a[contains(text(),'戶籍記事/罰鍰清單')]");
                    this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);

                    verify = WebUtils.clickBtn(this.selenium, verifyBtnXpath);
                    if (!verify.isGiveUpOperation()) {
                        break;
                    }
                }
            }

        }
        // span[@id='j_id_2k:button']/button[3]
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        // Thread.sleep(1000l);
        if (this.selenium.isElementPresent("//div[contains(@id,'growl2']/div/div/div")) {
            final String growl2Content = this.selenium.getText("//div[contains(@id,'growl2']");
            LOGGER.info(growl2Content);
        }

        // 暫存//button[@id='tabView:saveBtn']
        //	this.selenium.click("//button[contains(@id,'saveBtn')]");
        WebUtils.handleClickBtn(this.selenium, "//button[contains(@id,'saveBtn')]");
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
    }

    /**
     * Input data01.
     *
     * @throws UnhandledAlertException the unhandled alert exception
     * @throws SeleniumException the selenium exception
     * @throws InterruptedException the interrupted exception
     */
    public void inputData01() throws UnhandledAlertException, SeleniumException, InterruptedException {
        this.selenium.click("//a[contains(text(),'死亡者')]");
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        String element01 = this.selenium.getText("document.poopupForm.elements[1]");
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        System.out.println(element01);
        WebUtils.scroolbarDownUp(this.selenium, this.driver);

        // 選擇登記項目
        selectDeathItem(DeathItem.DEATH);

        // 國民身分證是否繳回
        selectIDPolicy(IDPolicy.RETURN);

        // 死亡日期
        typeDeathYyymmdd(getTodayYyyMMdd());

        // 死亡日期確定方式
        selectDeathWay(DeathWay.SURE);

        // 死亡地點性質
        selectDeathPlace(DeathPlace.CLINIC);

        // 死忙地點(國別)
        typeBirthPlaceAC("001");
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        // 死亡原因
        typeDeathReason("死亡原因");

        // 附繳證件
    }

    /**
     * 死亡原因.
     *
     * @param reason the reason
     */
    public void typeDeathReason(final String reason) {
        // //fieldset[@id='tabView:relatedApplyItems']/div/table[2]/tbody/tr/td/input
        this.selenium.type("//fieldset[@id='tabView:relatedApplyItems']/div/table[2]/tbody/tr/td/input", reason);
        this.selenium.fireEvent("//fieldset[@id='tabView:relatedApplyItems']/div/table[2]/tbody/tr/td/input", "blur");
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);

    }

    /**
     * 死忙地點(國別).
     *
     * @param birthPlaceAC the birth place ac
     */
    public void typeBirthPlaceAC(final String birthPlaceAC) {
        SRISWebUtils.typeAutoCompleteBySpanXpath(this.selenium, "//span[contains(@id,'deadPlaceNationalityAC')]", birthPlaceAC);
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);

    }

    /**
     * 死亡地點性質.
     *
     * @param item the item
     */
    public void selectDeathPlace(final DeathPlace item) {
        // div[@id='tabView:deathPlaceCode']/div[2]/span
        // div[@id='tabView:deathPlaceCode_panel']/div/ul/li[2]
        this.selenium.click("//div[@id='tabView:deathPlaceCode']/div[2]/span");
        this.selenium.fireEvent("//div[@id='tabView:deathPlaceCode']/div[2]/span", "blur");
        final String xpath = String.format("//div[@id='tabView:deathPlaceCode_panel']/div/ul/li[%s]", item.value);
        this.selenium.click(xpath);
        selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        this.selenium.fireEvent(xpath, "blur");
        selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
    }

    /**
     * 死亡日期確定方式.
     *
     * @param item the item
     */
    public void selectDeathWay(final DeathWay item) {
        // input[@id='tabView:deathWay:0']
        final String xpath = String.format("//input[@id='tabView:deathWay:%s']", item.value);
        this.selenium.click(xpath);
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        this.selenium.fireEvent(xpath, "blur");
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
    }

    /**
     * 死亡日期.
     *
     * @param birthYyymmdd the birth yyymmdd
     */
    public void typeDeathYyymmdd(final String birthYyymmdd) {
        // input[@id='j_id_2k:birthYyymmdd:j_id_uj']
        final String yyy = org.apache.commons.lang.StringUtils.substring(birthYyymmdd, 0, 3);
        final String mm = org.apache.commons.lang.StringUtils.substring(birthYyymmdd, 3, 5);
        final String dd = org.apache.commons.lang.StringUtils.substring(birthYyymmdd, 5, 7);

        this.selenium.type("//fieldset[@id='tabView:relatedApplyItems']/div/table/tbody/tr/td[3]/span/input", yyy);
        this.selenium.fireEvent("//fieldset[@id='tabView:relatedApplyItems']/div/table/tbody/tr/td[3]/span/input", "blur");
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);

        this.selenium.type("//fieldset[@id='tabView:relatedApplyItems']/div/table/tbody/tr/td[3]/span/input[2]", mm);
        this.selenium.fireEvent("//fieldset[@id='tabView:relatedApplyItems']/div/table/tbody/tr/td[3]/span/input[2]", "blur");
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);

        this.selenium.type("//fieldset[@id='tabView:relatedApplyItems']/div/table/tbody/tr/td[3]/span/input[3]", dd);
        this.selenium.fireEvent("//fieldset[@id='tabView:relatedApplyItems']/div/table/tbody/tr/td[3]/span/input[3]", "blur");
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);

    }

    /**
     * 國民身分證是否繳回.
     *
     * @param item the item
     */
    public void selectIDPolicy(final IDPolicy item) {
        // input[@id='tabView:returnId:0']
        final String xpath = String.format("//input[@id='tabView:returnId:%s']", item.value);
        this.selenium.click(xpath);
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        this.selenium.fireEvent(xpath, "blur");
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
    }

    /**
     * 選擇登記項目 如果選擇死忙宣告,還要增加輸入"辦理法院名稱","裁定日期","宣告類別".
     *
     * @param item the item
     */
    public void selectDeathItem(final DeathItem item) {
        // input[@id='tabView:deathItem:1']
        final String xpath = String.format("//input[@id='tabView:deathItem:%s']", item.value);
        this.selenium.click(xpath);
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        this.selenium.fireEvent(xpath, "blur");
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
    }

    /**
     * The Enum IDPolicy.
     */
    enum IDPolicy {

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
    enum DeathItem {

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
    enum DeathWay {

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
    enum DeathPlace {

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
