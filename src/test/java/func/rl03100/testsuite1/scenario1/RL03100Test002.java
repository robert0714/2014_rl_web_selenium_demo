/*
 * Copyright (c) 2010-2020 IISI.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of IISI.
 */
package func.rl03100.testsuite1.scenario1;
 

import func.rl.common.Rl03100Page;
import func.rl.common.RlHompage;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test; 
import org.study.selenium.AbstractSeleniumTestCase;

import static org.junit.Assert.assertTrue;

/**
 *
 */
public class RL03100Test002 extends AbstractSeleniumTestCase {
    private static final Logger logger = Logger.getLogger(AbstractSeleniumTestCase.class);
    private String user =null;
    private String passwd =null;
    
    @Before
    public void beforeTest() throws Exception {
        user ="RF1203008";
        passwd ="RF1203008";
    }
    
    @Test
    public void testLogin() throws InterruptedException  {
        logger.info("----------------------------------------");
        logger.info("testHomePage()");
        this.selenium.windowMaximize();
        final String sitLoginPage = "/rl/pages/common/login.jsp";
        this.selenium.open(sitLoginPage);//RF1203008 
        this. selenium.waitForPageToLoad("40000");
        logger.info(this.selenium.getLocation());
        final String currentUrl =this.selenium.getLocation();
        if (StringUtils.contains(currentUrl, sitLoginPage)) {
            this.selenium.type("name=j_username", user);
            this.selenium.type("name=j_password", passwd);
            this. selenium.click("css=input[type=\"submit\"]");
        } else {

            final String mainUrl = getMainUrl(currentUrl);
            //得到https://idpfl.ris.gov.tw:8443
            final String openAuthorizationUrl = mainUrl + "/nidp/idff/sso?id=1&sid=1&option=credential&sid=1";
            //https://idpfl.ris.gov.tw:8443/nidp/idff/sso?id=1&sid=1&option=credential&sid=1
            this.selenium.open(openAuthorizationUrl);

           logger.info(selenium.getLocation());
           
           this. selenium.type("//input[@name='Ecom_User_ID']", this.user);
           this. selenium.type("//input[@name='Ecom_Password']",this. passwd);
           this.selenium.click("//input[@name='loginButton2']");
            //https://idpfl.ris.gov.tw:8443/nidp/idff/sso?id=1&sid=1&option=credential&sid=1
            // 然後必須想辦法到target所指定網址
           this.selenium.open("/rl/");
            //http://rlfl.ris.gov.tw/rl/
        }
        this. selenium.waitForPageToLoad("30000");
        // Sleep the thread if you want to view the rendered page while testing.
        assertTrue(true);
    }
    @Test
    public void testOpenRl03100() throws Exception {
        final RlHompage homepage = new RlHompage(this.selenium, this.driver);
        homepage.enterRl03100();
        Rl03100Page rl31000Page = new Rl03100Page(this.selenium, this.driver);
    }
    
}
