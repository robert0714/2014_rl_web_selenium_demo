/*
 * Copyright (c) 2010-2020 IISI.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of IISI.
 */
package generator.template;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 *
 */
public class TemplateGenerator {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
      System.out.println(   outPutStr("01210")) ; 
    }

    public static String outPutStr(final String code) throws IOException {
        final String template = IOUtils.toString(TemplateGenerator.class.getResourceAsStream("RlTemplatePage.template"));
        final int tagCount = StringUtils.countMatches(template, "%s");
//        System.out.println(tagCount);
        final String[] values = new String[tagCount];
        for (int i = 0; i < values.length; ++i) {
            values[i] = code;
        }
        return String.format(template, values);
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
