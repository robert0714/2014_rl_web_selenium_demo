package func.rl00003.testsuite1.scenario1;

import java.util.List;
import java.util.Set;

import com.iisi.dao.TableJDBCDao;
import com.thoughtworks.selenium.Selenium;

import func.rl.common.HouseholdMaintainPage;
import func.rl.common.PagePartialURL;
import func.rl.common.Rl0172bPage;
import func.rl.common.Rl02a10Page;
import func.rl.common.RlHompage;
import func.rl.common.TypingApplication;
import func.rl.common.WebUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RLLoginTest {
    protected static Logger logger = Logger.getLogger(RLLoginTest.class);
    private Selenium selenium;
    private  WebDriver driver ;
    List<String[]> personIdSiteIdList;
    @Before
    public void setUp() throws Exception {
	final TableJDBCDao dao =new TableJDBCDao();
	personIdSiteIdList = dao.getPersonIdSiteIdList();
//	 driver = WebUtils.linuxMachine();
	 driver = WebUtils.windowsMachine();
//	driver = new FirefoxDriver();
	//http://192.168.9.94:6280/rl/pages/common/login.jsp
	final String baseUrl = "http://192.168.10.18:6180/rl/";
//	final String baseUrl = "http://192.168.10.18:6280/rl/";
	
	final Dimension targetSize = new Dimension(1500,860);
	driver.manage().window().setSize(targetSize);
	selenium = new WebDriverBackedSelenium(driver, baseUrl);
    }
    
    @Test
    public void testRLLogin() throws Exception {
	final RlHompage homepage = new RlHompage(selenium,driver);
//	homepage.login("RF1200123","RF1200123");
	//
	homepage.login("RQ0100123","RQ0100123");
	selenium.waitForPageToLoad("30000");
//	homepage.enterRl00001();
	if(CollectionUtils.isNotEmpty(personIdSiteIdList)){
	    for(String[] stringArray: personIdSiteIdList){
		
		final String personId = stringArray[0];
		if (StringUtils.contains(personId, "*")) {
		    continue;
		}
		final String siteId = stringArray[1];

		selenium.waitForPageToLoad("30000");
		// //div[contains(@id,'orgNameWay')]
		
		homepage.enterRl00001();
		try {
		    process(homepage, personId, siteId);
		} catch (Exception e) {
		    e.printStackTrace();
		    WebUtils.handleRLAlert(selenium);
		}
	    }
	}else{
	    homepage.enterRl00001();
	    process(homepage,null, null);
	}	
    }
    
    private void process(final RlHompage homepage  ,final String personId, final String siteId)throws  Exception {
	final TypingApplication aTypingApplication = homepage.typingApplication();
	selenium.waitForPageToLoad("30000");
	aTypingApplication.setPersonId(personId);
	aTypingApplication.setSiteId(siteId);
	aTypingApplication.typingApplication();
	
	if (selenium.isElementPresent("//input[contains(@id,'alert_flag')]")) {
	    selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
	}
	
	
	//http://192.168.10.18:6280/rl/faces/pages/func/rl00001/householdMaintain.xhtml?windowId=5ae
	HouseholdMaintainPage householdMaintainPage = null;
	
	if(StringUtils.contains(driver.getCurrentUrl(), "/rl00001/householdMaintain.xhtml")){
	    
	    selenium.waitForPageToLoad("300000");
	    householdMaintainPage = new HouseholdMaintainPage(  selenium,   driver);
	    selenium.waitForPageToLoad("300000");
	    while(!householdMaintainPage.switchTab()){
		logger.debug("轉不過去");
	    }
	   //發現所需延遲時間需要更久
	    selenium.waitForPageToLoad("300000");	   
	    if (selenium.isElementPresent("//input[contains(@id,'alert_flag')]")) {
		selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
	    }
	    householdMaintainPage.clickRl1722B();	   

	    
	}
	//http://192.168.10.18:6280/rl/faces/pages/func/rl00001/_rl0172b/rl0172b.xhtml?windowId=344
	int count =0;
	while(!StringUtils.contains(driver.getCurrentUrl(), "_rl0172b/rl0172b.xhtml")){
	    Thread.sleep(6000l);
	    if(count>5){
		break;
	    }
	    count++;
	}
	if(StringUtils.contains(driver.getCurrentUrl(), "_rl0172b/rl0172b.xhtml")){
	    selenium.waitForPageToLoad("300000");
	    Rl0172bPage rl0172bPage = new Rl0172bPage(  selenium,   driver);
	    rl0172bPage.switchTab();
	    selenium.waitForPageToLoad("300000");
	    
	    while (StringUtils.contains(driver.getCurrentUrl(), "_rl0172b/rl0172b.xhtml")) {
		logger.debug(driver.getCurrentUrl());
		if (StringUtils.contains(driver.getCurrentUrl(), "/rl00001/householdMaintain.xhtml")) {
		    break;
		}
		Thread.sleep(5000);
		selenium.refresh();
	    }
	}
	 WebUtils.scroolbarDownUp(selenium, driver);
	
	if (householdMaintainPage != null && StringUtils.contains(driver.getCurrentUrl(), PagePartialURL.householdMaintain.toString())) {
	    householdMaintainPage.processPrintView();
	    if (selenium.isElementPresent("//input[contains(@id,'alert_flag')]")) {
		selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
		selenium.refresh();
	    }
	    selenium.waitForPageToLoad("3000");
	    householdMaintainPage.processAppyCahange();
	}
	if (selenium.isElementPresent("//input[contains(@id,'alert_flag')]")) {
	    selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
	    selenium.refresh();
	}
	selenium.waitForPageToLoad("3000");
	//進入2A10
	Rl02a10Page rl02a10Page =new Rl02a10Page(selenium, driver);
	rl02a10Page.switchTab();
    }

    @After
    public void tearDown() throws Exception {
//	  selenium.click("id=logoutButton");
//	  selenium.waitForPageToLoad("30000");
//    	 selenium.stop();
    }
}