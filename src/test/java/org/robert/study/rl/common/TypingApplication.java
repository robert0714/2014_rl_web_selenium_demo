package org.robert.study.rl.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;

import com.thoughtworks.selenium.Selenium;

public class TypingApplication {
	WebDriver driver;
	private Selenium selenium;
	
	public TypingApplication(  Selenium selenium) {
		super();
		this . selenium = selenium;
		typingApplication() ;
	}

	private void typingApplication() {
		selenium.type("document.masterForm.elements[2]", "B120626995");
		selenium.type("document.masterForm.elements[3]", "65000120");
		selenium.click("document.masterForm.elements[15]");
		selenium.waitForPageToLoad("30000");
		selenium.type("document.masterForm.elements[29]", "M220872416");
		selenium.type("document.masterForm.elements[30]", "65000120");
		selenium.type("document.masterForm.elements[33]", "本人");
	}
	
}
