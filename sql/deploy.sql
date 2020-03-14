-- drop database if exists and recreate it
DROP DATABASE IF EXISTS magnet;
CREATE DATABASE magnet;

USE magnet;
SET time_zone = '+00:00';

-- create tables
CREATE TABLE user (
    username VARCHAR(255) NOT NULL,
    fullname VARCHAR(255) NOT NULL,
    pwd      VARCHAR(255) NOT NULL,
    PRIMARY KEY (username)
);

CREATE TABLE thread (
    thread_id   INT          NOT NULL AUTO_INCREMENT,
    author      VARCHAR(255) NOT NULL,
    recipient   VARCHAR(255) NOT NULL,
    posted_on   DATETIME     NOT NULL,
    content     TEXT(65535)  NOT NULL,
    PRIMARY KEY (thread_id),
    FOREIGN KEY (author)    REFERENCES user (username),
    FOREIGN KEY (recipient) REFERENCES user (username)
);

CREATE TABLE tag (
    thread_id     INT          NOT NULL,
    tagged_user   VARCHAR(255) NOT NULL,
    PRIMARY KEY (thread_id, tagged_user),
    FOREIGN KEY (thread_id)   REFERENCES thread (thread_id) ON DELETE CASCADE,
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
    comment_id     INT          NOT NULL AUTO_INCREMENT,
    thread_id      INT          NOT NULL,
    commenter      VARCHAR(255) NOT NULL,
    commented_on   DATETIME     NOT NULL,
    content        TEXT(65535)  NOT NULL,
    PRIMARY KEY (comment_id),
    FOREIGN KEY (thread_id) REFERENCES thread (thread_id) ON DELETE CASCADE,
    FOREIGN KEY (commenter) REFERENCES user (username)
);

CREATE TABLE liker (
    username   VARCHAR(128) NOT NULL,
    thread_id  INT          NOT NULL,
    PRIMARY KEY (username, thread_id),
    FOREIGN KEY (username)  REFERENCES user (username),
    FOREIGN KEY (thread_id) REFERENCES thread (thread_id) ON DELETE CASCADE
);

CREATE TABLE disliker (
    username   VARCHAR(128) NOT NULL,
    thread_id  INT          NOT NULL,
    PRIMARY KEY (username, thread_id),
    FOREIGN KEY (username)  REFERENCES user (username),
    FOREIGN KEY (thread_id) REFERENCES thread (thread_id) ON DELETE CASCADE
);

CREATE TABLE request (
    sender    VARCHAR(255) NOT NULL,
    recipient VARCHAR(255) NOT NULL,
    PRIMARY KEY (sender, recipient),
    FOREIGN KEY (sender)    REFERENCES user (username),
    FOREIGN KEY (recipient) REFERENCES user (username)
);

CREATE TABLE farmer (
    username VARCHAR(255) NOT NULL,
    xp       INT          NOT NULL,
    wealth   INT          NOT NULL,
    PRIMARY KEY (username),
    FOREIGN KEY (username) REFERENCES user (username)
);

CREATE TABLE crop (
    crop_name  VARCHAR(255) NOT NULL,
    cost       INT          NOT NULL,
    crop_time  INT          NOT NULL,
    xp         INT          NOT NULL,
    min_yield  INT          NOT NULL,
    max_yield  INT          NOT NULL,
    sale_price INT          NOT NULL,
    PRIMARY KEY (crop_name)
);

CREATE TABLE plot (
    farmer         VARCHAR(255) NOT NULL,
    plot_id        INT          NOT NULL,
    crop_name      VARCHAR(255) NULL,
    time_planted   DATETIME     NULL,
    yield          INT          NULL,
    percent_stolen INT          NULL,
    PRIMARY KEY (farmer, plot_id),
    FOREIGN KEY (farmer)    REFERENCES farmer (username),
    FOREIGN KEY (crop_name) REFERENCES crop (crop_name)
);

CREATE TABLE stealing (
    victim         VARCHAR(255) NOT NULL,
    stolen_plot_id INT          NOT NULL,
    stealer        VARCHAR(255) NOT NULL,
    PRIMARY KEY (victim, stolen_plot_id, stealer),
    FOREIGN KEY (victim, stolen_plot_id) REFERENCES plot (farmer, plot_id),
    FOREIGN KEY (stealer)                REFERENCES farmer (username)
);

CREATE TABLE inventory (
    username  VARCHAR(255) NOT NULL,
    crop_name VARCHAR(255) NOT NULL,
    quantity  INT          NOT NULL,
    PRIMARY KEY (username, crop_name),
    FOREIGN KEY (username)  REFERENCES farmer (username),
    FOREIGN KEY (crop_name) REFERENCES crop (crop_name)
);

CREATE TABLE gift (
    gifter    VARCHAR(255) NOT NULL,
    giftee    VARCHAR(255) NOT NULL,
    crop_name VARCHAR(255) NOT NULL,
    gifted_on DATETIME     NOT NULL,
    PRIMARY KEY (gifter, giftee, crop_name, gifted_on),
    FOREIGN KEY (gifter)    REFERENCES farmer (username),
    FOREIGN KEY (giftee)    REFERENCES farmer (username),
    FOREIGN KEY (crop_name) REFERENCES crop (crop_name)
);

-- create stored procedures
CREATE PROCEDURE get_user(IN _username VARCHAR(255))
    SELECT username, fullname
    FROM user
    WHERE username = _username;

CREATE PROCEDURE get_friends(IN _username VARCHAR(255))
    SELECT username, fullname FROM (
        SELECT user_1 AS friend_name FROM friend WHERE user_2 = _username
        UNION
        SELECT user_2 FROM friend WHERE user_1 = _username
    ) AS f
    JOIN user ON friend_name = user.username;

/*
* Left join appends all tagging information onto each post before
* filtering based on the WHERE and IN conditions.
* Therefore, for every post record, we can check if the current
* user is tagged. If so, then we set `is_tagged` to TRUE.
*/
CREATE PROCEDURE get_thread(IN _thread_id INT, IN _username VARCHAR(255))
    SELECT th.thread_id AS thread_id, author, recipient, content, comment_count,
           IF(ta.tagged_user = _username, TRUE, FALSE) AS is_tagged
    FROM thread th
    LEFT JOIN tag ta ON th.thread_id = ta.thread_id AND tagged_user = _username
    LEFT JOIN (
        SELECT thread_id, COUNT(*) AS comment_count
        FROM comment WHERE thread_id = _thread_id
    ) AS c ON th.thread_id = c.thread_id
    WHERE th.thread_id = _thread_id;

CREATE PROCEDURE get_thread_comments_latest_last(IN _thread_id INT, IN _limit INT)
    SELECT commenter, content
    FROM (
        SELECT * FROM comment WHERE thread_id = _thread_id
        ORDER BY commented_on DESC
        LIMIT _limit
    ) AS c
    ORDER BY commented_on;

CREATE PROCEDURE get_likers(IN _thread_id INT)
    SELECT u.username AS username, fullname
    FROM liker l JOIN user u
    ON l.username = u.username
    WHERE thread_id = _thread_id
    ORDER BY username;

CREATE PROCEDURE get_dislikers(IN _thread_id INT)
    SELECT u.username AS username, fullname
    FROM disliker d JOIN user u
    ON d.username = u.username
    WHERE thread_id = _thread_id
    ORDER BY username;

CREATE PROCEDURE get_news_feed_threads(IN _username VARCHAR(255), IN _limit INT)
    SELECT th.thread_id AS thread_id, author, recipient, content,
           IFNULL(comment_count, 0) AS comment_count,
           IF(ta.tagged_user = _username, TRUE, FALSE) AS is_tagged
    FROM thread th
    LEFT JOIN tag ta ON th.thread_id = ta.thread_id AND tagged_user = _username
    LEFT JOIN (
        SELECT thread_id, COUNT(*) AS comment_count
        FROM comment
        GROUP BY thread_id
    ) AS c ON th.thread_id = c.thread_id
    WHERE recipient = _username
    OR recipient IN (
        SELECT user_1 AS friend_name FROM friend WHERE user_2 = _username
        UNION
        SELECT user_2 FROM friend WHERE user_1 = _username
    )
    OR th.thread_id IN (
        SELECT thread_id FROM tag WHERE tagged_user = _username
    )
    ORDER BY posted_on DESC
    LIMIT _limit;

CREATE PROCEDURE get_wall_threads(IN _username VARCHAR(255), IN _limit INT)
    SELECT th.thread_id AS thread_id, author, recipient, content,
           IFNULL(comment_count, 0) AS comment_count,
           IF(ta.tagged_user = _username, TRUE, FALSE) AS is_tagged
    FROM thread th
    LEFT JOIN tag ta ON th.thread_id = ta.thread_id AND tagged_user = _username
    LEFT JOIN (
        SELECT thread_id, COUNT(*) AS comment_count
        FROM comment
        GROUP BY thread_id
    ) AS c ON th.thread_id = c.thread_id
    WHERE recipient = _username
    OR th.thread_id IN (
        SELECT thread_id FROM tag WHERE tagged_user = _username
    )
    ORDER BY posted_on DESC
    LIMIT _limit;

CREATE PROCEDURE get_tagged_usernames(IN _thread_id INT)
    SELECT tagged_user FROM tag WHERE thread_id = _thread_id;

DELIMITER $$
CREATE TRIGGER validate_tag BEFORE INSERT ON tag
FOR EACH ROW BEGIN
    DECLARE is_friend BOOLEAN;
    SELECT COUNT(*) INTO is_friend FROM (
        SELECT user_1 FROM friend WHERE user_2 = NEW.tagged_user
        UNION
        SELECT user_2 FROM friend WHERE user_1 = NEW.tagged_user
    ) AS f
    WHERE user_1 IN (
        SELECT author FROM thread WHERE thread_id = NEW.thread_id
    );
    IF (is_friend = FALSE) THEN
        SIGNAL SQLSTATE '45000' SET message_text = 'Cannot tag non-friends';
    END IF;
END$$
CREATE PROCEDURE add_tag(IN _thread_id INT, IN _username VARCHAR(255))
BEGIN
    DECLARE is_friend BOOLEAN;
    SELECT COUNT(*) INTO is_friend FROM (
        SELECT user_1 FROM friend WHERE user_2 = _username
        UNION
        SELECT user_2 FROM friend WHERE user_1 = _username
    ) AS f
    WHERE user_1 IN (
        SELECT author FROM thread WHERE thread_id = _thread_id
    );
    IF (is_friend = TRUE) THEN
        INSERT INTO tag (thread_id, tagged_user) VALUES (_thread_id, _username);
    END IF;
END$$
DELIMITER ;

CREATE PROCEDURE remove_tag(IN _thread_id INT, IN _username VARCHAR(255))
    DELETE FROM tag WHERE thread_id = _thread_id AND tagged_user = _username;

CREATE PROCEDURE delete_thread(IN _thread_id INT, IN _username VARCHAR(255))
    DELETE FROM thread WHERE thread_id = _thread_id AND (author = _username OR recipient = _username);

CREATE PROCEDURE reply_to_thread(IN _thread_id INT, IN _username VARCHAR(255), IN _content TEXT(65535))
    INSERT INTO comment (thread_id, commenter, commented_on, content) VALUES (_thread_id, _username, NOW(), _content);

CREATE PROCEDURE like_thread(IN _thread_id INT, IN _username VARCHAR(255))
    INSERT INTO liker (username, thread_id) VALUES (_username, _thread_id);

CREATE PROCEDURE dislike_thread(IN _thread_id INT, IN _username VARCHAR(255))
    INSERT INTO disliker (username, thread_id) VALUES (_username, _thread_id);

CREATE PROCEDURE verify_credentials(IN _username VARCHAR(255), IN _password VARCHAR(255))
    SELECT COUNT(*) AS is_valid FROM user WHERE username = _username AND pwd = _password;

CREATE PROCEDURE user_exists(IN _username VARCHAR(255))
    SELECT COUNT(*) AS user_exists FROM user WHERE username = _username;

CREATE PROCEDURE add_user(IN _username VARCHAR(255), IN _fullname VARCHAR(255), IN _pwd VARCHAR(255))
    INSERT INTO user (username, fullname, pwd) VALUES (_username, _fullname, _pwd);

DELIMITER $$
CREATE PROCEDURE get_farmer_detail(IN _username VARCHAR(255))
BEGIN
    SET @rank := 0;
    SELECT username, xp, r.wealth, r.wealth_rank
    FROM farmer JOIN (
        SELECT (@rank := @rank + 1) AS wealth_rank, wealth
        FROM (
            SELECT DISTINCT wealth FROM farmer
        ) AS w
        ORDER BY wealth DESC
    ) AS r ON farmer.wealth = r.wealth
    WHERE username = _username;
END$$
DELIMITER ;