package org.robert.study.rl.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.MouseAction;

import com.thoughtworks.selenium.Selenium;

public class TypingApplication {
	private WebDriver driver;
	private String personId;
	private String siteId;
	private Map<String,String> siteIdMap;
	private Selenium selenium;
	
	public TypingApplication(final   Selenium selenium,final WebDriver driver) {
		super();
		this . selenium = selenium;
		this . siteIdMap = getSiteIdMap();
		this.driver =driver;
	}
	private Map<String,String> getSiteIdMap(){
		Properties props = new Properties();
		try {
		    props.load(TypingApplication.class.getResourceAsStream("RSCD0107.properties"));
		} catch (Exception e) {
		    e.printStackTrace();
		}
		final Set<Object> keys = props.keySet();
		final Map<String, String> siteIdMap = new HashMap<String, String>();
		for (Object key : keys) {
		    Object value = props.get(key);
		    siteIdMap.put(key.toString(), value.toString());
		}
		return siteIdMap;
	    }
	public void typingApplication() {
	    	String siteLocation =String.format("label=%s：%s", getSiteId(),siteIdMap.get(getSiteId()));
	    	selenium.type("document.masterForm.elements[3]", getPersonId());
	    	selenium.waitForPageToLoad("30000");
		selenium.type("document.masterForm.elements[4]",  "");
		Actions builder = new Actions(driver);
//		    driver.findElement(By.xpath("//input[@id='j_id37_j_id_4e:inputValue']")).sendKeys("444");
		WebElement selectorElement = driver.findElement(By.xpath("//input[contains(@id,'inputValue')]"));
		builder.keyDown(Keys.CONTROL).click(selectorElement).keyUp(Keys.CONTROL);
		selenium.waitForPageToLoad("30000");
		selectorElement.click();
		selenium.waitForPageToLoad("30000");
		selectorElement.sendKeys(getSiteId());
		selenium.waitForPageToLoad("30000");
//		selenium.runScript("document.getElementsByName('masterForm')[4].value = 10010070;"); 
//		selenium.select("//select[contains(@id,'items_input')]", "label=10010070：嘉義縣新港鄉");
		selenium.select("//select[contains(@id,'items_input')]", siteLocation);
		
		selenium.waitForPageToLoad("360000");
		selenium.click("document.masterForm.elements[14]");
		selenium.waitForPageToLoad("30000");
		
		selenium.type("document.masterForm.elements[15]", getPersonId());
	    	selenium.waitForPageToLoad("30000");
		selenium.type("document.masterForm.elements[16]",getSiteId());
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[contains(@id,'items_input')]", siteLocation);
		selenium.waitForPageToLoad("360000");
		selenium.click("document.masterForm.elements[16]");
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
		personId ="F208368696";
	    }
	    return personId;
	}

	public void setPersonId(String personId) {
	    this.personId = personId;
	}

	public String getSiteId() {
	    if(StringUtils.isBlank(siteId)){
		siteId ="10010070";
	    }
	    return siteId;
	}

	public void setSiteId(String siteId) {
	    this.siteId = siteId;
	}
	
}
