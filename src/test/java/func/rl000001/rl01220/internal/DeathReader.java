package func.rl000001.rl01220.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeathReader {
    private  final static Logger LOGGER = LoggerFactory.getLogger(DeathReader.class);
    public static  List<String[]> getPsedoData(){
	 final List<String[]> result =new ArrayList<String[]>();
	 final  InputStream ins = DeathReader.class.getResourceAsStream("need2Dead");
	 try {
	     for(String line : IOUtils.readLines(ins) ){
		 result.add(  StringUtils.splitPreserveAllTokens(line, ","));
	     }
	} catch (IOException e) {
	    LOGGER.info(e.getMessage(), e);
	}
	 return result ;
    }
}
