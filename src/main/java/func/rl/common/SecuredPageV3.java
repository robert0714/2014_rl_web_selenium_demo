//package func.rl.common;
//
//import org.apache.commons.lang3.StringUtils;
//import org.openqa.selenium.By;
//import org.openqa.selenium.NoSuchElementException;
//import org.openqa.selenium.UnhandledAlertException;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.PageFactory;
//import org.openqa.selenium.support.ui.LoadableComponent;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.study.selenium.AbstractSeleniumV2TestCase;
//
//import com.thoughtworks.selenium.SeleniumException;
//
//import func.rl000001.common.RL01210Test001V3;
//import static org.junit.Assert.fail;
//
//public class SecuredPageV3 extends LoadableComponent<SecuredPageV3> {
//  private  final Logger logger = LoggerFactory.getLogger(SecuredPageV3.class);
//  private final WebDriver driver;
//  private final LoadableComponent<?> parent;
//  private final String username;
//  private final String password;
//  private final String sitLoginPage = "/rl/pages/common/login.jsp"; 
//  private final String ssoLoginPage = "/nidp/idff/sso";
//  public SecuredPageV3(WebDriver driver, LoadableComponent<?> parent, String username, String password) {
//    this.driver = driver;
//    this.parent = parent;
//    this.username = username;
//    this.password = password;
//  }
//  public SITLoginPageV3 sit(final WebDriver driver ) {
//      return PageFactory.initElements(driver, SITLoginPageV3.class);
//  }
//  public SSOPageV3 uat(final WebDriver driver ) {
//      return PageFactory.initElements(driver, SSOPageV3.class);
//  }
//  public void login(final WebDriver driver , final String user, final String passwd) throws UnhandledAlertException, SeleniumException {
//     
//      final String indexPage ="/rl/faces/pages/index.xhtml";
//      final String partialPage ="/rl/faces/pages";
//     
//      AbstractSeleniumV2TestCase.open(indexPage);
//     
//      final String currentUrl = driver.getCurrentUrl();
//      logger.info("辨識基準頁面網址: {}" , currentUrl);
//      if (StringUtils.contains(currentUrl, partialPage)) {
//          AbstractSeleniumV2TestCase.open("/rl/");
//          //http://rlfl.ris.gov.tw/rl/
//      } else if (StringUtils.contains(currentUrl, sitLoginPage)) { 
//      	  final SITLoginPageV3 sit = sit(driver);
//          sit.login(driver, user, passwd);
//      } else { 
//      	final SSOPageV3 uat = uat(driver); 
//      	
//          uat.login(driver, user, passwd);
//      } 
//  }
//  @Override
//  protected void load() {
//	  this.parent.get();
//
//    String originalUrl = driver.getCurrentUrl();
//
//    // Sign in
//    login(this.driver, this.username,  this.password);
//
//    // Now return to the original URL
//    driver.get(originalUrl);
//  }
//
//  @Override
//  protected void isLoaded() throws Error {
//    // If you're signed in, you have the option of picking a different login.
//    // Let's check for the presence of that.
//	  final String currentUrl = driver.getCurrentUrl();
//		boolean  jugement =  currentUrl.contains(sitLoginPage)|| currentUrl.contains(ssoLoginPage)  ; 
//	  if(!jugement){
//		  logger.info("curent url: {}" , currentUrl);
//		  
//		  throw new Error(String.format("The wrong page has loaded: ", this.driver.getCurrentUrl()));
//	  }
////    try {
////      WebElement div = driver.findElement(By.id("multilogin-dropdown"));
////    } catch (NoSuchElementException e) {
////      fail("Cannot locate user name link");
////    }
//  }
//}
