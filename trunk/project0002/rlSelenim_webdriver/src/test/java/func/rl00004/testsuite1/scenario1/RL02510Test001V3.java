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
import func.rl00001.Rl00001PageV3;
import func.rl00001.Rl00004PageV3;
import func.rl00001._rl01210.Rl01210PageV3;
import func.rl00001._rl02510.Rl02510PageV3;

import org.apache.commons.collections.CollectionUtils;
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
 * 戶口名簿 (登入,吳依兒童情境測試)
 */
public class RL02510Test001V3 extends AbstractSeleniumV2TestCase {
    private final static Logger LOGGER = LoggerFactory.getLogger(RL02510Test001V3.class);
    private String user = null;
    private String passwd = null;
    private Rl02510PageV3 rl01210Page = null;;
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
    public void testOpenRl02500() throws Exception {
        final RlHompageV3 homepage = new RlHompageV3(this.driver);
        homepage.login(this.driver, this.user, this.passwd);
        final Rl00004PageV3 rl00004Page = new Rl00004PageV3(driver);
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
                rl01210Page = rl00004Page.clickRl02510();
                if (rl01210Page != null) {
                    demo01(rl01210Page);
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
     * Demo Scenario. 展示為無依兒童的情境
     */
    public void demo01(Rl02510PageV3 rl01210Page)  {
        driver.findElement(By.xpath("//input[@id='txnPersonIdTx']")).clear();
        driver.findElement(By.xpath("//input[@id='txnPersonIdTx']")).sendKeys("A126222114");
        driver.findElement(By.xpath("//input[@id='applicantSameTxnPerson']")).click();
        driver.findElement(By.id("j_id_ey:3:j_id_ez:j_id_f1")).click();
        driver.findElement(By.id("txnPersonIdTx")).click();
        driver.findElement(By.xpath("//div[@id='growl2_container']/div/div/div")).click();
        driver.findElement(By.xpath("//td[@id='currentPersonIdTD']/input")).clear();
        driver.findElement(By.xpath("//td[@id='currentPersonIdTD']/input")).sendKeys("C100202427");
        driver.findElement(By.xpath("//input[@id='applicant1PersonId']")).click();
        driver.findElement(By.xpath("//input[@id='applicant1PersonId']")).clear();
        driver.findElement(By.xpath("//input[@id='applicant1PersonId']")).sendKeys("C100202427");
        driver.findElement(By.xpath("(//a[contains(text(),'戶口名簿')])[5]")).click();
        driver.findElement(By.xpath("//td[2]/table/tbody/tr/td[2]/input")).click();
        driver.findElement(By.xpath("//td[2]/table/tbody/tr/td/input")).click();
        driver.findElement(By.xpath("//td[3]/table/tbody/tr/td[2]/input")).click();
        driver.findElement(By.xpath("//td[3]/table/tbody/tr/td/input")).click();
        driver.findElement(By.xpath("//td[3]/table/tbody/tr/td[2]/input")).click();
        driver.findElement(By.xpath("//textarea[@id='tabViewId:registerContent']")).clear();
        driver.findElement(By.xpath("//textarea[@id='tabViewId:registerContent']")).sendKeys("1234565");
        driver.findElement(By.xpath("//td/button")).click();
        driver.findElement(By.xpath("//table[2]/tbody/tr/td/input")).clear();
        driver.findElement(By.xpath("//table[2]/tbody/tr/td/input")).sendKeys("1A5wwxxxzzzz");
        driver.findElement(By.xpath("//td/button")).click();
        driver.findElement(By.xpath("//td[2]/button")).click();
        driver.findElement(By.xpath("//td[2]/button")).click();
        driver.findElement(By.xpath("//div/span/span[2]/button[2]")).click();
        driver.findElement(By.xpath("//td[2]/button")).click();
        driver.findElement(By.xpath("//div/span/span[2]/button")).click();
        driver.findElement(By.xpath("//div/span/span[2]/button[2]")).click();
        driver.findElement(By.xpath("//tr[2]/td/button")).click();
        driver.findElement(By.xpath("//div[5]/div[2]/div/div[2]/table/tbody/tr[2]/td/button[2]")).click();
        driver.findElement(By.xpath("//div[5]/div/button")).click();
        driver.findElement(By.xpath("//div/span/span[2]/button[2]")).click();
        driver.findElement(By.xpath("//div[5]/div/button")).click();
        driver.findElement(By.xpath("//div[5]/div/button")).click();
        driver.findElement(By.xpath("//div[5]/div/button")).click();
        driver.findElement(By.xpath("//div/span/span[2]/button[2]")).click();
        driver.findElement(By.xpath("//tr[2]/td/button[2]")).click();
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
