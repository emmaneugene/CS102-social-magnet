-- drop database if exists and recreate it
DROP DATABASE IF EXISTS magnet;
CREATE DATABASE magnet;

-- use this database to create and populate the tables
USE magnet;
SET time_zone = '+00:00';

-- create commands (this is just an example)
CREATE TABLE user (
    username VARCHAR(255) NOT NULL,
    fullname VARCHAR(128) NOT NULL,
    pwd VARCHAR(128) NOT NULL,
    PRIMARY KEY (username)
);

CREATE TABLE post (
    post_id INT NOT NULL AUTO_INCREMENT,
    author VARCHAR(255) NOT NULL,
    recipient VARCHAR(255) NOT NULL,
    content TEXT(65535) NOT NULL,
    posted_on DATETIME NOT NULL,
    PRIMARY KEY (post_id),
    FOREIGN KEY (author) REFERENCES user (username),
    FOREIGN KEY (recipient) REFERENCES user (username)
);

CREATE TABLE tag (
    post_id INT NOT NULL,
    tagged_user VARCHAR(255) NOT NULL,
    PRIMARY KEY (post_id, tagged_user),
    FOREIGN KEY (post_id) REFERENCES post (post_id),
    FOREIGN KEY (tagged_user) REFERENCES user (username)
);

CREATE TABLE friend (
    user_1 VARCHAR(255) NOT NULL,
    user_2 VARCHAR(255) NOT NULL,
    PRIMARY KEY (user_1, user_2),
    FOREIGN KEY (user_1) REFERENCES user (username),
    FOREIGN KEY (user_2) REFERENCES user (username)
);

CREATE TABLE comment (
    comment_id INT NOT NULL AUTO_INCREMENT,
    content TEXT(65535) NOT NULL,
    post_id INT NOT NULL,
    commenter VARCHAR(255) NOT NULL,
    commented_on DATETIME NOT NULL,
    PRIMARY KEY (comment_id),
    FOREIGN KEY (post_id) REFERENCES post (post_id),
    FOREIGN KEY (commenter) REFERENCES user (username)
);

CREATE TABLE likes (
    username VARCHAR(128) NOT NULL,
    post_id INT NOT NULL,
    PRIMARY KEY (username, post_id),
    FOREIGN KEY (username) REFERENCES user (username),
    FOREIGN KEY (post_id) REFERENCES post (post_id)
);

CREATE TABLE dislikes (
    username VARCHAR(128) NOT NULL,
    post_id INT NOT NULL,
    PRIMARY KEY (username, post_id),
    FOREIGN KEY (username) REFERENCES user (username),
    FOREIGN KEY (post_id) REFERENCES post (post_id)
);

CREATE TABLE requests (
    sender VARCHAR(255) NOT NULL,
    recipient VARCHAR(255) NOT NULL,
    PRIMARY KEY (sender, recipient),
    FOREIGN KEY (sender) REFERENCES user (username),
    FOREIGN KEY (recipient) REFERENCES user (username)
);

-- insert commands (needed for your application to work)
-- INSERT INTO contact (contact_id, given_name, family_name, email)
-- VALUES
-- (1, 'Dukester', 'Lee', geek@smu.edu.sg');