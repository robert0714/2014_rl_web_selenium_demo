package com.example.tests;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class AppletTestingWebdriver {

    /**
     * https://seleniumonlinetrainingexpert.wordpress.com/category/course-content/selenium-java-applet-automation/
     * @param args
     */
    public static void main(String[] args) {
	WebDriver myTestDriver = new FirefoxDriver();
	myTestDriver.manage().window().maximize();

	myTestDriver.navigate().to("http://www.raditha.com/java/alert.php");

	try {
	    Thread.sleep(5000L);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	JavascriptExecutor js = (JavascriptExecutor) myTestDriver;
	js.executeScript("document.jsap.setText('This is a test of Robert');");
	myTestDriver.close();
    }
}
