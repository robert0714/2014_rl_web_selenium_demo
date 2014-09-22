package func.rl000001.rl01220.internal;

import com.iisi.dao.TableJDBCDao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class DeathReader.
 */
public class DeathReader {
    
    /** The Constant LOGGER. */
    private  final static Logger LOGGER = LoggerFactory.getLogger(DeathReader.class);
    
    /** The Constant sql. */
    private static final String sql ="select first 10  person_id, site_id from rldf004m where site_id = '65000120' and person_id like 'F%' and person_name ='無姓無名' and  personal_mark='0'";
    
    public static List<String[]> getJDBCData() {
        final Connection conn = TableJDBCDao.getInstance();
        final List<String[]> result = new ArrayList<String[]>();
        Statement stmt;
        try {
            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {

                // Retrieve by column name

                final String personId = rs.getString("person_id");
                final String siteId = rs.getString("site_id");

                // Display values
                result.add(new String[] { personId, siteId });
            }
            rs.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
        return result ; 
    }
    
    /**
     * Gets the psedo data.
     *
     * @return the psedo data
     */
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
