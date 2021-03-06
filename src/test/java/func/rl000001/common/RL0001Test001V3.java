/*
 * Copyright (c) 2010-2020 IISI.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of IISI.
 */
package func.rl000001.common;
 


import java.util.ArrayList;
import java.util.List;
 
import func.rl.common.RlHompageV3;  
import func.rl00001.Rl00001PageV3;  
import org.junit.Before;
import org.junit.Test;  
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.study.selenium.AbstractSeleniumV2TestCase;
 

import static org.junit.Assert.assertTrue;

/**
 * 出生登記 基本展示 (登入,吳依兒童情境測試)
 */
public class RL0001Test001V3 extends AbstractSeleniumV2TestCase {
     private  final Logger logger = LoggerFactory.getLogger(RL0001Test001V3.class);
     private  Rl00001PageV3 rl00001Page;
     
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
	final RlHompageV3 homepage = new RlHompageV3(this.driver);

	homepage.login(this.driver, this.user, this.passwd);
                 
        assertTrue(true);
    } 
    @Test
    public void testOpenRl00001() throws Exception {
    	rl00001Page = new Rl00001PageV3(this.driver);
    	final RlHompageV3 homepage = new RlHompageV3(this.driver);

    	homepage.login(this.driver, this.user, this.passwd);
    	 
    	rl00001Page.get();
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
