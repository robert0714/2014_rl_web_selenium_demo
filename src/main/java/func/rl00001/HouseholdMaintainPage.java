package func.rl00001;


import java.util.Set;

import org.apache.commons.lang3.StringUtils; 
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.selenium.Selenium;

import func.rl.common.WebUtils;

public class HouseholdMaintainPage {
	private  final Logger logger = LoggerFactory.getLogger(getClass());
    private WebDriver driver;
    private Selenium selenium;

    public HouseholdMaintainPage(final Selenium selenium, final WebDriver driver) {
	super();
	this.selenium = selenium;
	this.driver = driver;
    }

    public boolean  switchTab() throws InterruptedException {
	final String currentUrl = driver.getCurrentUrl();
	boolean firstSwitch =false;
	boolean sencondSwitch =false;
	if (StringUtils.contains(currentUrl, "/rl00001/householdMaintain.xhtml")) {
	    final String tabSelected01Xpath = "//a[contains(text(),'戶籍登記作業')]";
	    final String tabSelected02Xpath = "//input[@id='更正變更登記categoryRadio']";
	    
	    WebDriverWait wait = new WebDriverWait(driver, 10);
	    
	    if(selenium.isElementPresent(tabSelected01Xpath)){
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(tabSelected01Xpath)));
		selenium.click(tabSelected01Xpath);
		selenium.waitForPageToLoad("300000");
		firstSwitch=true;
	    }
	    
	    if(firstSwitch && selenium.isElementPresent(tabSelected02Xpath)){
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(tabSelected02Xpath)));
		selenium.click(tabSelected02Xpath);
		selenium.waitForPageToLoad("300000");
		sencondSwitch=true;
	    }	   
	}
	return sencondSwitch;
    }
    /******
     * 存檔測試程序
     * *****/
    public void processAppyCahange() throws InterruptedException {	
	boolean hitable = false;
	final String printBtnXpath = "//*[@id='saveBtnId']";
	Thread.sleep(1000);
	//*[@id='saveBtnId']
	//div[contains(@id,'saveBtnId')]/button
	if (selenium.isElementPresent(printBtnXpath)) {
	    final WebElement printBtn = driver.findElement(By.xpath(printBtnXpath));
	    final String disabledAttribute = printBtn.getAttribute("disabled");
	    logger.debug("-----------------disabledAttribute: " + disabledAttribute);
	    if (StringUtils.equals(disabledAttribute, Boolean.TRUE.toString())) {
		hitable = false;
	    } else if (disabledAttribute == null || StringUtils.equals(disabledAttribute, Boolean.FALSE.toString())) {
		hitable = true;
		
	    }
	}
	Thread.sleep(1000);
	if ( hitable) {
	    if (selenium.isElementPresent("//input[contains(@id,'alert_flag')]")) {
		selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
	    }
	    selenium.click(printBtnXpath);
	    selenium.waitForPageToLoad("30000");
	    while(StringUtils.contains(driver.getCurrentUrl(), "/rl00001/householdMaintain.xhtml")){
		Thread.sleep(3000);
		if(!StringUtils.contains(driver.getCurrentUrl(), "/rl00001/householdMaintain.xhtml")){
		    break;
		}
	    }
	    WebUtils.scroolbarDownUp(selenium, driver);
	}
	
	// div[@id='j_id39_j_id_sx_content']/button
    }
    /******
     * 列印申請書測試程序
     * *****/
    public void processPrintView() throws InterruptedException {
	boolean giveUpOperation = false;

	// Save the WindowHandle of Parent Browser Window
	final String parentWindowId = driver.getWindowHandle();
	logger.debug("parentWindowId: " + parentWindowId);
	boolean printBtnXpathHit = false;
	final String printBtnXpath = "//div[contains(@id,'householdbtns_content')]/button";

	if (selenium.isElementPresent(printBtnXpath)) {
	    final WebElement printBtn = driver.findElement(By.xpath(printBtnXpath));
	    final String disabledAttribute = printBtn.getAttribute("disabled");
	    logger.debug("-----------------disabledAttribute: " + disabledAttribute);
	    if (StringUtils.equals(disabledAttribute, Boolean.TRUE.toString())) {
		printBtnXpathHit = false;
	    } else if (disabledAttribute == null || StringUtils.equals(disabledAttribute, Boolean.FALSE.toString())) {
		printBtnXpathHit = true;
		giveUpOperation = WebUtils.handleClickBtn(selenium, printBtnXpath);
	    }
	}

	if (!giveUpOperation && printBtnXpathHit) {
	    // 預覽申請書會彈跳出視窗
	    int count = 0;
	    privntViewLoop: while (printBtnXpathHit) {
		Thread.sleep(5000);// 建議5秒
		boolean printViewPresent = false;
		try {
		    final Set<String> windowHandles = driver.getWindowHandles();
		    browerWindowLoop: for (final String windowId : windowHandles) {
			if (!StringUtils.equalsIgnoreCase(windowId, parentWindowId)) {
			    // Switch to the Help Popup Browser Window
			    driver.switchTo().window(windowId);
			    String currentUrl = driver.getCurrentUrl();
			    logger.debug(currentUrl);
			    if (StringUtils.contains(currentUrl, "common/popupContent.xhtml")) {
				// 戶役資訊服務網
				String title = driver.getTitle();
				logger.debug("title: " + title);
				WebUtils.scroolbarDownUp(selenium, driver);
				// *[@id="j_id4_j_id_9:j_id_y"]/span
				// *[@id="j_id4_j_id_9:j_id_y"]
				selenium.click("//form/div/div/div/div[2]/button");// 端未列印
				// form/div/div/div/div[2]/button[2]
				// selenium.click("//form/div/div/div/div[2]/button[2]");//關閉
				printViewPresent = true;
				break browerWindowLoop;
			    }
			}
		    }
		} catch (NoSuchWindowException e) {
		    e.printStackTrace();
		}

		if (printViewPresent) {
		    // Close the Help Popup Window
		    driver.close();

		    // Move back to the Parent Browser Window
		    driver.switchTo().window(parentWindowId);
		    break privntViewLoop;
		}
		if (count > 10) {
		    break privntViewLoop;
		}
		count++;
	    }
	}
	// div[@id='j_id39_j_id_sx_content']/button
    }
    public void clickRl1722B()throws InterruptedException{
	   switchTab(); 
	    
	   final String rl0172BXpath = "//a[contains(text(),'姓名變更／冠姓／從姓登記')]";
	   logger.debug("rl172Bclick.isVisible()<HouseholdMaintainPage>: "+selenium.isVisible(rl0172BXpath));
	    
	    if(selenium.isElementPresent(rl0172BXpath)){
//		WebElement rl172Bclick = driver.findElement(By.xpath(rl0172BXpath));
//		logger.debug("rl172Bclick.isDisplayed(): " + rl172Bclick.isDisplayed());
//		logger.debug("rl172Bclick.isEnabled(): " + rl172Bclick.isEnabled());
		
		selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
//		rl172Bclick.click();
		selenium.click(rl0172BXpath);
		selenium.waitForPageToLoad("300000");
	    }
	
    }
    
}
