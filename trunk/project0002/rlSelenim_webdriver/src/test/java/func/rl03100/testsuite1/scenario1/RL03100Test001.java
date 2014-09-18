package func.rl03100.testsuite1.scenario1;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.iisi.dao.TableJDBCDao;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

import func.pseudo.PsedoRedFile;
import func.pseudo.RlAccount;
import func.pseudo.RlAccount.Account;
import func.rl.common.RlHompage;
import func.rl.common.WebUtils;
import func.rl03100.Rl03100Page;

import org.openqa.selenium.Dimension; 
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.study.selenium.SeleniumTestHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class RL03100Test001 {
	private static final Logger logger = LoggerFactory.getLogger(RL03100Test001.class);
    private Selenium selenium;
    private RemoteWebDriver driver;
    private String user =null;
    private String passwd =null;
    List<String[]> personIdSiteIdList;

    @Before
    public void setUp() throws Exception {
    	final Account userAcc = RlAccount.getRandomAccount();
//        user ="RF1203008";
//        passwd ="RF1203008";
    	user =userAcc.getUser();
    	passwd =userAcc.getPasswd();
        final TableJDBCDao dao = new TableJDBCDao();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//        personIdSiteIdList = dao.getPersonIdSiteIdList();
        this.personIdSiteIdList = getPsedoData();
        final   RemoteWebDriver remoteDriver = WebUtils.windowsMachine();    
        
//        this.driver = remoteDriver;
//        String baseUrl = "http://192.168.10.18:6280/rl/";
//        final Dimension targetSize = new Dimension(1500, 860);
//        this.driver.manage().window().setSize(targetSize);
//        final String remoteNodIp = WebUtils.getIPOfNode(remoteDriver);        
//        System.out.println(remoteNodIp);
//        if(StringUtils.contains(remoteNodIp, "140")){
//            baseUrl = "http://rlfl.ris.gov.tw/rl/";
//        }else{
//            baseUrl = "http://192.168.10.18:6280/rl/";
//        }
//        this.selenium = new WebDriverBackedSelenium( this.driver, baseUrl);        
//        this. selenium.open(baseUrl);
        
        
        this.driver = remoteDriver;
        final Dimension targetSize = new Dimension(1500, 860);
        this.driver.manage().window().setSize(targetSize);
        this.selenium = SeleniumTestHelper.initWebDriver(driver);
        //http://192.168.10.20:4444/grid/api/proxy?id=http://140.92.86.42:5555
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
        result.addAll(PsedoRedFile.readFiles())       ;
        return  result;
    }
    @Test
    public void testLogin() throws InterruptedException  {
        final RlHompage homepage = new RlHompage(this.selenium, this.driver);
        homepage.login(this.selenium,this.user, this.passwd);
        this. selenium.waitForPageToLoad("30000");
        // Sleep the thread if you want to view the rendered page while testing.
        assertTrue(true);
    }
    @Test
    public void testOpenRl03100() throws Exception {
        final RlHompage homepage = new RlHompage(this.selenium, this.driver);
        homepage.login(this.selenium,this.user, this.passwd);
        homepage.enterRl03100();
        Rl03100Page rl31000Page = new Rl03100Page(this.selenium,  null);
        
        if (CollectionUtils.isNotEmpty( this.personIdSiteIdList)) {
            for (String[] stringArray :  this.personIdSiteIdList) {
                this. selenium.waitForPageToLoad("30000");
                homepage.enterRl03100();
                this. selenium.waitForPageToLoad("30000");
                final String personId = stringArray[0];
                if (StringUtils.contains(personId, "*")) {
                    continue;
                }
                final String siteId = stringArray[1];

                try {
                    process4(personId, siteId);
                } catch (Exception e) {
                    e.printStackTrace();
                    WebUtils.handleRLAlert(selenium);
                }
            }
        } else {
            homepage.enterRl03100();
            this. selenium.waitForPageToLoad("30000");
            process4("C100201902", "65000120");
            //      process4(null,null);
        }
        
        
        this. selenium.waitForPageToLoad("30000");        
        // Sleep the thread if you want to view the rendered page while testing.
        assertTrue(true);
    }
}