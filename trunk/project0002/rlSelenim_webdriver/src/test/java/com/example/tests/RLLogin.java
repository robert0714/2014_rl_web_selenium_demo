package com.example.tests;

import com.thoughtworks.selenium.Selenium;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.robert.study.rl.common.RlHompage;
import org.robert.study.rl.common.TypingApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.Pattern;
import static org.apache.commons.lang3.StringUtils.join;


public class RLLogin {
	private Selenium selenium;
	private  WebDriver driver ;
	@Before
	public void setUp() throws Exception {
	    	
		WebDriver driver = new FirefoxDriver();
		String baseUrl = "http://192.168.10.18:6280/rl/";
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
	}

	@Test
	public void testRLLogin() throws Exception {
		RlHompage homepage  =new RlHompage(selenium,driver);
		TypingApplication aTypingApplication = homepage.typingApplication();
	}

	@After
	public void tearDown() throws Exception {
//		selenium.click("id=logoutButton");
//		selenium.waitForPageToLoad("30000");
//		selenium.stop();
	}
}