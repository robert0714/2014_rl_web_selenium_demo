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
import func.rl000001.rl01220.internal.DeathReader;
import func.rl00001.HouseholdMaintainPageV2;
import func.rl00001.Rl00001PageV2;
import func.rl00001._rl01220.Rl01220PageV2;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.RandomUtils;
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
 *
 */
public class RL01220Test001V2 extends AbstractSeleniumV2TestCase {
	private  final Logger logger = LoggerFactory.getLogger(RL01220Test001V2.class);
    private String user = null;
    private String passwd = null;
    List<String[]> personIdSiteIdList;
    List<String[]> appPersonIdSiteIdList;
    @Before
    public void beforeTest() throws Exception {
	this.user ="RF1203008";
	this.passwd ="RF1203008";
        this.personIdSiteIdList = DeathReader.getPsedoData();
        this.appPersonIdSiteIdList = getPsedoData();
    }
    @Test
    public void testLogin() throws InterruptedException  {
        final RlHompageV2 homepage = new RlHompageV2(this.driver);
        homepage.login(this.driver, this.user, this.passwd);

        // Sleep the thread if you want to view the rendered page while testing.
        assertTrue(true);
    }
    @Test
    public void testOpenRl01220() throws Exception {
        final RlHompageV2 homepage = new RlHompageV2( this.driver);
        homepage.login(this.driver,this.user, this.passwd);
        final Rl00001PageV2 rl00001Page = new Rl00001PageV2( this.driver); 
        final Rl01220PageV2 rl01220Page = new Rl01220PageV2( this. driver);
        final WebDriverWait wait = new WebDriverWait(driver, 60);
        if (CollectionUtils.isNotEmpty( this.personIdSiteIdList)) {
            for (String[] stringArray :  this.personIdSiteIdList) {
                homepage.enterRl00001();
               
                final String personId = stringArray[0];
                if (StringUtils.contains(personId, "*")) {
                    continue;
                }
                final String siteId = stringArray[1];
                
                try {

                    WebUtils.pageLoadTimeout(this.driver);
                    rl00001Page.typeTxnPerson(personId, siteId);
                    final int randomInt = RandomUtils.nextInt(this.appPersonIdSiteIdList.size());
                    final   String[] applicat1Data = this.appPersonIdSiteIdList.get(randomInt);
                    
                    rl00001Page.displayTxId();
                    
                    rl00001Page.typeApplicat1(applicat1Data[0], applicat1Data[1], "爸媽");                    

                    WebUtils.pageLoadTimeout(this.driver);
                    boolean trunPage = rl00001Page.clickRl01220();

                    if (!trunPage  ) {
                        logger.info("發生無法進入Rl01220 死亡登記/死亡宣告登記");
                        continue;
                    }                    
                    final ExpectedCondition<Boolean> turnPageExpected = new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver input) {
                            //預覽列印是新增加視窗
                            return (driver.getCurrentUrl().contains(rl01220Page.getPartialURL()));
                        }
                    };
                    
                    wait.until(turnPageExpected);
                    rl01220Page.switchTab();
                    WebUtils.pageLoadTimeout(this.driver); 
                    
                    HouseholdMaintainPageV2  householdMaintainPage = new HouseholdMaintainPageV2(driver);;

                    
                    if (householdMaintainPage != null
                            && StringUtils.contains(driver.getCurrentUrl(), PagePartialURL.householdMaintain.toString())) {
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
        
//        rl00001Page.redirectPage();
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
