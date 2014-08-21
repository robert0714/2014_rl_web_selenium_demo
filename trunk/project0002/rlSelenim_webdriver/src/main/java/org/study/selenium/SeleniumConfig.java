package org.study.selenium;

public class SeleniumConfig {
    private static String seleniumServerHostName = "192.168.10.20";
    private static int seleniumServerPort = 4444;
    private static String applicationServerHostName = "192.168.10.18";
    private static int applicationServerPort = 6280;

    //You might have to use full path e.g on Fedora 12: 'firefox /usr/lib/firefox-3.5.4/firefox'
    //See http://seleniumhq.org/docs/05_selenium_rc.html#firefox-on-linux
    //for mac osx, use 'safari'
    private static String targetBrowser = "firefox";
    //For linux: private static String targetBrowser = "firefox /usr/lib/firefox-3.5.4/firefox";

    public static String waitForPageToLoad = "30000";

    public SeleniumConfig(String seleniumServerHostName, int seleniumServerPort, final String targetBrowser) {
        this.seleniumServerHostName = seleniumServerHostName;
        this.seleniumServerPort = seleniumServerPort;
        this.targetBrowser = targetBrowser;
    }
    public static String getSeleniumServerHostName() {
        return seleniumServerHostName;
    }

    public static int getSeleniumServerPort() {
        return seleniumServerPort;
    }

    public static String getTargetBrowser() {
        return targetBrowser;
    }

    public static String getWaitForPageToLoad() {
        return waitForPageToLoad;
    }

    public static void setWaitForPageToLoad(String waitForPageToLoad) {
        SeleniumConfig.waitForPageToLoad = waitForPageToLoad;
    }

}
