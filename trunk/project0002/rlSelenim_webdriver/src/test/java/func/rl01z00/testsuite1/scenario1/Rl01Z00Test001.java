/*
 * Copyright (c) 2010-2020 IISI.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of IISI.
 */
package func.rl01z00.testsuite1.scenario1;
 

 
import java.util.List;
 
import func.rl.common.RlHompage; 
import func.rl01z00.Rl01Z00Page; 
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
public class Rl01Z00Test001 extends AbstractSeleniumTestCase {
	private  final Logger logger = LoggerFactory.getLogger(Rl01Z00Test001.class);
    private String user = null;
    private String passwd = null;
    List<String[]> personIdSiteIdList;
    
    @Before
    public void beforeTest() throws Exception { 
        user ="RF1203008";
        passwd ="RF1203008";
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
        Rl01Z00Page rl01Z00Page =new Rl01Z00Page(this.selenium, this.driver);
        homepage.enterRl01Z00();
        int size = rl01Z00Page.countApplicationNum();
        for(int i =1 ; i<size ;++i){
            rl01Z00Page.selectApplication(i);
            rl01Z00Page.processPrintView();
            this. selenium.waitForPageToLoad("30000");
            Thread.sleep(1000l);
        }
        
        
        this. selenium.waitForPageToLoad("30000");        
        // Sleep the thread if you want to view the rendered page while testing.
//        assertTrue(true);
    } 
}
