package func.rl.common;

import java.io.File;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.OutputType.*;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;

public class Rl03100Page {
    protected final static  Logger logger = Logger.getLogger(Rl03100Page.class);
    private WebDriver driver;
    private Selenium selenium;

    private final String rl0172bPartialUlr ="_rl0172b/rl0172b.xhtml";
    private final String rl03100PartialUlr ="rl03100/rl03100.xhtml";
    private final String rl03100DetailPartialUlr ="rl03100/rl03100_m_detail.xhtml";
    
    public Rl03100Page(final Selenium selenium, final WebDriver driver)throws  UnhandledAlertException,SeleniumException  {
	super();
	this.selenium = selenium;
	this.driver = driver;
    }
    public void typeApplication(final String personId ,final String siteId,final String outputFolderPath) throws UnhandledAlertException, SeleniumException, InterruptedException {
	selenium.focus("//tr[2]/td/span/input");
	selenium.type("//tr[2]/td/span/input", personId);//輸入統號
	
	//span[2]/input//輸入作業點
	WebElement selectorElement = driver.findElement(By.xpath("//span[2]/input"));
	selenium.focus("//span[2]/input");
	selenium.waitForPageToLoad("30000");
	selectorElement.clear();
	selectorElement.sendKeys(siteId);
	
	boolean giveUpOperation = WebUtils.handleClickBtn(selenium, "//td/button");
	
	try {
	    final 	Alert alert = driver.switchTo().alert();
	    final 	String textOnAlert = alert.getText();
	    logger.info(textOnAlert);
	    alert.accept();
	} catch (Exception e) {
	    logger.info(e.getMessage(),e);
	}
	
	if (!giveUpOperation && StringUtils.contains(driver.getCurrentUrl(), rl03100DetailPartialUlr)){
	    WebUtils.scroolbarDown(selenium, driver);
	    WebUtils.takeScreen(driver,  new File(outputFolderPath+personId+"_"+siteId+"_01.png"));
	    WebUtils.scroolbarUp(selenium, driver);
	    WebUtils.takeScreen(driver,  new File(outputFolderPath+personId+"_"+siteId+"_02.png"));
	}
    }
    public void switchTab() throws UnhandledAlertException, SeleniumException, InterruptedException {
	final String currentUrl = driver.getCurrentUrl();
	if (StringUtils.contains(currentUrl, rl03100PartialUlr)) {
	    selenium.refresh();
	    selenium.click("//input[@id='default:marriage:1']");//選擇登記婚後
	    selenium.waitForPageToLoad("300000");
	    selenium.click("//input[@id='default:classification:0']");//選擇結婚
	    selenium.waitForPageToLoad("300000");
	    selenium.click("//input[@id='default:certificateOfCE:0']");//選擇中文
	    selenium.waitForPageToLoad("300000");
	    selenium.click("//input[@id='default:pinyIn:0']");//選擇漢語拼音
	    selenium.waitForPageToLoad("300000");
	    String clickBtnXpath="//a/button";
	    
	    
	    boolean giveUpOperation=WebUtils.handleClickBtn(selenium, clickBtnXpath);
	    //選擇下一步
	    if(giveUpOperation){
		//放棄操作
	    }else{
		//繼續操作
		//input[contains(@id,'alert_flag')]
		selenium.waitForPageToLoad("300000");
		String selectionPath = "//input[contains(@id,'SELECTION')]";
		if (selenium.isElementPresent(selectionPath)) {
		    System.out.println("Yes");
		}
		//*[@id="SELECTION:j_id_cs:0:j_id_cu"]/div[2]
		WebElement selectionPathElement = driver.findElement(By.xpath(selectionPath));
		selectionPathElement.click();
		selenium.waitForPageToLoad("300000");
		//button[@id='SELECTION:j_id_e7']
		selenium.click("//button[contains(@id,'SELECTION')]");//選擇第一張
		selenium.waitForPageToLoad("300000");
		
		selenium.click("//*[contains(@id,'verifyAppDataBtnId')]/span");
		selenium.waitForPageToLoad("300000");
		
		final String parentWindowId = driver.getWindowHandle();
		logger.debug("parentWindowId: " + parentWindowId);
		boolean printBtnXpathHit = false;
		final String printBtnXpath = "//button[contains(@id,'printButtonId')]";

		if (selenium.isElementPresent(printBtnXpath)) {
					final WebElement printBtn = driver.findElement(By.xpath(printBtnXpath));
					final String disabledAttribute = printBtn.getAttribute("disabled");
					logger.debug("-----------------disabledAttribute: " + disabledAttribute);
					if (StringUtils.equals(disabledAttribute, Boolean.TRUE.toString())) {
						printBtnXpathHit = false;
					} else if (disabledAttribute == null || StringUtils.equals(disabledAttribute, Boolean.FALSE.toString())) {
						printBtnXpathHit = true;
						giveUpOperation = WebUtils.handleClickBtn(selenium, printBtnXpath);
						selenium.waitForPageToLoad("300000");
					}
					selenium.waitForPageToLoad("300000");
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
				    
				    logger.debug(driver.getCurrentUrl());
				    if (StringUtils.contains(driver.getCurrentUrl(), "common/popupContent.xhtml")) {
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
		
		selenium.click("//button[contains(@id,'printButtonId')]");
		selenium.waitForPageToLoad("300000");
		selenium.click("//button[contains(@id,'saveBtnId')]");
		selenium.waitForPageToLoad("300000");
	    }
	}
    }
    
    private static String retrieveFirstName(final String element01){
	 String firstName = null;
	int pos = StringUtils.indexOf(element01, "(");
	int end = StringUtils.indexOf(element01, ")");	
	String tmp =StringUtils.substring(element01, pos, end);
	logger.debug(tmp);
	String[] stringArray = StringUtils.splitPreserveAllTokens(tmp, "：");
	if(stringArray.length>1){
	    firstName =StringUtils.trim(stringArray[2]);
	}
	return firstName;
  }
    private static String retrieveLastName(final String element01){
	 String lastName = null;
	int pos = StringUtils.indexOf(element01, "(");
	int end = StringUtils.indexOf(element01, ")");	
	String tmp =StringUtils.substring(element01, pos, end);
	String[] stringArray = StringUtils.splitPreserveAllTokens(tmp, "：");
	if(stringArray.length>1){
	    lastName = StringUtils.replace(stringArray[1],"名","").trim();
	}
	return lastName;
   }
   
    public void inputData01()throws  UnhandledAlertException,SeleniumException, InterruptedException{
	selenium.click("//a[contains(text(),'當事人、申請資料')]");
	    selenium.waitForPageToLoad("300000");
	    String element01 =selenium.getText("document.poopupForm.elements[1]");
	    String lastName = retrieveLastName(element01);
	    String firstName = retrieveFirstName(element01);
	    
	    
	    
	   logger.debug("firstName: "+firstName);
	   logger.debug("lastName: "+lastName);
	   
	    selenium.waitForPageToLoad("300000");

	    final String nationalityACXpath = "//input[contains(@id,'mainNationalityAC')]";

	    if (selenium.isElementPresent(nationalityACXpath)) {
		selenium.focus(nationalityACXpath);
		logger.info("selenium.getLocation(): " +selenium.getLocation());
		WebElement nationalityElement = driver.findElement(By.xpath(nationalityACXpath));
		
		//如果是使用//*[contains(@id,'mainNationalityAC')],則會沒有辦法將value傳入		
		nationalityElement.sendKeys("022");
		selenium.waitForPageToLoad("30000");
	    }
	   
	   
//	    selenium.type("document.poopupForm.elements[13]", lastName);
//	    selenium.type("document.poopupForm.elements[14]", firstName);
	    selenium.focus("//*[contains(@id,'beforeMidenName')]/span/input");
	    selenium.type("//*[contains(@id,'beforeMidenName')]/span/input", lastName);
	    selenium.type("//*[contains(@id,'beforeFirstName')]/span/input", firstName);
	    
//	    selenium.type("document.poopupForm.elements[17]", firstName);
//	    selenium.type("document.poopupForm.elements[18]", firstName+lastName);	    
	    selenium.type("//span[contains(@id,'afterMidenName')]/span/input", lastName);
	    String afterFirstNameXpath = "//*[contains(@id,'afterFirstName')]/span/input";
	    selenium.focus(afterFirstNameXpath);//故意填錯
	    
	    WebElement inputFirstNameElement = driver.findElement(By.xpath(afterFirstNameXpath));	   
	    selenium.waitForPageToLoad("30000");
	    inputFirstNameElement.clear();
	    if(StringUtils.contains(firstName, "測試")){
		inputFirstNameElement.sendKeys(StringUtils.replace(firstName, "測試", ""));
	    }else{
		inputFirstNameElement.sendKeys(firstName+"測試");
	    }    

	    selenium.waitForPageToLoad("30000");
	    logger.debug("element1: "+element01);
	    
	    
	    selenium.click("//div[contains(@id,'afterItem')]/label");
	    selenium.click("//div[contains(@id,'afterItem_panel')]/div/ul/li[24]");
	    
	    selenium.waitForPageToLoad("300000");
	  //div[@id='j_id19_j_id_2h:updateReasonCode']/div[2]/span
	  //div[@id='j_id19_j_id_2h:updateReasonCode_panel']/div/ul/li[2]
	   
	    selenium.focus("//div[contains(@id,'updateReasonCode')]/div[2]/span");
	    selenium.waitForPageToLoad("300000");
	    selenium.click("//div[contains(@id,'updateReasonCode')]/div[2]/span");
	    selenium.waitForPageToLoad("300000");
	    selenium.click("//div[contains(@id,'updateReasonCode_panel')]/div/ul/li[2]");
	    	    
	    selenium.waitForPageToLoad("300000");	    
	    
	    selenium.focus("//div[contains(@id,'orgNameWay')]/div[2]/span");
	    selenium.waitForPageToLoad("300000");
	    selenium.click("//div[contains(@id,'orgNameWay')]/div[2]/span");
	    selenium.waitForPageToLoad("300000");
	    selenium.click("//div[contains(@id,'orgNameWay_panel')]/div/ul/li[3]");
	    
	    selenium.waitForPageToLoad("300000");
	    
	    WebUtils.scroolbarDownUp(selenium, driver);
	   

	    
	    
    }
    public void inputData02()throws  UnhandledAlertException,SeleniumException, InterruptedException  {
	 WebUtils.scroolbarDownUp(selenium, driver);
	 selenium.waitForPageToLoad("30000");
	 selenium.focus("//a[contains(text(),'戶籍記事/罰鍰清單')]");
	 selenium.click("//a[contains(text(),'戶籍記事/罰鍰清單')]");

	final String clickBtnXpath = "//*[contains(@id,'verifyAppData')]";// 資料驗證
	selenium.focus(clickBtnXpath);
	selenium.click(clickBtnXpath);

	boolean giveUpOperation = false;

	String verifyBtn = selenium.getText(clickBtnXpath);// 資料驗證
	if (StringUtils.equalsIgnoreCase(StringUtils.trim(verifyBtn), "資料驗證")) {
	    giveUpOperation = WebUtils.handleClickBtn(selenium, clickBtnXpath);

	}
	if (giveUpOperation) {
	    // *[@id="j_id19_j_id_2h:doCancel"]
	    selenium.click("//*[contains(@id,'doCancel')]");// 據說是關閉視窗
	}
	selenium.waitForPageToLoad("1000");
	if (StringUtils.contains(driver.getCurrentUrl(), "_rl0172b/rl0172b.xhtml")) {
	    final String tmpSaveBtnXPath = "//span[4]/button[3]";
	    final String tmpSaveBtnText = selenium.getText(tmpSaveBtnXPath); // 暫存
	    final WebElement tmpSaveBtn = driver.findElement(By.xpath(tmpSaveBtnXPath));
	    final String disabledAttribute = tmpSaveBtn.getAttribute("disabled");
	    logger.debug("tmpSaveBtn: " + tmpSaveBtnText);
	    logger.debug("disabledAttribute: " + disabledAttribute);
	    if (StringUtils.equalsIgnoreCase(StringUtils.trim(tmpSaveBtnText), "暫存")) {
		// selenium.click("//span[4]/button[3]");//據說是暫存
		// selenium.waitForPageToLoad("1000");
		if (!giveUpOperation) {
		    while (!StringUtils.equals(disabledAttribute, Boolean.TRUE.toString()) && StringUtils.contains(driver.getCurrentUrl(), rl0172bPartialUlr)) {
			String targetUrl = driver.getCurrentUrl();
			logger.debug(targetUrl);

			if (selenium.isElementPresent(tmpSaveBtnXPath) && selenium.isVisible(tmpSaveBtnXPath)
				&& StringUtils.contains(targetUrl, rl0172bPartialUlr)) {
			    selenium.click(tmpSaveBtnXPath);// 據說是暫存
			    selenium.waitForPageToLoad("60000");
			    while (StringUtils.contains(driver.getCurrentUrl(), rl0172bPartialUlr)) {
				Thread.sleep(6000);
				if (!StringUtils.contains(driver.getCurrentUrl(), rl0172bPartialUlr)) {
				    break;
				}
			    }
			} else {
			    break;
			}
		    }
		} 
	    }
	}
	selenium.waitForPageToLoad("100000");
    }
    
}
