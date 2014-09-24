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
import func.rl.common.RlHompageV3;
import func.rl.common.WebUtils;
import func.rl.common.internal.GrowlMsg; 
import func.rl00001.HouseholdMaintainPageV3;
import func.rl00001.Rl00001PageV3;
import func.rl00001._rl01210.Rl01210PageV3;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.study.selenium.AbstractSeleniumV2TestCase;

import static org.junit.Assert.assertTrue;

/**
 * 出生登記 基本展示 (登入,吳依兒童情境測試)
 */
public class RL01210Test001V3 extends AbstractSeleniumV2TestCase {
    private final static Logger LOGGER = LoggerFactory.getLogger(RL01210Test001V3.class);
    private String user = null;
    private String passwd = null;
    private Rl01210PageV3 rl01210Page = null;;
    List<String[]> personIdSiteIdList;

    @Before
    public void beforeTest() throws Exception {
        user = "RF1203008";
        passwd = "RF1203008";
        this.personIdSiteIdList = getPsedoData();
    }

    @Test
    public void testLogin() throws InterruptedException {
        final RlHompageV3 homepage = new RlHompageV3(this.driver);

        homepage.login(this.driver, this.user, this.passwd);

        assertTrue(true);
    }

    @Test
    public void testOpenRl01210() throws Exception {
        final RlHompageV3 homepage = new RlHompageV3(this.driver);
        homepage.login(this.driver, this.user, this.passwd);
        final Rl00001PageV3 rl00001Page = new Rl00001PageV3(driver);
        if (CollectionUtils.isNotEmpty(this.personIdSiteIdList)) {
        	int count = 0 ; 
        	
        	 LOGGER.info("測試資料共{}組  " ,this.personIdSiteIdList.size());
            for (String[] stringArray : this.personIdSiteIdList) {
                final String personId = stringArray[0];
                if (StringUtils.contains(personId, "*")) {
                    continue;
                }
                final String siteId = stringArray[1];
                final String txId = rl00001Page.displayTxId();
                count++;
                
                LOGGER.info("第{}組測試資料  " ,count);
                
                
                rl00001Page.get();
                
                pageLoadTimeout(this.driver);  
                        
                rl00001Page.typeApplicat1(personId, siteId, "爸媽");
                rl01210Page = rl00001Page.clickRl01210();
                if (rl01210Page != null) {
                    demo01(rl01210Page);
                }
                HouseholdMaintainPageV3 householdMaintainPage = null;

                //頁籤翻轉測試
                pageLoadTimeout(this.driver);  
                
                if (StringUtils.contains(driver.getCurrentUrl(), "/rl00001/householdMaintain.xhtml")) {

                    householdMaintainPage = new HouseholdMaintainPageV3(driver,rl01210Page);

                    while (!householdMaintainPage.switchTab()) {
                        LOGGER.debug("轉不過去");
                    }
                    isAlertPresent();
                }
                pageLoadTimeout(this.driver);  
                
                if (householdMaintainPage != null
                        && StringUtils.contains(driver.getCurrentUrl(), PagePartialURL.householdMaintain.toString())) {
                    householdMaintainPage.processPrintView();
                    isAlertPresent();
                    householdMaintainPage.processAppyCahange();
                }
                LOGGER.info("第{}組測試資料測試完成  " ,count);
            }
        }
        LOGGER.info("測試資料共{}組完成  " ,this.personIdSiteIdList.size());
        assertTrue(true);
    }

    /**
     * Demo Scenario. 展示為無依兒童的情境
     */
    public void demo01(Rl01210PageV3 rl01210Page)  {
        LOGGER.info("展示為無依兒童的情境");
        WebUtils.pageLoadTimeout(this.driver);
        rl01210Page.tabBasicHouseholdData.click();
        WebUtils.pageLoadTimeout(this.driver);

        inputOnTab01ForDemo(rl01210Page);
        
        WebUtils.pageLoadTimeout(this.driver);
        
        inputOnTab02(rl01210Page);
    }

    /**
     * Input data02.
     */
    public void inputOnTab02(final Rl01210PageV3 rl01210Page)  {
        WebUtils.pageLoadTimeout(this.driver);
        rl01210Page.tabNotes.click();
        WebUtils.pageLoadTimeout(this.driver);

        int count = 0;
        //資料驗證
        LOGGER.info("點選資料驗證");
        GrowlMsg verify = WebUtils.clickBtn(this.driver, rl01210Page.verifyBtn);
        final String errorExtMessage = verify.getErrorExtMessage();
        final String errorMessage = verify.getErrorMessage();
        if (org.apache.commons.lang.StringUtils.isNotBlank(errorMessage)
                || org.apache.commons.lang.StringUtils.isNotBlank(errorExtMessage)) {
            LOGGER.info(".....");
            while (count < 10) {
                //超過10次資料驗證...要點選關閉視窗放棄
                if (StringUtils.equalsIgnoreCase("請輸入發現地點", errorExtMessage)) {

                    rl01210Page.tabBasicHouseholdData.click();
                    WebUtils.pageLoadTimeout(this.driver);

                    rl01210Page.typeBirthPlaceAC("63000");

                    WebUtils.pageLoadTimeout(this.driver);

                    rl01210Page.tabNotes.click();

                    verify = WebUtils.clickBtn(this.driver, rl01210Page.verifyBtn);
                    if (!verify.isGiveUpOperation()) {
                        break;
                    }
                }

                count++;
            }
        }
        WebUtils.pageLoadTimeout(this.driver);
        if (verify.isGiveUpOperation()) {
            LOGGER.info("點選關閉視窗");
            WebUtils.clickBtn(this.driver, rl01210Page.closeBtn);
        }

        //暫存
        WebUtils.pageLoadTimeout(this.driver);
        LOGGER.info("點選暫存");
        final GrowlMsg result = WebUtils.clickBtn(this.driver, rl01210Page.tempSaveBtn);
        if(!result.isGiveUpOperation()){
            final HouseholdMaintainPageV3 l2page =  new HouseholdMaintainPageV3(this.driver , rl01210Page);
            l2page.get();
        }
    }
    
    
    /**
     * 在頁籤01上輸入資料.展示為無依兒童的情境
     */
    public void inputOnTab01ForDemo(Rl01210PageV3 rl01210Page) {
        pageLoadTimeout(this.driver);    
        
        rl01210Page.tabBasicHouseholdData.click();
         
        
        WebUtils.scroolbarDownUp(this.driver);
        
        pageLoadTimeout(this.driver);    
        
        //選擇無依兒童        
        rl01210Page.checkBirthKind(Rl01210PageV3.BirthKind.INNOCENTI);
         
        pageLoadTimeout(this.driver);    
        
        
        //自立新戶
        rl01210Page.setNewHousehold(true);
        
        pageLoadTimeout(this.driver);     
        
        WebUtils.scroolbarDownUp(this.driver);
        
        pageLoadTimeout(this.driver);        
        
        //非自立新戶(入他人戶)
        rl01210Page.setNewHousehold(false);

        pageLoadTimeout(this.driver);        
        
        //輸入戶長統號
        rl01210Page.typeHouseholdHeadId("C100202427");
        
        pageLoadTimeout(this.driver);         
        
        //輸入戶號
        rl01210Page.typeHouseholdId("F5261129");
        
        pageLoadTimeout(this.driver);           
        
        rl01210Page.getRLDF001MByClickBtn();
        
        pageLoadTimeout(this.driver);       
        
        WebUtils.scroolbarDown(this.driver);
        
        rl01210Page.typeLastName("無姓");
        
        rl01210Page. typeFirstName("無名");
        
        rl01210Page. typeBirthYyymmdd("1010203");
        
        rl01210Page. typeBirthOrderSexSelectOneMenu();
        
        rl01210Page. typeRelationShip("稱謂");
        
        rl01210Page. typeBirthPlaceAC("63000");

    }
    private void pageLoadTimeout (WebDriver driver){
        WebUtils.pageLoadTimeout(this.driver);
        try {
            Thread.sleep(3000l);
        } catch (InterruptedException e) {
           LOGGER.error( e.getMessage(), e);
        }
    }
    private List<String[]> getPsedoData() {
        final List<String[]> result = new ArrayList<String[]>();
        //        result.add(new String[]{"B120138605","10010070"});
        //        result.add(new String[]{"B120702178","10010070"});
        //        result.add(new String[]{"C120600821","10010070"});
        //        result.add(new String[]{"C124277999","10010070"});
        //        result.add(new String[]{"E120499839","10010070"});
        //        result.add(new String[]{"E121473795","10010070"});
        //        result.add(new String[]{"E122760528","10010070"});
        //        result.add(new String[]{"F108308572","10010070"});
        //        result.add(new String[]{"G129180762","10010070"});
        result.add(new String[] { "C100201902", "65000120" });
        result.add(new String[] { "C100202427", "65000120" });
        result.add(new String[] { "C100202632", "65000120" });
        result.add(new String[] { "C100203166", "65000120" });

        result.add(new String[] { "C100204092", "65000120" });
        result.add(new String[] { "C100204985", "65000120" });
        result.add(new String[] { "C100205802", "65000120" });

        result.add(new String[] { "C100205973", "65000120" });
        result.add(new String[] { "C100206783", "65000120" });
        result.add(new String[] { "C100208634", "65000120" });

        result.add(new String[] { "C100209060", "65000120" });
        result.add(new String[] { "C100210661", "65000120" });

        result.add(new String[] { "C100213359", "65000120" });
        result.add(new String[] { "C100217366", "65000120" });

        return result;
    }
}
