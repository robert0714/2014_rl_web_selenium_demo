/*
 * Copyright (c) 2010-2020 IISI.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of IISI.
 */
package func.rl00004.testsuite1.scenario1;


import java.util.ArrayList;
import java.util.List;

import func.rl.common.PagePartialURL;
import func.rl.common.RlHompageV3;
import func.rl.common.WebUtils;
import func.rl.common.internal.GrowlMsg;
import func.rl00001.HouseholdMaintainPageV3;
import func.rl00001.Rl00004PageV3;
import func.rl00001._rl02510.Rl02510PageV3;
import func.rl00001._rl02510.Rl02510PageV3.ApplyItem;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.study.selenium.AbstractSeleniumV2TestCase;

import static org.junit.Assert.assertTrue;

/**
 * 戶口名簿 (登入,吳依兒童情境測試)
 */
public class RL02510Test001V3 extends AbstractSeleniumV2TestCase {
    private final static Logger LOGGER = LoggerFactory.getLogger(RL02510Test001V3.class);
    private String user = null;
    private String passwd = null;
    private Rl02510PageV3 rl02510Page = null;;
    List<String[]> personIdSiteIdList;

    @Before
    public void beforeTest() throws Exception {
        user = "RF1203008";
        passwd = "RF1203008";
        this.personIdSiteIdList = getPsedoData();
    }

    @Test
    public void testLogin() throws InterruptedException {
        final RlHompageV3 homepage =   PageFactory.initElements(driver, RlHompageV3.class);

        homepage.login(this.driver, this.user, this.passwd);

        assertTrue(true);
    }

    @Test
    public void testOpenRl02500() throws Exception {
        final RlHompageV3 homepage =   PageFactory.initElements(driver, RlHompageV3.class);
        homepage.login(this.driver, this.user, this.passwd);
        final Rl00004PageV3 rl00004Page = PageFactory.initElements(driver,Rl00004PageV3.class);
        if (CollectionUtils.isNotEmpty(this.personIdSiteIdList)) {
        	int count = 0 ; 
        	
        	 LOGGER.info("測試資料共{}組  " ,this.personIdSiteIdList.size());
            for (String[] stringArray : this.personIdSiteIdList) {
                final String personId = stringArray[0];
                if (StringUtils.contains(personId, "*")) {
                    continue;
                }
                final String siteId = stringArray[1];
                final String txId = rl00004Page.displayTxId();
                count++;
                
                LOGGER.info("第{}組測試資料  " ,count);
                
                
                rl00004Page.get();
                
                pageLoadTimeout(this.driver);  
                
                
                rl00004Page.typeTxnPerson(personId, siteId);
                
                rl00004Page.applicantSameTxnPerso.click();
//                rl00004Page.typeApplicat1(personId, siteId, "爸媽");
                rl02510Page = rl00004Page.clickRl02510();
                pageLoadTimeout(this.driver);  
                if (rl02510Page != null) {
                    demo01(rl02510Page);
                }
                HouseholdMaintainPageV3 householdMaintainPage = null;

                 
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
     * Demo Scenario. 展示為戶口名簿
     */
    public void demo01(final Rl02510PageV3 rl02510Page)  {
             
        //補領
        rl02510Page.apply(ApplyItem.REAPPLY);
        //換領
        rl02510Page.apply(ApplyItem.RENEWAL);
        //初領
        rl02510Page.apply(ApplyItem.FIRST);  
        
        
        //列印全戶動態記事(否)
        rl02510Page.printDynamicNotes(false); 
        
        //列印全戶動態記事(是)
        rl02510Page.printDynamicNotes(true); 
       
        //列印父、母、配偶統號(否)
        rl02510Page.printRelationId(false);
        
        //列印父、母、配偶統號(是)
        rl02510Page.printRelationId(true);
        
         
        String prefix = rl02510Page.getRandomCharachters() ;
        //戶口名簿封面編號        
        rl02510Page.typeRl02510IdNo(prefix + RandomUtils.nextInt(10000));
        
        //填寫備註
        rl02510Page.typeRegisterContent("填寫備註測試"); 
        
       
        
        //驗證查詢
        rl02510Page.clickVerifyBtn();
        
        
        rl02510Page. waitPrintBtnClickable();
        
        //列印戶口名簿
        rl02510Page.clickPrintCertificate();
        
        //這時候要切換視窗
        rl02510Page.waitForReceiptPanalPresent();
        
        
        ///確定申請     
        final GrowlMsg msg = rl02510Page.clickSureBtn();
        
        if( StringUtils.isNoneBlank( msg.getExtMessage()) && StringUtils.isNoneBlank(  msg.getMessage()) ){
            if(StringUtils.containsAny(msg.getExtMessage(), "該戶口名簿封面編號已使用過，請重新輸入") ){
                rl02510Page.clickRePrintBtn();
            }
        }
        
        pageLoadTimeout(driver);
        

        //等待確認暫存結束(由於會因為作業資料過多....而需要的時間而增加)
        rl02510Page.waitForSaveTmpFinished();
    }
    
    
    private void pageLoadTimeout (WebDriver driver){
        WebUtils.pageLoadTimeout(this.driver);
//        try {
//            Thread.sleep(3000l);
//        } catch (InterruptedException e) {
//           LOGGER.error( e.getMessage(), e);
//        }
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
