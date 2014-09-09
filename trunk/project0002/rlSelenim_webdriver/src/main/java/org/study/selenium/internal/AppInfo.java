/*
 * Copyright (c) 2010-2020 IISI.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of IISI.
 */
package org.study.selenium.internal;

/**
 *
 */
public enum AppInfo {
    DEV("192.168.10.18","rl",6280,"192"),
    DEVin140("140.92.86.173","rl",6280,"140"),//如果DEVin140在UAT之前....則140的機器都是會跑SIT
    UAT("rlfl.ris.gov.tw","rl",80,"140");
    
     
    
    private AppInfo(String applicationServerHostName, String appPath, int applicationServerPort, String prefixPremoteIp) {
        this.applicationServerHostName = applicationServerHostName;
        this.appPath = appPath;
        this.applicationServerPort = applicationServerPort;
        this.prefixPremoteIp = prefixPremoteIp;
    }
    private String applicationServerHostName;
    private String appPath;
    private int applicationServerPort;
    private String prefixPremoteIp;
    
    public String getAppUrl() {
//        return super.toString();
        String result =  "http://" + applicationServerHostName + ":"
                + applicationServerPort + "/"+appPath+"/";
        return result ; 
    }
    public String getPrefixPremoteIp() {
        return this.prefixPremoteIp;
    }
    
    
}
