package func.rl00003.testsuite1.scenario1;

import java.util.List;
import java.util.Set;

import com.iisi.dao.TableJDBCDao;
import com.thoughtworks.selenium.Selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.robert.study.rl.common.Utils;
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
//	 driver = Utils.linuxMachine();
	 driver = Utils.windowsMachine();
//	driver = new FirefoxDriver();
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
		
		homepage.enterRl00001();
		process(homepage, personId, siteId);
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
	selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
	selenium.waitForPageToLoad("300000");
	
	Thread.sleep(6000l);
	
	selenium.waitForPageToLoad("300000");
	String currentUrl = driver.getCurrentUrl();
	//http://192.168.10.18:6280/rl/faces/pages/func/rl00001/householdMaintain.xhtml?windowId=5ae
	
	
	if(StringUtils.contains(currentUrl, "/rl00001/householdMaintain.xhtml")){
	    
	    
	    selenium.waitForPageToLoad("300000");
	    HouseholdMaintainPage householdMaintainPage = new HouseholdMaintainPage(  selenium,   driver);
	    selenium.waitForPageToLoad("300000");
	    Thread.sleep(6000l);
	    householdMaintainPage.switchTab();
	   //發現所需延遲時間需要更久
	    selenium.waitForPageToLoad("300000");
	    Thread.sleep(6000l);
	    selenium.waitForPageToLoad("300000");
	    Thread.sleep(6000l);
	    selenium.waitForPageToLoad("300000");
	    selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
	    householdMaintainPage.clickRl1722B();	   
//	   Thread.sleep(6000l);
	    
	}
	currentUrl = driver.getCurrentUrl();
	//http://192.168.10.18:6280/rl/faces/pages/func/rl00001/_rl0172b/rl0172b.xhtml?windowId=344
	
	
	if(StringUtils.contains(currentUrl, "_rl0172b/rl0172b.xhtml")){
	    selenium.waitForPageToLoad("300000");
	    Rl0172bPage rl0172bPage = new Rl0172bPage(  selenium,   driver);
	    rl0172bPage.switchTab();
	    selenium.waitForPageToLoad("300000");	    
	}
	
	while(true){	    
	    currentUrl = driver.getCurrentUrl();
	    System.out.println(currentUrl);
	    if(StringUtils.contains(currentUrl, "/rl00001/householdMaintain.xhtml")){
		break;
	    }
	    
	}
	
	 Utils.scroolbarDownUp(selenium, driver);
	
	
	
	boolean giveUpOperation=false ;
	
	//Save the WindowHandle of Parent Browser Window
	final String parentWindowId = driver.getWindowHandle();
	System.out.println("parentWindowId: "+parentWindowId);
	boolean printBtnXpathHit =false;
	final String printBtnXpath = "//div[contains(@id,'sx_content')]/button";
	
	if (selenium.isElementPresent(printBtnXpath)) {
	    final WebElement printBtn = driver.findElement(By.xpath(printBtnXpath));
	    final  String disabledAttribute = printBtn.getAttribute("disabled");
	    System.out.println("-----------------disabledAttribute: "+disabledAttribute);
	    if(StringUtils.equals(disabledAttribute, Boolean.TRUE.toString())){
		printBtnXpathHit=false;
	    }else if(disabledAttribute==null || StringUtils.equals(disabledAttribute, Boolean.FALSE.toString())){
		printBtnXpathHit=true;
		giveUpOperation=Utils.handleClickBtn(selenium, printBtnXpath);
	    }
	}
	
	if (!giveUpOperation && printBtnXpathHit) {
	    // 預覽申請書會彈跳出視窗
	    int count =0;
	    privntViewLoop: while (printBtnXpathHit) {
		Thread.sleep(5000);//建議5秒
		boolean printViewPresent = false;
		try {
		    final Set<String> windowHandles = driver.getWindowHandles();
		    browerWindowLoop:
		    for (final String windowId : windowHandles) {
			if (!StringUtils.equalsIgnoreCase(windowId, parentWindowId)) {
			    // Switch to the Help Popup Browser Window
			    driver.switchTo().window(windowId);
			    currentUrl=driver.getCurrentUrl();
			    System.out.println(currentUrl);	
			    if (StringUtils.contains(currentUrl, "common/popupContent.xhtml")) {
				// 戶役資訊服務網
				String title = driver.getTitle();
				System.out.println("title: " + title);

				// *[@id="j_id4_j_id_9:j_id_y"]/span
				// *[@id="j_id4_j_id_9:j_id_y"]
				selenium.click("//form/div/div/div/div[2]/button");// 端未列印
				// form/div/div/div/div[2]/button[2]
				// selenium.click("//form/div/div/div/div[2]/button[2]");//關閉
				printViewPresent = true;
				break browerWindowLoop;
			    }
			}
		    }
		} catch (NoSuchWindowException e) {
		    e.printStackTrace();
		}

		if (printViewPresent) {
		    // Close the Help Popup Window
		    driver.close();

		    // Move back to the Parent Browser Window
		    driver.switchTo().window(parentWindowId);
		    break privntViewLoop;
		}
		if(count>10){
		    break privntViewLoop;
		}
		count++;
	    }
	}
	//div[@id='j_id39_j_id_sx_content']/button
    }

    @After
    public void tearDown() throws Exception {
//	  selenium.click("id=logoutButton");
//	  selenium.waitForPageToLoad("30000");
//    	 selenium.stop();
    }
}