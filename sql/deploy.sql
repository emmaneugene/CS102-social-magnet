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
    pwd      VARCHAR(128) NOT NULL,
    PRIMARY KEY (username)
);

CREATE TABLE post (
    post_id   INT          NOT NULL AUTO_INCREMENT,
    author    VARCHAR(255) NOT NULL,
    recipient VARCHAR(255) NOT NULL,
    posted_on DATETIME     NOT NULL,
    content   TEXT(65535)  NOT NULL,
    PRIMARY KEY (post_id),
    FOREIGN KEY (author)    REFERENCES user (username),
    FOREIGN KEY (recipient) REFERENCES user (username)
);

CREATE TABLE tag (
    post_id     INT          NOT NULL,
    tagged_user VARCHAR(255) NOT NULL,
    PRIMARY KEY (post_id,     tagged_user),
    FOREIGN KEY (post_id)     REFERENCES post (post_id),
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
    comment_id   INT          NOT NULL AUTO_INCREMENT,
    post_id      INT          NOT NULL,
    commenter    VARCHAR(255) NOT NULL,
    commented_on DATETIME     NOT NULL,
    content      TEXT(65535)  NOT NULL,
    PRIMARY KEY (comment_id),
    FOREIGN KEY (post_id)   REFERENCES post (post_id),
    FOREIGN KEY (commenter) REFERENCES user (username)
);

CREATE TABLE likes (
    username VARCHAR(128) NOT NULL,
    post_id  INT          NOT NULL,
    PRIMARY KEY (username, post_id),
    FOREIGN KEY (username) REFERENCES user (username),
    FOREIGN KEY (post_id)  REFERENCES post (post_id)
);

CREATE TABLE dislikes (
    username VARCHAR(128) NOT NULL,
    post_id  INT          NOT NULL,
    PRIMARY KEY (username, post_id),
    FOREIGN KEY (username) REFERENCES user (username),
    FOREIGN KEY (post_id)  REFERENCES post (post_id)
);

CREATE TABLE requests (
    sender    VARCHAR(255) NOT NULL,
    recipient VARCHAR(255) NOT NULL,
    PRIMARY KEY (sender,    recipient),
    FOREIGN KEY (sender)    REFERENCES user (username),
    FOREIGN KEY (recipient) REFERENCES user (username)
);

CREATE TABLE farmer (
    username VARCHAR(255) NOT NULL,
    xp INT NOT NULL,
    wealth INT NOT NULL,
    PRIMARY KEY (username),
    FOREIGN KEY (username) REFERENCES user (username)
);

CREATE TABLE crop (
    crop_name VARCHAR(255) NOT NULL,
    cost INT NOT NULL,
    crop_time INT NOT NULL,
    xp INT NOT NULL,
    min_yield INT NOT NULL,
    max_yield INT NOT NULL,
    sale_price INT NOT NULL,
    PRIMARY KEY (crop_name)
);

CREATE TABLE plot (
    farmer VARCHAR(255) NOT NULL,
    plot_id INT NOT NULL,
    crop_name VARCHAR(255) NULL,
    time_planted DATETIME NULL,
    yield INT NULL,
    percent_stolen INT NULL,
    PRIMARY KEY (farmer, plot_id),
    FOREIGN KEY (farmer) REFERENCES farmer (username),
    FOREIGN KEY (crop_name) REFERENCES crop (crop_name)
);

CREATE TABLE stealing (
    victim VARCHAR(255) NOT NULL,
    stolen_plot_id INT NOT NULL,
    stealer VARCHAR(255) NOT NULL,
    PRIMARY KEY (victim, stolen_plot_id, stealer),
    FOREIGN KEY (victim, stolen_plot_id) REFERENCES plot (farmer, plot_id),
    FOREIGN KEY (stealer) REFERENCES farmer (username)
);

CREATE TABLE inventory (
    username VARCHAR(255) NOT NULL,
    crop_name VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY (username, crop_name),
    FOREIGN KEY (username) REFERENCES farmer (username),
    FOREIGN KEY (crop_name) REFERENCES crop (crop_name)
);

CREATE TABLE gift (
    gifter VARCHAR(255) NOT NULL,
    giftee VARCHAR(255) NOT NULL,
    crop_name VARCHAR(255) NOT NULL,
    gifted_on DATETIME NOT NULL,
    PRIMARY KEY (gifter, giftee, crop_name, gifted_on),
    FOREIGN KEY (gifter) REFERENCES farmer (username),
    FOREIGN KEY (giftee) REFERENCES farmer (username),
    FOREIGN KEY (crop_name) REFERENCES crop (crop_name)
);
