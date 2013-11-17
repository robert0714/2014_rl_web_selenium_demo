package func.rl00003.testsuite1.scenario1;

import java.net.MalformedURLException;
import java.net.URL;

import com.thoughtworks.selenium.Selenium;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.robert.study.rl.common.RlHompage;
import org.robert.study.rl.common.TypingApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RLLoginTest {
    private Selenium selenium;

    @Before
    public void setUp() throws Exception {	
	RemoteWebDriver driver = linuxMachine();
	final String baseUrl = "http://192.168.10.18:6280/rl/";

	// WebDriver driver = new FirefoxDriver();
	selenium = new WebDriverBackedSelenium(driver, baseUrl);
    }
    public RemoteWebDriver linuxMachine() throws MalformedURLException{	
	URL remoteAddress = new URL("http://192.168.9.49:4444/wd/hub");	
	// have tried using the below commented out lines as well, but in all
	// cases I face errors.
	// URL remoteAddress = new URL("http://mymachine:4444/grid/register");
	// URL remoteAddress = new URL("http://mymachine:4444/wd/hub");
	DesiredCapabilities dc = DesiredCapabilities.firefox();
	dc.setBrowserName("firefox");	
	dc.setPlatform(Platform.LINUX);
	RemoteWebDriver driver = new RemoteWebDriver(remoteAddress, dc);
	return driver;
    }
    @Test
    public void testRLLogin() throws Exception {
	RlHompage homepage = new RlHompage(selenium);
	TypingApplication aTypingApplication = homepage.typingApplication();
    }

    @After
    public void tearDown() throws Exception {
	// selenium.click("id=logoutButton");
	// selenium.waitForPageToLoad("30000");
	// selenium.stop();
    }
}