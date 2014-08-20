package org.study.selenium;


import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

import func.rl.common.WebUtils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.study.selenium.internal.AppInfo;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

public class SeleniumTestHelper {

    private static final Logger logger = Logger.getLogger(SeleniumTestHelper.class);
    private static final String seleniumServerUrl = "http://" + SeleniumConfig.getSeleniumServerHostName() + ":"
            + SeleniumConfig.getSeleniumServerPort() + "/";
    
    /**
     * @see "http://code.google.com/p/selenium/wiki/ChromeDriver"
     * @return
     */
    public static Selenium initWebDriver(final RemoteWebDriver driver) {
        logger.info("*** Starting selenium WebDriver ...");
        final Dimension targetSize = new Dimension(1500, 860);
        driver.manage().window().setSize(targetSize);         
//        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        
        final String remoteNodIp = WebUtils.getIPOfNode(driver);
        
        final AppInfo[] all = org.study.selenium.internal.AppInfo.values();
        
        for(AppInfo unit : all){
            if(StringUtils.contains(remoteNodIp, unit.getPrefixPremoteIp())){
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
        logger.info("*** Stopping selenium client driver ...");        
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
