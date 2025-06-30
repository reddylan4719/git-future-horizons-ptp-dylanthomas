package com.ptp;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/* 
==================================================
THIS DOA WILL BE USED FOR SUPER USERS/DEVS
======================================================
DEVS CAN DO EVERYTHING A REGULAR USER CAN AND 
VIEW USER LIST | DONE
REMOVE ANY USER BUT "superuser" | done through UserDoa
ADD/REMOVE/UPDATE GAMES/GENRES | DONE
VIEW ALL USERS TASKS | Done
=======================================================
*/
public class SuperUserDoa extends UserDao
{
    public List<User> getUsers() 
	{
		try 
		{
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users");
	
			List<User> users = new ArrayList<>();
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) 
			{
				int id = rs.getInt(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				String username = rs.getString(4);
				String password = rs.getString(5);
                String email = rs.getString(6);

				User user = new User(id, firstName, lastName, username, password, email);
				users.add(user);
			}

			return users;
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}


		return new ArrayList<>();
	}

    public List<Task> getAllUserTasks() 
	{
		try 
		{
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM tasks");

			List<Task> tasks = new ArrayList<>();
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) 
			{
				int id = rs.getInt(1);
                int userID = rs.getInt(2);
				int gameID = rs.getInt(3);
				String progress = rs.getString(4);
				
				Task task = new Task(id, userID, gameID, progress);
				tasks.add(task);
			}

			return tasks;
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}


		return new ArrayList<>();
	}
    
    public boolean updateGame(int id, String name, int genreID, String madeBy, Date releasDate) 
	{
		try 
		{
			PreparedStatement stmt = connection.prepareStatement("UPDATE games SET game_name = ?, genre_id = ?, made_by = ?, release_date = ? WHERE game_id = ?");
			
			stmt.setString(1, name);
			stmt.setInt(2, genreID);
			stmt.setString(3, madeBy);
			stmt.setDate(4, releasDate);
			stmt.setInt(5, id);
			
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

    public boolean updateGenre(int id, String name) 
	{
		try 
		{
			PreparedStatement stmt = connection.prepareStatement("UPDATE genres SET genre_name = ? WHERE genre_id = ?");
			
			stmt.setString(1, name);
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

    public boolean deleteGame(int id) 
	{
		try 
		{
			PreparedStatement stmt = connection.prepareStatement("DELETE FROM games WHERE game_id = ?");
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

    public boolean deleteGenre(int id) 
	{
		try 
		{
			PreparedStatement stmt = connection.prepareStatement("DELETE FROM genres WHERE genre_id = ?");
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

    public void addGame(Game game) throws GameNotCreatedException 
	{
		try 
		{
			int count;
			PreparedStatement stmt = connection.prepareStatement("INSERT INTO games(game_name, genre_id, made_by, release_date) VALUES (?, ?, ?, ?)");
			stmt.setString(1, game.getName());
			stmt.setInt(2, game.getGenreID());
			stmt.setString(3, game.getMadeBy());
            stmt.setDate(4, game.getReleaseDate());
			count = stmt.executeUpdate();
			if (count == 0) 
			{
				throw new GameNotCreatedException(game);
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

    public void addGenre(Genre genre) throws GenreNotCreatedException 
	{
		try 
		{
			int count;
			PreparedStatement stmt = connection.prepareStatement("INSERT INTO genres(genre_name) VALUES (?)");
			stmt.setString(1, genre.getName());
			count = stmt.executeUpdate();
			if (count == 0) 
			{
				throw new GenreNotCreatedException(genre);
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
}
