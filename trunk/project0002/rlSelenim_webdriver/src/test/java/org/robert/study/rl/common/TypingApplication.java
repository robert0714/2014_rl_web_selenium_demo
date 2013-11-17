package org.robert.study.rl.common;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;

import com.thoughtworks.selenium.Selenium;

public class TypingApplication {
	private WebDriver driver;
	private String personId;
	private String siteId;
	private Selenium selenium;
	
	public TypingApplication(final   Selenium selenium) {
		super();
		this . selenium = selenium;
//		typingApplication() ;
	}

	public void typingApplication() {
	    	selenium.type("document.masterForm.elements[3]", getPersonId());
	    	selenium.waitForPageToLoad("30000");
		selenium.type("document.masterForm.elements[4]", getSiteId());
		selenium.waitForPageToLoad("30000");
		selenium.click("document.masterForm.elements[14]");
		selenium.waitForPageToLoad("30000");
		
		selenium.type("document.masterForm.elements[15]", getPersonId());
	    	selenium.waitForPageToLoad("30000");
		selenium.type("document.masterForm.elements[16]", getSiteId());
		selenium.waitForPageToLoad("30000");
		selenium.type("document.masterForm.elements[19]", "本人");
		selenium.waitForPageToLoad("30000");
		
		
		selenium.click("document.masterForm.elements[42]");
//		selenium.click("//body/div[2]/div/div/div");
		selenium.waitForPageToLoad("30000");
//		selenium.type("document.masterForm.elements[33]", "本人");
	}

	public String getPersonId() {
	    if(StringUtils.isBlank(personId)){
		personId ="C100217366";
	    }
	    return personId;
	}

	public void setPersonId(String personId) {
	    this.personId = personId;
	}

	public String getSiteId() {
	    if(StringUtils.isBlank(siteId)){
		siteId ="65000120";
	    }
	    return siteId;
	}

	public void setSiteId(String siteId) {
	    this.siteId = siteId;
	}
	
}
