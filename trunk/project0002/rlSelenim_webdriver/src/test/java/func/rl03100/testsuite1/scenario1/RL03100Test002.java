/*
 * Copyright (c) 2010-2020 IISI.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of IISI.
 */
package func.rl03100.testsuite1.scenario1;
 


import java.util.ArrayList;
import java.util.List;

import func.rl.common.RlHompage;
import func.rl.common.WebUtils;
import func.rl03100.Rl03100Page;

import org.apache.commons.collections.CollectionUtils;
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
    private static final Logger logger = Logger.getLogger(RL03100Test002.class);
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
        final RlHompage homepage = new RlHompage(this.selenium, this.driver);
        homepage.login(this.selenium,this.user, this.passwd);
        this. selenium.waitForPageToLoad("30000");
        
        // Sleep the thread if you want to view the rendered page while testing.
        assertTrue(true);
    }
    @Test
    public void testOpenRl03100() throws Exception {
        final RlHompage homepage = new RlHompage(this.selenium, this.driver);
        homepage.login(this.selenium,this.user, this.passwd);
        homepage.enterRl03100();
        Rl03100Page rl31000Page = new Rl03100Page(this.selenium,  null);
        
        if (CollectionUtils.isNotEmpty( this.personIdSiteIdList)) {
            for (String[] stringArray :  this.personIdSiteIdList) {
                this. selenium.waitForPageToLoad("30000");
                homepage.enterRl03100();
                this. selenium.waitForPageToLoad("30000");
                final String personId = stringArray[0];
                if (StringUtils.contains(personId, "*")) {
                    continue;
                }
                final String siteId = stringArray[1];

                try {
                    process4(personId, siteId);
                } catch (Exception e) {
                    e.printStackTrace();
                    WebUtils.handleRLAlert(selenium);
                }
            }
        } else {
            homepage.enterRl03100();
            this. selenium.waitForPageToLoad("30000");
            process4("C100201902", "65000120");
            //      process4(null,null);
        }
        
        
        this. selenium.waitForPageToLoad("30000");        
        // Sleep the thread if you want to view the rendered page while testing.
        assertTrue(true);
    }
    private void process4(final String personId, final String siteId) throws Exception {
        Rl03100Page rl00004Page = new Rl03100Page(selenium, driver);
        rl00004Page.typeApplication(personId, siteId, "/home/weblogic/Desktop/PIC/");
    }
    private List<String[]> getPsedoData(){
        final List<String[]>  result = new ArrayList<String[]>();
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
        
        result.add(new String[]{"A129677773","10010070"});
        result.add(new String[]{"B120138605","10010070"});
        result.add(new String[]{"B120702178","10010070"});
        result.add(new String[]{"C120600821","10010070"});
        result.add(new String[]{"C124277999","10010070"});
        result.add(new String[]{"E120499839","10010070"});
        result.add(new String[]{"E121473795","10010070"});
        result.add(new String[]{"E122760528","10010070"});
        result.add(new String[]{"F108308572","10010070"});
        result.add(new String[]{"G129180762","10010070"});
        
        return  result;
    }
}
