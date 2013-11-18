package org.robert.study.rl.common;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;

import com.thoughtworks.selenium.Selenium;

public class Rl0172bPage {
    private WebDriver driver;
    private Selenium selenium;

    public Rl0172bPage(final Selenium selenium, final WebDriver driver)throws org.openqa.selenium.UnhandledAlertException  {
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
    public void switchTab()throws org.openqa.selenium.UnhandledAlertException  {
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
	    selenium.type("document.poopupForm.elements[17]", lastName);
	    selenium.type("document.poopupForm.elements[18]", firstName+lastName);
	    
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
	}
    }
}
