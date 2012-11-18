package index;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {
	private static DatabaseManager instance = null;
	private Connection conn = null;
	
	private DatabaseManager(){
    	try {

    		Class.forName("com.mysql.jdbc.Driver").newInstance();
    	    conn =
    	       DriverManager.getConnection("jdbc:mysql://localhost/movies?" +
    	                                   "user=admin&password=Wp9jW");
    	} catch (SQLException ex) {
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());
    	} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static DatabaseManager getInstance(){
		if (instance == null)
			instance = new DatabaseManager();

		return instance;
	}

	public ResultSet select(String query){
		java.sql.Statement stmt;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
		    rs = stmt.executeQuery(query);	
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return rs;
	}
	public ResultSet update(String query){
		return null;
	}
	public ResultSet delete(String query){
		return null;
	}
	public ResultSet insert(String query){
		return null;
	}
	
}
