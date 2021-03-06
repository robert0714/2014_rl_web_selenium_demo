/*
 * Copyright (c) 2010-2020 IISI.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of IISI.
 */
package func.rl000001.common;
 


import java.util.ArrayList;
import java.util.List;

import func.rl.common.PagePartialURL;
import func.rl.common.RlHompage;
import func.rl.common.WebUtils;
import func.rl00001.HouseholdMaintainPage;
import func.rl00001.Rl00001Page;
import func.rl00001._rl01210.Rl01210Page;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.study.selenium.AbstractSeleniumTestCase;
import org.study.selenium.SeleniumConfig;

import static org.junit.Assert.assertTrue;

/**
 *
 */
public class RL01210Test001 extends AbstractSeleniumTestCase {
	private  final Logger logger = LoggerFactory.getLogger(RL01210Test001.class);
    private String user = null;
    private String passwd = null;
    List<String[]> personIdSiteIdList;
    
    @Before
    public void beforeTest() throws Exception {
        user ="RF1203008";
        passwd ="RF1203008";
        this.personIdSiteIdList = getPsedoData();
    }
    @Test
    public void testLogin() throws InterruptedException  {
        final RlHompage homepage = new RlHompage(this.selenium, this.driver);
        homepage.login(this.selenium,this.user, this.passwd);
        this. selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
                
        // Sleep the thread if you want to view the rendered page while testing.
        assertTrue(true);
    }
    @Test
    public void testOpenRl01210() throws Exception {
        final RlHompage homepage = new RlHompage(this.selenium, this.driver);
        homepage.login(this.selenium,this.user, this.passwd);
        Rl00001Page rl00001Page = new Rl00001Page(selenium, driver);
        
        
        if (CollectionUtils.isNotEmpty( this.personIdSiteIdList)) {
            for (String[] stringArray :  this.personIdSiteIdList) {
                homepage.enterRl00001();
                
                this. selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
               
                final String personId = stringArray[0];
                if (StringUtils.contains(personId, "*")) {
                    continue;
                }
                final String siteId = stringArray[1];
                this. selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
                
                try {                    
                    this. selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
//                    Thread.sleep(1000l);
                    rl00001Page.typeApplicat1(personId, siteId,"爸嗎");
                    this. selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
                    rl00001Page.clickRl01210();
//                    boolean giveUpOperation =  WebUtils.handleRLAlert(this.selenium);
//                    if(giveUpOperation){ 
//                        continue;
//                    }
//                    Thread.sleep(1000l);
                    this. selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
                    Rl01210Page rl01210Page = new Rl01210Page(this. selenium, this. driver);
                    this. selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
                    rl01210Page.switchTab();
                    this. selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
//                    Thread.sleep(1000l);
                    HouseholdMaintainPage householdMaintainPage = null;

                    if (StringUtils.contains(driver.getCurrentUrl(), "/rl00001/householdMaintain.xhtml")) {

                        this. selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
                        householdMaintainPage = new HouseholdMaintainPage(selenium, driver);
                        this. selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
                        while (!householdMaintainPage.switchTab()) {
                            logger.debug("轉不過去");
                        }
                        //發現所需延遲時間需要更久
                        selenium.waitForPageToLoad("300000");
                        if (selenium.isElementPresent("//input[contains(@id,'alert_flag')]")) {
                            selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
                        }
                    }
                    if (householdMaintainPage != null
                            && StringUtils.contains(driver.getCurrentUrl(), PagePartialURL.householdMaintain.toString())) {
                        householdMaintainPage.processPrintView();
                        this. selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
                        if (selenium.isElementPresent("//input[contains(@id,'alert_flag')]")) {
                            selenium.runScript("document.getElementsByName('ae_l_leaveCheck')[0].value = null;");
                            selenium.refresh();
                        }
                        this. selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
                        householdMaintainPage.processAppyCahange();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    WebUtils.handleRLAlert(selenium);
                }
            }
        } 
        
        rl00001Page.redirectPage();
        this. selenium.waitForPageToLoad("30000");        
        // Sleep the thread if you want to view the rendered page while testing.
        assertTrue(true);
    }
    
    
    private List<String[]> getPsedoData(){
        final List<String[]>  result = new ArrayList<String[]>();        
//        result.add(new String[]{"B120138605","10010070"});
//        result.add(new String[]{"B120702178","10010070"});
//        result.add(new String[]{"C120600821","10010070"});
//        result.add(new String[]{"C124277999","10010070"});
//        result.add(new String[]{"E120499839","10010070"});
//        result.add(new String[]{"E121473795","10010070"});
//        result.add(new String[]{"E122760528","10010070"});
//        result.add(new String[]{"F108308572","10010070"});
//        result.add(new String[]{"G129180762","10010070"});
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
        
        
        
        return  result;
    }
}
