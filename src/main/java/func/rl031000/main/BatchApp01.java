package func.rl031000.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;



import func.rl.common.Rl03100Page;
import func.rl.common.RlHompage;
import func.rl.common.WebUtils;

public class BatchApp01 {
    protected static Logger logger = Logger.getLogger(BatchApp01.class); 

    /**
     * @param args
     * @throws MalformedURLException 
     */
    
    public static void main(String[] args) {
	BatchApp01 app = new BatchApp01();

	final List<Config> configs = app.getBatchContig();
	for (Config config : configs) {
	    app.startup(config);
	}
    }
    public void startup(final Config config) { 
	DesiredCapabilities capabilities = new DesiredCapabilities();
	capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);	
	
	final WebDriver driver = new FirefoxDriver(capabilities);
//	final WebDriver driver = WebUtils.windowsMachine();
	
	final Dimension targetSize = new Dimension(1500,860);
	driver.manage().window().setSize(targetSize);
	WebDriverBackedSelenium  selenium = new WebDriverBackedSelenium(driver, config.baseUrl);
	
	try {
	    final List<String[]> personIdSiteIdList = getPerosnIdSiteId(config);
	    final RlHompage homepage = new RlHompage(selenium, driver);
	    for (String[] stringArray : personIdSiteIdList) {
		selenium.waitForPageToLoad("30000");
		homepage.enterRl03100();
		selenium.waitForPageToLoad("30000");
		final String personId = stringArray[0];
		if (StringUtils.contains(personId, "*")) {
		    continue;
		}
		final String siteId = stringArray[1];

		try {
		    Rl03100Page rl00004Page = new Rl03100Page(selenium, driver);
		    rl00004Page.typeApplication(personId, siteId, config.picFolderPath);
		} catch (Exception e) {
		    e.printStackTrace();
		    WebUtils.handleRLAlert(selenium);
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
		} finally{
	    if(selenium!= null   ){
		selenium.stop();
	    }
	}
    }
    public  List<String[]> getPerosnIdSiteId(final Config config){
	final List<String[]> personIdSiteIdList =new ArrayList<String[]>();
	logger.info("txnPersonFilePath: "+config.txnPersonFilePath);
	File srcFile = new File(config.txnPersonFilePath);
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
     
    public List<Config> getBatchContig() {
	final List<Config> result = new ArrayList<Config>();
	final File dir = new File("conf");
	final File[] listFiles = dir.listFiles();
	if (ArrayUtils.isNotEmpty(listFiles)) {
	    for (File file : listFiles) {
		if (file.isFile()) {
		    FileInputStream in = null;
		    try {
			in = new FileInputStream(file);
			Properties properties = new Properties();

			properties.load(in);
			Config newConfig = new Config();
			newConfig.userName = properties.getProperty("userName");
			newConfig.userPasswd = properties.getProperty("userPasswd");
			newConfig.baseUrl = properties.getProperty("baseUrl");
			newConfig.picFolderPath = properties.getProperty("picFolderPath");
			newConfig.txnPersonFilePath = properties.getProperty("txnPersonFilePath");
			logger.info("userName: " + newConfig.userName);
			logger.info("userPasswd: " + newConfig.userPasswd);
			logger.info("baseUrl: " + newConfig.baseUrl);
			logger.info("picFolderPath: " + newConfig.picFolderPath);
			logger.info("txnPersonFilePath: " + newConfig.txnPersonFilePath);
			result.add(newConfig);
		    } catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		    } finally {
			try {
			    if (in != null) {
				in.close();
			    }
			} catch (IOException e) {
			    e.printStackTrace();
			    logger.error(e.getMessage(), e);
			}
		    }
		}
	    }
	}else{
	    logger.info("no configuration fle in "+dir.getAbsoluteFile());
	}

	return result;
    }

    protected class Config {
	protected String userName;
	protected String userPasswd;
	protected String baseUrl;
	protected String picFolderPath;
	protected String txnPersonFilePath;
    }
}
