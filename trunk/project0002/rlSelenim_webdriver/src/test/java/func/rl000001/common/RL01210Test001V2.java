/*
 * Copyright (c) 2010-2020 IISI.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of IISI.
 */
package func.rl000001.common;
 


import java.io.File;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import func.rl.common.PagePartialURL;
import func.rl.common.RlHompage;
import func.rl.common.RlHompageV2;
import func.rl.common.WebUtils;
import func.rl00001.HouseholdMaintainPage;
import func.rl00001.HouseholdMaintainPageV2;
import func.rl00001.Rl00001Page;
import func.rl00001.Rl00001PageV2;
import func.rl00001._rl01210.Rl01210Page;
import func.rl00001._rl01210.Rl01210PageV2;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test; 
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.study.selenium.AbstractSeleniumTestCase;
import org.study.selenium.AbstractSeleniumV2TestCase;
import org.study.selenium.SeleniumConfig;

import static org.junit.Assert.assertTrue;

/**
 *
 */
public class RL01210Test001V2 extends AbstractSeleniumV2TestCase {
    private static final String closeBeforeUnloadAlert ="document.getElementsByName('ae_l_leaveCheck')[0].value = null;"; 
    private static final String alertFlagXpath ="//*[contains(@id,'alert_flag')]";
    private  final Logger logger = LoggerFactory.getLogger(RL01210Test001V2.class);
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
	final RlHompageV2 homepage = new RlHompageV2(this.driver);
	homepage.login(this.driver, this.user, this.passwd);
	Rl00001PageV2 rl00001Page = new Rl00001PageV2(this.driver);
                
        // Sleep the thread if you want to view the rendered page while testing.
        assertTrue(true);
    }
    @Test
    public void testOpenRl01210() throws Exception {
	final SimpleDateFormat sdf =new SimpleDateFormat(this.getClass().getSimpleName()+"yyyy_MM_dd_ss");
        final RlHompageV2 homepage = new RlHompageV2( this.driver);
        homepage.login(this.driver,this.user, this.passwd);
        Rl00001PageV2 rl00001Page = new Rl00001PageV2( this.driver);
        Rl01210PageV2 rl01210Page = new Rl01210PageV2(  this. driver);
        
	if (CollectionUtils.isNotEmpty(this.personIdSiteIdList)) {
	    for (String[] stringArray : this.personIdSiteIdList) {
		try {
		    homepage.enterRl00001();

		    final String personId = stringArray[0];
		    if (StringUtils.contains(personId, "*")) {
		        continue;
		    }
		    final String siteId = stringArray[1];

		    rl00001Page.typeApplicat1(personId, siteId, "爸嗎");
		    boolean trunPage = rl00001Page.clickRl01210();

		    if (!trunPage  ) {
		        logger.info("發生無法進入Rl01210出生登記");
		        continue;
		    }
		    WebUtils.pageLoadTimeout(this.driver);

		    rl01210Page.switchTab();

		    HouseholdMaintainPageV2 householdMaintainPage = null;

		    if (StringUtils.contains(driver.getCurrentUrl(), "/rl00001/householdMaintain.xhtml")) {

		        householdMaintainPage = new HouseholdMaintainPageV2(driver);

		        while (!householdMaintainPage.switchTab()) {
		    	logger.debug("轉不過去");
		        }
		        // 發現所需延遲時間需要更久
		        // final WebDriverWait wait = new WebDriverWait(driver, 60);
		        // wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(alertFlagXpath)));
		        // ((RemoteWebDriver) this.driver).executeScript(closeBeforeUnloadAlert, "");
		        isAlertPresent();

		    }
		    if (householdMaintainPage != null && StringUtils.contains(driver.getCurrentUrl(), PagePartialURL.householdMaintain.toString())) {
		        householdMaintainPage.processPrintView();
		        isAlertPresent();
		        householdMaintainPage.processAppyCahange();
		    }
		} catch (Exception e) {
		    logger.error(e.getMessage(),e);
		    //發生錯誤...螢幕截圖		    
		    WebUtils.takeScreen(driver, getClass());
		   
		}

	    }
	} 
        
        rl00001Page.redirectPage(); 
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
