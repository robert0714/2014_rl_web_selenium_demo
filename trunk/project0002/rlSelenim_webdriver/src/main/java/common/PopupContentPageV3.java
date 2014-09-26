/*
 * Copyright (c) 2010-2020 IISI.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of IISI.
 */
package common;
 

import func.rl.common.WebUtils;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
/**
 * The Class PopupContentPageV3.
 */
public class PopupContentPageV3 extends LoadableComponent<PopupContentPageV3>{ 
    
    /** The driver. */
    private final WebDriver driver;
    
    /** The logger. */
    private  final Logger logger = LoggerFactory.getLogger(getClass());
    
    /** The popupContent partial url. */
    private final String partialURL = "common/popupContent.xhtml";
    
    /** * 列印全部. */
    @FindBy(how = How.XPATH, using = "//td[2]/button")
    private WebElement printRadioAll;
    
    /** * 列印本頁. */
    @FindBy(how = How.XPATH, using = "//input[2]")
    private WebElement printRadioCurrent;    
    
    /** * 列印範圍. */
    @FindBy(how = How.XPATH, using = "//input[3]")
    private WebElement printRadioScope;
    
    /** * 列印起始範圍. */
    @FindBy(how = How.XPATH, using = "//input[contains(@id,'printStartPage')]")
    private WebElement printStartInputText;
    
    /** * 列印結束範圍. */
    @FindBy(how = How.XPATH, using = "//input[contains(@id,'printEndPage')]")
    private WebElement prinEndInputText;
    
    /** * 雙面列印. */
    @FindBy(how = How.XPATH, using = "//input[@name='twoSidePrint']")
    private WebElement twoSidePrintcheckBox;
    
    /** * 端末列印按鈕. */
    @FindBy(how = How.XPATH, using = "//span[contains(@id,'pdfbanner')]/span[2]/button")
    private WebElement printBtn;
        
    /** * 關閉按鈕. */
    @FindBy(how = How.XPATH, using = "//span[contains(@id,'pdfbanner')]/span[2]/button[2]")
    private WebElement closeBtn;
    
    /** * 輸入跳頁頁碼. */
    @FindBy(how = How.XPATH, using = "//input[contains(@id,'gotopages')]")
    private WebElement gotopagesInputText;
     
    /** * 跳頁按鈕. */
    @FindBy(how = How.XPATH, using = "//button")
    private WebElement gotopagesBtn;
        
    /** * 上一頁按鈕. */
    @FindBy(how = How.XPATH, using = "//button[2]")
    private WebElement backwardPageBtn;
    
    /** * 下一頁按鈕. */
    @FindBy(how = How.XPATH, using = "//button[3]")
    private WebElement forwardPageBtn;
    
    /** The current page num. */
    @FindBy(how = How.XPATH, using = "/html/body/table/tbody/tr/td/form/div/div/span/span[1]/span[1]/span")
    private WebElement currentPageNum;
    
    /** The current page info. */
    @FindBy(how = How.XPATH, using = "/html/body/table/tbody/tr/td/form/div/div/span/span[1]/span[1]")
    private WebElement currentPageInfo;
    
    /**
     * Instantiates a new popup content page v3.
     *
     * @param driver the driver
     */
    public PopupContentPageV3(WebDriver driver) {
        super();
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Load.
     */
    @Override
    protected void load() {
        final String mainUrl = WebUtils .getMainUrl( this.driver.getCurrentUrl());
        //http://192.168.10.18:6280/rl/faces/pages/common/popupContent.xhtml?windowId=998#{currentWindow.id}
        final String url = String.format("%s/rl/faces/pages/common/popupContent.xhtml", mainUrl ); 
        logger.info("導向: {}  ",url);   
        this.driver.get(url);
    }
    
    /**
     * Checks if is loaded.
     *
     * @throws Error the error
     */
    @Override
    protected void isLoaded() throws Error {
        final String currentUrl = this.driver.getCurrentUrl();
        //由於頁面網址會帶上windowId,會造成誤判       
        if(! StringUtils.contains(currentUrl, this.partialURL)){
            final String msg  =String.format("The wrong page has loaded: ", this.driver.getCurrentUrl()) ; 
            logger.info(msg);           
            throw new Error(msg);
        } 
    }

    /**
     * 點擊端末列印按鈕
     * Click print btn.
     */
    public void clickPrintBtn(){
        this.printBtn.click();
    }
    
    /**
     * 點擊關閉按鈕
     * Click close btn.
     */
    public void clickCloseBtn(){
        this.closeBtn.click();
    }
    
    /**
     * 輸入跳頁頁碼
     * Type gotopages.
     *
     * @param keysToSend the keys to send
     */
    public void typeGotopages(final int keysToSend){
        this.gotopagesInputText.clear();
        this.gotopagesInputText.sendKeys(String.valueOf(keysToSend));
    }
    
    /**
     * 點擊跳頁按鈕
     * Click gotopages btn.
     */
    public void clickGotopagesBtn(){
        this.gotopagesBtn.click();
    }
    
    /**
     * 點擊上一頁按鈕
     * Click backward page btn.
     */
    public void clickBackwardPageBtn(){
        this.backwardPageBtn.click();
    }
    
    /**
     * 點擊下一頁按鈕
     * Click forward page btn.
     */
    public void clickForwardPageBtn(){
        this.forwardPageBtn.click();
    }
    public void getInfo(){
        final String currentPageNum =  this.currentPageNum.getText();
        final  String currentPageInfo =  this. currentPageInfo.getText();
        logger.info(currentPageNum);
        logger.info(currentPageInfo);
    }
    /**
     * Prints the scope.
     * 輸入列印範圍
     * @param parm the parm
     * @param first the first
     * @param end the end
     */
    public void printScope(final PrintScopeParm parm, final int first, final int end) {
        switch (parm) {
            case ALL:
                this.printRadioAll.click();
                break;
            case CURRENT:
                this.printRadioCurrent.click();
                break;
            case SCOPE:
                this.printRadioScope.click();
                this.printStartInputText.clear();
                this.printStartInputText.sendKeys(String.valueOf(first));
                this.prinEndInputText.clear();
                this.prinEndInputText.sendKeys(String.valueOf(end));
                break;
            default:
                break;
        }

    }
    
    /**
     * The Enum PrintScopeParm.
     */
    public enum PrintScopeParm{
        /**  全部. */
        ALL(0),
        
        /**  列印本頁. */
        CURRENT(1),        
        
        /**  列印範圍. */
        SCOPE(2),
        /**
        * Instantiates a new birth kind.
        *
        * @param value the value
        */
        ;
        private PrintScopeParm(int value) {
            this.value = value;
        }

        /** The value. */
        private int value;
    }
}
