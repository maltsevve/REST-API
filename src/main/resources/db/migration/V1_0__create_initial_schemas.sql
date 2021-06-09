CREATE TABLE IF NOT EXISTS users
(
    user_id int AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS files
(
    file_id int AUTO_INCREMENT NOT NULL PRIMARY KEY,
    filename varchar(255)
);

CREATE TABLE IF NOT EXISTS events
(
    event_id int AUTO_INCREMENT NOT NULL PRIMARY KEY,
    event_time timestamp NOT NULL,
    status varchar(255) NOT NULL,
    file_id int NOT NULL,
    user_id int NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users(user_id)
)
