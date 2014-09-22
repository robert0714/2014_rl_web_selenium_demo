package com.example.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class IssueList {
    private final WebDriver driver;

    public IssueList(WebDriver driver) {
	super();
	this.driver = driver;
	// This call sets the WebElement fields.
	PageFactory.initElements(driver, this);
    }

}
