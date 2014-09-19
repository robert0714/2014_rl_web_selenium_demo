package func.rl00001._rl01220;

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
 */
public class Rl01220Page {
    private static final Logger LOGGER = LoggerFactory.getLogger(Rl01220Page.class);
    private WebDriver driver;
    private Selenium selenium;

    private final String rl01220PartialUlr = "_rl01220/rl01220.xhtml";

    public Rl01220Page(final Selenium selenium, final WebDriver driver) throws UnhandledAlertException, SeleniumException {
	super();
	this.selenium = selenium;
	this.driver = driver;
    }

    public void switchTab() throws UnhandledAlertException, SeleniumException, InterruptedException {
	final String currentUrl = this.driver.getCurrentUrl();
	if (StringUtils.contains(currentUrl, this.rl01220PartialUlr)) {
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

    public void inputData02() throws UnhandledAlertException, SeleniumException, InterruptedException {
	this.selenium.click("//a[contains(text(),'戶籍記事/罰鍰清單')]");
	this.selenium.waitForPageToLoad("300000");
	// 資料驗證
	final String verifyBtnXpath = "//span[contains(@id,'button')]/button";
	// this.selenium.click(verifyBtnXpath );
	// 資料驗證
	GrowlMsg verify = WebUtils.clickBtn(this.selenium, verifyBtnXpath);
	final String errorExtMessage = verify.getErrorExtMessage();
	final String errorMessage = verify.getErrorMessage();
	if (org.apache.commons.lang.StringUtils.isNotBlank(errorMessage) || org.apache.commons.lang.StringUtils.isNotBlank(errorExtMessage)) {
	    System.out.println(".....");
	    while (true) {
		if (StringUtils.equalsIgnoreCase("請輸入發現地點", errorExtMessage)) {
		    // Thread.sleep(1000l);
		    this.selenium.click("//a[contains(text(),'全戶基本資料')]");
		    this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
		    typeBirthPlaceAC("63000");
		    this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
		    this.selenium.click("//a[contains(text(),'戶籍記事/罰鍰清單')]");
		    this.selenium.waitForPageToLoad("300000");

		    verify = WebUtils.clickBtn(this.selenium, verifyBtnXpath);
		    if (!verify.isGiveUpOperation()) {
			break;
		    }
		}
	    }

	}
	// span[@id='j_id_2k:button']/button[3]
	this.selenium.waitForPageToLoad("300000");
	// Thread.sleep(1000l);
	if (this.selenium.isElementPresent("//div[contains(@id,'growl2']/div/div/div")) {
	    final String growl2Content = this.selenium.getText("//div[contains(@id,'growl2']");
	    LOGGER.info(growl2Content);
	}

	// 暫存
	this.selenium.click("//span[contains(@id,'button')]/button[3]");
	this.selenium.waitForPageToLoad("300000");
    }

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
	typeDeathYyymmdd("1010201");

	// 死亡日期確定方式
	selectDeathWay(DeathWay.SURE);

	// 死亡地點性質
	selectDeathPlace(DeathPlace.CLINIC);

	// 死忙地點(國別)
	typeBirthPlaceAC("001");

	// 死亡原因
	// 附繳證件
    }

    /**
     * 死亡原因
     * */
    public void typeDeathReason(final String reason) {
	// //fieldset[@id='tabView:relatedApplyItems']/div/table[2]/tbody/tr/td/input
	this.selenium.type("//fieldset[@id='tabView:relatedApplyItems']/div/table[2]/tbody/tr/td/input", reason);
	this.selenium.fireEvent("//fieldset[@id='tabView:relatedApplyItems']/div/table[2]/tbody/tr/td/input", "blur");
	this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);

    }

    /**
     * 死忙地點(國別)
     * */
    public void typeBirthPlaceAC(final String birthPlaceAC) {
	SRISWebUtils.typeAutoCompleteBySpanXpath(this.selenium, "//span[contains(@id,'deadPlaceNationalityAC')]", birthPlaceAC);
	this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);

    }

    /**
     * 死亡地點性質
     * */
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
     * 死亡日期確定方式
     * */
    public void selectDeathWay(final DeathWay item) {
	// input[@id='tabView:deathWay:0']
	final String xpath = String.format("//input[@id='tabView:deathWay:%s']", item.value);
	this.selenium.click(xpath);
	this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
	this.selenium.fireEvent(xpath, "blur");
	this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
    }

    /**
     * 死亡日期
     * */
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
     * 國民身分證是否繳回
     * */
    public void selectIDPolicy(final IDPolicy item) {
	// input[@id='tabView:returnId:0']
	final String xpath = String.format("//input[@id='tabView:returnId:%s']", item.value);
	this.selenium.click(xpath);
	this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
	this.selenium.fireEvent(xpath, "blur");
	this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
    }

    /**
     * 選擇登記項目 如果選擇死忙宣告,還要增加輸入"辦理法院名稱","裁定日期","宣告類別"
     * */
    public void selectDeathItem(final DeathItem item) {
	// input[@id='tabView:deathItem:1']
	final String xpath = String.format("//input[@id='tabView:deathItem:%s']", item.value);
	this.selenium.click(xpath);
	this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
	this.selenium.fireEvent(xpath, "blur");
	this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
    }

    enum IDPolicy {
	RETURN(0), // 國民身分證繳回,是
	NO(1), // 國民身分證繳回 ,未
	NEVER_EXIST(2), // 未領證
	;
	private IDPolicy(int value) {
	    this.value = value;
	}

	private int value;
    }

    enum DeathItem {
	DEATH(0), // 死忙
	DEATH_CLAIM(1), // 死亡宣告
	;
	private DeathItem(int value) {
	    this.value = value;
	}

	private int value;
    }

    enum DeathWay {
	SURE(0), // 確定
	FOUND(1), // 發現
	ASSUME(2), // 推定
	;
	private DeathWay(int value) {
	    this.value = value;
	}

	private int value;
    }

    enum DeathPlace {
	HOSPITAL(2), // 醫院
	CLINIC(3), // 診所
	HOUSE(4), // 住居所
	OTHER(5), // 其他
	FOREIGN(6), // 國外
	ORG(7), // 長期照護或安養機構
	;
	private DeathPlace(int value) {
	    this.value = value;
	}

	private int value;
    }
}
