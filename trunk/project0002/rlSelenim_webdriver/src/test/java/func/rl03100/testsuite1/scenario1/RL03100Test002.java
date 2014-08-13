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
        selenium.windowMaximize();
        final String sitLoginPage = "/rl/pages/common/login.jsp";
        selenium.open(sitLoginPage);//RF1203008 
        selenium.waitForPageToLoad("40000");
        logger.info(selenium.getLocation());
//      final String currentUrl = driver.getCurrentUrl();
        final String currentUrl =selenium.getLocation();
        if (StringUtils.contains(currentUrl, sitLoginPage)) {
            //      selenium.type("name=j_username", getUser() );
            //      selenium.type("name=j_password", getPasswd() );
            selenium.type("name=j_username", user);
            selenium.type("name=j_password", passwd);
            selenium.click("css=input[type=\"submit\"]");
        } else {

            final String mainUrl = getMainUrl(currentUrl);
            //得到https://idpfl.ris.gov.tw:8443
            final String openAuthorizationUrl = mainUrl + "/nidp/idff/sso?id=1&sid=1&option=credential&sid=1";
            //https://idpfl.ris.gov.tw:8443/nidp/idff/sso?id=1&sid=1&option=credential&sid=1
            selenium.open(openAuthorizationUrl);

           logger.info(selenium.getLocation());
            //      String user = getUser();
            //      String passwd =  getPasswd();
            selenium.type("//input[@name='Ecom_User_ID']", user);
            selenium.type("//input[@name='Ecom_Password']", passwd);
            selenium.click("//input[@name='loginButton2']");
            //https://idpfl.ris.gov.tw:8443/nidp/idff/sso?id=1&sid=1&option=credential&sid=1
            // 然後必須想辦法到target所指定網址
            selenium.open("/rl/");
            //http://rlfl.ris.gov.tw/rl/
        }
        selenium.waitForPageToLoad("30000");
        // Sleep the thread if you want to view the rendered page while testing.
        Thread.sleep(30000);
        assertTrue(true);
    }
    @Test
    public void testOpenRl03100() throws Exception {
        final RlHompage homepage = new RlHompage(selenium, driver);
        homepage.enterRl03100();
        Rl03100Page rl31000Page = new Rl03100Page(selenium, driver);
    }
    
}
