package func.rl.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;

public class Rl0172bPage {
    protected final static  Logger logger = Logger.getLogger(Rl0172bPage.class);
    private WebDriver driver;
    private Selenium selenium;

    private final String rl0172bPartialUlr ="_rl0172b/rl0172b.xhtml";
    
    public Rl0172bPage(final Selenium selenium, final WebDriver driver)throws  UnhandledAlertException,SeleniumException  {
	super();
	this.selenium = selenium;
	this.driver = driver;
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
    public void switchTab()throws  UnhandledAlertException,SeleniumException, InterruptedException  {
	final String currentUrl = driver.getCurrentUrl();
	if (StringUtils.contains(currentUrl, rl0172bPartialUlr)) {
	    selenium.click("//a[contains(text(),'戶籍記事/罰鍰清單')]");
	    selenium.waitForPageToLoad("300000");
	    selenium.click("//a[contains(text(),'當事人、申請資料')]");
	    selenium.waitForPageToLoad("300000");
	    String element01 =selenium.getText("document.poopupForm.elements[1]");
	    String lastName = retrieveLastName(element01);
	    String firstName = retrieveFirstName(element01);
	    
	    
	    
	   logger.debug("firstName: "+firstName);
	   logger.debug("lastName: "+lastName);
	   
	    selenium.waitForPageToLoad("300000");

	    final String nationalityACXpath = "//*[contains(@id,'mainNationalityAC')]";

	    if (selenium.isElementPresent(nationalityACXpath)) {
		selenium.focus(nationalityACXpath);
		WebElement nationalityElement = driver.findElement(By.xpath(nationalityACXpath));
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
	    selenium.click("//div[contains(@id,'updateReasonCode')]/div[2]/span");
	    
	    
	    selenium.focus("//div[contains(@id,'updateReasonCode_panel')]/div/ul/li[2]");
	    selenium.click("//div[contains(@id,'updateReasonCode_panel')]/div/ul/li[2]");
	    
	    
	    selenium.waitForPageToLoad("300000");
	    
	   
	   logger.debug("orgNameWayElement.isVisible(): "+selenium.isVisible("//div[contains(@id,'orgNameWay')]/div[2]/span"));
	    
	    selenium.focus("//div[contains(@id,'orgNameWay')]/div[2]/span");
	    selenium.click("//div[contains(@id,'orgNameWay')]/div[2]/span");
	    
	   
	    selenium.focus("//div[contains(@id,'orgNameWay_panel')]/div/ul/li[3]");
	    selenium.click("//div[contains(@id,'orgNameWay_panel')]/div/ul/li[3]");
	    
	    
	    selenium.waitForPageToLoad("300000");
	    
	    
	    WebUtils.scroolbarDownUp(selenium, driver);

	    
	    
	    
	    selenium.focus("//a[contains(text(),'戶籍記事/罰鍰清單')]");
	    selenium.click("//a[contains(text(),'戶籍記事/罰鍰清單')]");
	    
	   
	    final String clickBtnXpath ="//*[contains(@id,'verifyAppData')]";//資料驗證
	    selenium.focus(clickBtnXpath);
	    selenium.click(clickBtnXpath);
	    
	   boolean giveUpOperation=false;
	 
	   String verifyBtn = selenium.getText(clickBtnXpath);//資料驗證
	   if(StringUtils.equalsIgnoreCase(StringUtils.trim(verifyBtn),"資料驗證")){
	       giveUpOperation=WebUtils.handleClickBtn(selenium, clickBtnXpath);
	      
	   }
	   if (giveUpOperation){
	     //*[@id="j_id19_j_id_2h:doCancel"]
	       selenium.click("//*[contains(@id,'doCancel')]");//據說是關閉視窗
	   }
	   selenium.waitForPageToLoad("1000"); 
	    if (StringUtils.contains(driver.getCurrentUrl(), "_rl0172b/rl0172b.xhtml")) {
		final String tmpSaveBtnXPath = "//span[4]/button[3]";
		final String tmpSaveBtnText = selenium.getText(tmpSaveBtnXPath); // 暫存
		final WebElement tmpSaveBtn = driver.findElement(By.xpath(tmpSaveBtnXPath));
		final  String disabledAttribute = tmpSaveBtn.getAttribute("disabled");
		logger.debug("tmpSaveBtn: " + tmpSaveBtnText);
		logger.debug("disabledAttribute: " + disabledAttribute);
		if (StringUtils.equalsIgnoreCase(StringUtils.trim(tmpSaveBtnText), "暫存")) {
		    // selenium.click("//span[4]/button[3]");//據說是暫存
		    // selenium.waitForPageToLoad("1000");
		    if (!giveUpOperation) {
			while (!StringUtils.equals(disabledAttribute, Boolean.TRUE.toString()) 
				&&
				StringUtils.contains(driver.getCurrentUrl(), rl0172bPartialUlr)) {
			    String targetUrl = driver.getCurrentUrl();
			   logger.debug(targetUrl);

			    if (selenium.isElementPresent(tmpSaveBtnXPath) && selenium.isVisible(tmpSaveBtnXPath)
				    && StringUtils.contains(targetUrl, rl0172bPartialUlr)) {				
				selenium.click(tmpSaveBtnXPath);// 據說是暫存
				selenium.waitForPageToLoad("60000");
				while(StringUtils.contains(driver.getCurrentUrl(), rl0172bPartialUlr)){
				    Thread.sleep(6000);
				    if (!StringUtils.contains(driver.getCurrentUrl(), rl0172bPartialUlr)) {
					break;
				    }
				}
			    } else {
				break;
			    }
			}
		    }else{
			
		    }

		}
	    }
	   
	   
	   selenium.waitForPageToLoad("100000"); 
	}
    }
}
