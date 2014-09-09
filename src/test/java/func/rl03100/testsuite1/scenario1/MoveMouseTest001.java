package func.rl03100.testsuite1.scenario1;

import java.util.ArrayList;
import java.util.List;

import com.iisi.dao.TableJDBCDao;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

import func.rl.common.WebUtils;
import func.rl03100.Rl03100Page;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
 

public class MoveMouseTest001 {
	private static final Logger logger = LoggerFactory.getLogger(MoveMouseTest001.class);
    private Selenium selenium;
    private WebDriver driver;
    List<String[]> personIdSiteIdList;

    @Before
    public void setUp() throws Exception {
        
        final TableJDBCDao dao = new TableJDBCDao();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//        personIdSiteIdList = dao.getPersonIdSiteIdList();
        this.personIdSiteIdList = getPsedoData();
        //	 driver = WebUtils.linuxMachine();
        final   RemoteWebDriver remoteDriver = WebUtils.getWindowsMachine("192.168.9.70", "192.168.10.20", 4444);
        this.driver = remoteDriver;
        //	driver = new FirefoxDriver(capabilities);
        //http://192.168.9.94:6280/rl/pages/common/login.jsp
        String baseUrl = "http://192.168.10.18:6280/rl/";
//        final String baseUrl = "http://rlfl.ris.gov.tw/rl/";
        //http://rlfl.ris.gov.tw/rl/
        //http://rlfl.ris.gov.tw/rl/
        final Dimension targetSize = new Dimension(1500, 860);
        this.driver.manage().window().setSize(targetSize);
        
//        this.selenium = SeleniumTestHelper.init();
        final String remoteNodIp = WebUtils.getIPOfNode(remoteDriver);        
        System.out.println(remoteNodIp);
        if(StringUtils.contains(remoteNodIp, "140")){
            baseUrl = "http://rlfl.ris.gov.tw/rl/";
        }else{
            baseUrl = "http://192.168.10.18:6280/rl/";
        }
        this.selenium = new WebDriverBackedSelenium( this.driver, baseUrl);
        
        this. selenium.open(baseUrl);
        //http://192.168.10.20:4444/grid/api/proxy?id=http://140.92.86.42:5555
    }

    @Test
    public void testRLLogin() throws Exception {
        Mouse mouse = ((HasInputDevices) driver).getMouse();
        WebElement myElement = driver.findElement(By.xpath(""));
//        Locatable hoverItem = (Locatable) driver.findElement(by);
    }

    private void process4(final String personId, final String siteId) throws Exception {
        Rl03100Page rl00004Page = new Rl03100Page(selenium, driver);
        rl00004Page.typeApplication(personId, siteId, "/home/weblogic/Desktop/PIC/");
    }

    @After
    public void tearDown() throws Exception {
        this.selenium.stop();
    }
    private List<String[]> getPsedoData(){
        final List<String[]>  result = new ArrayList<String[]>();
        result.add(new String[]{"C100201902","65000120"});
        result.add(new String[]{"C100202427","65000120"});
        result.add(new String[]{"C100202632","65000120"});
        result.add(new String[]{"C100203166","65000120"});
        
        result.add(new String[]{"C100204092","65000120"});
        result.add(new String[]{"C100204985","65000120"});
        result.add(new String[]{"C100205802","65000120"});
        
        result.add(new String[]{"C100205973","65000120"});
        result.add(new String[]{"C100206783","65000120"});
        result.add(new String[]{"C100208634","65000120"});
        
        result.add(new String[]{"C100209060","65000120"});
        result.add(new String[]{"C100210661","65000120"});
        
        result.add(new String[]{"C100213359","65000120"});
        result.add(new String[]{"C100217366","65000120"});
        
        result.add(new String[]{"A129677773","10010070"});
        result.add(new String[]{"B120138605","10010070"});
        result.add(new String[]{"B120702178","10010070"});
        result.add(new String[]{"C120600821","10010070"});
        result.add(new String[]{"C124277999","10010070"});
        result.add(new String[]{"E120499839","10010070"});
        result.add(new String[]{"E121473795","10010070"});
        result.add(new String[]{"E122760528","10010070"});
        result.add(new String[]{"F108308572","10010070"});
        result.add(new String[]{"G129180762","10010070"});
        
        return  result;
    }
}