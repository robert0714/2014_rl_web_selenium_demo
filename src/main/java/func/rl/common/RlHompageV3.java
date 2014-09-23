package func.rl.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.message.BasicNameValuePair;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.study.selenium.AbstractSeleniumV2TestCase;
import org.study.selenium.SeleniumConfig;

import com.thoughtworks.selenium.SeleniumException;

import func.rl00001.Rl00001PageV2;

public class RlHompageV3 {
    private final WebDriverWait wait ;
    private WebDriver driver;
    private final String patialUrl ="/rl/faces/pages/index.xhtml";
    private  final Logger logger = LoggerFactory.getLogger(getClass());

    public RlHompageV3(final WebDriver driver) throws UnhandledAlertException, SeleniumException {
        super();
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10 /*timeout in seconds*/);
    }

    private Map<String, String> retrieve(String query) {
        Map<String, String> result = new HashMap<String, String>();
        final String[] data = StringUtils.splitPreserveAllTokens(query, "&");
        for (String unit : data) {
            final String[] unitData = StringUtils.splitPreserveAllTokens(unit, "=");
            result.put(unitData[0], unitData[1]);
        }

        return result;
    }

    private List<BasicNameValuePair> retrieveParams(String query) {
        List<BasicNameValuePair> result = new ArrayList<BasicNameValuePair>();
        Map<String, String> map = retrieve(query);
        for (String key : map.keySet()) {
            result.add(new BasicNameValuePair(key, map.get(key)));
        }
        return result;
    }
    public SITLoginPageV3 sit(final WebDriver driver ) {
        return PageFactory.initElements(driver, SITLoginPageV3.class);
    }
    public SSOPageV3 uat(final WebDriver driver ) {
        return PageFactory.initElements(driver, SSOPageV3.class);
    }
    public void login(final WebDriver driver , final String user, final String passwd) throws UnhandledAlertException, SeleniumException {
        final String sitLoginPage = "/rl/pages/common/login.jsp";
        final String homapage ="/rl/faces/pages/index.xhtml";
        final String partialPage ="/rl/faces/pages";
        isAlertPresent();
        AbstractSeleniumV2TestCase.open(homapage);
        isAlertPresent();
        String currentUrl = driver.getCurrentUrl();
        logger.info("辨識基準頁面網址: {}" , currentUrl);
        if (StringUtils.contains(currentUrl, partialPage)) {
            AbstractSeleniumV2TestCase.open("/rl/");
            //http://rlfl.ris.gov.tw/rl/
        } else if (StringUtils.contains(currentUrl, sitLoginPage)) {
            final SITLoginPageV3 sit = new SITLoginPageV3(driver);
            sit.login(driver, user, passwd);
        } else {
            final SSOPageV3 uat = new SSOPageV3(driver);
            uat.get();
            uat.login(driver, user, passwd);
        }
        isAlertPresent();
    }
    public void login(final String user, final String passwd) throws UnhandledAlertException, SeleniumException {
        login(this.driver ,user, passwd);
    }
    
    /**
     * 進入現戶簿頁
     * ***/
    public void enterRl00001() {
	isAlertPresent() ;
	driver.manage().timeouts().pageLoadTimeout(SeleniumConfig.waitForPageToLoadS, TimeUnit.SECONDS);   
//	WebUtils.pageLoadTimeout(this.driver);
	
        this.wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='navmenu-v']/li")));
        
        logger.info("進入左邊選單: 登記作業");
        // 進入登記作業,
        
        this.driver.findElement(By.xpath("//*[@id='navmenu-v']/li")).click();
        
        final String rl00001Xpath = "//a[contains(@href, '/rl/faces/pages/func/rl00001/rl00001.xhtml')]";
         
        
        WebUtils.pageLoadTimeout(this.driver); 
        isAlertPresent();
        
        logger.info("進入左邊選單: 點選現戶簿頁");
        
        this.driver.findElement(By.xpath(rl00001Xpath)).click();
        
        isAlertPresent();
        final String currentUrl = this.driver.getCurrentUrl();
        
        this.logger.debug(currentUrl);        

    }   
    public void enterRl01Z00() {
	isAlertPresent() ;
	driver.manage().timeouts().pageLoadTimeout(SeleniumConfig.waitForPageToLoadS, TimeUnit.SECONDS);   
//	WebUtils.pageLoadTimeout(this.driver);
	
        this.wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='navmenu-v']/li")));
	
     // 進入戶籍申請書管理, 
        this.driver.findElement(By.xpath("//*[@id='navmenu-v']/li[10]")).click();
        
        final String rl01z00Xpath = "//a[contains(@href, '/rl/faces/pages/func/rl01z00/rl01z00.xhtml')]";
        
        WebUtils.pageLoadTimeout(this.driver);
//      isBeforeUnloadEventPresent();
        isAlertPresent();
      
        this.driver.findElement(By.xpath(rl01z00Xpath)).click();
        
        final String currentUrl = this.driver.getCurrentUrl();
        
        this.logger.debug(currentUrl);
    }
    
    private boolean isAlertPresent() {
	try {
	    Alert alert = driver.switchTo().alert();
	    this.logger.info(alert.getText());
	    alert.accept();
	    this.logger.debug("alert was present");
	    return true;
	} catch (NoAlertPresentException e) {
	    // Modal dialog showed
	    return false;
	}catch (UnhandledAlertException e) {
	    // Modal dialog showed
	    return false;
	}
    }
}
