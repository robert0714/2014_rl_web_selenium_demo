
package func.rl00001._rl01210;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;

import func.rl.common.WebUtils;


import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 */
public class Rl01210Page {
    protected final static Logger logger = Logger.getLogger(Rl01210Page.class);
    private WebDriver driver;
    private Selenium selenium;

    private final String rl01210PartialUlr = "_rl01210/rl01210.xhtml";

    public Rl01210Page(final Selenium selenium, final WebDriver driver) throws UnhandledAlertException,
            SeleniumException {
        super();
        this.selenium = selenium;
        this.driver = driver;
    }

    public void switchTab()throws  UnhandledAlertException,SeleniumException, InterruptedException  {
        final String currentUrl = this.driver.getCurrentUrl();
        if (StringUtils.contains(currentUrl,this. rl01210PartialUlr)) {
            this.selenium.refresh();
            this. selenium.focus("//a[contains(text(),'戶籍記事/罰鍰清單')]");
            this.selenium.click("//a[contains(text(),'戶籍記事/罰鍰清單')]");
            this. selenium.waitForPageToLoad("300000");
            this.  selenium.focus("//a[contains(text(),'全戶基本資料')]");
            this. selenium.click("//a[contains(text(),'全戶基本資料')]");
            inputData01();
//            inputData02();
           
        }
    }
    public void inputData01() throws UnhandledAlertException, SeleniumException, InterruptedException {
        this. selenium.click("//a[contains(text(),'全戶基本資料/出生者、父母資料')]");
        this. selenium.waitForPageToLoad("300000");
        String element01 =this. selenium.getText("document.poopupForm.elements[1]");

        this. selenium.waitForPageToLoad("300000");
        System.out.println(element01);
       

        WebUtils.scroolbarDownUp(this.selenium,this. driver);

    }
    public void checkBirthKind(final BirthKind type){
        
    }
    enum BirthKind{
        WEDLOCK,//婚生
        POSTHUMOUS,//遺腹子
        OUTOFWEDLOCK1,//非婚生（續辦認領）
        OUTOFWEDLOCK2,//非婚生（不續辦認領）
        INNOCENTI,//無依兒童
    }
}
