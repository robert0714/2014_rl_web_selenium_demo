package func.rl01z00;

import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;


public class Rl01Z00Page {
	private static final Logger logger = LoggerFactory.getLogger(Rl01Z00Page.class);
    private WebDriver driver;
    private Selenium selenium;

    private final String rl01z00PartialUlr = "rl01z00/rl01z00.xhtml";

    public Rl01Z00Page(final Selenium selenium, final WebDriver driver) throws UnhandledAlertException,
            SeleniumException {
        super();
        this.selenium = selenium;
        this.driver = driver;
    }

   

}
