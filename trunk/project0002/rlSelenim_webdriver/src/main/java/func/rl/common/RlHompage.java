package func.rl.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.DocFlavor.URL;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EncodingUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;

import func.rl00001.Rl00001Page;

public class RlHompage {
    private WebDriver driver;
    private Selenium selenium;
    private String user;
    private String passwd;
    private final String patialUrl ="/rl/faces/pages/index.xhtml";
    private  final Logger logger = LoggerFactory.getLogger(getClass());

    public RlHompage(final Selenium selenium, final WebDriver driver) throws UnhandledAlertException, SeleniumException {
        super();
        this.driver = driver;
        this.selenium = selenium;
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

    public void login(final Selenium selenium , final String user, final String passwd) throws UnhandledAlertException, SeleniumException {
        final String sitLoginPage = "/rl/pages/common/login.jsp";
        final String homapage ="/rl/faces/pages/index.xhtml";
        final String partialPage ="/rl/faces/pages";
        selenium.open(homapage);
        String currentUrl = driver.getCurrentUrl();
        logger.info("辨識基準頁面網址: {}" , currentUrl);
        
        
        if (StringUtils.contains(currentUrl, partialPage)) {
            selenium.open("/rl/");
            //http://rlfl.ris.gov.tw/rl/
        } else if (StringUtils.contains(currentUrl, sitLoginPage)) {
            //		selenium.type("name=j_username", getUser() );
            //		selenium.type("name=j_password", getPasswd() );
            selenium.type("name=j_username", user);
            selenium.type("name=j_password", passwd);
            selenium.click("css=input[type=\"submit\"]");
        } else {
            final String mainUrl = WebUtils .getMainUrl(currentUrl);
            //得到https://idpfl.ris.gov.tw:8443
            String openAuthorizationUrl = mainUrl + "/nidp/idff/sso?id=1&sid=1&option=credential&sid=1";//https://idpfl.ris.gov.tw:8443/nidp/idff/sso?id=1&sid=1&option=credential&sid=1
            selenium.open(openAuthorizationUrl);

            System.out.println(driver.getCurrentUrl());
            //		String user = getUser();
            //		String passwd =  getPasswd();
            selenium.type("//input[@name='Ecom_User_ID']", user);
            selenium.type("//input[@name='Ecom_Password']", passwd);
            selenium.click("//input[@name='loginButton2']");
            //https://idpfl.ris.gov.tw:8443/nidp/idff/sso?id=1&sid=1&option=credential&sid=1
            // 然後必須想辦法到target所指定網址
            selenium.open("/rl/");//http://rlfl.ris.gov.tw/rl/
        }
    }
    public void login(final String user, final String passwd) throws UnhandledAlertException, SeleniumException {
        login(this.selenium ,user, passwd);
    } 


    public Rl00001Page typingApplication() throws UnhandledAlertException, SeleniumException {
        return new Rl00001Page(this.selenium, this.driver);
    }

     
    public void enterRl01Z00() {
        while (this.selenium.isElementPresent("//*[@id='navmenu-v']/li")) {
            this.selenium.refresh();
            this.selenium.waitForPageToLoad("30000");
            this.selenium.click("//*[@id='navmenu-v']/li[10]");// 進入戶籍申請書管理, 
            this.selenium.waitForPageToLoad("30000");

            final String rl01z00Xpath = "//a[contains(@href, '/rl/faces/pages/func/rl01z00/rl01z00.xhtml')]"; 
//            WebElement rl00001Element = this.driver.findElement(By.xpath(rl00001Xpath));
//            WebDriverWait wait = new WebDriverWait(this.driver, 10);
//
//            wait.until(ExpectedConditions.visibilityOf(rl00001Element));

            if (this.selenium.isVisible(rl01z00Xpath) && this.selenium.isElementPresent(rl01z00Xpath)) {

                if (this.selenium.isElementPresent("//input[contains(@id,'alert_flag')]")) {
                    this.selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
                }

                this.selenium.click(rl01z00Xpath);
                this.selenium.waitForPageToLoad("7000");
               
                this.selenium.refresh();
                // 進入現戶簿頁
                // selenium.open("/rl/faces/pages/func/rl00001/rl00001.xhtml");

                this.selenium.waitForPageToLoad("30000");
                final String currentUrl = this.driver.getCurrentUrl();
                // http://192.168.10.18:6280/rl/faces/pages/func/rl00001/householdMaintain.xhtml?windowId=5ae

                this.logger.debug(currentUrl);
                if (StringUtils.contains(currentUrl, "rl01z00/rl01z00.xhtml")) {
                    if (this.selenium.isElementPresent("//input[contains(@id,'alert_flag')]")) {
                        this.selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
                    }
                    this.selenium.refresh();
                    break;
                }
            }
        }
    }
    /**
     * 進入現戶簿頁
     * ***/
    public void enterRl00001() {

        while (this.selenium.isElementPresent("//*[@id='navmenu-v']/li")) {
            this.selenium.refresh();
            this.selenium.waitForPageToLoad("30000");
            this.selenium.click("//*[@id='navmenu-v']/li");// 進入登記作業,
            this.selenium.waitForPageToLoad("30000");

            final String rl00001Xpath = "//a[contains(@href, '/rl/faces/pages/func/rl00001/rl00001.xhtml')]";

//            WebElement rl00001Element = this.driver.findElement(By.xpath(rl00001Xpath));
//            WebDriverWait wait = new WebDriverWait(this.driver, 10);
//
//            wait.until(ExpectedConditions.visibilityOf(rl00001Element));

            if (this.selenium.isVisible(rl00001Xpath) && this.selenium.isElementPresent(rl00001Xpath)) {

                if (this.selenium.isElementPresent("//input[contains(@id,'alert_flag')]")) {
                    this.selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
                }

                this.selenium.click(rl00001Xpath);
                this.selenium.waitForPageToLoad("7000");
               
                this.selenium.refresh();
                // 進入現戶簿頁
                // selenium.open("/rl/faces/pages/func/rl00001/rl00001.xhtml");

                this.selenium.waitForPageToLoad("30000");
                final String currentUrl = this.driver.getCurrentUrl();
                // http://192.168.10.18:6280/rl/faces/pages/func/rl00001/householdMaintain.xhtml?windowId=5ae

                this.logger.debug(currentUrl);
                if (StringUtils.contains(currentUrl, "rl00001/rl00001.xhtml")) {
                    if (this.selenium.isElementPresent("//input[contains(@id,'alert_flag')]")) {
                        this.selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
                    }
                    this.selenium.refresh();
                    break;
                }
            }
        }

    }

    /**
     * 進入解鎖作業
     * ***/
    public void enterRl03100() {
        this.selenium.waitForPageToLoad("30000");
        while (this.selenium.isElementPresent("//*[@id='navmenu-v']/li")) {
            this.selenium.waitForPageToLoad("30000");
            
         // 進入戶籍查詢作業
            if (this.selenium.isElementPresent("//*[@id='navmenu-v']/li[6]/div/span")) {
                this.selenium.focus("//*[@id='navmenu-v']/li[6]/div/span");
                this.selenium.click("//*[@id='navmenu-v']/li[6]/div/span");
                this.selenium.waitForPageToLoad("30000");
                if (this.selenium.isElementPresent("//input[contains(@id,'alert_flag')]")) {
                    this.selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
                }
                try {
                    if (this.selenium
                            .isElementPresent("//a[contains(@href, '/rl/faces/pages/func/rl03100/rl03100.xhtml')]")) {
                        this.selenium.click("//a[contains(@href, '/rl/faces/pages/func/rl03100/rl03100.xhtml')]");
                        this.selenium.waitForPageToLoad("30000");
                    }
                } catch (SeleniumException e) {
                    // TODO Auto-generated catch block
                    //如果點選左邊Menu失敗...改以url方式處理
//                    this.selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
                    this.selenium.open("/rl/faces/pages/func/rl03100/rl03100.xhtml");
                }

            }
            if (StringUtils.contains( this.selenium.getLocation(), "rl03100/rl03100.xhtml")) {
                break;
            }
        }
    }

    /**
     * 進入解鎖作業
     * ***/
    protected void enterRl00003() {
        this.selenium.waitForPageToLoad("30000");
        // selenium.click("//ul[@id='navmenu-v']/li[12]/ul/li[7]/a");
        this.selenium.click("//ul[@id='navmenu-v']/li[12]");// 進入解鎖作業,
        this.selenium.waitForPageToLoad("30000");
        this.selenium.open("/rl/faces/pages/func/rl00003/rl00003.xhtml");
        this.selenium.waitForPageToLoad("30000");
        ////td/button
        this.selenium.click("//td/button");// 點選按鈕,
        this.selenium.waitForPageToLoad("30000");
    }

    /**
     * 進入文件核發
     * ***/
    public void enterRl00004() {
        while (this.selenium.isElementPresent("//*[@id='navmenu-v']/li")) {
            this.selenium.waitForPageToLoad("30000");
            this.selenium.click("//*[@id='navmenu-v']/li[3]/div/span");// 進入文件核發
            this.selenium.waitForPageToLoad("30000");

            //a[contains(@href, '/rl/faces/pages/func/rl00004/rl00004.xhtml')]
            if (this.selenium.isElementPresent("//input[contains(@id,'alert_flag')]")) {
                this.selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
            }
            selenium.click("//a[contains(@href, '/rl/faces/pages/func/rl00004/rl00004.xhtml')]");
            //	    selenium.open("/rl/faces/pages/func/rl00004/rl00004.xhtml");

            if (StringUtils.contains(driver.getCurrentUrl(), "rl00004/rl00004.xhtml")) {
                break;
            }
        }
    }

    public String getUser() {
        if (StringUtils.isBlank(user)) {
            this.user = "RF1203008";
        }
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPasswd() {
        if (StringUtils.isBlank(passwd)) {
            this.passwd = "RF1203008";
        }
        return this.passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

}
