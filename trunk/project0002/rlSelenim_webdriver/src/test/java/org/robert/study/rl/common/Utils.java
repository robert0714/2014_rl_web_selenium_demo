package org.robert.study.rl.common;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.thoughtworks.selenium.Selenium;

public class Utils {

    /****
     * 按鈕Xpath為clickBtnXpath點選後
     * 針對訊息作處理
     * 有錯誤訊息回傳 true,無錯誤訊息回傳false
     * ***/
   public static  boolean handleClickBtn(final  Selenium selenium,final String clickBtnXpath){
       boolean giveUpOperation=false ;
       selenium.click(clickBtnXpath);//據說是資料驗證
       selenium.waitForPageToLoad("6000");
       if( selenium.isElementPresent("//*[@id='growl2_container']/div/div")){
	   int count=0;	  
	   while(true){			      
	       String errorMessage =selenium.getText("//*[@id='growl2_container']/div/div/div[2]/span");
	       String errorExtMessage =selenium.getText("//*[@id='growl2_container']/div/div/div[2]/p");		     
	       System.out.println(errorMessage);
	       System.out.println(errorExtMessage);
	       selenium.click("//span[4]/button");
	       if(count>5){
		   giveUpOperation=true;
		   break;
	       }
	       count++;
	   }
       }
       return giveUpOperation;
   }
   /***
    * 讓捲軸上下跑
    * **/
   public static void scroolbarDownUp(final  Selenium selenium,final  WebDriver driver){
	driver.switchTo().defaultContent();
	// Following is the code that scrolls through the page
	for (int second = 0;; second++) {
	    if (second >= 3) {
		break;
	    }
	    ((RemoteWebDriver) driver).executeScript("window.scrollBy(0,200)", "");

	    selenium.waitForPageToLoad("1000");
	}
	for (int second = 0;; second++) {
	    if (second >= 10) {
		break;
	    }
	    ((RemoteWebDriver) driver).executeScript("window.scrollBy(0,-200)", "");

	    selenium.waitForPageToLoad("1000");
	}
   }

    public static RemoteWebDriver windowsMachine() throws MalformedURLException {
	URL remoteAddress = new URL("http://192.168.9.51:4444/wd/hub");
	// have tried using the below commented out lines as well, but in all
	// cases I face errors.
	// URL remoteAddress = new URL("http://mymachine:4444/grid/register");
	// URL remoteAddress = new URL("http://mymachine:4444/wd/hub");
	DesiredCapabilities dc = DesiredCapabilities.firefox();
	dc.setBrowserName("firefox");
	dc.setPlatform(Platform.WINDOWS);
	RemoteWebDriver driver = new RemoteWebDriver(remoteAddress, dc);
	return driver;
    }

    public static RemoteWebDriver linuxMachine() throws MalformedURLException {
	URL remoteAddress = new URL("http://192.168.9.51:4444/wd/hub");
	// have tried using the below commented out lines as well, but in all
	// cases I face errors.
	// URL remoteAddress = new URL("http://mymachine:4444/grid/register");
	// URL remoteAddress = new URL("http://mymachine:4444/wd/hub");
	DesiredCapabilities dc = DesiredCapabilities.firefox();
	dc.setBrowserName("firefox");
	dc.setPlatform(Platform.LINUX);
	RemoteWebDriver driver = new RemoteWebDriver(remoteAddress, dc);
	return driver;
    }
}
