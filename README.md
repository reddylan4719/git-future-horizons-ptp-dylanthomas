# git-future-horizons-ptp-dylanthomas

A repo for the  Progress Tracker Project Capstone





For this project I have provided the schema and database as well as the table diagram (Both the .mwb and a .png). Be sure to run the schema then the data sql scripts before testing the code. 



The ConnectionManager.java is where you'll find the URL, username, and password for connecting to the SQL database. 



TrackerConnection holds the code responsible for connecting to database and logging in a user.



UserDao extends TrackerConnection and SuperUserDao extends UserDao. UserDao is used for regular users while SuperUserDao is used for super users.



If you look in the database, you will see user 1 is labeled a super user. Logging in as a super user to gain extra data manipulation options (You are NOT allowed to update or delete the superuser even as the superuser). The superuser is also not allowed to have tasks.



To login as the super user:

username: superuser

password: super123



Other users currently in database:

USERNAMES 	PASSWORDS

--------------------------

bsmith		secret456

--------------------------

clee		pass789

--------------------------

dianae		mypassword

--------------------------

ethanw		securepass

--------------------------



Usernames are set as unique in the database so creating/updating a user with an already existing username will fail and have you try again.

