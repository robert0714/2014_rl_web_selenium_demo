package func.rl031000.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.firefox.FirefoxDriver;


import func.rl.common.Rl03100Page;
import func.rl.common.RlHompage;
import func.rl.common.WebUtils;

public class App01 {
    protected static Logger logger = Logger.getLogger(App01.class); 
    /**
     * @param args
     */
    public static void main(String[] args) {
	final String baseUrl = "http://192.168.10.18:6280/rl/";
	final WebDriver driver = new FirefoxDriver();
	final Dimension targetSize = new Dimension(1500,860);
	driver.manage().window().setSize(targetSize);
	final WebDriverBackedSelenium selenium = new WebDriverBackedSelenium(driver, baseUrl);
	final List<String[]> personIdSiteIdList = getPerosnIdSiteId();
	final RlHompage homepage = new RlHompage(selenium,driver);
	for(String[] stringArray :personIdSiteIdList){
	    selenium.waitForPageToLoad("30000");
		homepage.enterRl03100();
		selenium.waitForPageToLoad("30000");
		final String personId = stringArray[0];
		if (StringUtils.contains(personId, "*")) {
		    continue;
		}
		final String siteId = stringArray[1];

		
		try {
		    Rl03100Page rl00004Page =new Rl03100Page(selenium,driver);
		    rl00004Page.typeApplication(personId, siteId,"/home/weblogic/Desktop/PIC/");
		} catch (Exception e) {
		    e.printStackTrace();
		    WebUtils.handleRLAlert(selenium);
		}
	}
    }
    public static List<String[]> getPerosnIdSiteId(){
	final List<String[]> personIdSiteIdList =new ArrayList<String[]>();
	personIdSiteIdList.add(new String[]{"C100201902","65000120"});
	
	return personIdSiteIdList;
    }
     
}
