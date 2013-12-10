package func.rl.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;

public class RlHompage {
    private  WebDriver driver;
    private Selenium selenium;
    protected final  Logger logger = Logger.getLogger(getClass());
    public RlHompage(final WebDriver driver) throws  UnhandledAlertException,SeleniumException  {
	super();
	this.driver = driver;
	//http://192.168.10.18:6180/rl/pages/common/login.jsp
	//http://192.168.9.94:6280/rl/pages/common/login.jsp
	final String baseUrl = "http://192.168.10.18:6180";
	selenium = new WebDriverBackedSelenium(driver, baseUrl);
	login();
    }

    public RlHompage(final Selenium selenium ,final WebDriver driver)throws  UnhandledAlertException,SeleniumException  {
	super();
	this.driver = driver;
	this.selenium = selenium;
	login();
    }

    private void login()throws  UnhandledAlertException,SeleniumException  {
	selenium.open("/rl/pages/common/login.jsp");//RF1203008
//	selenium.type("name=j_username", "RF1200123");
//	selenium.type("name=j_password", "RF1200123");
	
//	selenium.type("name=j_username", "RQ0700123");
//	selenium.type("name=j_password", "RQ0700123");
	
	selenium.type("name=j_username", "RF1203008");
	selenium.type("name=j_password", "RF1203008");
	
	selenium.click("css=input[type=\"submit\"]");
	
    }

    public TypingApplication typingApplication()throws  UnhandledAlertException,SeleniumException   {
	return new TypingApplication(selenium,driver);
    }
    protected void replacePageTest(){
	enterRl00001();
	selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;"); 
	enterRl00001();
	selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;"); 
	enterRl00001();
	selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;"); 
	enterRl00001();
	selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;"); 
	enterRl00001();
	selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;"); 
	enterRl00001();
	selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;"); 
	enterRl00001();
    }
    /**
     * 進入現戶簿頁
     * ***/
    public void enterRl00001() {
	
	while(selenium.isElementPresent("//*[@id='navmenu-v']/li") ){
	    
	    selenium.waitForPageToLoad("30000");
	    selenium.click("//*[@id='navmenu-v']/li");// 進入登記作業,
	    selenium.waitForPageToLoad("30000");
	    
	    final String rl00001Xpath = "//a[contains(@href, '/rl/faces/pages/func/rl00001/rl00001.xhtml')]";
	    
	    WebElement rl00001Element = driver.findElement(By.xpath(rl00001Xpath));
	    WebDriverWait wait = new WebDriverWait(driver, 10);
	    
	    wait.until(ExpectedConditions.visibilityOf(rl00001Element));    
	    
	    if (selenium.isVisible(rl00001Xpath) && selenium.isElementPresent(rl00001Xpath)) {
		
		if (selenium.isElementPresent("//input[contains(@id,'alert_flag')]")) {
		    selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
		}
		
		selenium.click(rl00001Xpath);// 進入現戶簿頁
		// selenium.open("/rl/faces/pages/func/rl00001/rl00001.xhtml");
		
		selenium.waitForPageToLoad("30000");
		String currentUrl = driver.getCurrentUrl();
		// http://192.168.10.18:6280/rl/faces/pages/func/rl00001/householdMaintain.xhtml?windowId=5ae

		logger.debug(currentUrl);
		if (StringUtils.contains(currentUrl, "rl00001/rl00001.xhtml")) {
		    break;
		}
	    }
	}
	
	
    }

    /**
     * 進入解鎖作業
     * ***/
    protected void enterRl00003() {
	selenium.waitForPageToLoad("30000");
	// selenium.click("//ul[@id='navmenu-v']/li[12]/ul/li[7]/a");
	selenium.click("//ul[@id='navmenu-v']/li[12]");// 進入解鎖作業,
	selenium.waitForPageToLoad("30000");
	selenium.open("/rl/faces/pages/func/rl00003/rl00003.xhtml");
	selenium.waitForPageToLoad("30000");
	////td/button
	selenium.click("//td/button");// 點選按鈕,
	selenium.waitForPageToLoad("30000");
    }
    /**
     * 進入文件核發
     * ***/
    public void enterRl00004() {
	while (selenium.isElementPresent("//*[@id='navmenu-v']/li")) {
	    selenium.waitForPageToLoad("30000");
	    selenium.click("//*[@id='navmenu-v']/li[3]/div/span");// 進入文件核發
	    selenium.waitForPageToLoad("30000");
	    selenium.click("//a[contains(@href, '/rl/faces/pages/func/rl00004/rl00004.xhtml')]");
	  //a[contains(@href, '/rl/faces/pages/func/rl00004/rl00004.xhtml')]
	    if (selenium.isElementPresent("//input[contains(@id,'alert_flag')]")) {
		selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
	    }
	    selenium.open("/rl/faces/pages/func/rl00004/rl00004.xhtml");
	    
	    if (StringUtils.contains(driver.getCurrentUrl(), "rl00004/rl00004.xhtml")) {
		break;
	    }
	}
    }
}
