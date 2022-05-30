package net.usermanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import net.usermanagement.model.User;

// Provides CRUD database operations for the table users in the Database
public class UserDao {
	
	private static final String url =
			//"jdbc:postgresql://" + dotenv.get("AWS_RDS_DB_URL") // AWS DB
			"jdbc:postgresql://localhost:5432/bankApp"; // local DB
			;
    private static final String user = "postgres";
    private static final String password = "password";
    
	private static final String INSERT_USERS_SQL = "INSERT INTO users" + "  (name, email, country) VALUES "
			+ " (?, ?, ?);";
	private static final String SELECT_USER_BY_ID = "select id,name,email,country from users where id =?";
	private static final String SELECT_ALL_USERS = "select * from users";
	private static final String DELETE_USERS_SQL = "delete from users where id = ?;";
	private static final String UPDATE_USERS_SQL = "update users set name = ?,email= ?, country =? where id = ?;";
    
    protected Connection getConnection() {
    	Connection connection = null;
    	
    	try {
    		Class.forName("org.postgresql.Driver");
    		connection = DriverManager.getConnection(url, user, password);
    	} catch (SQLException e) {
    		e.printStackTrace();
    	} catch (ClassNotFoundException e) {
    		e.printStackTrace();
    	}
    	return connection;
    }
  
    
    // cREATE OR INSERT USER
     
    public void insertUser(User user) throws SQLException {
    	try {
    		Connection connection = getConnection();
    		PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL);
    		preparedStatement.setString(1, user.getName());
    		preparedStatement.setString(2, user.getEmail());
    		preparedStatement.setString(3, user.getCountry());
    		preparedStatement.executeUpdate();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    
    // Update User
	public boolean updateUser(User user) throws SQLException {
		boolean rowUpdated = false;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);) {
			statement.setString(1, user.getName());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getCountry());
			statement.setInt(4, user.getId());

			rowUpdated = statement.executeUpdate() > 0;
		}catch(Exception e) {
    		e.printStackTrace();
    	}
		
		return rowUpdated;
	}
	
	// Select User by ID
	public User selectUser(int id) {
		User user = null;
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();
				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) 
		{
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				user = new User(id, name, email, country);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	// Select all Users
	public List<User> selectAllUsers() {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<User> users = new ArrayList<>();
		
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();

			// Step 2:Create a statement using connection object
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
			System.out.println(preparedStatement);
			
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				users.add(new User(id, name, email, country));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	// Delete user
	public boolean deleteUser(int id) throws SQLException {
		boolean rowDeleted = false;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowDeleted;
	}

    
    
    
    

}
