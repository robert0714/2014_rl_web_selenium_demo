package com.example.webdriver;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FooTest {

    private EditIssue editIssue;

    @Before
    public void prepareComponents() {
      WebDriver driver = new FirefoxDriver();

      ProjectPage project = new ProjectPage(driver, "selenium");
      SecuredPage securedPage = new SecuredPage(driver, project, "robert0714@gmail.com", "AAAAAA");
//      editIssue = new EditIssue(driver, securedPage);
      editIssue = new EditIssue(driver ,securedPage);
    }

    @Test
    public void demonstrateNestedLoadableComponents() {
      editIssue.get();

      editIssue.setSummary("Summary");
      editIssue.enterDescription("This is an example");
    }

}
