package func.rl00001._rl02510;
  

import func.rl.common.WebUtils;
import func.rl.common.internal.GrowlMsg;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.study.selenium.SRISWebUtils;
 
// TODO: Auto-generated Javadoc
/**
 * The Class Rl02510PageV3
 *  戶口名簿請補換發.
 */
public class Rl02510PageV3 extends LoadableComponent<Rl02510PageV3>{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(Rl02510PageV3.class);

    /** The driver. */
    private WebDriver driver;
 
    /** The o action. */
    private final Actions oAction ;
    
    /** The wait. */
    private  WebDriverWait wait  ;
    
    /** The rl01210 partial ulr. */
    private final String partialURL = "_rl01210/rl01210.xhtml";

    /** The parent. */
    private final LoadableComponent<?> parent;
    
    /** * 戶籍記事/罰鍰清單 頁籤. */
    @FindBy(how = How.XPATH, using = "//a[contains(text(),'戶籍記事/罰鍰清單')]")
    private WebElement tabNotes;
    
    /** * 全戶基本資料 頁籤. */
    @FindBy(how = How.XPATH, using = "//a[contains(text(),'全戶基本資料')]")
    private  WebElement tabBasicHouseholdData;
    
    /** * 資料驗證按鈕. */
    @FindBy(how = How.XPATH, using = "//td/button")
    private  WebElement verifyBtn;
    
    /** * 暫存按鈕. */
    @FindBy(how = How.XPATH, using = "//span[contains(@id,'button')]/button[3]")
    private   WebElement tempSaveBtn;
    
    /** * 關閉視窗按鈕. */
    @FindBy(how = How.XPATH, using = "//span[contains(@id,'button')]/button[4]")
    private   WebElement closeBtn;
    
    /** * 初領. */
    @FindBy(how = How.XPATH, using = "//input[@id='tabViewId:applyCodeId:0']")
    private   WebElement selectAppyCodeId0  ;
    
    /** * 補領. */
    @FindBy(how = How.XPATH, using = "//input[@id='tabViewId:applyCodeId:1']")
    private   WebElement selectAppyCodeId1  ;
    
    /** * 換領. */
    @FindBy(how = How.XPATH, using = "//input[@id='tabViewId:applyCodeId:2']")
    private   WebElement selectAppyCodeId2  ;
    
    /** * 列印全戶動態記事(是). */
    @FindBy(how = How.XPATH, using = "//td[2]/table/tbody/tr/td/input")
    private   WebElement printDynamicNotesYes  ;
    
    /** * 列印全戶動態記事(否). */
    @FindBy(how = How.XPATH, using = "//td[2]/table/tbody/tr/td[2]/input")
    private   WebElement printDynamicNotesNo  ;
    
    /** * 列印父、母、配偶統號(是). */
    @FindBy(how = How.XPATH, using = "//td[3]/table/tbody/tr/td/input")
    private   WebElement printRelationIdYes  ;
    
    /** * 列印父、母、配偶統號(否). */
    @FindBy(how = How.XPATH, using = "//td[3]/table/tbody/tr/td[2]/input")
    private   WebElement printRelationIdNo  ;
    
    
    /** * 戶口名簿封面編號. */
    @FindBy(how = How.XPATH, using = "//table[2]/tbody/tr/td/input")
    private   WebElement rl02510IdNo  ;
    
    /** * 填寫備註. */
    @FindBy(how = How.XPATH, using = "//textarea[contains(@id,'registerContent')]")
    private   WebElement registerContent  ;
    
    /** * 列印戶口名簿. */
    @FindBy(how = How.XPATH, using = "//td[2]/button")
    private   WebElement printCertificate  ;
    
    
    /**
     * Instantiates a new rl01210 page.
     *
     * @param driver the driver
     */
    public Rl02510PageV3(final WebDriver driver )   {
        super();
        this.driver = driver;
        this.parent = null;
        this.wait = new WebDriverWait(driver, 60);
        this.oAction = new Actions(this.driver);
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Instantiates a new rl01210 page.
     *
     * @param driver the driver
     * @param parent the parent
     */
    public Rl02510PageV3(final WebDriver driver ,final LoadableComponent<?> parent)   {
        super();
        this.driver = driver;
        this.parent = parent;
        this.wait = new WebDriverWait(driver, 60);
        this.oAction = new Actions(this.driver);
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Load.
     */
    @Override
    protected void load() {
        final String mainUrl = WebUtils .getMainUrl( this.driver.getCurrentUrl());
        //http://192.168.10.18:6280/rl/faces/pages/func/rl00001/_rl01240/rl01240.xhtml?windowId=846
        final String url = String.format("%s/rl/faces/pages/func/rl00001/%s", mainUrl,partialURL);
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
            throw new Error(String.format("The wrong page has loaded: ", this.driver.getCurrentUrl()));
        } 
    }
    
    /**
     * Switch tab.
     * 進行頁籤轉換
     */
    public void switchTab()  {
        final String currentUrl = this.driver.getCurrentUrl();
        if (StringUtils.contains(currentUrl, this.partialURL)) {
            this.driver.navigate().refresh();
            
            WebUtils.pageLoadTimeout(this.driver);
            this.tabNotes.click();
           
            WebUtils.pageLoadTimeout(this.driver);
            this.tabBasicHouseholdData.click();

        }
    } 
    
    /**
     * Prints the dynamic notes.
     * 列印全戶動態記事
     * @param yes the yes
     */
    public void printDynamicNotes(boolean yes){
        if(yes){
            this.printDynamicNotesYes.click();
        }else{
            this.printDynamicNotesNo.click();
        }
    } 
    /**
     * Prints the relation ids.
     * 列印父、母、配偶統號
     * @param yes the yes
     */
    public void printRelationId(boolean yes){
        if(yes){
            this.printRelationIdYes.click();
        }else{
            this.printRelationIdNo.click();
        }
    } 
    
    /**
     * Type register content.
     * 填寫備註
     * @param content the content
     */
    public void typeRegisterContent(final String content){
        this.registerContent.clear();
        this.registerContent.sendKeys(content);
    }
    
    
    /**
     * Type 戶口名簿封面編號
     * 填寫戶口名簿封面編號.
     *
     * @param id the id
     */
    public void typeRl02510IdNo(final String id){
        this.rl02510IdNo.clear();
        this.rl02510IdNo.sendKeys(id);
    }
    
    
    /**
     * Click verify btn.
     * 驗證查詢
     * @return the growl msg
     */
    public GrowlMsg clickVerifyBtn(){
        oAction.moveToElement(this.verifyBtn);
        final GrowlMsg verification = WebUtils.clickBtn(this.driver, this.verifyBtn);
        return verification;
    }
    /**
     * Click verify btn.
     * 列印戶口名簿
     * @return the growl msg
     */
    public void clickPrintCertificate(){
        oAction.moveToElement(this.printCertificate);
//        final GrowlMsg verification = WebUtils.clickBtn(this.driver, this.printCertificate);
        SRISWebUtils.newPdfPreview(this.driver, this.printCertificate);
//        return verification;
    }
    
    /**
     * Gets the partial url.
     *
     * @return the partial url
     */
    public String getPartialURL() {
        return this.partialURL;
    }
    /**
     * 請領種類.
     * 
     * @param applyCode the apply code
     */
    public void apply(final ApplyItem applyCode){
        switch (applyCode) {
            case FIRST:
                this.selectAppyCodeId0.click();
                break;
            case REAPPLY:
                this.selectAppyCodeId1.click();
                break;
            case RENEWAL:
                this.selectAppyCodeId2.click();
                break;
        }
    }
    /**
     * The Enum ApplyItem.
     * 請領種類,初領 (FIRST), 補領(REAPPLY), 換領(RENEWAL)
     */
    public enum ApplyItem{
        
        /**  初領. */
        FIRST(0),
        
        /**  補領. */
        REAPPLY(1),        
        
        /**  換領. */
        RENEWAL(2),
        /**
        * Instantiates a new birth kind.
        *
        * @param value the value
        */
        ;
        private ApplyItem(int value) {
            this.value = value;
        }

        /** The value. */
        private int value;
    }

   
}
