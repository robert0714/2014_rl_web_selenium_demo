package com.iisi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class TableJDBCDao {
    public List<String[]> getPersonIdSiteIdList(){
	final List<String[]> result = new ArrayList<String[]>();
	final Connection conn=getInstance ();
	Statement stmt;
	try {
	    stmt = conn.createStatement();
	    String sql = "SELECT first 7 person_id,site_id FROM rldf004m where personal_mark='0'";
	    ResultSet rs = stmt.executeQuery(sql);
	    while (rs.next()) {
		// Retrieve by column name
		
		final String personId = rs.getString("person_id");
		final String siteId = rs.getString("site_id");

		// Display values
//		System.out.print("person_id: " + personId);
//		System.out.println("site_id: " + siteId);
		result.add(new String[]{personId,siteId});
	    }
	    rs.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	}finally{
	    if(conn!=null){
		try {
		    conn.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	    }
	}
	return result;
    }
    public static Connection getInstance (){
//	String url ="jdbc:informix-sqli://192.168.10.18:4526/twu10070:informixserver=aix2;DB_LOCALE=zh_tw.utf8;CLIENT_LOCALE=zh_tw.utf8;GL_USEGLU=1";
	String url ="jdbc:informix-sqli://192.168.10.18:4526/teun0020:informixserver=aix2;DB_LOCALE=zh_tw.utf8;CLIENT_LOCALE=zh_tw.utf8;GL_USEGLU=1";
	String username = "srismapp";
	String password ="ris31123";
	String driver = "com.informix.jdbc.IfxDriver";	
	Connection conn=null;
	try {
	    Class.forName(driver);
	    conn = DriverManager.getConnection(url, username, password);
	} catch (SQLException e) {
	    e.printStackTrace();
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	}
	return conn;
    }
}
