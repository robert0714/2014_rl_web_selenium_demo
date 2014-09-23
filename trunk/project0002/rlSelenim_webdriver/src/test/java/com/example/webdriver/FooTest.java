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
      SecuredPage securedPage = new SecuredPage(driver, project, "robert0714@gmail.com", "79111080");
      editIssue = new EditIssue(driver, securedPage);
//      editIssue = new EditIssue(driver );
    }

    @Test
    public void demonstrateNestedLoadableComponents() {
      editIssue.get();

      editIssue.setSummary("Summary");
      editIssue.enterDescription("This is an example");
    }

}
