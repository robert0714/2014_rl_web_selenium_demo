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
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

import func.rl.common.WebUtils;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.Point;
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
    private final static Logger LOGGER = LoggerFactory.getLogger(SRISWebUtils.class);

    public static void newPdfPreview(final WebDriver driver, final WebElement printBtn) {
        boolean giveUpOperation = false;
        final String disabledAttribute = printBtn.getAttribute("disabled");
        LOGGER.debug("disabledAttribute: {}" , disabledAttribute);
        if (StringUtils.equals(disabledAttribute, Boolean.TRUE.toString())) {
           return ;
        }  
        // Save the WindowHandle of Parent Browser Window
        final String parentWindowId = driver.getWindowHandle();
        LOGGER.debug("parentWindowId: " + parentWindowId);
        //除了printBtnXpath不同 , 大部分都是不同的

        final WebDriverWait wait = new WebDriverWait(driver, 120);

        wait.until(ExpectedConditions.visibilityOf(printBtn));

        final int originalSize = driver.getWindowHandles().size();
        LOGGER.info("預覽列印");
        
        WebUtils.handleClickBtn(driver, printBtn);

        final ExpectedCondition<Boolean> popupExpected = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver input) {
                //預覽列印是新增加視窗
                return (driver.getWindowHandles().size() > originalSize);
            }
        };


        wait.until(popupExpected);
        LOGGER.info("預覽列印視窗出現");

        // 預覽申請書會彈跳出視窗
        int count = 0;
        privntViewLoop: while (count < 10) {

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
                        //    isAlertPresent(driver);
                        if (StringUtils.contains(currentUrl, "common/popupContent.xhtml")) {
                            // 戶役資訊服務網
                            String title = driver.getTitle();
                            LOGGER.debug("title: {}" , title);
                            final String terminatorPrintXpath = "//span[contains(@id,'pdfbanner')]/span[2]/button[2]";
                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(terminatorPrintXpath)));
                            LOGGER.info("等待預覽列印網頁");

                            final WebElement imgElement = driver.findElement(By.xpath("//*[@id='pdfpreviewimg']"));
                            wait.until(ExpectedConditions.visibilityOf(imgElement));
                            
                            
                            LOGGER.info("imgElement presented");

                            final String pdfViewerContentXpath = "//*[contains(@id,'pdfViewerContent')]/input";
                            final WebElement hiddenElement = driver.findElement(By.xpath(pdfViewerContentXpath));
                            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(pdfViewerContentXpath)));
                            LOGGER.info("base64 presented");

                            final String base64 = hiddenElement.getText();
                            LOGGER.info("預覽列印網頁內容 (base64): {}", base64);

                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(terminatorPrintXpath)));

                            WebUtils.scroolbarDownUp(driver);
                           
                            LOGGER.info("轉動卷軸 " );
                            
                            //點擊關閉視窗
                            driver.findElement(By.xpath(terminatorPrintXpath)).click();
                            
                            final ExpectedCondition<Boolean> popupCloseExpected = new ExpectedCondition<Boolean>() {
                                public Boolean apply(WebDriver input) {
                                 final Set<String> nowWindowHandles = driver.getWindowHandles();
                                     if (!nowWindowHandles.contains(windowId)) {
                                     return true;
                                     }else{
                                     return false;
                                     }
                                }
                            };
                            wait.until(popupCloseExpected);
                            //等到popup 關閉之後
                            LOGGER.info("關閉預覽視窗結束 " );
                            
                            // Move back to the Parent Browser Window
                            final Set<String> nowWindowHandles = driver.getWindowHandles();
                            nowWindowHandles.remove(parentWindowId);
                            if (!nowWindowHandles.contains(windowId)) {
                                //只有在被關閉的情形下才會找不到
                                driver.switchTo().window(parentWindowId);
                                if (StringUtils.contains(driver.getCurrentUrl(), "/rl00001/householdMaintain.xhtml")) {
                                    break privntViewLoop;
                                }
                            } else {
                                printViewPresent = true;
                                break browerWindowLoop;
                            }

                        }
                    }
                }
            } catch (NoSuchWindowException e) {
                LOGGER.debug(e.getMessage(), e);
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
                LOGGER.debug(e.getMessage(), e);
                driver.switchTo().window(parentWindowId);
                break privntViewLoop;
            }
            if (count > 10) {
                break privntViewLoop;
            }
            count++;
        }
    
    }

    public static void newPdfPreview(final WebDriver driver, final String printBtnXpath) {
        final WebElement printBtn = driver.findElement(By.xpath(printBtnXpath));
        newPdfPreview(driver, printBtn);
    }

    /**
     * 輸入出生日期
     * Type birth yyymmdd.
     *
     * @param birthYyymmdd the birth yyymmdd
     */
    public static void typeYyymmdd(final String birthYyymmdd,final WebDriver driver,final  String xpath) {
        final WebDriverWait wait =    new WebDriverWait(driver, 60);
        
        //input[@id='j_id_2k:birthYyymmdd:j_id_uj']
        final String yyy = org.apache.commons.lang.StringUtils.substring(birthYyymmdd, 0, 3);
        final String mm = org.apache.commons.lang.StringUtils.substring(birthYyymmdd, 3, 5);
        final String dd = org.apache.commons.lang.StringUtils.substring(birthYyymmdd, 5, 7);
       
        final String  yyyXpath= xpath+"/input";
        final String  mmXpath= xpath+"/input[2]";
        final String  ddXpath= xpath+"/input[3]";
        
        
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(yyyXpath))).sendKeys(yyy);
        WebUtils.pageLoadTimeout(driver);
        
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(mmXpath))).sendKeys(mm);
        WebUtils.pageLoadTimeout(driver);
        
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(ddXpath))).sendKeys(dd);
        WebUtils.pageLoadTimeout(driver);
    }
    
    private static boolean isAlertPresent(final WebDriver driver) {
        try {
            Alert alert = driver.switchTo().alert();
            LOGGER.info(alert.getText());
            alert.accept();
            LOGGER.debug("alert was present");
            return true;
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            // Modal dialog showed
            return false;
        }
    }

    public static void typeAutoComplete(final WebDriver driver, final String xpath, final String value) {
        /***
         * ex: //td[contains(@id,'currentPersonSiteIdTD')]/span/input
         * **/
        final String typeXpath = xpath + "/span/input";

        final WebElement oWE = driver.findElement(By.xpath(typeXpath));

        /***
         * ex: //td[contains(@id,'currentPersonSiteIdTD')]/span/span/img
         * **/
        final String closeXpath = xpath + "/span/span/img";
        
        final String selectXpath = xpath + "/span/div";
        
        final Actions oAction = new Actions(driver);
        
        for (int i = 0; i < 1; ++i) {
            WebUtils.pageLoadTimeout(driver);
            driver.findElement(By.xpath(typeXpath)).click();
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
           
            oAction.moveToElement(oWE);
            oAction.doubleClick(oWE).build().perform();
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//            if (StringUtils.isNotBlank(oWE.getText())) {
//                oWE.clear();
//                driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//            }
            oWE.clear();
            oWE.sendKeys(value);
        } 
        
        /**
         * 使用debug mode可以正常...但是normal mode就是發生異常...社群建議直接使用 javascript work around
         * **/
//        final  WebDriverWait wait = new WebDriverWait(driver, 10);        
//        final WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(selectXpath)));
//        oAction.moveToElement(element).build().perform();
//        WebUtils.pageLoadTimeout(driver);
//        oAction.doubleClick(element).build().perform();
        
        /***
         * 由於發現使用Selenium2 (WebDrvier有異常不能正常操作,所以實作暫時改用Selenium1) ,使用closeXpath又不是每次都ok
         * ***/
        WebDriverBackedSelenium selenium = new WebDriverBackedSelenium(driver, driver.getCurrentUrl());
        
        selenium.fireEvent(typeXpath, "blur");
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        WebUtils.pageLoadTimeout(driver);
    }

    public static void typeAutoCompleteBySpanXpath(final WebDriver driver, final String spanXpath, final String value) {
        /***
         * ex: //td[contains(@id,'currentPersonSiteIdTD')]/span/input
         * **/
        final String typeXpath = spanXpath + "/input";

        /***
         * ex: //td[contains(@id,'currentPersonSiteIdTD')]/span/span/img
         * **/
        final String closeXpath = spanXpath + "/span/img";
        
        final String selectXpath = spanXpath + "/div";

        final WebElement oWE = driver.findElement(By.xpath(typeXpath));
        
        final Actions oAction = new Actions(driver);
        for (int i = 0; i < 1; ++i) {
            WebUtils.pageLoadTimeout(driver);
            driver.findElement(By.xpath(typeXpath)).click();
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            
           
            oAction.moveToElement(oWE).build().perform();
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            
            oAction.doubleClick(oWE).build().perform();
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            ;
//            if (StringUtils.isNotBlank(oWE.getText())) {
//                oWE.clear();
//                driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//            }
            oWE.clear();
            
            oAction.sendKeys(oWE,value).build().perform(); 
            WebUtils.pageLoadTimeout(driver);
        }
        
//        oAction.moveToElement(driver.findElement(By.xpath(selectXpath))).build().perform();
//        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//        oAction.doubleClick(driver.findElement(By.xpath(selectXpath))).build().perform();
//        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        
        /***
         * 由於發現使用Selenium2 (WebDrvier有異常不能正常操作,所以實作暫時改用Selenium1) ,使用closeXpath又不是每次都ok
         * ***/
        WebDriverBackedSelenium selenium = new WebDriverBackedSelenium(driver, driver.getCurrentUrl());
        
        selenium.fireEvent(typeXpath, "blur");
        WebUtils.pageLoadTimeout(driver);
    }

    public static void typeAutoCompleteBySpanXpath(final Selenium selenium, final String spanXpath, final String value) {
        /***
         * ex: //td[contains(@id,'currentPersonSiteIdTD')]/span/input
         * **/
        final String typeXpath = spanXpath + "/input";

        /***
         * ex: //td[contains(@id,'currentPersonSiteIdTD')]/span/span/img
         * **/
        final String closeXpath = spanXpath + "/span/img";

        for (int i = 0; i < 2; ++i) {
            selenium.doubleClick(typeXpath);
            selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
            selenium.type(typeXpath, value);
        }
        selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        selenium.fireEvent(typeXpath, "blur");
        selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
    }

    public static void typeAutoComplete(final Selenium selenium, final String xpath, final String value) {
        /***
         * ex: //td[contains(@id,'currentPersonSiteIdTD')]/span/input
         * **/
        final String typeXpath = xpath + "/span/input";

        /***
         * ex: //td[contains(@id,'currentPersonSiteIdTD')]/span/span/img
         * **/
        final String closeXpath = xpath + "/span/span/img";

        for (int i = 0; i < 2; ++i) {
            selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
            selenium.doubleClick(typeXpath);
            selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
            selenium.type(typeXpath, value);
        }
        selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        selenium.fireEvent(typeXpath, "blur");
        //        selenium.click(closeXpath);
        selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
    }
}
