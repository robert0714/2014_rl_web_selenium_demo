package func.rl.common;

import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.study.selenium.AbstractSeleniumV2TestCase;

import com.thoughtworks.selenium.SeleniumException;

public class SITLoginPageV3  extends LoadableComponent<SITLoginPageV3>{ 
    private WebDriver driver;
    private String loadPage; 
    private final String patialUrl ="/rl/faces/pages/index.xhtml";
    private  final Logger logger = LoggerFactory.getLogger(getClass());

    public SITLoginPageV3(final WebDriver driver) throws UnhandledAlertException, SeleniumException {
        super();
        this.driver = driver; 
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.NAME, using = "j_username")
    WebElement usernameInput;
    
    @FindBy(how = How.NAME, using = "j_password")
    WebElement passwordInput;
    
    @FindBy(how = How.XPATH, using = "//input[@value='登入']")
    WebElement logonSubmit;
     
    public void login(final WebDriver driver , final String user, final String passwd) {
        usernameInput.sendKeys(user);
        passwordInput.sendKeys(passwd);
        logonSubmit.click();
    }
    @Override
    protected void load() {
        final String sitLoginPage = "/rl/pages/common/login.jsp";
        //https://idpfl.ris.gov.tw:8443/nidp/idff/sso?id=1&sid=1&option=credential&sid=1
        loadPage = AbstractSeleniumV2TestCase.open(sitLoginPage);
//    this.driver.get("https://idpfl.ris.gov.tw:8443/nidp/idff/sso?id=1&sid=1&option=credential&sid=1");
    }

    @Override
    protected void isLoaded() throws Error {
        if(!this.driver.getCurrentUrl().equals(this.loadPage)){
            throw new Error(String.format("The wrong page has loaded: ", this.driver.getCurrentUrl()));
        } 
    }
}
