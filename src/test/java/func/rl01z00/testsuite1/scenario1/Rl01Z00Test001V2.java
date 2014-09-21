/*
 * Copyright (c) 2010-2020 IISI.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of IISI.
 */
package func.rl01z00.testsuite1.scenario1;
  
import java.util.List;
 
import func.rl.common.RlHompageV2;
import func.rl.common.WebUtils;
import func.rl01z00.Rl01Z00PageV2;

import org.junit.Before;
import org.junit.Test; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.study.selenium.AbstractSeleniumV2TestCase;
import static org.junit.Assert.assertTrue;

/**
 *
 */
public class Rl01Z00Test001V2 extends AbstractSeleniumV2TestCase {
    private  final Logger logger = LoggerFactory.getLogger(Rl01Z00Test001V2.class);
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
	final RlHompageV2 homepage = new RlHompageV2(this.driver);
	homepage.login(this.driver, this.user, this.passwd);
                
        // Sleep the thread if you want to view the rendered page while testing.
        assertTrue(true);
    }
    @Test
    public void testOpenRl01210() throws Exception {
	final RlHompageV2 homepage = new RlHompageV2(this.driver);
	homepage.login(this.driver, this.user, this.passwd);
        Rl01Z00PageV2 rl01Z00Page =new Rl01Z00PageV2( this.driver);
        homepage.enterRl01Z00();
        int size = rl01Z00Page.countApplicationNum();
        
        for(int i =1 ; i<size ;++i){
            rl01Z00Page.selectApplication(i);
            rl01Z00Page.processPrintView();
            WebUtils.pageLoadTimeout(this.driver);
            this.driver.navigate().refresh();
        }
    } 
}
