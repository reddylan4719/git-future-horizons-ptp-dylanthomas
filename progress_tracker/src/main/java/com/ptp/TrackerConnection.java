package com.ptp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class TrackerConnection 
{
    protected Connection connection = null;

	public void establishConnection() throws ClassNotFoundException, SQLException 
	{
		if(connection == null) 
		{
			connection = ConnectionManager.getConnection();
		}
	}
	
	public void closeConnection() throws SQLException 
	{
		connection.close();
	}

	public Optional<User> loginUser(String username, String password)
	{
		try 
		{
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) 
			{
				int userID = rs.getInt(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				String email = rs.getString(6);
				
				User user = new User(userID, firstName, lastName, username, password, email);
				
				return Optional.of(user);
			}

		}
		catch (SQLException e)
		{
			System.out.println("SQL Exception");
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		System.out.println("User not found");

		return Optional.empty();
	}
}
