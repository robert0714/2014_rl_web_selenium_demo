package func.rl00001;


import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils; 
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 


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
    
    private boolean ready(){
        boolean result =false ;
        final JavascriptExecutor js = (JavascriptExecutor)  driver;
//        Object aaa = js.executeScript("var aaa= risPdfPrinterApplet.getConvertStatus();console.log('robert test: '+aaa);");
        final Object jsValue = js.executeScript("return  risPdfPrinterApplet.getConvertStatus();");
        if(jsValue != null){
            if(jsValue instanceof String){
                result =  Boolean.valueOf((String)jsValue);
            }else if(jsValue instanceof Boolean ){
                result = (Boolean)jsValue;
            }
            
        }
       
        return result;
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

        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(printBtnXpath)));
        final WebElement printBtn = this.driver.findElement(By.xpath(printBtnXpath));
        
        final String disabledAttribute = printBtn.getAttribute("disabled");
        logger.debug("-----------------disabledAttribute: " + disabledAttribute);
        if (StringUtils.equals(disabledAttribute, Boolean.TRUE.toString())) {
            printBtnXpathHit = false;
        } else if (disabledAttribute == null || StringUtils.equals(disabledAttribute, Boolean.FALSE.toString())) {
            printBtnXpathHit = true;
            giveUpOperation = WebUtils.handleClickBtn(this.driver ,  printBtnXpath);
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
                                while (!ready()) {
                                    //applet的id為risPdfPrinterApplet方法為getConvertState
                                    driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
                                    ;// 建議5秒畢竟cognos實在太慢了
                                    
                                }
                                WebUtils.scroolbarDownUp( driver);
                                // *[@id="j_id4_j_id_9:j_id_y"]/span
                                // *[@id="j_id4_j_id_9:j_id_y"]
                                this.driver.findElement(By.xpath("//span[contains(@id,'pdfbanner')]/span[2]/button[2]")).click();
                                 // 端未列印
                                // form/div/div/div/div[2]/button[2]
                                // selenium.click("//form/div/div/div/div[2]/button[2]");//關閉
                                printViewPresent = true;
                                break browerWindowLoop;
			    }
			}
		    }
		} catch (NoSuchWindowException e) {
		    logger.debug(e.getMessage() ,e );
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
}
