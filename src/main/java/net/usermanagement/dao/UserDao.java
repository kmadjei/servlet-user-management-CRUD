package net.usermanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import dao.Database;

// Provides CRUD database operations for the table users in the Database
public class UserDao {
	
	private static final String url =
			"jdbc:postgresql://" + dotenv.get("AWS_RDS_DB_URL") // AWS DB
			//"jdbc:postgresql://localhost/bankApp"; // local DB
			;
    private static final String user = dotenv.get("AWS_DB_USERNAME");
    private static final String password = dotenv.get("AWS_DB_PASSWORD");;
    
    private static Database instance = new Database(); //load single instance
    
    
    
    
    
    
    
    
    
    
    
    
   private static final String SELECT_USER_BY_ID = "SELECT "
    
    
    // private constructor allows for Database to have one instance
    private Database() {} 
    
    public static Database getInstance() {
    	return instance;
    }
    
    public static Connection conn;
    
    public Connection connect() throws SQLException{
    	 
    	conn = DriverManager.getConnection(url, user, password);
        return conn;
    }
    
}
