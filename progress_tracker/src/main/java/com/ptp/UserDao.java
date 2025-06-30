package com.ptp;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao extends TrackerConnection
{
	// -----------------------------------------------------------------------
	// THIS DAO WILL BE USED BY REGULAR CUSTOMERS WHO SIGN INTO THEIR ACCOUNTS
	// ------------------------------------------------------------------------
	// USERS WILL BE ABLE TO UPDATE/CREATE/DELETE THEIR PROFILE, ADD/REMOVE/VIEW/UPDATE THEIR TASKS AND TASK STATUSES, VIEW AVAILABLE GAMES, VIEW GAMES BY SPECIFIC GENRE 
	// ------------------------------------------------------------------------
	public User getUsers(String username)
	{
		try 
		{
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
	
			stmt.setString(1, username);
			
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) 
			{
				int id = rs.getInt(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				username = rs.getString(4);
				String password = rs.getString(5);
				String email = rs.getString(6);
	
				User user = new User(id, firstName, lastName, username, password, email);	
				return user;
			}
		}
		catch (SQLException e)
		{
			System.out.println("SQL Exception");
			System.out.println(e.getMessage());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}


		return null;
	}
	public List<Game> getAllGames() 
	{
		try 
		{
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM games");
	
			List<Game> games = new ArrayList<>();
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) 
			{
				int id = rs.getInt(1);
				String name = rs.getString(2);
				int genreID = rs.getInt(3);
				String madeBy = rs.getString(4);
				Date releaseDate = rs.getDate(5);

				Game game = new Game(id, name, genreID, madeBy, releaseDate);
				games.add(game);
			}

			return games;
		}
		catch (SQLException e)
		{
			System.out.println("SQL Exception");
			System.out.println(e.getMessage());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}


		return new ArrayList<>();
	}

	public List<Task> getAllUserTasks(int userID) 
	{
		try 
		{
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM tasks WHERE user_id = ?");
			stmt.setInt(1, userID);

			List<Task> tasks = new ArrayList<>();
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) 
			{
				int id = rs.getInt(1);
				int gameID = rs.getInt(3);
				String progress = rs.getString(4);
				
				Task task = new Task(id, userID, gameID, progress);
				tasks.add(task);
			}

			return tasks;
		}
		catch (SQLException e)
		{
			System.out.println("SQL Exception");
			System.out.println(e.getMessage());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}


		return new ArrayList<>();
	}

	public Optional<Game> findGameById(int id) 
	{
		try 
		{
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM games WHERE game_id = ?");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) 
			{
				String name = rs.getString(2);
				int genreID = rs.getInt(3);
				String madeBy = rs.getString(4);
				Date releaseDate = rs.getDate(5);

				Game game = new Game(id, name, genreID, madeBy, releaseDate);

				return Optional.of(game);
			}

		}
		catch (SQLException e)
		{
			System.out.println("SQL Exception");
			System.out.println(e.getMessage());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		return Optional.empty();
	}

	public Optional<Task> findTaskById(int id) 
	{
		try 
		{
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM tasks WHERE task_id = ?");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) 
			{
				int userID = rs.getInt(2);
				int gameID = rs.getInt(3);
				String progress = rs.getString(4);
				
				Task task = new Task(id, userID, gameID, progress);
				
				return Optional.of(task);
			}

		}
		catch (SQLException e)
		{
			System.out.println("SQL Exception");
			System.out.println(e.getMessage());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		return Optional.empty();
	}

	public boolean updateProfile(User user) 
	{
		try 
		{
			PreparedStatement stmt = connection.prepareStatement("UPDATE users SET first_name = ?, last_name = ?, username = ?, password = ?, email = ? WHERE user_id = ?");
			stmt.setString(1, user.getFirstName());
			stmt.setString(2, user.getLastName());
			stmt.setString(3, user.getUsername());
			stmt.setString(4, user.getPassword());	
			stmt.setString(5, user.getEmail());
			stmt.setInt(6, user.getId());
			
			int count = stmt.executeUpdate();
			
			if (count > 0) 
			{
				return true;
			}

		}
		catch (SQLException e)
		{
			System.out.println("SQL Exception");
			System.out.println(e.getMessage());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		return false;
	}

	public boolean updateTaskProgress(int id, String progress) 
	{
		try 
		{
			PreparedStatement stmt = connection.prepareStatement("UPDATE tasks SET progress = ? WHERE task_id = ?");
			
			stmt.setString(1, progress);
			stmt.setInt(2, id);
			
			int count = stmt.executeUpdate();
			
			if (count > 0) 
			{
				return true;
			}

		}
		catch (SQLException e)
		{
			System.out.println("SQL Exception");
			System.out.println(e.getMessage());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		return false;
	}

	public boolean deleteProfile(int id) 
	{
		try 
		{
			PreparedStatement stmt = connection.prepareStatement("DELETE FROM users WHERE user_id = ?");
			stmt.setInt(1, id);
			
			int count = stmt.executeUpdate();
			
			if (count > 0) 
			{
				return true;
			}

		}
		catch (SQLException e)
		{
			System.out.println("SQL Exception");
			System.out.println(e.getMessage());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		return false;
	}

	public boolean deleteTask(int id) 
	{
		try 
		{
			PreparedStatement stmt = connection.prepareStatement("DELETE FROM tasks WHERE task_id = ?");
			stmt.setInt(1, id);
			
			int count = stmt.executeUpdate();
			
			if (count > 0) 
			{
				return true;
			}

		}
		catch (SQLException e)
		{
			System.out.println("SQL Exception");
			System.out.println(e.getMessage());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		return false;
	}

	public void addProfile(User user) throws UserNotCreatedException 
	{
		// Only used to create new accounts and by super user
		try 
		{
			// No repeat usernames
			int count;
			PreparedStatement stmt = connection.prepareStatement("insert into users(first_name, last_name, username, password, email) values(?, ?, ?, ?, ?)");
			stmt.setString(1, user.getFirstName());
			stmt.setString(2, user.getLastName());
			stmt.setString(3, user.getUsername());
			stmt.setString(4, user.getPassword());	
			stmt.setString(5, user.getEmail());
			count = stmt.executeUpdate();
			if (count == 0) 
			{
				throw new UserNotCreatedException(user);
			}
		}
		catch (SQLException e)
		{
			System.out.println("SQL Exception");
			System.out.println(e.getMessage());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public void addTask(Task task) throws TaskNotCreatedException 
	{
		try 
		{
			int count;
			PreparedStatement stmt = connection.prepareStatement("insert into tasks(user_id, game_id, progress) values(?, ?, ?)");
			stmt.setInt(1, task.getUserID());
			stmt.setInt(2, task.getGameID());
			stmt.setString(3, task.getProgress());
			count = stmt.executeUpdate();
			if (count == 0) 
			{
				throw new TaskNotCreatedException(task);
			}
		}
		catch (SQLException e)
		{
			System.out.println("SQL Exception");
			System.out.println(e.getMessage());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public List<Game> findByGenre(String genreName) 
	{
		try 
		{
			List<Game> games = new ArrayList<>();

			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM games JOIN genres ON games.genre_id = genres.genre_id WHERE genre_name = ?");
			stmt.setString(1, genreName);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) 
			{
				int id = rs.getInt(1);
				String name = rs.getString(2);
				int genreID = rs.getInt(3);
				String madeBy = rs.getString(4);
				Date releaseDate = rs.getDate(5);

				Game game = new Game(id, name, genreID, madeBy, releaseDate);
				games.add(game);
			}

			return games;
		}
		catch (SQLException e)
		{
			System.out.println("SQL Exception");
			System.out.println(e.getMessage());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		return new ArrayList<>();
	}	
}