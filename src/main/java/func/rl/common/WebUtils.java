package func.rl.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.thoughtworks.selenium.Selenium;

// TODO: Auto-generated Javadoc
/**
 * The Class WebUtils.
 */
public class WebUtils {
    
    /** The Constant logger. */
    protected static final Logger logger = Logger.getLogger(WebUtils.class);

    /**
     * **
     * 按鈕Xpath為clickBtnXpath點選後
     * 針對訊息作處理
     * 有錯誤訊息回傳 true,無錯誤訊息回傳false .
     *
     * @param selenium the selenium
     * @param clickBtnXpath the click btn xpath
     * @return true, if successful
     */
    public static boolean handleClickBtn(final Selenium selenium, final String clickBtnXpath) {
        boolean giveUpOperation = false;
        selenium.click(clickBtnXpath);//據說是資料驗證
        selenium.waitForPageToLoad("60000");//等待6秒...不見得msg出來,改成60秒
        if (selenium.isElementPresent("//*[@id='growl2_container']/div/div")) {
            int count = 0;
            while (true) {
                String errorMessage = selenium.getText("//*[@id='growl2_container']/div/div/div[2]/span");
                String errorExtMessage = selenium.getText("//*[@id='growl2_container']/div/div/div[2]/p");
                logger.info(errorMessage);
                logger.info(errorExtMessage);
                selenium.click(clickBtnXpath);
                if (count > 3) {
                    giveUpOperation = true;
                    break;
                }
                count++;
            }
        }
        return giveUpOperation;
    }

    /**
     * *
     * 讓捲軸上下跑
     * *.
     *
     * @param selenium the selenium
     * @param driver the driver
     */
    public static void scroolbarDownUp(final Selenium selenium, final WebDriver driver) {
        driver.switchTo().defaultContent();
        // Following is the code that scrolls through the page
        for (int second = 0;; second++) {
            if (second >= 3) {
                break;
            }
            ((RemoteWebDriver) driver).executeScript("window.scrollBy(0,200)", "");

            selenium.waitForPageToLoad("1000");
        }
        for (int second = 0;; second++) {
            if (second >= 10) {
                break;
            }
            ((RemoteWebDriver) driver).executeScript("window.scrollBy(0,-200)", "");

            selenium.waitForPageToLoad("1000");
        }
    }

    /**
     * *
     * 讓捲軸下跑
     * *.
     *
     * @param selenium the selenium
     * @param driver the driver
     */
    public static void scroolbarDown(final Selenium selenium, final WebDriver driver) {
        driver.switchTo().defaultContent();
        // Following is the code that scrolls through the page
        for (int second = 0;; second++) {
            if (second >= 3) {
                break;
            }
            ((RemoteWebDriver) driver).executeScript("window.scrollBy(0,200)", "");

            selenium.waitForPageToLoad("1000");
        }
    }

    /**
     * *
     * 讓捲軸上跑
     * *.
     *
     * @param selenium the selenium
     * @param driver the driver
     */
    public static void scroolbarUp(final Selenium selenium, final WebDriver driver) {
        driver.switchTo().defaultContent();
        // Following is the code that scrolls through the page

        for (int second = 0;; second++) {
            if (second >= 10) {
                break;
            }
            ((RemoteWebDriver) driver).executeScript("window.scrollBy(0,-200)", "");

            selenium.waitForPageToLoad("1000");
        }
    }

    /**
     * Windows machine.
     *
     * @return the remote web driver
     * @throws MalformedURLException the malformed url exception
     */
    public static RemoteWebDriver windowsMachine() throws MalformedURLException {
        final RemoteWebDriver driver = getWindowsMachine("192.168.9.47", "192.168.10.20", 4444);
        return driver;
    }

    /**
     * Gets the windows machine.
     *
     * @param spcificIp the spcific ip
     * @param seleniumServerUrl the selenium server url
     * @param seleniumServerPort the selenium server port
     * @return the windows machine
     * @throws MalformedURLException the malformed url exception
     */
    public static RemoteWebDriver getWindowsMachine(final String spcificIp, final String seleniumServerUrl,
            final int seleniumServerPort) throws MalformedURLException {
        final RemoteWebDriver driver = getMachine(Platform.WINDOWS, spcificIp, seleniumServerUrl, seleniumServerPort);
        return driver;
    }
    /**
     * Linux machine.
     *
     * @return the remote web driver
     * @throws MalformedURLException the malformed url exception
     */
    public static RemoteWebDriver linuxMachine() throws MalformedURLException {
        final RemoteWebDriver driver = getLinucMachine("192.168.9.47", "192.168.10.20", 4444);
        return driver;
    }
    public static RemoteWebDriver getLinucMachine(final String spcificIp, final String seleniumServerUrl,
            final int seleniumServerPort) throws MalformedURLException {
        final RemoteWebDriver driver = getMachine(Platform.LINUX, spcificIp, seleniumServerUrl, seleniumServerPort);
        return driver;
    }
    /**
     * Gets the machine.
     * 
     * http://127.0.0.1:5555/wd/hub/static/resource/hub.html
     * @param platmform the platmform
     * @param spcificIp the spcific ip
     * @param seleniumServerUrl the selenium server url
     * @param seleniumServerPort the selenium server port
     * @return the machine
     * @throws MalformedURLException the malformed url exception
     */
    public static RemoteWebDriver getMachine(final Platform platmform ,final String spcificIp, final String seleniumServerUrl,
            final int seleniumServerPort) throws MalformedURLException {
        final URL remoteAddress = new URL("http://" + seleniumServerUrl + ":" + seleniumServerPort + "/wd/hub");
        // have tried using the below commented out lines as well, but in all
        // cases I face errors.
        // URL remoteAddress = new URL("http://mymachine:4444/grid/register");
        // URL remoteAddress = new URL("http://mymachine:4444/wd/hub");
        final DesiredCapabilities dc = DesiredCapabilities.firefox();
        dc.setBrowserName(BrowserType.FIREFOX);
        dc.setPlatform(platmform);

        dc.setCapability("screenrecorder", true);
        dc.setCapability("screenshot", true);

        if (StringUtils.isNotBlank(spcificIp)) {
            //交代指明要遙控哪一台機器
            final String spificUrl = "http://" + spcificIp + ":5555";
            //ex: http://192.168.9.47:5555
            dc.setCapability("id", spificUrl);
        }

        final RemoteWebDriver driver = new RemoteWebDriver(remoteAddress, dc);
        System.out.println(getIPOfNode(driver));
        return driver;
    }
    /**
     * *
     * 可以得到是遙控哪一個node的ip.
     *
     * @param remoteDriver the remote driver
     * @return the IP of node
     */
    public static String getIPOfNode(RemoteWebDriver remoteDriver) {
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

    /**
     * Extract object.
     *
     * @param resp the resp
     * @return the JSON object
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws JSONException the JSON exception
     */
    private static JSONObject extractObject(HttpResponse resp) throws IOException, JSONException {
        InputStream contents = resp.getEntity().getContent();
        StringWriter writer = new StringWriter();
        IOUtils.copy(contents, writer, "UTF8");
        JSONObject objToReturn = new JSONObject(writer.toString());
        return objToReturn;
    }

   

    /**
     * Handle rl alert.
     *
     * @param selenium the selenium
     * @return true, if successful
     */
    public static boolean handleRLAlert(final Selenium selenium) {
        boolean giveUpOperation = false;
        selenium.waitForPageToLoad("6000");
        if (selenium.isElementPresent("//*[@id='growl2_container']/div/div")) {
            int count = 0;
            while (true) {
                String errorMessage = selenium.getText("//*[@id='growl2_container']/div/div/div[2]/span");
                String errorExtMessage = selenium.getText("//*[@id='growl2_container']/div/div/div[2]/p");
                logger.info(errorMessage);
                logger.info(errorExtMessage);
                if (count > 3) {
                    giveUpOperation = true;
                    break;
                }
                count++;
            }
        }
        return giveUpOperation;
    }

    /**
     * Take screen.
     *
     * @param driver the driver
     * @param outputFile the output file
     */
    public static void takeScreen(final WebDriver driver, final File outputFile) {
        try {
            if (driver instanceof FirefoxDriver) {
                File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(scrFile, outputFile);
            } else {
                WebDriver augmentedDriver = new Augmenter().augment(driver);
                File scrFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(scrFile, outputFile);
            }

            //Take screenshot and convert into byte[] 	    
            //	    byte[] decodedScreenshot = Base64.decodeBase64(((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.BASE64).getBytes());	
            //	    //write the byte array into your local folder	
            //	    FileOutputStream fos = new FileOutputStream(outputFile); 
            //	    fos.write(decodedScreenshot); 
            //	    fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Extract.
     *
     * @param expr the expr
     * @param src the src
     * @return the sets the
     */
    public static Set<String> extract(final String expr, final String src) {
        final Set<String> result = new HashSet<String>();
        final Pattern pattern = Pattern.compile(expr);
        final Matcher matcher = pattern.matcher(src);
        Set<String> set = new HashSet<String>();
        while (matcher.find()) {
            String data = matcher.group();
            result.add(data);
        }
        return result;
    }
}
