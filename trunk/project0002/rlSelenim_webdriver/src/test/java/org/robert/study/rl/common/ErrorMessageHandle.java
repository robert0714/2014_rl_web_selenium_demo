package org.robert.study.rl.common;

import com.thoughtworks.selenium.Selenium;

public class ErrorMessageHandle {

    /****
     * 按鈕Xpath為clickBtnXpath點選後
     * 針對訊息作處理
     * 有錯誤訊息回傳 true,無錯誤訊息回傳false
     * ***/
   public static  boolean handleClickBtn(final  Selenium selenium,final String clickBtnXpath){
       boolean giveUpOperation=false ;
       selenium.click(clickBtnXpath);//據說是資料驗證
       selenium.waitForPageToLoad("6000");
       if( selenium.isElementPresent("//*[@id='growl2_container']/div/div")){
	   int count=0;	  
	   while(true){			      
	       String errorMessage =selenium.getText("//*[@id='growl2_container']/div/div/div[2]/span");
	       String errorExtMessage =selenium.getText("//*[@id='growl2_container']/div/div/div[2]/p");		     
	       System.out.println(errorMessage);
	       System.out.println(errorExtMessage);
	       selenium.click("//span[4]/button");
	       if(count>5){
		   giveUpOperation=true;
		   break;
	       }
	       count++;
	   }
       }
       return giveUpOperation;
   }

}
