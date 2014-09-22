/*
 * Copyright (c) 2010-2020 IISI.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of IISI.
 */
package org.study.selenium;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.thoughtworks.selenium.Selenium;

import func.rl.common.WebUtils;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class SRISWebUtils {
    private  final static Logger LOGGER = LoggerFactory.getLogger(SRISWebUtils.class);
    
    public static void newPdfPreview(final WebDriver driver,final String printBtnXpath){

        boolean giveUpOperation = false;

        // Save the WindowHandle of Parent Browser Window
        final String parentWindowId = driver.getWindowHandle();
        LOGGER.debug("parentWindowId: " + parentWindowId);
        //除了printBtnXpath不同 , 大部分都是不同的
//        final String printBtnXpath = "//button[@id='sendBt:sendBt']";


        WebDriverWait wait = new WebDriverWait(driver, 60);
        final WebElement printBtn = driver.findElement(By.xpath(printBtnXpath));
        wait.until(ExpectedConditions.visibilityOf(printBtn) );
        
        final int originalSize = driver.getWindowHandles().size() ;
        LOGGER.info("預覽列印");
        giveUpOperation = WebUtils.handleClickBtn(driver ,  printBtnXpath);
        
        final  ExpectedCondition<Boolean> popupExpected =  new ExpectedCondition<Boolean>() {
	    public Boolean apply(WebDriver input) {
		//預覽列印是新增加視窗
		return (driver.getWindowHandles().size() > originalSize);
	    }
	};	
	if (!giveUpOperation ) {
	    
	    wait.until(popupExpected);
	    LOGGER.info("預覽列印視窗出現");
	    
	    // 預覽申請書會彈跳出視窗
	    int count = 0;
	    privntViewLoop: while (true) {
		
		boolean printViewPresent = false;
		try {
		    final Set<String> windowHandles = driver.getWindowHandles();
		    windowHandles.remove(parentWindowId);
		    browerWindowLoop: for (final String windowId : windowHandles) {
			if (!StringUtils.equalsIgnoreCase(windowId, parentWindowId)) {
			    // Switch to the Help Popup Browser Window
			    driver.switchTo().window(windowId);
			    String currentUrl = driver.getCurrentUrl();
			    LOGGER.debug(currentUrl);	
//			    isAlertPresent(driver);
			    if (StringUtils.contains(currentUrl, "common/popupContent.xhtml")) {
				// 戶役資訊服務網
				String title = driver.getTitle();
				LOGGER.debug("title: " + title);
                                final String terminatorPrintXpath = "//span[contains(@id,'pdfbanner')]/span[2]/button[2]";
                                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(terminatorPrintXpath)));
                                LOGGER.info("等待預覽列印網頁");
                                
                                final  WebElement imgElement = driver.findElement(By.xpath("//*[@id='pdfpreviewimg']"));
                                wait.until(ExpectedConditions.visibilityOf(imgElement));
                                LOGGER.info("imgElement presented");
                                
                                final String pdfViewerContentXpath = "//*[contains(@id,'pdfViewerContent')]/input";
                                final  WebElement hiddenElement = driver.findElement(By.xpath(pdfViewerContentXpath));
                                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(pdfViewerContentXpath)));
                                LOGGER.info("base64 presented");
                                
                                final  String base64 = hiddenElement.getText() ;           
                                LOGGER.info("預覽列印網頁內容 (base64): {}",base64);
                                
                                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(terminatorPrintXpath)));
                                
                                WebUtils.scroolbarDownUp( driver);
                                // *[@id="j_id4_j_id_9:j_id_y"]/span
                                // *[@id="j_id4_j_id_9:j_id_y"]
                                driver.findElement(By.xpath(terminatorPrintXpath)).click();
                                 // 端未列印
                                // form/div/div/div/div[2]/button[2]
                                // selenium.click("//form/div/div/div/div[2]/button[2]");//關閉
                                
                                // Move back to the Parent Browser Window
                                final Set<String> nowWindowHandles = driver.getWindowHandles();
                                nowWindowHandles.remove(parentWindowId);
                                if(!nowWindowHandles.contains(windowId)){
                                    //只有在被關閉的情形下才會找不到
                                    driver.switchTo().window(parentWindowId);
                                    if(StringUtils.contains(driver.getCurrentUrl(), "/rl00001/householdMaintain.xhtml")){
                                        break privntViewLoop;
                                    }
                                }else{
                                    printViewPresent = true;
                                    break browerWindowLoop;
                                }
                                
			    }
			}
		    }
		} catch (NoSuchWindowException e) {
		    LOGGER.debug(e.getMessage() ,e );
		}

                try {
                    //當點擊關閉預覽視窗按鈕失敗時,需要強制關閉
                    if (printViewPresent && !StringUtils.equalsIgnoreCase(driver.getWindowHandle(), parentWindowId)) {
                        // Close the Help Popup Window
                        driver.close();

                        // Move back to the Parent Browser Window
                        driver.switchTo().window(parentWindowId);
                        break privntViewLoop;
                    }
                } catch (NoSuchWindowException e) {
                    LOGGER.debug(e.getMessage() ,e );
                    driver.switchTo().window(parentWindowId);
                    break privntViewLoop;
                }
		if (count > 10) {
		    break privntViewLoop;
		}
		count++;
	    }
	} 
    }
    
    private static boolean isAlertPresent(final WebDriver driver) {
        try {
            Alert alert = driver.switchTo().alert();
            LOGGER.info(alert.getText());
            alert.accept();
            LOGGER.debug("alert was present");
            return true;
        } catch (Exception e) {
            LOGGER.info(e.getMessage() , e );
            // Modal dialog showed
            return false;
        }   
    }
    public static void typeAutoComplete(final WebDriver driver ,final String xpath ,final String value){
        /***
         * ex: //td[contains(@id,'currentPersonSiteIdTD')]/span/input
         * **/
        final String typeXpath = xpath + "/span/input";
        
        final WebElement oWE = driver.findElement(By.xpath(typeXpath));
        
        /***
         * ex: //td[contains(@id,'currentPersonSiteIdTD')]/span/span/img
         * **/
        final String closeXpath =  xpath +"/span/span/img";
        
        for (int i = 0; i < 1; ++i) {
            WebUtils.pageLoadTimeout(driver);
            driver.findElement(By.xpath(typeXpath)).click();
            driver.manage().timeouts().implicitlyWait(20,  TimeUnit.SECONDS);
            Actions oAction = new Actions(driver);
            oAction.moveToElement(oWE);
            oAction.doubleClick(oWE).build().perform();
            driver.manage().timeouts().implicitlyWait(20,  TimeUnit.SECONDS);
            if (StringUtils.isNotBlank(oWE.getText())) {
		oWE.clear();
		driver.manage().timeouts().implicitlyWait(20,  TimeUnit.SECONDS);
	    }
            oWE.sendKeys(value); 
            driver.manage().timeouts().implicitlyWait(20,  TimeUnit.SECONDS);
        }
        driver.findElement(By.xpath(closeXpath)).click();
        WebUtils.pageLoadTimeout(driver); 
    }
    
    public static void typeAutoCompleteBySpanXpath(final WebDriver driver,final String spanXpath ,final String value){
        /***
         * ex: //td[contains(@id,'currentPersonSiteIdTD')]/span/input
         * **/
        final String typeXpath = spanXpath + "/input";
        
        /***
         * ex: //td[contains(@id,'currentPersonSiteIdTD')]/span/span/img
         * **/
        final String closeXpath =  spanXpath +"/span/img";
        
        final WebElement oWE = driver.findElement(By.xpath(typeXpath));
        for (int i = 0; i < 1; ++i) {            
            WebUtils.pageLoadTimeout(driver);
            driver.findElement(By.xpath(typeXpath)).click();
            driver.manage().timeouts().implicitlyWait(20,  TimeUnit.SECONDS);
            Actions oAction = new Actions(driver);
            oAction.moveToElement(oWE);
            oAction.doubleClick(oWE).build().perform();
            driver.manage().timeouts().implicitlyWait(20,  TimeUnit.SECONDS);;
	    if (StringUtils.isNotBlank(oWE.getText())) {
		oWE.clear();
		driver.manage().timeouts().implicitlyWait(20,  TimeUnit.SECONDS);
	    }
            oWE.sendKeys(value); 
            WebUtils.pageLoadTimeout(driver);
        }
        driver.findElement(By.xpath(closeXpath)).click();
        WebUtils.pageLoadTimeout(driver); 
       
    }
    public static void typeAutoCompleteBySpanXpath(final Selenium selenium ,final String spanXpath ,final String value){
        /***
         * ex: //td[contains(@id,'currentPersonSiteIdTD')]/span/input
         * **/
        final String typeXpath = spanXpath + "/input";
        
        /***
         * ex: //td[contains(@id,'currentPersonSiteIdTD')]/span/span/img
         * **/
        final String closeXpath =  spanXpath +"/span/img";
        
        for (int i = 0; i < 2; ++i) {
            selenium.doubleClick(typeXpath);
            selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
            selenium.type(typeXpath,  value); 
        }
        selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        selenium.fireEvent(typeXpath, "blur");
        selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
    }
    
    public static void typeAutoComplete(final Selenium selenium ,final String xpath ,final String value){
        /***
         * ex: //td[contains(@id,'currentPersonSiteIdTD')]/span/input
         * **/
        final String typeXpath = xpath + "/span/input";
        
        /***
         * ex: //td[contains(@id,'currentPersonSiteIdTD')]/span/span/img
         * **/
        final String closeXpath =  xpath +"/span/span/img";
        
        for (int i = 0; i < 2; ++i) {
            selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
            selenium.doubleClick(typeXpath);
            selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
            selenium.type(typeXpath,  value);
        }
        selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        selenium.fireEvent(typeXpath, "blur");
//        selenium.click(closeXpath);
        selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
    }
    //================================================
    //== [Enumeration types] Block Start
    //== [Enumeration types] Block End 
    //================================================
    //== [static variables] Block Start
    //== [static variables] Block Stop 
    //================================================
    //== [instance variables] Block Start
    //== [instance variables] Block Stop 
    //================================================
    //== [static Constructor] Block Start
    //== [static Constructor] Block Stop 
    //================================================
    //== [Constructors] Block Start (含init method)
    //== [Constructors] Block Stop 
    //================================================
    //== [Static Method] Block Start
    //== [Static Method] Block Stop 
    //================================================
    //== [Accessor] Block Start
    //== [Accessor] Block Stop 
    //================================================
    //== [Overrided Method] Block Start (Ex. toString/equals+hashCode)
    //== [Overrided Method] Block Stop 
    //================================================
    //== [Method] Block Start
    //####################################################################
    //## [Method] sub-block : 
    //####################################################################    
    //== [Method] Block Stop 
    //================================================
    //== [Inner Class] Block Start
    //== [Inner Class] Block Stop 
    //================================================
}
