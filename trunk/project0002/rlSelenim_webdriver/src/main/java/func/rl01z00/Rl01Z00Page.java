package func.rl01z00;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.study.selenium.SeleniumConfig;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;

import func.rl.common.WebUtils;

import java.util.Set;

public class Rl01Z00Page {
    private static final Logger logger = LoggerFactory.getLogger(Rl01Z00Page.class);
    private WebDriver driver;
    private Selenium selenium;

    private final String rl01z00PartialUlr = "rl01z00/rl01z00.xhtml";

    public Rl01Z00Page(final Selenium selenium, final WebDriver driver) throws UnhandledAlertException, SeleniumException {
        super();
        this.selenium = selenium;
        this.driver = driver;
    }

    /******
     * 列印申請書測試程序
     * *****/
    public void processPrintView() throws InterruptedException {
        boolean giveUpOperation = false;

        // Save the WindowHandle of Parent Browser Window
        final String parentWindowId = driver.getWindowHandle();
        logger.debug("parentWindowId: " + parentWindowId);
        boolean printBtnXpathHit = false;
        final String printBtnXpath = "//button[@id='sendBt:sendBt']";

        if (selenium.isElementPresent(printBtnXpath)) {
            final WebElement printBtn = driver.findElement(By.xpath(printBtnXpath));
           
            this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
            final String disabledAttribute = printBtn.getAttribute("disabled");
            logger.debug("-----------------disabledAttribute: " + disabledAttribute);
            if (StringUtils.equals(disabledAttribute, Boolean.TRUE.toString())) {
                printBtnXpathHit = false;
            } else if (disabledAttribute == null || StringUtils.equals(disabledAttribute, Boolean.FALSE.toString())) {
                printBtnXpathHit = true;
                this.selenium.click(printBtnXpath);
                giveUpOperation=false;
            }
            this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        }

        if (!giveUpOperation && printBtnXpathHit) {
            // 預覽申請書會彈跳出視窗
            int count = 0;
            privntViewLoop: while (printBtnXpathHit) {
                Thread.sleep(5000);// 建議5秒
                boolean printViewPresent = false;
                try {
                    final Set<String> windowHandles = driver.getWindowHandles();
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
                                Thread.sleep(5000);// 建議5秒畢竟cognos實在太慢了
                                WebUtils.scroolbarDownUp(selenium, driver);
                                // *[@id="j_id4_j_id_9:j_id_y"]/span
                                // *[@id="j_id4_j_id_9:j_id_y"]
                                selenium.click("//span[contains(@id,'pdfbanner')]/span[2]/button[2]");// 端未列印
                                // form/div/div/div/div[2]/button[2]
                                // selenium.click("//form/div/div/div/div[2]/button[2]");//關閉
                                printViewPresent = true;
                                break browerWindowLoop;
                            }
                        }
                    }
                } catch (NoSuchWindowException e) {
                    logger.debug(e.getMessage(), e);
                }

                if (printViewPresent) {
                    // Close the Help Popup Window
                    driver.close();

                    // Move back to the Parent Browser Window
                    driver.switchTo().window(parentWindowId);
                    break privntViewLoop;
                }
                if (count > 10) {
                    break privntViewLoop;
                }
                count++;
            }
        }
        // div[@id='j_id39_j_id_sx_content']/button
    }

}
