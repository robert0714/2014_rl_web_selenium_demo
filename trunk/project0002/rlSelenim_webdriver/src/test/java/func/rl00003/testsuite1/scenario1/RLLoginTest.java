package func.rl00003.testsuite1.scenario1;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;

import com.gargoylesoftware.htmlunit.util.UrlUtils;
import com.iisi.dao.TableJDBCDao;
import com.thoughtworks.selenium.Selenium;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.robert.study.rl.common.HouseholdMaintainPage;
import org.robert.study.rl.common.Rl0172bPage;
import org.robert.study.rl.common.RlHompage;
import org.robert.study.rl.common.TypingApplication;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RLLoginTest {
    private Selenium selenium;
    private  WebDriver driver ;
    List<String[]> personIdSiteIdList;
    @Before
    public void setUp() throws Exception {
	final TableJDBCDao dao =new TableJDBCDao();
	personIdSiteIdList = dao.getPersonIdSiteIdList();
//	 driver = linuxMachine();
	driver = new FirefoxDriver();
	final String baseUrl = "http://192.168.10.18:6280/rl/";

	
	selenium = new WebDriverBackedSelenium(driver, baseUrl);
	
	
    }
    public RemoteWebDriver linuxMachine() throws MalformedURLException{	
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
    @Test
    public void testRLLogin() throws Exception {
	if(CollectionUtils.isNotEmpty(personIdSiteIdList)){
	    for(String[] stringArray: personIdSiteIdList){
		try {
		    final String personId = stringArray[0];
		    if(StringUtils.contains(personId, "*")){
			continue;
		    }
		    final String siteId = stringArray[1];
		    process(personId, siteId);
		    selenium.waitForPageToLoad("30000");
		    Thread.sleep(6000l);
		    selenium.waitForPageToLoad("30000");
		    try {
			// Get the Alert
			Alert alert = driver.switchTo().alert();
			if (alert != null) {
			// Get the Text displayed on Alert using getText() method of Alert class
			String textOnAlert = alert.getText();

			// Click OK button, by calling accept() method of Alert Class
			alert.accept();

			// Verify Alert displayed correct message to user
			// assertEquals("Hello! I am an alert box!",textOnAlert);
			}
		    } catch (NoAlertPresentException e1) {
			
		    }
		    Actions action = new Actions(driver);
		    action.sendKeys(Keys.ESCAPE);
		    
		    String currentWindowId = driver.getWindowHandle();
		    
		    //Code that brings up the popup
		    Set<String> allWindows = driver.getWindowHandles();
		    if (!allWindows.isEmpty()) {
			for (String windowId : allWindows) {
			    driver.switchTo().window(windowId);

			    if (driver.getPageSource().contains("Build my Car - Configuration - Online Chat")) {
				try {

				    // Find the Close Button on Chat Popup Window and close the Popup
				    // by clicking Close Button instead of closing it directly
				    WebElement closeButton = driver.findElement(By.id("closebutton"));
				    closeButton.click();
				    break;
				} catch (NoSuchWindowException e) {
				    e.printStackTrace();
				}
			    }
			}
		    }
		    // Move back to the Parent Browser Window
		    driver.switchTo().window(currentWindowId);

		    String mainhndl = driver.getWindowHandle();

		    for (String handle : allWindows) {
			if (!mainhndl.equals(handle)) {
			    WebDriver popup = driver.switchTo().window(handle);
			    popup.close();
			}
		    }
		} catch (org.openqa.selenium.UnhandledAlertException  e) {
		    System.out.println("..........................................");
		    e.printStackTrace();
		}catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	}else{
	    process(null, null);
	}	
    }
    private String retireveWindowId(final String link){
	if(StringUtils.contains(link, "windowId=") && StringUtils.contains(link, "?")){
	    String[] predata = StringUtils.splitPreserveAllTokens(link, "?");
	    if(predata.length>1){
		return predata[1];
	    }
	}
	return null;
    }
    private void process(final String personId, final String siteId) throws org.openqa.selenium.UnhandledAlertException{
	final RlHompage homepage = new RlHompage(selenium,driver);
	selenium.waitForPageToLoad("30000");
	final TypingApplication aTypingApplication = homepage.typingApplication();
	selenium.waitForPageToLoad("30000");
	aTypingApplication.setPersonId(personId);
	aTypingApplication.setSiteId(siteId);
	aTypingApplication.typingApplication();
	selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
	selenium.waitForPageToLoad("300000");
	try {
	    Thread.sleep(6000l);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	selenium.waitForPageToLoad("300000");
	String currentUrl = driver.getCurrentUrl();
	//http://192.168.10.18:6280/rl/faces/pages/func/rl00001/householdMaintain.xhtml?windowId=5ae
	System.out.println(currentUrl);
	if(StringUtils.contains(currentUrl, "/rl00001/householdMaintain.xhtml")){
	    selenium.waitForPageToLoad("300000");
	    HouseholdMaintainPage householdMaintainPage = new HouseholdMaintainPage(  selenium,   driver);
	    selenium.waitForPageToLoad("300000");
	    householdMaintainPage.switchTab();
	    selenium.waitForPageToLoad("300000");
	    selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
	    householdMaintainPage.clickRl1722B(currentUrl);
	    try {
		Thread.sleep(6000l);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
	currentUrl = driver.getCurrentUrl();
	//http://192.168.10.18:6280/rl/faces/pages/func/rl00001/_rl0172b/rl0172b.xhtml?windowId=344
	if(StringUtils.contains(currentUrl, "_rl0172b/rl0172b.xhtml")){
	    selenium.waitForPageToLoad("300000");
	    Rl0172bPage rl0172bPage = new Rl0172bPage(  selenium,   driver);
	    rl0172bPage.switchTab();
	    
	    
	}
//	selenium.stop();
//	try {
//
//	    // Get the Alert
//	    Alert alert = driver.switchTo().alert();
//
//	    // Get the Text displayed on Alert using getText() method of Alert class
//	    String textOnAlert = alert.getText();
//
//	    // Click OK button, by calling accept() method of Alert Class
//	    alert.accept();
//
//	    // Verify Alert displayed correct message to user
//	    // assertEquals("Hello! I am an alert box!",textOnAlert);
//
//	} catch (NoAlertPresentException e) {
	
//	}
    }

    @After
    public void tearDown() throws Exception {
	// selenium.click("id=logoutButton");
	// selenium.waitForPageToLoad("30000");
//	 selenium.stop();
    }
}