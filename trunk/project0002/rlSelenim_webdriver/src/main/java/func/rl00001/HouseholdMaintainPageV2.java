package func.rl00001;


import java.util.Set; 

import org.apache.commons.lang3.StringUtils; 
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.study.selenium.SRISWebUtils;


import func.rl.common.WebUtils;

public class HouseholdMaintainPageV2 {
    private static final String closeBeforeUnloadAlert ="document.getElementsByName('ae_l_leaveCheck')[0].value = null;"; 
    private static final String alertFlagXpath ="//*[contains(@id,'alert_flag')]";
	private  final Logger logger = LoggerFactory.getLogger(getClass());
    private WebDriver driver;

    public HouseholdMaintainPageV2( final WebDriver driver) {
	super();
	this.driver = driver;
    }

    public boolean  switchTab() throws InterruptedException {
	final String currentUrl = driver.getCurrentUrl(); 
	boolean sencondSwitch =false;
        if (StringUtils.contains(currentUrl, "/rl00001/householdMaintain.xhtml")) {
            final String tabSelected01Xpath = "//a[contains(text(),'戶籍登記作業')]";
            final String tabSelected02Xpath = "//input[@id='更正變更登記categoryRadio']";

            WebDriverWait wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(tabSelected01Xpath)));
            this.driver.findElement(By.xpath(tabSelected01Xpath)).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(tabSelected02Xpath)));
            sencondSwitch = true;
        }
	return sencondSwitch;
    }
    /******
     * 存檔測試程序
     * *****/
    public void processAppyCahange() throws InterruptedException {	
	boolean hitable = false;
	final String printBtnXpath = "//*[@id='saveBtnId']"; 
	//*[@id='saveBtnId']
	//div[contains(@id,'saveBtnId')]/button
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(printBtnXpath)));
        
        final WebElement printBtn = driver.findElement(By.xpath(printBtnXpath));
        final String disabledAttribute = printBtn.getAttribute("disabled");
        logger.debug("-----------------disabledAttribute: " + disabledAttribute);
        if (StringUtils.equals(disabledAttribute, Boolean.TRUE.toString())) {
            hitable = false;
        } else if (disabledAttribute == null || StringUtils.equals(disabledAttribute, Boolean.FALSE.toString())) {
            hitable = true;
        } 
        
	if ( hitable) { 
    
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(alertFlagXpath)));
            ((RemoteWebDriver) this.driver).executeScript(closeBeforeUnloadAlert, "");
             
            this.driver.findElement(By.xpath(printBtnXpath)).click();

	    while(StringUtils.contains(driver.getCurrentUrl(), "/rl00001/householdMaintain.xhtml")){
		Thread.sleep(3000);
		if(!StringUtils.contains(driver.getCurrentUrl(), "/rl00001/householdMaintain.xhtml")){
		    break;
		}
	    }
	    WebUtils.scroolbarDownUp( driver);
	}
	
	// div[@id='j_id39_j_id_sx_content']/button
    }
    
    /******
     * 列印申請書測試程序
     * *****/
    public void processPrintView() throws InterruptedException {
	final String printBtnXpath = "//div[contains(@id,'householdbtns_content')]/button";
	SRISWebUtils.newPdfPreview(driver, printBtnXpath);
    } 
} 
