package generator;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 測試程式碼產生器
 * **/
public class TestCodeGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestCodeGenerator.class);
    public static void main(String[] args)  {
	List<Operation> data = new TestCodeGenerator().retrieveData();
	System.out.println(data.size());
    }
    protected List<Operation> retrieveData(){	
	final List<String> initializedData = new ArrayList<String>();
	// <property name="operationCode" value="01110" />
	// <property name="operationCategory" value="更正變更登記" />
	// <property name="operationName" value="個人記事更正登記" />
	// <property name="operationPageName" value="_rl01110/rl01110.xhtml" />
	final String operationCodeReg = ".*<property name=\"operationCode\"\\s*value=\"(.*)\"\\s*\\/>.*";
	final String operationCategoryReg = ".*<property name=\"operationCategory\"\\s*value=\"(.*)\"\\s*\\/>.*";
	final String operationNameReg = ".*<property name=\"operationName\"\\s*value=\"(.*)\"\\s*\\/>.*";
	final String operationPageNameReg = ".*<property name=\"operationPageName\"\\s*value=\"(.*)\"\\s*\\/>.*";
	
	final Pattern patternOPC = Pattern.compile(operationCodeReg);
	final Pattern patternOPCR = Pattern.compile(operationCategoryReg);
	final Pattern patternON = Pattern.compile(operationNameReg);
	final Pattern patternOPN = Pattern.compile(operationPageNameReg);
	final InputStream is = TestCodeGenerator.class.getResourceAsStream("operationContext.xml");
	
	try {
	    final List<String> lines = IOUtils.readLines(is);
	    
	    for (int i = 0; i < lines.size(); ++i) {
	        final String line = lines.get(i);
	        final String patternOPCName =  matchData(patternOPC, line);
	        final String patternOPCRName =  matchData(patternOPCR, line);
	        final String patternONName =  matchData(patternON, line);
	        final String patternOPNName =  matchData(patternOPN, line);
	        if(StringUtils.isNotBlank(patternOPCName)){
	    	 initializedData.add(patternOPCName);
	        }
	        if(StringUtils.isNotBlank(patternOPCRName)){
	    	 initializedData.add(patternOPCRName);
	        }
	        if(StringUtils.isNotBlank(patternONName)){
	    	 initializedData.add(patternONName);
	        }
	        if(StringUtils.isNotBlank(patternOPNName)){
	    	 initializedData.add(patternOPNName);
	        }
	    }
	} catch (IOException e) {
	    LOGGER.error(e.getMessage(), e);
	    
	}
	final List<Operation> result =new ArrayList<TestCodeGenerator.Operation>();
	Operation unit =null;
	for(int i = 0; i < initializedData.size(); ++i){
	    final String line = initializedData.get(i);
	    switch (i % 4) {
	    case 0:
		
		unit =new Operation();
		unit.setOperationCode(line);
		break;
	    case 1:
		unit.setOperationCategory(line);
		break;
	    case 2:
		unit.setOperationName(line);
		break;
	    case 3:
		unit.setOperationPageName(line);
		if(unit!=null){
		    result.add(unit);
		}
		break;
	    }
	}
	return result;
    }
    private static String matchData(final Pattern pattern ,final String line){
	final Matcher matcher = pattern.matcher(line);
	if (matcher.find()) {
	    final String found = matcher.group(matcher.groupCount());
	    if (StringUtils.isNotBlank(found)) {
		return found;
	    }
	}
	return null;
    }
    static class OutputFile{
	private String packageName;
	private String fileName;
	private String fileContent;
	public String getPackageName() {
	    return packageName;
	}
	public void setPackageName(String packageName) {
	    this.packageName = packageName;
	}
	public String getFileName() {
	    return fileName;
	}
	public void setFileName(String fileName) {
	    this.fileName = fileName;
	}
	public String getFileContent() {
	    return fileContent;
	}
	public void setFileContent(String fileContent) {
	    this.fileContent = fileContent;
	}
	
    }
    static class Operation{
	private String operationCode;
	private String operationCategory;
	private String operationName;
	private String operationPageName;
	public String getOperationCode() {
	    return operationCode;
	}
	public void setOperationCode(String operationCode) {
	    this.operationCode = operationCode;
	}
	public String getOperationCategory() {
	    return operationCategory;
	}
	public void setOperationCategory(String operationCategory) {
	    this.operationCategory = operationCategory;
	}
	public String getOperationName() {
	    return operationName;
	}
	public void setOperationName(String operationName) {
	    this.operationName = operationName;
	}
	public String getOperationPageName() {
	    return operationPageName;
	}
	public void setOperationPageName(String operationPageName) {
	    this.operationPageName = operationPageName;
	}
	
    }
}
