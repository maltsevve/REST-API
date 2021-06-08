CREATE TABLE IF NOT EXISTS users
(
    UserId int AUTO_INCREMENT NOT NULL PRIMARY KEY,
    Name varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS files
(
    FileId int AUTO_INCREMENT NOT NULL PRIMARY KEY,
    FileName varchar(255)
);

CREATE TABLE IF NOT EXISTS events
(
    EventId int AUTO_INCREMENT NOT NULL PRIMARY KEY,
    EventTime timestamp NOT NULL,
    Status varchar(255) NOT NULL,
    FileId int NOT NULL,
    UserId int NOT NULL,

    FOREIGN KEY (UserId) REFERENCES users(UserID)
)
