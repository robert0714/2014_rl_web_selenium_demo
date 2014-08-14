package org.study.selenium;

import bsh.StringUtil;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

import func.rl.common.WebUtils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class SeleniumTestHelper {

    private static final Logger logger = Logger.getLogger(SeleniumTestHelper.class);
    private static final String appUrl = "http://" + SeleniumConfig.getApplicationServerHostName() + ":"
            + SeleniumConfig.getApplicationServerPort() + "/rl/";
    private static final String seleniumServerUrl = "http://" + SeleniumConfig.getSeleniumServerHostName() + ":"
            + SeleniumConfig.getSeleniumServerPort() + "/";
    
    public static DefaultSelenium init() {
        logger.info("*** Starting selenium client driver ...");
        
        
        final DefaultSelenium defaultSelenium = new DefaultSelenium(SeleniumConfig.getSeleniumServerHostName(),
                SeleniumConfig.getSeleniumServerPort(), "*" + SeleniumConfig.getTargetBrowser(), appUrl);
        defaultSelenium.start();
        return defaultSelenium;
    }

    /**
     * @see "http://code.google.com/p/selenium/wiki/ChromeDriver"
     * @return
     */
    public static Selenium initWebDriver(final WebDriver driver) {
        logger.info("*** Starting selenium WebDriver ...");

//        final WebDriver driver = new FirefoxDriver();
        final Dimension targetSize = new Dimension(1500, 860);
        driver.manage().window().setSize(targetSize); 
        
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        final Selenium selenium = new WebDriverBackedSelenium(driver, appUrl);
//        new WebDriverBackedSelenium
//        selenium.start();      
        return selenium;
    }

    public static void destroy(Selenium selenium) {
        logger.info("*** Stopping selenium client driver ...");        
        selenium.stop();
    }

    public static RemoteWebDriver getWindowsMachine(final String spcificIp) throws MalformedURLException {        
        final RemoteWebDriver driver = WebUtils.getWindowsMachine(spcificIp, SeleniumConfig.getApplicationServerHostName() ,SeleniumConfig.getSeleniumServerPort());
        System.out.println(getIPOfNode(driver));
        return driver;
    }
    /***
     * 可以得到是遙控哪一個node的ip
     * */
    private static String getIPOfNode(RemoteWebDriver remoteDriver) {
        String hostFound = null;
        try {
            HttpCommandExecutor ce = (HttpCommandExecutor) remoteDriver.getCommandExecutor();
            String hostName = ce.getAddressOfRemoteServer().getHost();
            int port = ce.getAddressOfRemoteServer().getPort();
            HttpHost host = new HttpHost(hostName, port);
            DefaultHttpClient client = new DefaultHttpClient();
            URL sessionURL = new URL("http://" + hostName + ":" + port + "/grid/api/testsession?session="
                    + remoteDriver.getSessionId());
            BasicHttpEntityEnclosingRequest r = new BasicHttpEntityEnclosingRequest("POST", sessionURL.toExternalForm());
            HttpResponse response = client.execute(host, r);
            JSONObject object = extractObject(response);
            URL myURL = new URL(object.getString("proxyId"));
            if ((myURL.getHost() != null) && (myURL.getPort() != -1)) {
                hostFound = myURL.getHost();
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return hostFound;
    }

    private static JSONObject extractObject(HttpResponse resp) throws IOException, JSONException {
        InputStream contents = resp.getEntity().getContent();
        StringWriter writer = new StringWriter();
        IOUtils.copy(contents, writer, "UTF8");
        JSONObject objToReturn = new JSONObject(writer.toString());
        return objToReturn;
    }
}
