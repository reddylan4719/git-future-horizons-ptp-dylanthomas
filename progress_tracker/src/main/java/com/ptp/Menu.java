package com.ptp;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/*
This class will be the interactive menu used by both users and super users to interact with the database
*/
public class Menu {

	// single scanner we will use through our program.
	private static Scanner sc;
	
	// use service class you created to handle all the CRUD operations
	


	public static void mainMenu() 
	{
		
		// once we enter menu, can initialize scanner
		sc = new Scanner(System.in);

		// Create tracker object for signing in
		//TrackerConnection uDao = new TrackerConnection();
		UserDao uDao = new UserDao();
		SuperUserDoa suDao = new SuperUserDoa();

		// Will be instantiated in first loop if a user is found/created
		User user = null;

		try 
		{
			uDao.establishConnection();
		} 
		catch (Exception e) 
		{
			System.out.println("Failed to establish connection");
			e.printStackTrace();
		}

		System.out.println("Welcome to the Progress Tracking Application!");
		
		// used to exit loop
		boolean exit = false;
		boolean nextLoop = false;
		
		while(!exit && !nextLoop) 
		{
			
			System.out.println("\nWhat would you like to do?");
			System.out.println("1. Login");
			System.out.println("2. Create an account");
			System.out.println("3. Exit\n");
			
			
			int input = sc.nextInt();
			sc.nextLine(); // will stop issue with an infinite loop w/ scanner (new line character can get stuck in buffer)
			
			String username;
			String password;
			

			switch (input) 
			{
				case 1:
					System.out.println("Username: ");
					username = sc.next();
					System.out.println("Password: ");

					password = sc.next();
					
					Optional<User> login = uDao.loginUser(username, password);

					if (login.isPresent()) 
					{
						user = login.get();
						if (user.getUsername() != null) 
						{
							System.out.println("User found! Welcome " + user.getUsername());
							nextLoop = true;	
						}
						else
						{
							System.out.println("User not found");
						}
					}
					break;
				case 2:
					System.out.println("What is your first name? ");
					String fn = sc.next();
					System.out.println("What is your last name?");
					String ln = sc.next();
					System.out.println("Enter a username: ");
					username = sc.next();
					System.out.println("Enter a password");
					password = sc.next();
					System.out.println("What is your email address? ");
					String email = sc.next();

					user = new User(0, fn, ln, username, password, email);
					try 
					{
						uDao.addProfile(user);
						user = uDao.getUsers(username);
					} 
					catch (UserNotCreatedException e) 
					{
						e.printStackTrace();
						continue;
					}

					System.out.println("Profile created!");
					
					nextLoop = true;
					break;
				case 3:
					exit = true;
					break;
				default:
					System.out.println("\nPlease enter an option listed (number 1 - 3)");
					break;
			}
			
		}

		if (user.getUsername().equals("superuser")) 
		{
			while (!exit) 
			{
				// if login user is the super user
				// Establish connection with userDao
				suDao.connection = uDao.connection;
				
				System.out.println("\nSuper User Menu - What would you like to do?");
				System.out.println("1. Display a user profile");
				System.out.println("2. List all games");
				System.out.println("3. List all tasks (all users)");
				System.out.println("4. Find game by ID");
				System.out.println("5. Find task by ID");
				System.out.println("6. Update a user profile");
				System.out.println("7. Update a task's progress");
				System.out.println("8. Delete a user account");
				System.out.println("9. Delete a task");
				System.out.println("10. Add new task");
				System.out.println("11. List games by genre");
				
				// Super user exclusive
				System.out.println("12. View all users");
				System.out.println("13. Update a game");
				System.out.println("14. Update a genre");
				System.out.println("15. Delete a game");
				System.out.println("16. Delete a genre");
				System.out.println("17. Add a game");
				System.out.println("18. Add a genre");
				System.out.println("19. Exit");
			
				int input = sc.nextInt();
				sc.nextLine(); // Consume newline
			
				int id;
				String progress;
				String name;
			
				switch (input) {
					case 1:
						 System.out.print("Enter the username of the user to view: ");
						 String targetUsername = sc.nextLine();
						 System.out.println(suDao.getUsers(targetUsername));

						 break;
					case 2:
						suDao.getAllGames().forEach(System.out::println);
						break;
					case 3:
						suDao.getAllUserTasks().forEach(System.out::println);
						break;
					case 4:
						System.out.print("Enter game ID: ");
						id = sc.nextInt();
						sc.nextLine();

						Optional<Game> game = suDao.findGameById(id);
						
						if (game.isPresent()) 
						{
							System.out.println(game.get());
						}
						break;
					case 5:
						System.out.print("Enter task ID: ");
						id = sc.nextInt();
						sc.nextLine();
						
						Optional<Task> oTask = suDao.findTaskById(id);
						
						if (oTask.isPresent()) 
						{
							System.out.println(oTask.get());
						}
						break;
					case 6:
						id = sc.nextInt();
						sc.nextLine();
						
						if (id == 1) 
						{
							// Can't update superuser
							System.out.println("You cannot update the superuser account.");
							break;
						}

						System.out.print("New First Name: ");
						user.setFirstName(sc.nextLine());
						System.out.print("New Last Name: ");
						user.setLastName(sc.nextLine());
						System.out.print("New Username: ");
						String username = sc.nextLine();
						if (username.equals("superuser")) 
						{
							System.out.println("You cannot update the superuser account.");
							break;
						}
						
						user.setUsername(username);
						System.out.print("New Password: ");
						user.setPassword(sc.nextLine());
						System.out.print("New Email: ");
						user.setEmail(sc.nextLine());
				
						if (suDao.updateProfile(user)) 
						{
							System.out.println("Profile updated successfully.");
						} 
						else 
						{
							System.out.println("Failed to update profile.");
						}
						break;
					case 7:
						System.out.print("Task ID: "); 
						id = sc.nextInt(); sc.nextLine();
						System.out.print("New Progress: "); 
						progress = sc.nextLine();
						if (suDao.updateTaskProgress(id, progress)) 
						{
							System.out.println("Task updated successfully.");
						} 
						else 
						{
							System.out.println("Failed to update task.");
						}
						break;
					case 8:
						System.out.print("Enter the id of the profile to delete: ");
						id = sc.nextInt();
						sc.nextLine();
						if (id == 1) 
						{
							// Can't delete superuser
							System.out.println("You cannot delete the superuser account.");
							break;
						}
					
						if (suDao.deleteProfile(id)) 
						{
							System.out.println("Profile deleted.");
						} 
						else 
						{
							System.out.println("Failed to delete profile.");
						}
						break;
					case 9:
						System.out.print("Task ID: ");
						id = sc.nextInt(); 
						sc.nextLine();
						if (suDao.deleteTask(id)) 
						{
							System.out.println("Task deleted.");
							exit = true;
						} 
						else 
						{
							System.out.println("Failed to delete task.");
						}
						break;
					case 10:
						try 
						{
							System.out.println("User ID: ");
							id = sc.nextInt();
							sc.nextLine();
							if (id == 1) 
							{
								// Can't give tasks to superuser
								System.out.println("Can't assign tasks to the superuser account.");
								break;
							}
							System.out.print("Game ID: ");
							int gameID = sc.nextInt(); 
							sc.nextLine();
							System.out.print("Progress: ");
							progress = sc.nextLine();
							Task task = new Task(0, id, gameID, progress);
							suDao.addTask(task);
							System.out.println("Task added.");
						} 
						catch (TaskNotCreatedException e) 
						{
							System.out.println("Add failed: " + e.getMessage());
						}
						break;
					case 11:
						System.out.print("Enter genre name: ");
						name = sc.nextLine();
						suDao.findByGenre(name).forEach(System.out::println);
						break;
					case 12:
						suDao.getUsers().forEach(System.out::println);
						break;
					case 13:
						System.out.print("Game ID: ");
						id = sc.nextInt(); sc.nextLine();
						System.out.print("New name: ");
						name = sc.nextLine();
						System.out.print("Genre ID: ");
						int genreID = sc.nextInt(); sc.nextLine();
						System.out.print("Made by: ");
						String madeBy = sc.nextLine();
						System.out.print("Release date (YYYY-MM-DD): ");
						Date releaseDate = Date.valueOf(sc.nextLine());
						if (suDao.updateGame(id, name, genreID, madeBy, releaseDate)) 
						{
							System.out.println("Game updated successfully.");
						} 
						else 
						{
							System.out.println("Failed to update game.");
						}
						break;
					case 14:
						System.out.print("Genre ID: ");
						id = sc.nextInt(); sc.nextLine();
						System.out.print("New genre name: ");
						name = sc.nextLine();
						if (suDao.updateGenre(id, name)) 
						{
							System.out.println("Genre updated successfully.");
						} 
						else 
						{
							System.out.println("Failed to update genre.");
						}
						break;
					case 15:
						System.out.print("Game ID: ");
						id = sc.nextInt(); sc.nextLine();
						if (suDao.deleteGame(id)) 
						{
							System.out.println("Game deleted successfully.");
						} 
						else 
						{
							System.out.println("Failed to delete game.");
						}
						break;
					case 16:
						System.out.print("Genre ID: ");
						id = sc.nextInt(); sc.nextLine();
						if (suDao.deleteGenre(id)) 
						{
							System.out.println("Genre deleted successfully.");
						} 
						else 
						{
							System.out.println("Failed to delete genre.");
						}
						break;
					case 17:
						try 
						{
							System.out.print("Game name: ");
							name = sc.nextLine();
							System.out.print("Genre ID: ");
							genreID = sc.nextInt(); sc.nextLine();
							System.out.print("Made by: ");
							madeBy = sc.nextLine();
							System.out.print("Release date (YYYY-MM-DD): ");
							releaseDate = Date.valueOf(sc.nextLine());
							Game newGame = new Game(0, name, genreID, madeBy, releaseDate);
							suDao.addGame(newGame);
							System.out.println("Game added.");
						} 
						catch (GameNotCreatedException e) 
						{
							System.out.println("Add failed: " + e.getMessage());
						}
						break;
					case 18:
						try 
						{
							System.out.print("Genre name: ");
							name = sc.nextLine();
							Genre genre = new Genre(0, name);
							suDao.addGenre(genre);
							System.out.println("Genre added.");
						} 
						catch (GenreNotCreatedException e) 
						{
							System.out.println("Add failed: " + e.getMessage());
						}
						break;
					case 19:
						exit = true;
						try 
						{
							uDao.closeConnection();
							suDao.closeConnection();
						} 
						catch (Exception e) 
						{
							System.out.println("Failed to disconnect");
							e.printStackTrace();
						}

						break;
					default:
						System.out.println("Please enter an option listed (number 1 - 19)");
						break;
					}
				
				}
			}
			else
			{
				
			while (!exit) 
			{
				// Else login user is a regular user
				// Establish connection with userDao
				//uDao.connection = tracker.connection;

				System.out.println("\nWhat would you like to do?");
				System.out.println("1. Display profile information");
				System.out.println("2. List all available games");
				System.out.println("3. List all your tasks");
				System.out.println("4. Find game by id");
				System.out.println("5. Find task by id");
				System.out.println("6. Update your profile");
				System.out.println("7. Update a tasks progress");
				System.out.println("8. Delete your account");
				System.out.println("9. Delete a task");
				System.out.println("10. Add new task");
				System.out.println("11. List games of a specified genre");
				System.out.println("12. Exit");
				
				
				int input = sc.nextInt();
				sc.nextLine(); // will stop issue with an infinite loop w/ scanner (new line character can get stuck in buffer)
				
				int id;
				
		
				switch (input) 
				{
					case 1: // Display profile info
						System.out.println(uDao.getUsers(user.getUsername()));
					
						break;
				
					case 2: // List all available games
						List<Game> allGames = uDao.getAllGames();
						allGames.forEach(System.out::println);
						break;
				
					case 3: // List all user tasks
						List<Task> tasks = uDao.getAllUserTasks(user.getId());
						tasks.forEach(System.out::println);
						break;
				
					case 4: // Find game by ID
						System.out.print("Enter game ID: ");
						id = sc.nextInt();
						sc.nextLine();

						Optional<Game> game = uDao.findGameById(id);
						
						if (game.isPresent()) 
						{
							System.out.println(game.get());
						}
						break;
				
					case 5: // Find task by ID
						System.out.print("Enter task ID: ");
						id = sc.nextInt();
						sc.nextLine();
						
						Optional<Task> task = uDao.findTaskById(id);
						
						if (task.isPresent()) 
						{
							System.out.println(task.get());
						}
						break;
				
					case 6: // Update profile
						System.out.print("New First Name: ");
						user.setFirstName(sc.nextLine());
						System.out.print("New Last Name: ");
						user.setLastName(sc.nextLine());
						System.out.print("New Username: ");
						user.setUsername(sc.nextLine());
						System.out.print("New Password: ");
						user.setPassword(sc.nextLine());
						System.out.print("New Email: ");
						user.setEmail(sc.nextLine());
				
						if (uDao.updateProfile(user)) 
						{
							System.out.println("Profile updated successfully.");
						} 
						else 
						{
							System.out.println("Failed to update profile.");
						}
						break;
				
					case 7: // Update task progress
						System.out.print("Enter task ID: ");
						id = sc.nextInt();
						sc.nextLine();
						System.out.print("Enter new progress (e.g., Not Started, In Progress, Completed): ");
						String progress = sc.nextLine();
						if (uDao.updateTaskProgress(id, progress)) 
						{
							System.out.println("Task progress updated.");
						} 
						else 
						{
							System.out.println("Failed to update task progress.");
						}
						break;
				
					case 8: // Delete account
						if (uDao.deleteProfile(user.getId())) 
						{
							System.out.println("Account deleted.");
							exit = true; // exit program after account deletion
						} 
						else 
						{
							System.out.println("Failed to delete account.");
						}
						break;
				
					case 9: // Delete a task
						System.out.print("Enter task ID to delete: ");
						int deleteTaskId = sc.nextInt();
						sc.nextLine();
						if (uDao.deleteTask(deleteTaskId)) 
						{
							System.out.println("Task deleted.");
						} 
						else 
						{
							System.out.println("Failed to delete task.");
						}
						break;
				
					case 10: // Add new task
						try 
						{
							System.out.print("Enter game ID to associate with task: ");
							int newGameId = sc.nextInt();
							sc.nextLine();
							System.out.print("Enter initial task progress: ");
							String newProgress = sc.nextLine();
							Task newTask = new Task(0, user.getId(), newGameId, newProgress);
							uDao.addTask(newTask);
							System.out.println("Task added.");
						} 
						catch (TaskNotCreatedException e) 
						{
							System.out.println("Failed to add task: " + e.getMessage());
						}
						break;
				
					case 11: // List games by genre
						System.out.print("Enter genre name: ");
						String genre = sc.nextLine();
						List<Game> gamesByGenre = uDao.findByGenre(genre);
						gamesByGenre.forEach(System.out::println);
						break;

					case 12:
						exit = true;
						try 
						{
							uDao.closeConnection();	
						} 
						catch (Exception e) 
						{
							System.out.println("Failed to disconnect");
							e.printStackTrace();
						}
						break;

					default:
						System.out.println("\nPlease enter an option listed (number 1 - 12)");
						break;
					}
				}
			}
		
		System.out.println("\n\nGoodBye!");
		
		// once we exit, will close the scanner
		sc.close();
	}
}