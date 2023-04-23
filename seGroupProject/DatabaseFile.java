package seGroupProject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.*;


public class DatabaseFile {
	  private Connection conn;
	  private DatabaseFile database;
	  public DatabaseFile()
	  {
		  Properties prop = new Properties();
		  FileInputStream fis;
		  String url = "";
		  String user = "";
		  String password = "";
		try {
			fis = new FileInputStream("seGroupProject/db.properties");
			prop.load(fis);
			url = prop.getProperty("url");
			user = prop.getProperty("user");
			password = prop.getProperty("password");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
	    try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	  }
		
		// function to be called when username is to be checked for repeated usernames
		public boolean verifyCreds (String username, String password, ArrayList<String> list) {
			boolean choice = false;
			String usepass = username + "," + password + ",";
			for (int i = 1; i < list.size(); i+=2) {
				if(list.get(i).equals(usepass)){
					choice = true;
				}
			}
			
			return choice;
		}
		
		// function to be called when user is creating a new account
		public boolean addAccount (String username, String password, ArrayList<String> list) {
			boolean choice = true;
			String newUser = username + ",";
			for (int i = 0; i < list.size(); i++) {
				if (newUser.equals(list.get(i))) {
					choice = false;
					return choice;
				}
			}
			
			return choice;
		}
	  
	  public ArrayList<String> query(String query)
	  {
	    int i = 0;
	    int rowCount = 0; // Detect empty result set
	    ArrayList<String> array = new ArrayList<String>();
	    
	    // Create a statement from the connection
	    Statement stmt;
	    try {
	    stmt= conn.createStatement();
	    
	    // Run the query
	    ResultSet rs = stmt.executeQuery(query);
	    
	    // get meta data
	    ResultSetMetaData rmd = rs.getMetaData();
	    
	    int noColumns = rmd.getColumnCount();
	    
	    while (rs.next()) {
	    	rowCount++;
	    	String record = "";
	    	for (i = 0; i < noColumns; i++) {
	    		String value = rs.getString(i+1);
	    		record += value + ",";
	    		array.add(record);
	    	}
	    }
	    
	    if (rowCount > 0)
	    	return array;
	    else
	    	return null;
	    }
	    catch (SQLException e){
	    	e.printStackTrace();
	    	
	    	return null;
	    }
	  }
	  
	  public void executeDML(String dml) throws SQLException
	  {
	    Statement stmt = conn.createStatement();
	    stmt.execute(dml);
	  }
	}