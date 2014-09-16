
package func.rl00001._rl01210;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;

import func.rl.common.WebUtils;


import org.apache.commons.lang3.StringUtils;  
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.study.selenium.SeleniumConfig;

/**
 *
 */
public class Rl01210Page {
	private static final Logger logger = LoggerFactory.getLogger(Rl01210Page.class);
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
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        String element01 =this. selenium.getText("document.poopupForm.elements[1]");
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        System.out.println(element01);
        WebUtils.scroolbarDownUp(this.selenium,this. driver);
        
        //選擇無依兒童        
        checkBirthKind(BirthKind.INNOCENTI);
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        //自立新戶
        setNewHousehold(true);
        WebUtils.scroolbarDownUp(this.selenium,this. driver);
        //非自立新戶(入他人戶)
        setNewHousehold(false);
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        //輸入戶長統號
        typeHouseholdHeadId("C100202427");
        //輸入戶號
        typeHouseholdId("F5261129");
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        getRLDF001MByClickBtn();
        this.selenium.waitForPageToLoad(SeleniumConfig.waitForPageToLoad);
        WebUtils.scroolbarDown(this.selenium,this. driver);
        typeLastName("無姓");
        typeFirstName("無名");
    }
    //輸入出生者資料的姓
    public void typeLastName(final String lastName){
        ////span[@id='j_id_2k:lastName']/span/input
        this.selenium.type("//span[contains(@id,'lastName')]/span/input", lastName);
    }
    //輸入出生者資料的名
    public void typeFirstName(final String firstName){
        ////span[@id='j_id_2k:firstName']/span/input
        this.selenium.type("//span[contains(@id,'firstName')]/span/input", firstName);
    }
    //取得全戶基本資料
    public void getRLDF001MByClickBtn(){
      //span[@id='j_id_2k:household']/table/tbody/tr/td/button
        this. selenium.click("//span[contains(@id,'household')]/table/tbody/tr/td/button"); 
    }
    public void typeHouseholdHeadId(final String householdHeadId){
        this.selenium.type("//input[contains(@id,'oldHouseholdHeadPersonId')]", householdHeadId);
    }
    public void typeHouseholdId(final String householdId){
        this.selenium.type("//input[contains(@id,'oldHouseholdId')]", householdId);
    }
    public void setNewHousehold(final boolean isNewHousehold){
      //input[@id='j_id_2k:isNewHousehold:0']
        final String xpath = String.format("//input[contains(@id,'isNewHousehold:%s')]", isNewHousehold?0:1);
        this. selenium.click(xpath);
    }
            
    public void checkBirthKind(final BirthKind type){
//        http://192.168.10.18:6280/rl/faces/pages/func/rl00001/_rl01210/rl01210.xhtml?windowId=4e5
//        this.selenium.type("//input[contains(@id,'txnPersonId')]", getPersonId());
         //input[@id='j_id_2k:birthKind:4']
        final String xpath = String.format("//input[contains(@id,'birthKind:%s')]", type.value);
        this. selenium.click(xpath);
    }
    enum BirthKind{
        WEDLOCK(0),//婚生
        POSTHUMOUS(1),//遺腹子
        OUTOFWEDLOCK1(2),//非婚生（續辦認領）
        OUTOFWEDLOCK2(3),//非婚生（不續辦認領）
        INNOCENTI(4),//無依兒童
        ;
        private BirthKind(int value){
            this.value = value ;
        }
        private int value;
    }
}
