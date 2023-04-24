package seGroupProject;
import java.io.*;
import java.sql.*;
import java.util.*;

public class DatabaseFile
{
  private Connection conn;
  //private ArrayList<String> q;
  //Add any other data fields you like – at least a Connection object is mandatory
  public void setConnection(String fn) throws IOException 
  {
    //Add your code here
	  Properties prop = new Properties();
	  FileInputStream fis = new FileInputStream(fn);
	  prop.load(fis);
	  String url = prop.getProperty("url");
	  String user = prop.getProperty("user");
	  String pass = prop.getProperty("password");   
	  
	  try {
		conn = DriverManager.getConnection(url,user,pass);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
  }

  public Connection getConnection()
  {
    return conn;
  }

  public ArrayList<String> query(String query) 
  {
    //Add your code here
	  ArrayList<String> q;
	  q = new ArrayList<String>();
	  Statement stmt;
	  ResultSet rs;
	  ResultSetMetaData rmd;
	  
	  try {
		stmt = conn.createStatement();
		rs = stmt.executeQuery(query);
		rmd = rs.getMetaData();
		
		int no_columns = rmd.getColumnCount();
		
		String name = rmd.getColumnName(1);
		
		while(rs.next())
		{
			q.add(rs.getString(1));
		}
		
	//	conn.close();
		
		System.out.println("Success");
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  return q;
  }
  
  public void executeDML(String dml) throws SQLException
  {
    //Add your code here
	  Statement stmt;
	  
	  stmt = conn.createStatement();
	  stmt.execute(dml);
  }
//Method for verifying a username and password.
  public boolean verifyAccount(String username, String password)
  {
	 boolean result = false;
	 ArrayList<String> q;
	  q = new ArrayList<String>();
	 
   q = query("select username, password,aes_decrypt(password,'key') from user;");
		   
	  for(String element: q) {
		  if (!element.contains(username)) {
			  result = false;
	}
	
	
	  
	  // Check the username and password.
	  
	  for(String e: q) {
		  
		  if (e.contains(username) && e.contains(password))
			  result = true;
		  else
			  result = false;
	  }
   }
	  return result;
  }
 
  public boolean createNewAccount(String username, String password)
  {
    boolean result = false;
    ArrayList<String> q;
	  q = new ArrayList<String>();
    
    q = query("select username from user");
    	
    for(String element : q) {
	if (element.contains(username)) {
	  result = false;
	}
    
    // Add the new account.
	else {
    try {
		executeDML("insert into user values( '" + username + "' ,aes_encrypt" + "('" + password + "'" + ",'key')");
		result = true;
    	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    }
	
    }
    return result;
    
  }
 
  }