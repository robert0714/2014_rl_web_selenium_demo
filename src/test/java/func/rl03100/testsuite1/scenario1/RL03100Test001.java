package func.rl03100.testsuite1.scenario1;

import java.util.List;
import java.util.Set;

import com.iisi.dao.TableJDBCDao;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

import func.rl.common.HouseholdMaintainPage;
import func.rl.common.PagePartialURL;
import func.rl.common.Rl00004Page;
import func.rl.common.Rl0172bPage;
import func.rl.common.Rl02a10Page;
import func.rl.common.Rl02d00Page;
import func.rl.common.Rl03100Page;
import func.rl.common.RlHompage;
import func.rl.common.TypingApplication;
import func.rl.common.WebUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RL03100Test001 {
    protected static Logger logger = Logger.getLogger(RL03100Test001.class);
    private Selenium selenium;
    private  WebDriver driver ;
    List<String[]> personIdSiteIdList;
    @Before
    public void setUp() throws Exception {
	final TableJDBCDao dao =new TableJDBCDao();
	DesiredCapabilities capabilities = new DesiredCapabilities();
	capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//	personIdSiteIdList = dao.getPersonIdSiteIdList();
//	 driver = WebUtils.linuxMachine();
//	 driver = WebUtils.windowsMachine();
	driver = new FirefoxDriver(capabilities);
	//http://192.168.9.94:6280/rl/pages/common/login.jsp
//	final String baseUrl = "http://192.168.10.18:6180";
	final String baseUrl = "http://192.168.10.18:6280/rl/";
//	final String baseUrl = "http://rlfl.ris.gov.tw/rl/";
	//http://rlfl.ris.gov.tw/rl/
	//http://rlfl.ris.gov.tw/rl/
	final Dimension targetSize = new Dimension(1500,860);
	driver.manage().window().setSize(targetSize);	
	selenium = new WebDriverBackedSelenium(driver, baseUrl);
	selenium.open(baseUrl);
    }
    
    @Test
    public void testRLLogin() throws Exception {
	final RlHompage homepage = new RlHompage(selenium,driver);
	homepage.login("RF1200123","RF1200123");
	if(CollectionUtils.isNotEmpty(personIdSiteIdList)){
	    for(String[] stringArray: personIdSiteIdList){
		selenium.waitForPageToLoad("30000");
		homepage.enterRl03100();
		selenium.waitForPageToLoad("30000");
		final String personId = stringArray[0];
		if (StringUtils.contains(personId, "*")) {
		    continue;
		}
		final String siteId = stringArray[1];

		
		try {
		    process4(personId,siteId);
		} catch (Exception e) {
		    e.printStackTrace();
		    WebUtils.handleRLAlert(selenium);
		}
	    }
	}else{
	    homepage.enterRl03100();
	    selenium.waitForPageToLoad("30000");
//	    process4("C100201902","65000120");
	    process4(null,null);
	}	
    }
    private void process4(final String personId, final String siteId)throws  Exception {
	Rl03100Page rl00004Page =new Rl03100Page(selenium,driver);
	rl00004Page.typeApplication(personId, siteId,"/home/weblogic/Desktop/PIC/");
    }

    @After
    public void tearDown() throws Exception {
    	 selenium.stop();
    }
}