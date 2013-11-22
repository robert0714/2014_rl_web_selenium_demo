package org.robert.study.rl.common;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

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
	    selenium.type("document.poopupForm.elements[14]", lastName);
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
	    
	    
//	    selenium.type("//span[contains(@id,'afterMarriedName')]/span/input", firstName+lastName);
	    //afterFirstName
//	    selenium.type("document.poopupForm.elements[18]", firstName+lastName);
	  //select[contains(@id,'items_input')]"
	  //span[contains(@id,'afterMidenName')]/span/input 
	  //span[contains(@id,'afterMarriedName')]/span/input
	    
	    System.out.println("element1: "+element01);
	    System.out.println("element2: "+selenium.getText("document.poopupForm.elements[2]"));
	    System.out.println("element3: "+selenium.getText("document.poopupForm.elements[3]"));
	    System.out.println("element4: "+selenium.getText("document.poopupForm.elements[4]"));
	    System.out.println("element5: "+selenium.getText("document.poopupForm.elements[5]"));
	    System.out.println("element6: "+selenium.getText("document.poopupForm.elements[6]"));
	    System.out.println("element7: "+selenium.getText("document.poopupForm.elements[7]"));
	    System.out.println("element8: "+selenium.getText("document.poopupForm.elements[8]"));
	    System.out.println("element9: "+selenium.getText("document.poopupForm.elements[9]"));
	    System.out.println("element10: "+selenium.getText("document.poopupForm.elements[10]"));
	    System.out.println("element11: "+selenium.getText("document.poopupForm.elements[11]"));
	    System.out.println("element12: "+selenium.getText("document.poopupForm.elements[12]"));
	    System.out.println("element13: "+selenium.getText("document.poopupForm.elements[13]"));
	    System.out.println("element14: "+selenium.getText("document.poopupForm.elements[14]"));
	    System.out.println("element15: "+selenium.getText("document.poopupForm.elements[15]"));
	    System.out.println("element16: "+selenium.getText("document.poopupForm.elements[16]"));
	    System.out.println("element17: "+selenium.getText("document.poopupForm.elements[17]"));
	    System.out.println("element18: "+selenium.getText("document.poopupForm.elements[18]"));
	    
	    selenium.click("//div[contains(@id,'afterItem')]/label");
	    selenium.click("//div[contains(@id,'afterItem_panel')]/div/ul/li[24]");
	    
	    selenium.waitForPageToLoad("300000");
	  //div[@id='j_id19_j_id_2h:updateReasonCode']/div[2]/span
	  //div[@id='j_id19_j_id_2h:updateReasonCode_panel']/div/ul/li[2]
	    selenium.click("//div[contains(@id,'updateReasonCode')]/div[2]/span");
	    selenium.click("//div[contains(@id,'updateReasonCode_panel')]/div/ul/li[2]");
	    
	    
	    selenium.waitForPageToLoad("300000");
	  //div[@id='j_id19_j_id_2h:orgNameWay']/div[2]/span
	  //div[@id='j_id19_j_id_2h:orgNameWay_panel']/div/ul/li[3]
	    selenium.click("//div[contains(@id,'orgNameWay')]/div[2]/span");
	    selenium.click("//div[contains(@id,'orgNameWay_panel')]/div/ul/li[3]");
	    selenium.waitForPageToLoad("300000");
//	    Actions builder = new Actions(driver);
//	    WebElement selector01Element = driver.findElement(By.xpath("//input[contains(@id,'afterItem')]"));
//	    builder.keyDown(Keys.CONTROL).click(selector01Element).keyUp(Keys.CONTROL);
//	    selenium.waitForPageToLoad("30000");
//	    selector01Element.click();
//	    selenium.waitForPageToLoad("30000");
//	    selector01Element.sendKeys("Z：另立名字");
//	    
//	    WebElement selector02Element = driver.findElement(By.xpath("//input[contains(@id,'updateReasonCode')]"));
//	    builder.keyDown(Keys.CONTROL).click(selector02Element).keyUp(Keys.CONTROL);
//	    selenium.waitForPageToLoad("30000");
//	    selector02Element.click();
//	    selenium.waitForPageToLoad("30000");
//	    selector02Element.sendKeys("Z：另立名字");
//	    
//	    WebElement selector03Element = driver.findElement(By.xpath("//input[contains(@id,'updateYyymmddPanel')]"));
//	    builder.keyDown(Keys.CONTROL).click(selector03Element).keyUp(Keys.CONTROL);
//	    selenium.waitForPageToLoad("30000");
//	    selector03Element.click();
//	    selenium.waitForPageToLoad("30000");
//	    selector03Element.sendKeys("Z：另立名字");
	    
	    WebElement tabFine = driver.findElement(By.xpath("//a[contains(text(),'戶籍記事/罰鍰清單')]"));
	    builder.keyDown(Keys.CONTROL).click(tabFine).keyUp(Keys.CONTROL);
	    selenium.waitForPageToLoad("30000");
	    tabFine.click();
	    
//	    selenium.click("//a[contains(text(),'戶籍記事/罰鍰清單')]");
//	    selenium.click("//button[4]");
	    Actions dragger = new Actions(driver);

	    WebElement draggablePartOfScrollbar = driver.findElement(By.xpath("//table[contains(@id,'risSystemTableForm')]"));
	    int numberOfPixelsToDragTheScrollbarDown = 50;

	    for (int i = 10; i < 500; i = i + numberOfPixelsToDragTheScrollbarDown) {
		try {
		    // this causes a gradual drag of the scroll bar, 10 units at a time
		    dragger.moveToElement(draggablePartOfScrollbar).clickAndHold().moveByOffset(0, numberOfPixelsToDragTheScrollbarDown).release().perform();
		    Thread.sleep(1000L);
		} catch (Exception e1) {
		}
	    }

	    // now drag opposite way (downwards)
	    numberOfPixelsToDragTheScrollbarDown = -50;
	    for (int i = 500; i > 10; i = i + numberOfPixelsToDragTheScrollbarDown) {
		// this causes a gradual drag of the scroll bar, -10 units at a time
		dragger.moveToElement(draggablePartOfScrollbar).clickAndHold().moveByOffset(0, numberOfPixelsToDragTheScrollbarDown).release().perform();
		try {
		    Thread.sleep(1000L);
		} catch (InterruptedException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }
	}
    }
}
