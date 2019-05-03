
DROP TABLE IF EXISTS user;

CREATE TABLE user(
    username VARCHAR(255) NOT NULL, 
    password VARCHAR(255) NOT NULL,
    region VARCHAR(255),
    gender VARCHAR(255), 
    age INT,
    wins INT, 
    loses INT,
    PRIMARY KEY(username) 
);

