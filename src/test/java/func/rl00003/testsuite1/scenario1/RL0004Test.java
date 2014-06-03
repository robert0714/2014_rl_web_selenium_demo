package func.rl00003.testsuite1.scenario1;

import java.util.List;
import java.util.Set;

import com.iisi.dao.TableJDBCDao;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RL0004Test {
    protected static Logger logger = Logger.getLogger(RL0004Test.class);
    private Selenium selenium;
    private  WebDriver driver ;
    List<String[]> personIdSiteIdList;
    @Before
    public void setUp() throws Exception {
	final TableJDBCDao dao =new TableJDBCDao();
	personIdSiteIdList = dao.getPersonIdSiteIdList();
//	 driver = WebUtils.linuxMachine();
//	 driver = WebUtils.windowsMachine();
	driver = new FirefoxDriver();
	//http://192.168.9.94:6280/rl/pages/common/login.jsp
//	final String baseUrl = "http://192.168.10.18:6180";
	final String baseUrl = "http://192.168.10.18:6280/rl/";
	
	final Dimension targetSize = new Dimension(1500,860);
	driver.manage().window().setSize(targetSize);
	selenium = new WebDriverBackedSelenium(driver, baseUrl);
    }
    
    @Test
    public void testRLLogin() throws Exception {
	final RlHompage homepage = new RlHompage(selenium,driver);
	selenium.waitForPageToLoad("30000");
	if(CollectionUtils.isNotEmpty(personIdSiteIdList)){
	    for(String[] stringArray: personIdSiteIdList){
		
		final String personId = stringArray[0];
		if (StringUtils.contains(personId, "*")) {
		    continue;
		}
		final String siteId = stringArray[1];

		selenium.waitForPageToLoad("30000");
		// //div[contains(@id,'orgNameWay')]
		
		homepage.enterRl00004();
		try {
		    process(homepage, personId, siteId);
		} catch (Exception e) {
		    e.printStackTrace();
		    WebUtils.handleRLAlert(selenium);
		}
	    }
	}else{
	    homepage.enterRl00004();
	    process(homepage,null, null);
	}	
    }
    
    private void process(final RlHompage homepage  ,final String personId, final String siteId)throws  Exception {
	
    }

    @After
    public void tearDown() throws Exception {
//	  selenium.click("id=logoutButton");
//	  selenium.waitForPageToLoad("30000");
//    	 selenium.stop();
    }
}