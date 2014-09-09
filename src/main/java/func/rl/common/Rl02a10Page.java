package func.rl.common;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;

public class Rl02a10Page {

	private  final Logger logger = LoggerFactory.getLogger(Rl02a10Page.class);
    private WebDriver driver;
    private Selenium selenium;

    private final String rl02a10PartialUlr ="_rl02a10/rl02a10.xhtml";
    public Rl02a10Page(final Selenium selenium, final WebDriver driver)throws  UnhandledAlertException,SeleniumException  {
	super();
	this.selenium = selenium;
	this.driver = driver;
    }
    public void switchTab()throws  UnhandledAlertException,SeleniumException, InterruptedException  {
	//進入2A10
		while(true){
		    Thread.sleep(1000);
		    if(StringUtils.contains(driver.getCurrentUrl(), "_rl02a10/rl02a10.xhtml")){
			logger.info("進入2A10");
			WebUtils.scroolbarDownUp(selenium, driver);
			if (selenium.isElementPresent("//*[@id='rl02a10infos_data']/tr/td[2]/div")) {
			    selenium.click("//*[@id='rl02a10infos_data']/tr/td[2]/div");
			}
			if (selenium.isElementPresent("//input[@id='tabViewId:agreedPrtOldPhoto:0']")) {
			    selenium.click("//input[@id='tabViewId:agreedPrtOldPhoto:0']");
			}
			
			selenium.click("//a[contains(text(),'申請人/受委託人')]");
			selenium.click("//a[contains(@href, '#tabViewId:familyMemberTab')]");
			selenium.click("//a[contains(text(),'申請人/受委託人')]");
			selenium.click("//a[contains(text(),'當事人/請領')]");
			WebUtils.scroolbarDown(selenium, driver);
			selenium.click("//fieldset[@id='rl02a10']/div/button[3]");//關閉視窗
			if (selenium.isElementPresent("//*[@id='confirmDialog2']")) {
			    //*[@id='j_id19_j_id_c3']
			    //*[@id='confirmDialog2']
			    //*[@id='confirm2']
			    //*[@id='decline2']
			    selenium.click("//*[@id='confirm2']");//確認
//			    selenium.click("//*[@id='decline2']");//取消
			}
			break;
		    }else if (StringUtils.contains(driver.getCurrentUrl(), "/rl00001/householdMaintain.xhtml")){
			break;
		    }else{
			break;
		    }
		  
		}
    }
}
