package func.rl031000.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils; 
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

import func.rl.common.RlHompage;
import func.rl.common.WebUtils;
import func.rl03100.Rl03100Page;

public class BatchApp01 {
	private static final Logger logger = LoggerFactory.getLogger(BatchApp01.class);
   
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
	// DesiredCapabilities capabilities = new DesiredCapabilities();
	// capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

	// final WebDriver driver = new FirefoxDriver(capabilities);
	// final WebDriver driver = WebUtils.windowsMachine();
	final WebDriver driver = new FirefoxDriver();

	final Dimension targetSize = new Dimension(1500, 860);
	driver.manage().window().setSize(targetSize);
	WebDriverBackedSelenium selenium = new WebDriverBackedSelenium(driver, config.baseUrl);
	selenium.open(config.baseUrl);
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy_mm_dd_MM_ss");
	try {
	    final List<String[]> personIdSiteIdList = getPerosnIdSiteId(config);
	    final RlHompage homepage = new RlHompage(selenium, driver);
	    homepage.login(config.userName, config.userPasswd);

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
		    WebUtils.takeScreen(driver, new File(config.picFolderPath + File.separator + sdf.format(new Date()) + ".png"));

		    WebUtils.handleRLAlert(selenium);
		    WebUtils.takeScreen(driver, new File(config.picFolderPath + File.separator + sdf.format(new Date()) + ".png"));
		     
		}
	    } 
	} catch (Exception e) {
	    e.printStackTrace();

	    WebUtils.takeScreen(driver, new File(config.picFolderPath + File.separator + sdf.format(new Date()) + ".png"));

	} finally {
	    
	    if (selenium != null) {
		selenium.stop();
	    }
	}
    }

    public List<String[]> getPerosnIdSiteId(final Config config) {
	final List<String[]> personIdSiteIdList = new ArrayList<String[]>();
	logger.info("txnPersonFilePath: " + config.txnPersonFilePath);
	File srcFile = new File(config.txnPersonFilePath);
	if (srcFile.exists()) {
	    try {
		List<String> tmpList = FileUtils.readLines(srcFile);
		for (String line : tmpList) {
		    personIdSiteIdList.add(StringUtils.splitPreserveAllTokens(line, ","));
		}
	    } catch (IOException e) {
		e.printStackTrace();
		logger.error(e.getMessage(), e);
	    }

	} else {
	    personIdSiteIdList.add(new String[] { "C100201902", "65000120" });
	}
	if (StringUtils.isNotEmpty(config.txnPersonFolderPath)) {
		
	    File srcFolderFile = new File(config.txnPersonFolderPath);
	    logger.info(srcFolderFile.getAbsolutePath());
	    
	    /******D:/userDatas/robert/Desktop/rl03100/txnPersonFolder*/
	    if (srcFolderFile !=null ) {
		final File[] listFiles = srcFolderFile.listFiles();
		if (ArrayUtils.isNotEmpty(listFiles)) {
		    for (File data : listFiles) {

			try {
			    List<String> tmpList = FileUtils.readLines(data);
			    for (String line : tmpList) {
				personIdSiteIdList.add(StringUtils.splitPreserveAllTokens(line, ","));
			    }
			} catch (IOException e) {
			    e.printStackTrace();
			    logger.error(e.getMessage(), e);
			}

		    }
		}
	    }
	}

	return personIdSiteIdList;
    }
     
    public List<Config> getBatchContig() {
	final List<Config> result = new ArrayList<Config>();
	final File dir = new File("conf");
	logger.info("path: "+dir.getAbsolutePath());
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
			newConfig.txnPersonFolderPath = properties.getProperty("txnPersonFolderPath");
			logger.info("userName: " + newConfig.userName);
			logger.info("userPasswd: " + newConfig.userPasswd);
			logger.info("baseUrl: " + newConfig.baseUrl);
			logger.info("picFolderPath: " + newConfig.picFolderPath);
			if(StringUtils.isNotBlank( newConfig.picFolderPath)){
				final File picdir = new File(newConfig.picFolderPath);
				if(!picdir.isDirectory() && !picdir.exists() ){
					picdir.mkdir();
				}
			}
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
	protected String txnPersonFolderPath;
	
    }
}
