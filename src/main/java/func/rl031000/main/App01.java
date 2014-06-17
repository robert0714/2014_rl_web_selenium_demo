package func.rl031000.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

import func.rl.common.Rl03100Page;
import func.rl.common.RlHompage;
import func.rl.common.WebUtils;

public class App01 {
    protected static Logger logger = Logger.getLogger(App01.class); 
    protected WebDriverBackedSelenium selenium;
    protected String userName;
    protected String userPasswd;
    protected String baseUrl;
    protected String picFolderPath;
    protected String txnPersonFilePath;
    /**
     * @param args
     * @throws MalformedURLException 
     */
    
    public static void main(String[] args)  {
	App01 app =new App01();
	try {
	    app.startup();
	} catch (Exception e) {
	    e.printStackTrace();
	    logger.error(e.getMessage(),e);
	} finally{
	    if(app.selenium!= null   ){
		app.selenium.stop();
	    }
	}
    }
    public void startup()throws MalformedURLException, FileNotFoundException{
	getContig();
	DesiredCapabilities capabilities = new DesiredCapabilities();
	capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	final WebDriver driver = new FirefoxDriver(capabilities);
//	final WebDriver driver = WebUtils.windowsMachine();
	
	final Dimension targetSize = new Dimension(1500,860);
	driver.manage().window().setSize(targetSize);
	selenium = new WebDriverBackedSelenium(driver, baseUrl);
	selenium.open(baseUrl);
	final List<String[]> personIdSiteIdList = getPerosnIdSiteId();
	final RlHompage homepage = new RlHompage(selenium,driver);
	homepage.login(userName, userPasswd);
	
	for(String[] stringArray :personIdSiteIdList){
	    selenium.waitForPageToLoad("30000");
		homepage.enterRl03100();
		selenium.waitForPageToLoad("30000");
		final String personId = stringArray[0];
		if (StringUtils.contains(personId, "*")) {
		    continue;
		}
		final String siteId = stringArray[1];

		
		try {
		    Rl03100Page rl00004Page =new Rl03100Page(selenium,driver);
		    rl00004Page.typeApplication(personId, siteId,picFolderPath);
		} catch (Exception e) {
		    e.printStackTrace();
		    WebUtils.handleRLAlert(selenium);
		}
	}
    }
    public  List<String[]> getPerosnIdSiteId(){
	final List<String[]> personIdSiteIdList =new ArrayList<String[]>();
	logger.info("txnPersonFilePath: "+txnPersonFilePath);
	File srcFile = new File(txnPersonFilePath);
	if(srcFile.exists()){
	    try {
		List<String >  tmpList = FileUtils.readLines(srcFile);
		for(String line : tmpList){
		    personIdSiteIdList.add(StringUtils.splitPreserveAllTokens(line,","));
		}
	    } catch (IOException e) {
		e.printStackTrace();
		 logger.error(e.getMessage(),e);
	    }
	    
	}else{
	    personIdSiteIdList.add(new String[]{"C100201902","65000120"});
	}
	
	
	return personIdSiteIdList;
    }
    public  void getContig() throws FileNotFoundException{	
	FileInputStream in = new FileInputStream("conf/config.properties");
	Properties properties = new Properties();
	try {
	    properties.load(in);
	} catch (IOException e) {
	    e.printStackTrace();
	    logger.error(e.getMessage(),e);
	}
	 userName = properties.getProperty("userName");
	 userPasswd = properties.getProperty("userPasswd");
	 baseUrl = properties.getProperty("baseUrl");
	 picFolderPath = properties.getProperty("picFolderPath");
	 txnPersonFilePath = properties.getProperty("txnPersonFilePath");
	 logger.info("userName: "+userName);
	 logger.info("userPasswd: "+userPasswd);
	 logger.info("baseUrl: "+baseUrl);
	 logger.info("picFolderPath: "+picFolderPath);
	 logger.info("txnPersonFilePath: "+txnPersonFilePath);
    }
     
}
