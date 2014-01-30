package func.rl.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.thoughtworks.selenium.Selenium;

public class WebUtils {
    protected static final  Logger logger = Logger.getLogger(WebUtils.class);
    /****
     * 按鈕Xpath為clickBtnXpath點選後
     * 針對訊息作處理
     * 有錯誤訊息回傳 true,無錯誤訊息回傳false
     * ***/
   public static  boolean handleClickBtn(final  Selenium selenium,final String clickBtnXpath){
       boolean giveUpOperation=false ;
       selenium.click(clickBtnXpath);//據說是資料驗證
       selenium.waitForPageToLoad("60000");//等待6秒...不見得msg出來,改成60秒
       if( selenium.isElementPresent("//*[@id='growl2_container']/div/div")){
	   int count=0;	  
	   while(true){
	       String errorMessage =selenium.getText("//*[@id='growl2_container']/div/div/div[2]/span");
	       String errorExtMessage =selenium.getText("//*[@id='growl2_container']/div/div/div[2]/p");		     
	       logger.info(errorMessage);
	       logger.info(errorExtMessage);
	       selenium.click(clickBtnXpath);
	       if(count>3){
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
   /***
    * 讓捲軸下跑
    * **/
   public static void scroolbarDown(final  Selenium selenium,final  WebDriver driver){
	driver.switchTo().defaultContent();
	// Following is the code that scrolls through the page
	for (int second = 0;; second++) {
	    if (second >= 3) {
		break;
	    }
	    ((RemoteWebDriver) driver).executeScript("window.scrollBy(0,200)", "");

	    selenium.waitForPageToLoad("1000");
	}
   }
   /***
    * 讓捲軸上跑
    * **/
   public static void scroolbarUp(final  Selenium selenium,final  WebDriver driver){
	driver.switchTo().defaultContent();
	// Following is the code that scrolls through the page
	
	for (int second = 0;; second++) {
	    if (second >= 10) {
		break;
	    }
	    ((RemoteWebDriver) driver).executeScript("window.scrollBy(0,-200)", "");

	    selenium.waitForPageToLoad("1000");
	}
   }
    public static RemoteWebDriver windowsMachine() throws MalformedURLException {
	URL remoteAddress = new URL("http://192.168.9.23:4444/wd/hub");
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
	URL remoteAddress = new URL("http://192.168.9.27:4444/wd/hub");
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
    public static  boolean handleRLAlert(final  Selenium selenium){
	       boolean giveUpOperation=false ;
	       selenium.waitForPageToLoad("6000");
	       if( selenium.isElementPresent("//*[@id='growl2_container']/div/div")){
		   int count=0;	  
		   while(true){			      
		       String errorMessage =selenium.getText("//*[@id='growl2_container']/div/div/div[2]/span");
		       String errorExtMessage =selenium.getText("//*[@id='growl2_container']/div/div/div[2]/p");		     
		       logger.info(errorMessage);
		       logger.info(errorExtMessage);
		       if(count>3){
			   giveUpOperation=true;
			   break;
		       }
		       count++;
		   }
	       }
	       return giveUpOperation;
	   }
    public static void takeScreen(final  WebDriver driver,final File outputFile){
	 try {
	     if(driver instanceof FirefoxDriver){
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, outputFile);
	     }else{
		WebDriver augmentedDriver = new Augmenter().augment(driver);
		File scrFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, outputFile);
	     }
	     
	     
	  //Take screenshot and convert into byte[] 	    
//	    byte[] decodedScreenshot = Base64.decodeBase64(((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.BASE64).getBytes());	
//	    //write the byte array into your local folder	
//	    FileOutputStream fos = new FileOutputStream(outputFile); 
//	    fos.write(decodedScreenshot); 
//	    fos.close();

	    
	    
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
    public static Set<String> extract(final String expr,final String src){
	final Set<String> result =new HashSet<String>();
	final Pattern pattern = Pattern.compile(expr);
	final Matcher matcher = pattern.matcher(src);
	Set<String> set = new HashSet<String>();
	while (matcher.find()) {
	    String data = matcher.group();
	    result.add(data);
	}
	return result;
    }
}
