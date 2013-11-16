package org.robert.study.rl.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;

import com.thoughtworks.selenium.Selenium;

public class RlHompage {
	WebDriver driver;
	private Selenium selenium;
	public RlHompage(final WebDriver driver) {
		super();
		this.driver = driver;
		final String baseUrl = "http://192.168.9.94:6280/rl/";
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
		login() ;
	}
	public RlHompage(  Selenium selenium) {
		super();
		this . selenium = selenium;
		login() ;
	}

	private void login() {
		selenium.open("/rl/pages/common/login.jsp");
		selenium.type("name=j_username", "RF1200123");
		selenium.type("name=j_password", "RF1200123");
		selenium.click("css=input[type=\"submit\"]");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=戶籍查詢/登記/文件核發");
		selenium.waitForPageToLoad("30000");
	}
	public TypingApplication typingApplication(){
		return new TypingApplication(selenium);
	}
	
}
