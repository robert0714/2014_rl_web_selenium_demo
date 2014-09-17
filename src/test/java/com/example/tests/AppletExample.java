package com.example.tests;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class AppletExample {

    /**
     * https://seleniumonlinetrainingexpert.wordpress.com/category/course-content/selenium-java-applet-automation/
     * @param args
     */
    public static void main(String[] args) {
	WebDriver myTestDriver = new FirefoxDriver();
	myTestDriver.manage().window().maximize();

	myTestDriver.navigate().to("http://docs.oracle.com/javase/tutorial/deployment/applet/iac.html");

	JavascriptExecutor js = (JavascriptExecutor) myTestDriver;
	for (int i = 0; i < 30; ++i) {
	    try {
		Thread.sleep(5000L);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	    js.executeScript("receiver.incrementCounter();");
	    /***
	     * <applet id="receiver" width="300" height="50" 
	     *   archive="examples/dist/applet_SenderReceiver/applet_SenderReceiver.jar" 
	     *   code="Receiver.class">
	     *   這支applet的id是receiver,裏頭有public void incrementCounter()
	     *   至於按鈕applet就不是那麼重要了
	     *   <applet width="300" height="50" 
	     *   archive="examples/dist/applet_SenderReceiver/applet_SenderReceiver.jar" 
	     *   code="Sender.class"
	     *   
	     * ***/
	    
	}
	myTestDriver.close();
    }
}
