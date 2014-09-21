package func.rl01z00;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.study.selenium.SeleniumConfig;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

import func.rl.common.WebUtils;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rl01Z00PageV2 {
    private static final Logger logger = LoggerFactory.getLogger(Rl01Z00PageV2.class);
    private WebDriver driver;

    private final String rl01z00PartialUlr = "rl01z00/rl01z00.xhtml";

    public Rl01Z00PageV2(final WebDriver driver) throws UnhandledAlertException, SeleniumException {
        super();
        this.driver = driver;
    }
    public int  countApplicationNum(){	
	/***
         * 由於發現使用Selenium2 (WebDrvier有異常不能正常操作,所以實作暫時改用Selenium1)
         * ***/
        WebDriverBackedSelenium selenium = new WebDriverBackedSelenium(driver, driver.getCurrentUrl());
	;
        final String content = selenium.getText("//div[@id='j_id_40_panel']/div/ul");
        final Matcher mather = Pattern.compile("(登記申請書)").matcher(content);
        int i = 0 ;
        while(mather.find()){
//            logger.info("groupCount : {}",mather.groupCount());
            ++i;
        }
        selenium =null;
        return  i ;
    }
    public static void main(String[] args){
        final String sample ="個人記事（補填、更正）登記申請書全戶動態記事（補填、更正）登記申請書除戶個人記事（補填、更正）登記申請書除戶全戶動態記事（補填、更正）登記申請書出生登記申請書（死亡∕死亡宣告）登記申請書結婚登記申請書離婚登記申請書認領登記申請書收養登記申請書終止收養登記申請書出生地登記申請書原住民身分及民族別登記申請書原住民傳統姓名羅馬拼音登記原住民傳統姓名羅馬拼音更正原住民傳統姓名羅馬拼音變更原住民傳統姓名羅馬拼音撤銷監護登記申請書未成年子女權利義務行使負擔登記申請書輔助登記申請書歸化國籍者姓名羅馬拼音登記歸化國籍者姓名羅馬拼音更正歸化國籍者姓名羅馬拼音變更歸化國籍者姓名羅馬拼音撤銷遷入戶籍登記申請書遷出戶籍登記申請書住址變更戶籍登記申請書分（合）戶登記申請書遷入戶籍登記申請書（通訊中斷後處理）遷出戶籍登記申請書（通訊中斷後處理）初設戶籍登記申請書印鑑登記申請書印鑑變更登記申請書印鑑註銷登記申請書門牌編釘登記申請書行政區劃及門牌更正登記申請書戶別更正登記申請書統一編號更正登記申請書姓名更正登記申請書出生日期更正登記申請書稱謂更正登記申請書出生別更正登記申請書配偶姓名更正登記申請書父姓名更正登記申請書母姓名更正登記申請書原住民身分及族別更正登記申請書出生地更正登記申請書戶號更正登記申請書養父姓名更正登記申請書養母姓名更正登記申請書親子關係父母姓名更正申請書戶長變更登記申請書姓名變更∕冠姓∕從姓登記申請書出生別變更登記申請書配偶姓名變更登記申請書父姓名變更登記申請書母姓名變更登記申請書養父姓名變更登記申請書養母姓名變更登記申請書統一編號變更登記申請書戶號變更登記申請書結婚撤銷登記申請書離婚撤銷登記申請書遷入撤銷登記申請書遷出撤銷登記申請書住址變更撤銷登記申請書父姓名更正撤銷登記母姓名更正撤銷登記養父姓名更正撤銷登記養母姓名更正撤銷登記配偶姓名更正撤銷登記出生別更正撤銷登記認領撤銷登記申請書收養撤銷登記申請書（死亡／死亡宣告）撤銷登記申請書出生撤銷登記申請書姓名變更撤銷登記戶籍撤銷登記申請書出生地撤銷登記申請書終止收養撤銷登記申請書廢止戶籍撤銷登記申請書原住民身分及民族別撤銷登記申請書分（合）戶撤銷登記申請書監護撤銷登記申請書未成年子女權利義務行使負擔撤銷登記申請書輔助撤銷登記申請書保護家庭暴力資料註記申請書結婚廢止登記申請書離婚廢止登記申請書配偶姓名更正廢止登記申請書戶籍廢止登記申請書監護廢止登記申請書未成年子女權利義務行使負擔廢止登記申請書輔助廢止登記申請書戶籍謄本申請書印鑑證明申請書門牌證明申請書戶口名簿申請書戶口統計資料申請書英文戶籍謄本申請書初領國民身分證申請書一人初領國民身分證申請書兩人補領國民身分證申請書一人補領國民身分證申請書兩人換領國民身分證申請書一人換領國民身分證申請書兩人中文結婚證明書申請書中文離婚證明書申請書英文結婚證明書申請書英文離婚證明書申請書現無戶籍者結婚登記申請書現無戶籍者離婚登記申請書";
        final Matcher mather = Pattern.compile("(登記申請書)").matcher(sample);
        int i=0 ;
        while(mather.find()){
            logger.info("groupCount : {}",mather.groupCount());
            ++i;
        }
        logger.info("  mather.regionEnd(): {}",  mather.regionEnd());
        logger.info("  mather size: {}",  i);
        
    }
    public void selectApplication(int i ) throws InterruptedException {
        final String applyCodeSelected = String.format("//div[@id='j_id_40_panel']/div/ul/li[%s]", i);
        this.driver.findElement(By.xpath("//td/div/label")).click();
        this.driver.findElement(By.xpath(applyCodeSelected)).click();
//        selenium.click("//td/div/label");
//        selenium.click(applyCodeSelected);
    }
    public void selectApplication() throws InterruptedException {
	this.driver.findElement(By.xpath("//td/div/label")).click();
        this.driver.findElement(By.xpath("//div[@id='j_id_40_panel']/div/ul/li[2]")).click();
//        selenium.click("//td/div/label");
//        selenium.click("//div[@id='j_id_40_panel']/div/ul/li[2]");
    }
    /******
     * 列印申請書測試程序
     * *****/
    public void processPrintView() throws InterruptedException {
        boolean giveUpOperation = false;

        // Save the WindowHandle of Parent Browser Window
        final String parentWindowId = driver.getWindowHandle();
        logger.debug("parentWindowId: " + parentWindowId);
        //除了printBtnXpath不同 , 大部分都是不同的
        final String printBtnXpath = "//button[@id='sendBt:sendBt']";


        WebDriverWait wait = new WebDriverWait(driver, 60);
        final WebElement printBtn = this.driver.findElement(By.xpath(printBtnXpath));
        wait.until(ExpectedConditions.visibilityOf(printBtn) );
        
        final int originalSize = driver.getWindowHandles().size() ;
        logger.info("預覽列印");
        giveUpOperation = WebUtils.handleClickBtn(this.driver ,  printBtnXpath);
        
        final  ExpectedCondition<Boolean> popupExpected =  new ExpectedCondition<Boolean>() {
	    public Boolean apply(WebDriver input) {
		//預覽列印是新增加視窗
		return (driver.getWindowHandles().size() > originalSize);
	    }
	};
	
	
	if (!giveUpOperation ) {
	    
	    wait.until(popupExpected);
	    logger.info("預覽列印視窗出現");
	    
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
			    logger.debug(currentUrl);
			    if (StringUtils.contains(currentUrl, "common/popupContent.xhtml")) {
				// 戶役資訊服務網
				String title = driver.getTitle();
				logger.debug("title: " + title);
                                final String terminatorPrintXpath = "//span[contains(@id,'pdfbanner')]/span[2]/button[2]";
                                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(terminatorPrintXpath)));
                                logger.info("等待預覽列印網頁");
                                
                                final  WebElement imgElement = this.driver.findElement(By.xpath("//*[@id='pdfpreviewimg']"));
                                wait.until(ExpectedConditions.visibilityOf(imgElement));
                                logger.info("imgElement presented");
                                
                                final String pdfViewerContentXpath = "//*[contains(@id,'pdfViewerContent')]/input";
                                final  WebElement hiddenElement = this.driver.findElement(By.xpath(pdfViewerContentXpath));
                                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(pdfViewerContentXpath)));
                                logger.info("base64 presented");
                                
                                final  String base64 = hiddenElement.getText() ;           
                                logger.info("預覽列印網頁內容 (base64): {}",base64);
                                
                                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(terminatorPrintXpath)));
                                
                                WebUtils.scroolbarDownUp( driver);
                                // *[@id="j_id4_j_id_9:j_id_y"]/span
                                // *[@id="j_id4_j_id_9:j_id_y"]
                                this.driver.findElement(By.xpath(terminatorPrintXpath)).click();
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
		    logger.debug(e.getMessage() ,e );
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
                    logger.debug(e.getMessage() ,e );
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
}
