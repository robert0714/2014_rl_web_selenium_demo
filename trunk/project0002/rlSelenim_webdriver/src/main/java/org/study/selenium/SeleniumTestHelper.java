package org.study.selenium;


import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

import func.rl.common.WebUtils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse; 
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.study.selenium.internal.AppInfo;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SeleniumTestHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(SeleniumTestHelper.class);
    private static final String seleniumServerUrl = "http://" + SeleniumConfig.getSeleniumServerHostName() + ":"
            + SeleniumConfig.getSeleniumServerPort() + "/";
    
    public static List<String> retrieveLocalIps(){
        String ipReg="\\d{1,}.\\d{1,}.\\d{1,}.\\d{1,}";
        final List<String> list = new ArrayList<String>();
        try {
            final Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                final NetworkInterface n = (NetworkInterface) e.nextElement();
                final  Enumeration<InetAddress> ee = n.getInetAddresses();
                while (ee.hasMoreElements()) {
                    final InetAddress i = (InetAddress) ee.nextElement();
                    LOGGER.info(i.getHostAddress());
                    if(i.getHostAddress().matches(ipReg)){
                        list.add(i.getHostAddress());
                    }
                }
            }
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            LOGGER.error("", e);
        }
        return list;
    }
    /**
     * @see "http://code.google.com/p/selenium/wiki/ChromeDriver"
     * @return
     * @throws MalformedURLException 
     */
    public static RisRemoteWebDriver initWebDriverV2(final WebDriver driver) throws MalformedURLException {        
        LOGGER.info("*** Starting selenium WebDriver ...");
       
        final List<String> ip4Address = retrieveLocalIps();
        final Dimension targetSize = new Dimension(1500, 860);
        driver.manage().window().setSize(targetSize);
        //        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); 
        String regIp =null;
        if (driver instanceof FirefoxDriver) {
            //使用local browser 進行操作
            for(String ip : ip4Address){
                if(!org.apache.commons.lang.StringUtils.equalsIgnoreCase("127.0.0.1", ip)){
                    regIp = ip;
                }
            }
        } else {
            //使用遙控別台機器browser
            final String remoteNodIp = WebUtils.getIPOfNode((RemoteWebDriver) driver); 
            regIp = remoteNodIp;
        }

        final AppInfo[] all = org.study.selenium.internal.AppInfo.values();
        for (AppInfo unit : all) {
            if (StringUtils.contains(regIp, unit.getPrefixPremoteIp())) { 
                
                
                if (!(driver instanceof JavascriptExecutor)) {
                    throw new IllegalStateException("Driver instance must support JS.");
                }
                if (!((HasCapabilities) driver).getCapabilities().isJavascriptEnabled()) {
                    throw new IllegalStateException("JS support must be enabled.");
                }
                driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); 
                return new RisRemoteWebDriver(unit.getAppUrl(), driver);
            }
        }
        final String baseUrl = String.format("http://%s:%s", SeleniumConfig.getSeleniumServerHostName(),SeleniumConfig.getSeleniumServerPort());
        return new RisRemoteWebDriver(baseUrl,driver);
    }
    
    /**
     * @see "http://code.google.com/p/selenium/wiki/ChromeDriver"
     * @return
     */
    public static Selenium initWebDriver(final WebDriver driver) {
        LOGGER.info("*** Starting selenium WebDriver ...");
       
        final List<String> ip4Address = retrieveLocalIps();
        final Dimension targetSize = new Dimension(1500, 860);
        driver.manage().window().setSize(targetSize);
        //        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); 
        String regIp =null;
        if (driver instanceof FirefoxDriver) {
            //使用local browser 進行操作
            for(String ip : ip4Address){
                if(!org.apache.commons.lang.StringUtils.equalsIgnoreCase("127.0.0.1", ip)){
                    regIp = ip;
                }
            }
        } else {
            //使用遙控別台機器browser
            final String remoteNodIp = WebUtils.getIPOfNode((RemoteWebDriver) driver); 
            regIp = remoteNodIp;
        }

        final AppInfo[] all = org.study.selenium.internal.AppInfo.values();
        for (AppInfo unit : all) {
            if (StringUtils.contains(regIp, unit.getPrefixPremoteIp())) {
                final Selenium selenium = new WebDriverBackedSelenium(driver, unit.getAppUrl());
                return selenium;
            }
        }
       
       //如果找步道可以切換...才用預設
        final DefaultSelenium defaultSelenium = new DefaultSelenium(SeleniumConfig.getSeleniumServerHostName(),
                SeleniumConfig.getSeleniumServerPort(), "*" + SeleniumConfig.getTargetBrowser(), "http://192.168.10.18:6280/rl/");
        defaultSelenium.start();
        
        return defaultSelenium;
    }

    public static void destroy(Selenium selenium) {
        LOGGER.info("*** Stopping selenium client driver ...");        
        selenium.stop();
    }
   
    

    private static JSONObject extractObject(HttpResponse resp) throws IOException, JSONException {
        InputStream contents = resp.getEntity().getContent();
        StringWriter writer = new StringWriter();
        IOUtils.copy(contents, writer, "UTF8");
        JSONObject objToReturn = new JSONObject(writer.toString());
        return objToReturn;
    }
}
