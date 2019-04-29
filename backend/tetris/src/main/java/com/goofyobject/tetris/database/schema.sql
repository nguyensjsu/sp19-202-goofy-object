sudu su mysql
-u root -p -h localhost

mysql -u admin -p -h localhost

CREATE USER 'admin'@'localhost' IDENTIFIED BY 12345678;

GRANT ALL PRIVILEGES ON my_company.* TO 'admin'@'localhost';
--- database sql

-- create database
CREATE DATABASE cmpe202_group;

USE cmpe202_group;

-- create users table
DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    userid        int primary key not null auto_increment,
    username      varchar(255) not null,         
    password      char(8) not null
);
-- create histroy table
DROP TABLE IF EXISTS history;
CREATE TABLE history
(
    userid        int not null,
    username      VARCHAR(255) not null,
    game_time     timestamp,
    game_flag     int not null,
    win_flag      int not null
);

