package func.rl00004.testsuite1.scenario1;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import func.rl.common.HouseholdMaintainPage;
import func.rl.common.PagePartialURL;
import func.rl.common.Rl00004Page;
import func.rl.common.Rl0172bPage;
import func.rl.common.Rl02a10Page;
import func.rl.common.Rl02d00Page;
import func.rl.common.RlHompage;
import func.rl.common.Rl00001Page;
import func.rl.common.WebUtils;

import org.study.selenium.AbstractSeleniumTestCase;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class RL02D00Test001 extends AbstractSeleniumTestCase {
    protected static Logger logger = Logger.getLogger(RL02D00Test001.class);
    private String user = null;
    private String passwd = null;
    List<String[]> personIdSiteIdList;

    @Before
    public void setUp() throws Exception {
        this.user = "RF1203008";
        this.passwd = "RF1203008";
        this.personIdSiteIdList = getPsedoData();
    }

    @Test
    public void testLogin() throws InterruptedException {
        final RlHompage homepage = new RlHompage(this.selenium, this.driver);
        homepage.login(this.selenium, this.user, this.passwd);
        this.selenium.waitForPageToLoad("30000");

        // Sleep the thread if you want to view the rendered page while testing.
        assertTrue(true);
    }

    @Test
    public void test2D00() throws Exception {
        final RlHompage homepage = new RlHompage(this.selenium, this.driver);
        homepage.login(this.selenium, this.user, this.passwd);
        this.selenium.waitForPageToLoad("30000");
        if (CollectionUtils.isNotEmpty(personIdSiteIdList)) {
            for (String[] stringArray : personIdSiteIdList) {

                final String personId = stringArray[0];
                if (StringUtils.contains(personId, "*")) {
                    continue;
                }
                final String siteId = stringArray[1];

                selenium.waitForPageToLoad("30000");
                // //div[contains(@id,'orgNameWay')]

                homepage.enterRl00004();
                try {
                    process(homepage, personId, siteId);
                } catch (Exception e) {
                    e.printStackTrace();
                    WebUtils.handleRLAlert(selenium);
                }
            }
        } else {
            homepage.enterRl00004();
            process4(null, null);
            //	    process(homepage,null, null);
        }
    }

    private void process4(final String personId, final String siteId) throws Exception {
        Rl00004Page rl00004Page = new Rl00004Page(selenium, driver);
        rl00004Page.typingApplication();
        Rl02d00Page rl02d00Page = new Rl02d00Page(selenium, driver);
        rl02d00Page.switchTab();
    }

    private void process(final RlHompage homepage, final String personId, final String siteId) throws Exception {
        final Rl00001Page aTypingApplication = homepage.typingApplication();
        selenium.waitForPageToLoad("30000");
        aTypingApplication.setPersonId(personId);
        aTypingApplication.setSiteId(siteId);
        aTypingApplication.typingApplication();

        if (selenium.isElementPresent("//input[contains(@id,'alert_flag')]")) {
            selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
        }

        //http://192.168.10.18:6280/rl/faces/pages/func/rl00001/householdMaintain.xhtml?windowId=5ae
        HouseholdMaintainPage householdMaintainPage = null;

        if (StringUtils.contains(driver.getCurrentUrl(), "/rl00001/householdMaintain.xhtml")) {

            selenium.waitForPageToLoad("300000");
            householdMaintainPage = new HouseholdMaintainPage(selenium, driver);
            selenium.waitForPageToLoad("300000");
            while (!householdMaintainPage.switchTab()) {
                logger.debug("轉不過去");
            }
            //發現所需延遲時間需要更久
            selenium.waitForPageToLoad("300000");
            if (selenium.isElementPresent("//input[contains(@id,'alert_flag')]")) {
                selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
            }
            householdMaintainPage.clickRl1722B();

        }
        //http://192.168.10.18:6280/rl/faces/pages/func/rl00001/_rl0172b/rl0172b.xhtml?windowId=344
        int count = 0;
        while (!StringUtils.contains(driver.getCurrentUrl(), "_rl0172b/rl0172b.xhtml")) {
            Thread.sleep(6000l);
            if (count > 5) {
                break;
            }
            count++;
        }
        if (StringUtils.contains(driver.getCurrentUrl(), "_rl0172b/rl0172b.xhtml")) {
            selenium.waitForPageToLoad("300000");
            Rl0172bPage rl0172bPage = new Rl0172bPage(selenium, driver);
            rl0172bPage.switchTab();
            selenium.waitForPageToLoad("300000");

            while (StringUtils.contains(driver.getCurrentUrl(), "_rl0172b/rl0172b.xhtml")) {
                logger.debug(driver.getCurrentUrl());
                if (StringUtils.contains(driver.getCurrentUrl(), "/rl00001/householdMaintain.xhtml")) {
                    break;
                }
                Thread.sleep(5000);
                selenium.refresh();
            }
        }
        WebUtils.scroolbarDownUp(selenium, driver);

        if (householdMaintainPage != null
                && StringUtils.contains(driver.getCurrentUrl(), PagePartialURL.householdMaintain.toString())) {
            householdMaintainPage.processPrintView();
            if (selenium.isElementPresent("//input[contains(@id,'alert_flag')]")) {
                selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
                selenium.refresh();
            }
            selenium.waitForPageToLoad("3000");
            householdMaintainPage.processAppyCahange();
        }
        if (selenium.isElementPresent("//input[contains(@id,'alert_flag')]")) {
            selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
            selenium.refresh();
        }
        selenium.waitForPageToLoad("3000");
        //進入2A10
        Rl02a10Page rl02a10Page = new Rl02a10Page(selenium, driver);
        rl02a10Page.switchTab();
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