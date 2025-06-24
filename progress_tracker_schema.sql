drop database if exists progress_tracker;
create database progress_tracker;

use progress_tracker;

create table users
(
	user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name varchar(50) not null,
    username varchar(255),
    password varchar(255),
    email varchar(255)
);

create table genres
(
	genre_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	genre_name varchar(255)
);

create table games
(
	game_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    game_name varchar(255),
    genre_id int,
    CONSTRAINT genre_id 
      FOREIGN KEY( genre_id )
      REFERENCES genres( genre_id ) 
      ON DELETE CASCADE ON UPDATE CASCADE,
    made_by varchar(255),
    release_date date
);

create table tasks
(
	task_id int not null auto_increment primary key,
    user_id int,
    CONSTRAINT user_id 
      FOREIGN KEY( user_id )
      REFERENCES users( user_id ) 
      ON DELETE CASCADE ON UPDATE CASCADE,
	game_id int,
    CONSTRAINT game_id 
      FOREIGN KEY( game_id )
      REFERENCES games( game_id ) 
      ON DELETE CASCADE ON UPDATE CASCADE,
      progress char
);