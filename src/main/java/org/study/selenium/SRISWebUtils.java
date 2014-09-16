/*
 * Copyright (c) 2010-2020 IISI.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of IISI.
 */
package org.study.selenium;

import com.thoughtworks.selenium.Selenium;

/**
 *
 */
public class SRISWebUtils {
    public static void typeAutoCompleteBySpanXpath(final Selenium selenium ,final String spanXpath ,final String value){
        /***
         * ex: //td[contains(@id,'currentPersonSiteIdTD')]/span/input
         * **/
        final String typeXpath = spanXpath + "/input";
        
        /***
         * ex: //td[contains(@id,'currentPersonSiteIdTD')]/span/span/img
         * **/
        final String closeXpath =  spanXpath +"/span/img";
        
        for (int i = 0; i < 2; ++i) {
            selenium.doubleClick(typeXpath);
            selenium.type(typeXpath,  value);
        }
        selenium.click(closeXpath);
    }
    public static void typeAutoComplete(final Selenium selenium ,final String xpath ,final String value){
        /***
         * ex: //td[contains(@id,'currentPersonSiteIdTD')]/span/input
         * **/
        final String typeXpath = xpath + "/span/input";
        
        /***
         * ex: //td[contains(@id,'currentPersonSiteIdTD')]/span/span/img
         * **/
        final String closeXpath =  xpath +"/span/span/img";
        
        for (int i = 0; i < 2; ++i) {
            selenium.doubleClick(typeXpath);
            selenium.type(typeXpath,  value);
        }
        selenium.click(closeXpath);
    }
    //================================================
    //== [Enumeration types] Block Start
    //== [Enumeration types] Block End 
    //================================================
    //== [static variables] Block Start
    //== [static variables] Block Stop 
    //================================================
    //== [instance variables] Block Start
    //== [instance variables] Block Stop 
    //================================================
    //== [static Constructor] Block Start
    //== [static Constructor] Block Stop 
    //================================================
    //== [Constructors] Block Start (含init method)
    //== [Constructors] Block Stop 
    //================================================
    //== [Static Method] Block Start
    //== [Static Method] Block Stop 
    //================================================
    //== [Accessor] Block Start
    //== [Accessor] Block Stop 
    //================================================
    //== [Overrided Method] Block Start (Ex. toString/equals+hashCode)
    //== [Overrided Method] Block Stop 
    //================================================
    //== [Method] Block Start
    //####################################################################
    //## [Method] sub-block : 
    //####################################################################    
    //== [Method] Block Stop 
    //================================================
    //== [Inner Class] Block Start
    //== [Inner Class] Block Stop 
    //================================================
}
