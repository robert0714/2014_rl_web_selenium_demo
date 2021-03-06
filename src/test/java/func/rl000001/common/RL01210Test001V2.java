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
import func.rl.common.RlHompageV2;
import func.rl.common.WebUtils;
import func.rl00001.HouseholdMaintainPageV2;
import func.rl00001.Rl00001PageV2;
import func.rl00001._rl01210.Rl01210PageV2;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test; 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.study.selenium.AbstractSeleniumV2TestCase;

import static org.junit.Assert.assertTrue;

/**
 * 出生登記 基本展示 (登入,吳依兒童情境測試)
 */
public class RL01210Test001V2 extends AbstractSeleniumV2TestCase {
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
                
        // Sleep the thread if you want to view the rendered page while testing.
        assertTrue(true);
    }
    @Test
    public void testOpenRl01210() throws Exception {
        final RlHompageV2 homepage = new RlHompageV2( this.driver);
        homepage.login(this.driver,this.user, this.passwd);
        final Rl00001PageV2 rl00001Page = new Rl00001PageV2( this.driver);
        final Rl01210PageV2 rl01210Page = new Rl01210PageV2(  this. driver);
        final WebDriverWait wait = new WebDriverWait(driver, 60);
	if (CollectionUtils.isNotEmpty(this.personIdSiteIdList)) {
	    for (String[] stringArray : this.personIdSiteIdList) {
		try {
		    homepage.enterRl00001();

		    final String personId = stringArray[0];
		    if (StringUtils.contains(personId, "*")) {
		        continue;
		    }
		    final String siteId = stringArray[1];
		    final String txId = rl00001Page.displayTxId();
		    rl00001Page.typeApplicat1(personId, siteId, "爸媽");
		    boolean trunPage = rl00001Page.clickRl01210();

		    if (!trunPage  ) {
		        logger.info("發生無法進入Rl01210出生登記");
		        continue;
		    }
                    final ExpectedCondition<Boolean> turnPageExpected = new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver input) {
                            //預覽列印是新增加視窗
                            return (driver.getCurrentUrl().contains(rl01210Page.getPartialURL()));
                        }
                    };
                    
                    wait.until(turnPageExpected);
                    
		    rl01210Page.switchTab();
		    
		    logger.info("進行Rl01210 Demo");
		    rl01210Page.demo01();

		    HouseholdMaintainPageV2 householdMaintainPage = null;

		    //頁籤翻轉測試
		    
		    if (StringUtils.contains(driver.getCurrentUrl(), "/rl00001/householdMaintain.xhtml")) {

		        householdMaintainPage = new HouseholdMaintainPageV2(driver);

		        while (!householdMaintainPage.switchTab()) {
		    	logger.debug("轉不過去");
		        }
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
