USE progress_tracker;

-- Insert Genres
INSERT INTO genres (genre_name) VALUES 
('Action'),
('Adventure'),
('Role-Playing'),
('Shooter'),
('Simulation'),
('Strategy');

-- Insert Users
INSERT INTO users (first_name, last_name, username, password, email) VALUES 
('SUPER', 'USER', 'superuser', 'super123', 'superuser@company.com'),
('Bob', 'Smith', 'bsmith', 'secret456', 'bob@example.com'),
('Charlie', 'Lee', 'clee', 'pass789', 'charlie@example.com'),
('Diana', 'Evans', 'dianae', 'mypassword', 'diana@example.com'),
('Ethan', 'Wong', 'ethanw', 'securepass', 'ethan@example.com');

-- Insert Games
INSERT INTO games (game_name, genre_id, made_by, release_date) VALUES 
('The Legend of Zelda: Breath of the Wild', 2, 'Nintendo', '2017-03-03'),
('Elden Ring', 3, 'FromSoftware', '2022-02-25'),
('Call of Duty: Modern Warfare II', 4, 'Infinity Ward', '2022-10-28'),
('Stardew Valley', 5, 'ConcernedApe', '2016-02-26'),
('Civilization VI', 6, 'Firaxis Games', '2016-10-21'),
('God of War Ragnarok', 1, 'Santa Monica Studio', '2022-11-09'),
('Animal Crossing: New Horizons', 5, 'Nintendo', '2020-03-20'),
('Final Fantasy XVI', 3, 'Square Enix', '2023-06-22'),
('Halo Infinite', 4, '343 Industries', '2021-12-08'),
('Fire Emblem: Three Houses', 6, 'Intelligent Systems', '2019-07-26');
