package org.robert.study.rl.common;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;

public class Rl0172bPage {
    private WebDriver driver;
    private Selenium selenium;

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
	System.out.println(tmp);
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
    public void switchTab()throws  UnhandledAlertException,SeleniumException  {
	final String currentUrl = driver.getCurrentUrl();
	if (StringUtils.contains(currentUrl, "_rl0172b/rl0172b.xhtml")) {
	    selenium.click("//a[contains(text(),'戶籍記事/罰鍰清單')]");
	    selenium.waitForPageToLoad("300000");
	    selenium.click("//a[contains(text(),'當事人、申請資料')]");
	    selenium.waitForPageToLoad("300000");
	    String element01 =selenium.getText("document.poopupForm.elements[1]");
	    String lastName = retrieveLastName(element01);
	    String firstName = retrieveFirstName(element01);
	    System.out.println("firstName: "+firstName);
	    System.out.println("lastName: "+lastName);
	    selenium.type("document.poopupForm.elements[13]", lastName);
	    selenium.type("document.poopupForm.elements[14]", firstName);
	    selenium.type("document.poopupForm.elements[17]", firstName);
	    selenium.type("document.poopupForm.elements[18]", firstName+lastName);
	    
	    selenium.type("//span[contains(@id,'afterMidenName')]/span/input", lastName);
	    selenium.type("//span[contains(@id,'afterFirstName')]/span/input", firstName);//故意填錯
	    Actions builder = new Actions(driver);
	    WebElement inputFirstNameElement = driver.findElement(By.xpath("//span[contains(@id,'afterFirstName')]/span/input"));
	    builder.keyDown(Keys.CONTROL).click(inputFirstNameElement).keyUp(Keys.CONTROL);
	    selenium.waitForPageToLoad("30000");
	    inputFirstNameElement.click();
	    selenium.waitForPageToLoad("30000");
	    inputFirstNameElement.sendKeys(firstName+lastName);//故意填錯
	    
	    System.out.println("element1: "+element01);
	    
	    
	    selenium.click("//div[contains(@id,'afterItem')]/label");
	    selenium.click("//div[contains(@id,'afterItem_panel')]/div/ul/li[24]");
	    
	    selenium.waitForPageToLoad("300000");
	  //div[@id='j_id19_j_id_2h:updateReasonCode']/div[2]/span
	  //div[@id='j_id19_j_id_2h:updateReasonCode_panel']/div/ul/li[2]
	    WebElement updateReasonCodeElement = driver.findElement(By.xpath("//div[contains(@id,'updateReasonCode')]/div[2]/span"));
	    System.out.println("updateReasonCodeElement.isDisplayed(): "+updateReasonCodeElement.isDisplayed());
	    System.out.println("updateReasonCodeElement.isEnabled(): "+updateReasonCodeElement.isEnabled());
	    selenium.focus("//div[contains(@id,'updateReasonCode')]/div[2]/span");	    
	    selenium.click("//div[contains(@id,'updateReasonCode')]/div[2]/span");
	    
	    WebElement updateReasonCodePanelElement = driver.findElement(By.xpath("//div[contains(@id,'updateReasonCode_panel')]/div/ul/li[2]"));
	    System.out.println("updateReasonCodePanelElement.isDisplayed(): "+updateReasonCodePanelElement.isDisplayed());
	    System.out.println("updateReasonCodePanelElement.isEnabled(): "+updateReasonCodePanelElement.isEnabled());
	    selenium.focus("//div[contains(@id,'updateReasonCode_panel')]/div/ul/li[2]");
	    selenium.click("//div[contains(@id,'updateReasonCode_panel')]/div/ul/li[2]");
	    
	    
	    selenium.waitForPageToLoad("300000");
	    
	    WebElement orgNameWayElement = driver.findElement(By.xpath("//div[contains(@id,'orgNameWay')]/div[2]/span"));
	    System.out.println("orgNameWayElement.isDisplayed(): "+orgNameWayElement.isDisplayed());
	    System.out.println("orgNameWayElement.isEnabled(): "+orgNameWayElement.isEnabled());
	    System.out.println("orgNameWayElement.isVisible(): "+selenium.isVisible("//div[contains(@id,'orgNameWay')]/div[2]/span"));
	    
	    selenium.focus("//div[contains(@id,'orgNameWay')]/div[2]/span");
	    selenium.click("//div[contains(@id,'orgNameWay')]/div[2]/span");
	    
	    WebElement orgNameWayPanelElement = driver.findElement(By.xpath("//div[contains(@id,'orgNameWay_panel')]/div/ul/li[3]"));
	    System.out.println("orgNameWayPanelElement.isDisplayed(): "+orgNameWayPanelElement.isDisplayed());
	    System.out.println("orgNameWayPanelElement.isEnabled(): "+orgNameWayPanelElement.isEnabled());	
	    selenium.focus("//div[contains(@id,'orgNameWay_panel')]/div/ul/li[3]");
	    selenium.click("//div[contains(@id,'orgNameWay_panel')]/div/ul/li[3]");
	    
	    
	    selenium.waitForPageToLoad("300000");
	    WebElement tabFine = driver.findElement(By.xpath("//a[contains(text(),'戶籍記事/罰鍰清單')]"));
	    builder.keyDown(Keys.CONTROL).click(tabFine).keyUp(Keys.CONTROL);
	    selenium.waitForPageToLoad("30000");
	    tabFine.click();
	    
	    
	    driver.switchTo().defaultContent();	    
	    // Following is the code that scrolls through the page
	    for (int second = 0;; second++) {
		if (second >= 3) {
		    break;
		}
		((RemoteWebDriver) driver).executeScript("window.scrollBy(0,200)", "");
		
		selenium.waitForPageToLoad("1000");
	    }
	    for (int second = 0;; second++) {
		if (second >= 10) {
		    break;
		}
		((RemoteWebDriver) driver).executeScript("window.scrollBy(0,-200)", "");
		
		selenium.waitForPageToLoad("1000");
	    }
	    selenium.focus("//a[contains(text(),'戶籍記事/罰鍰清單')]");
	    selenium.click("//a[contains(text(),'戶籍記事/罰鍰清單')]");
	    
	    selenium.focus("//button[4]");
	    //
//	    try {
//		Thread.sleep(60000);
//	    } catch (InterruptedException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	    }
	    ;
	    for(String btn: selenium.getAllButtons()){
		 System.out.println("btn: "+btn);
	    }
	   String btn01 = selenium.getText("//button[1]");
	   System.out.println("btn01: "+btn01);
	   
	   String btn02 = selenium.getText("//button[2]");
	   System.out.println("btn02: "+btn02);
	   
	   String btn03 = selenium.getText("//button[3]");
	   System.out.println("btn03: "+btn03);
	   
	   String btn04 = selenium.getText("//button[4]");
	   System.out.println("btn04: "+btn04);
	   
	   if(StringUtils.equalsIgnoreCase(StringUtils.trim(btn01),"資料驗證")){
	       selenium.click("//button[1]");
	   }
	   if(StringUtils.equalsIgnoreCase(StringUtils.trim(btn02),"資料驗證")){
	       selenium.click("//button[2]");
	   }
	   if(StringUtils.equalsIgnoreCase(StringUtils.trim(btn03),"資料驗證")){
	       selenium.click("//button[3]");
	   }
	   if(StringUtils.equalsIgnoreCase(StringUtils.trim(btn04),"資料驗證")){
	       selenium.click("//button[4]");//據說是關閉視窗
	   }
	   String verifyBtn = selenium.getText("//span[4]/button");//資料驗證
	   if(StringUtils.equalsIgnoreCase(StringUtils.trim(verifyBtn),"資料驗證")){
	       selenium.click("//span[4]/button");//據說是資料驗證
	   }
	   String tmpSaveBtn = selenium.getText("//span[4]/button[3]"); //暫存
	   System.out.println("tmpSaveBtn: "+tmpSaveBtn);
	   if(StringUtils.equalsIgnoreCase(StringUtils.trim(tmpSaveBtn),"暫存")){
//	       selenium.click("//span[4]/button[3]");//據說是暫存
//	       selenium.waitForPageToLoad("1000");
	       while(true){	    
		    String targetUrl = driver.getCurrentUrl();
		    System.out.println(targetUrl);
		    System.out.println("rl172Bclick.isVisible(): "+selenium.isVisible("//span[4]/button[3]"));
		    System.out.println("rl172Bclick.isVisible(): "+selenium.isEditable("//span[4]/button[3]") );
		    
		    if(selenium.isVisible("//span[4]/button[3]") && StringUtils.contains(targetUrl, "_rl0172b/rl0172b.xhtml")){
			selenium.click("//span[4]/button[3]");// 據說是暫存
			selenium.waitForPageToLoad("1000");
			if (!StringUtils.contains(driver.getCurrentUrl(), "_rl0172b/rl0172b.xhtml")) {
			    break;
			}
		    }else{
			break;
		    }
		    
		    
		}
	   }
	   selenium.waitForPageToLoad("1000"); 
	}
    }
}
