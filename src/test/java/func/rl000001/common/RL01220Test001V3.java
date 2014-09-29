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
import func.rl000001.rl01220.internal.DeathReader;
import func.rl00001.HouseholdMaintainPageV3;
import func.rl00001.Rl00001PageV3; 
import func.rl00001._rl01220.Rl01220PageV3; 

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test; 
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.study.selenium.AbstractSeleniumV2TestCase;

import static org.junit.Assert.assertTrue;

/**
 * 死亡\死亡宣告登記 基本展示 (死亡情境測試)
 */
public class RL01220Test001V3 extends AbstractSeleniumV2TestCase {
    private final static Logger LOGGER = LoggerFactory.getLogger(RL01220Test001V3.class);
    private String user = null;
    private String passwd = null;
    private Rl01220PageV3 rl01220Page = null;;
    List<String[]> personIdSiteIdList;
    List<String[]> appPersonIdSiteIdList;

    @Before
    public void beforeTest() throws Exception {
        user = "RF1203008";
        passwd = "RF1203008";
        //得到要辦理死忙的名單
        //      this.personIdSiteIdList = DeathReader.getPsedoData();
        this.personIdSiteIdList = DeathReader.getJDBCData();
        this.appPersonIdSiteIdList = getPsedoData();
    }

    @Test
    public void testLogin() throws InterruptedException {
        final RlHompageV3 homepage = new RlHompageV3(this.driver);

        homepage.login(this.driver, this.user, this.passwd);

        assertTrue(true);
    }

    @Test
    public void testOpenRl01220() throws Exception {
        final RlHompageV3 homepage = new RlHompageV3(this.driver);
        homepage.login(this.driver, this.user, this.passwd);
        final Rl00001PageV3 rl00001Page = new Rl00001PageV3(driver);
        if (CollectionUtils.isNotEmpty(this.personIdSiteIdList)) {
            int count = 0;

            LOGGER.info("測試資料共{}組  ", this.personIdSiteIdList.size());
            for (String[] stringArray : this.personIdSiteIdList) {
                final String personId = stringArray[0];
                if (StringUtils.contains(personId, "*")) {
                    continue;
                }
                final String siteId = stringArray[1];
                rl00001Page.get();
                
                final String txId = rl00001Page.displayTxId();
                count++;

                LOGGER.info("第{}組測試資料  ", count);

                

                pageLoadTimeout(this.driver);

                WebUtils.pageLoadTimeout(this.driver);
                rl00001Page.typeTxnPerson(personId, siteId);
                final int randomInt = RandomUtils.nextInt(this.appPersonIdSiteIdList.size());
                final   String[] applicat1Data = this.appPersonIdSiteIdList.get(randomInt);
                
                rl00001Page.displayTxId();
                
                rl00001Page.typeApplicat1(applicat1Data[0], applicat1Data[1], "爸媽");                    

                WebUtils.pageLoadTimeout(this.driver);
                
                
                rl01220Page = rl00001Page.clickRl01220();
                if (rl01220Page != null) {
                    demo01(rl01220Page);
                }
                HouseholdMaintainPageV3 householdMaintainPage = new HouseholdMaintainPageV3(driver, rl01220Page);

                 

                if (householdMaintainPage != null
                        && StringUtils.contains(driver.getCurrentUrl(), PagePartialURL.householdMaintain.toString())) {
                    householdMaintainPage.processPrintView();
                    isAlertPresent();
                    householdMaintainPage.processAppyCahange();
                }
                LOGGER.info("第{}組測試資料測試完成  ", count);
            }
        }
        LOGGER.info("測試資料共{}組完成  ", this.personIdSiteIdList.size());
        assertTrue(true);
    }

    /**
     * Demo Scenario. 展示為基本死亡的情境
     */
    public void demo01(Rl01220PageV3 rl01220Page)  {
        LOGGER.info(" 展示為基本死亡的情境");
        WebUtils.pageLoadTimeout(this.driver);
        rl01220Page.tabDeadPersonData.click();
        WebUtils.pageLoadTimeout(this.driver);

        inputOnTab01ForDemo(rl01220Page);
        
        WebUtils.pageLoadTimeout(this.driver);
        
        inputOnTab02(rl01220Page);
    }

    /**
     * Input data02.
     */
    public void inputOnTab02(final Rl01220PageV3 rl01220Page)  {
        WebUtils.pageLoadTimeout(this.driver);
        rl01220Page.tabNotes.click();
        WebUtils.pageLoadTimeout(this.driver);

        int count = 0;
        //資料驗證
        LOGGER.info("點選資料驗證");
        GrowlMsg verify = WebUtils.clickBtn(this.driver, rl01220Page.verifyBtn);
        final String errorExtMessage = verify.getExtMessage();
        final String errorMessage = verify.getMessage();
        if (org.apache.commons.lang.StringUtils.isNotBlank(errorMessage)
                || org.apache.commons.lang.StringUtils.isNotBlank(errorExtMessage)) {
            LOGGER.info(".....");
            while (count < 10) {
                //超過10次資料驗證...要點選關閉視窗放棄
                if (StringUtils.equalsIgnoreCase("請輸入死亡原因", errorExtMessage)) {

                    this.driver.findElement(By.xpath("//a[contains(text(),'死亡者')]")).click();
                    WebUtils.pageLoadTimeout(this.driver);
                    rl01220Page.typeDeathReason("死亡原因");
                    WebUtils.pageLoadTimeout(this.driver);

                    this.driver.findElement(By.xpath("//a[contains(text(),'戶籍記事/罰鍰清單')]")).click();
                    WebUtils.pageLoadTimeout(this.driver);

                    verify = WebUtils.clickBtn(this.driver, rl01220Page.verifyBtn);
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
            WebUtils.clickBtn(this.driver, rl01220Page.closeBtn);
        }

        //暫存
        WebUtils.pageLoadTimeout(this.driver);
        LOGGER.info("點選暫存");
        final GrowlMsg result = WebUtils.clickBtn(this.driver, rl01220Page.tempSaveBtn);
        if(!result.isGiveUpOperation()){
            final HouseholdMaintainPageV3 l2page =  new HouseholdMaintainPageV3(this.driver , rl01220Page);
            l2page.get();
        }
    }
    
    
    /**
     * 在頁籤01上輸入資料.展示為死亡登記基本的情境
     */
    public void inputOnTab01ForDemo(Rl01220PageV3 rl01220Page) {
        pageLoadTimeout(this.driver);    
        
        rl01220Page.tabDeadPersonData.click();
        pageLoadTimeout(this.driver);   
        
        WebUtils.scroolbarDownUp( this.driver);

        // 選擇登記項目
        rl01220Page.selectDeathItem(Rl01220PageV3.DeathItem.DEATH);

        pageLoadTimeout(this.driver);          
        
        // 國民身分證是否繳回
        rl01220Page.selectIDPolicy(Rl01220PageV3.IDPolicy.RETURN);

        pageLoadTimeout(this.driver);  
        // 死亡日期
        rl01220Page.typeDeathYyymmdd(rl01220Page.getTodayYyyMMdd());

        pageLoadTimeout(this.driver);
        
        // 死亡日期確定方式
        rl01220Page.selectDeathWay(Rl01220PageV3.DeathWay.SURE);

        pageLoadTimeout(this.driver);  
        
        // 死亡地點性質
        rl01220Page.selectDeathPlace(Rl01220PageV3.DeathPlace.HOSPITAL);
        pageLoadTimeout(this.driver);  
        
        // 死忙地點(國別)
        rl01220Page.typeBirthPlaceAC("001");
        
        pageLoadTimeout(this.driver);  
        
        // 死亡原因
        rl01220Page. typeDeathReason("死亡原因");

        pageLoadTimeout(this.driver);  
        // 附繳證件 
        

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
