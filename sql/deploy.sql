-- drop database if exists and recreate it
DROP DATABASE IF EXISTS magnet;
CREATE DATABASE magnet;

USE magnet;
SET GLOBAL time_zone = '+00:00';

/*
 * |---------------|
 * | Create Tables |
 * |---------------|
 */

/*
 * SOCIAL MAGNET
 */
CREATE TABLE user (
    username VARCHAR(255) BINARY NOT NULL,
    fullname VARCHAR(255)        NOT NULL,
    pwd      VARCHAR(255) BINARY NOT NULL,
    PRIMARY KEY (username)
);

CREATE TABLE thread (
    thread_id   INT                 NOT NULL AUTO_INCREMENT,
    author      VARCHAR(255) BINARY NOT NULL,
    recipient   VARCHAR(255) BINARY NOT NULL,
    posted_on   TIMESTAMP           NOT NULL,
    content     TEXT(65535)         NOT NULL,
    PRIMARY KEY (thread_id),
    FOREIGN KEY (author)    REFERENCES user (username),
    FOREIGN KEY (recipient) REFERENCES user (username)
);

CREATE TABLE tag (
    thread_id     INT                 NOT NULL,
    tagged_user   VARCHAR(255) BINARY NOT NULL,
    PRIMARY KEY (thread_id, tagged_user),
    FOREIGN KEY (thread_id)   REFERENCES thread (thread_id) ON DELETE CASCADE,
    FOREIGN KEY (tagged_user) REFERENCES user (username)
);

CREATE TABLE friend (
    user_1 VARCHAR(255) BINARY NOT NULL,
    user_2 VARCHAR(255) BINARY NOT NULL,
    PRIMARY KEY (user_1, user_2),
    FOREIGN KEY (user_1) REFERENCES user (username),
    FOREIGN KEY (user_2) REFERENCES user (username)
);

CREATE TABLE comment (
    comment_num    INT                 NOT NULL,
    thread_id      INT                 NOT NULL,
    commenter      VARCHAR(255) BINARY NOT NULL,
    commented_on   TIMESTAMP           NOT NULL,
    content        TEXT(65535)         NOT NULL,
    PRIMARY KEY (comment_num, thread_id),
    FOREIGN KEY (thread_id) REFERENCES thread (thread_id) ON DELETE CASCADE,
    FOREIGN KEY (commenter) REFERENCES user (username)
);

CREATE TABLE liker (
    username   VARCHAR(128) BINARY NOT NULL,
    thread_id  INT                 NOT NULL,
    PRIMARY KEY (username, thread_id),
    FOREIGN KEY (username)  REFERENCES user (username),
    FOREIGN KEY (thread_id) REFERENCES thread (thread_id) ON DELETE CASCADE
);

CREATE TABLE disliker (
    username   VARCHAR(128) BINARY NOT NULL,
    thread_id  INT                 NOT NULL,
    PRIMARY KEY (username, thread_id),
    FOREIGN KEY (username)  REFERENCES user (username),
    FOREIGN KEY (thread_id) REFERENCES thread (thread_id) ON DELETE CASCADE
);

CREATE TABLE request (
    sender    VARCHAR(255) BINARY NOT NULL,
    recipient VARCHAR(255) BINARY NOT NULL,
    PRIMARY KEY (sender, recipient),
    FOREIGN KEY (sender)    REFERENCES user (username),
    FOREIGN KEY (recipient) REFERENCES user (username)
);

/*
 * CITY FARMER
 */
CREATE TABLE farmer (
    username VARCHAR(255) BINARY NOT NULL,
    xp       INT                 NOT NULL,
    wealth   INT                 NOT NULL,
    PRIMARY KEY (username),
    FOREIGN KEY (username) REFERENCES user (username)
);

CREATE TABLE ranking (
    rank_level  INT          NOT NULL AUTO_INCREMENT,
    rank_min_xp INT          NOT NULL,
    rank_name   VARCHAR(255) NOT NULL,
    num_plots   INT          NOT NULL,
    PRIMARY KEY (rank_level)
);

CREATE TABLE crop (
    crop_name           VARCHAR(255) NOT NULL,
    cost                INT          NOT NULL,
    minutes_to_harvest  INT          NOT NULL,
    xp                  INT          NOT NULL,
    min_yield           INT          NOT NULL,
    max_yield           INT          NOT NULL,
    sale_price          INT          NOT NULL,
    PRIMARY KEY (crop_name)
);

CREATE TABLE plot (
    owner          VARCHAR(255) BINARY NOT NULL,
    plot_num       INT                 NOT NULL,
    crop_name      VARCHAR(255)        NOT NULL,
    time_planted   TIMESTAMP           NOT NULL,
    yield_of_crop  INT                 NOT NULL,
    yield_stolen   INT                 NOT NULL,
    PRIMARY KEY (owner, plot_num),
    FOREIGN KEY (owner)     REFERENCES farmer (username),
    FOREIGN KEY (crop_name) REFERENCES crop (crop_name)
);

CREATE TABLE stealing (
    victim          VARCHAR(255) BINARY NOT NULL,
    stolen_plot_num INT                 NOT NULL,
    stealer         VARCHAR(255) BINARY NOT NULL,
    PRIMARY KEY (victim, stolen_plot_num, stealer),
    FOREIGN KEY (victim, stolen_plot_num) REFERENCES plot (owner, plot_num) ON DELETE CASCADE,
    FOREIGN KEY (stealer)                 REFERENCES farmer (username)
);

CREATE TABLE inventory (
    owner     VARCHAR(255) BINARY NOT NULL,
    crop_name VARCHAR(255)        NOT NULL,
    quantity  INT                 NOT NULL,
    PRIMARY KEY (owner, crop_name),
    FOREIGN KEY (owner)     REFERENCES farmer (username),
    FOREIGN KEY (crop_name) REFERENCES crop (crop_name)
);

CREATE TABLE gift (
    gifter    VARCHAR(255) BINARY NOT NULL,
    giftee    VARCHAR(255) BINARY NOT NULL,
    crop_name VARCHAR(255)        NOT NULL,
    gifted_on TIMESTAMP           NOT NULL,
    PRIMARY KEY (gifter, giftee, crop_name, gifted_on),
    FOREIGN KEY (gifter)    REFERENCES farmer (username),
    FOREIGN KEY (giftee)    REFERENCES farmer (username),
    FOREIGN KEY (crop_name) REFERENCES crop (crop_name)
);

/*
 * |-----------------------|
 * | Load City Farmer Data |
 * |-----------------------|
 */

INSERT INTO crop (crop_name, cost, minutes_to_harvest, xp, min_yield, max_yield, sale_price) VALUES
("Papaya",     20, 30,  8,  75, 100, 15),
("Pumpkin",    30, 60,  5,  5,  200, 20),
("Sunflower",  40, 120, 20, 15, 20,  40),
("Watermelon", 50, 240, 1,  5,  800, 10);

INSERT INTO ranking (rank_min_xp, rank_name, num_plots) VALUES
(0,     "Novice",      5),
(1000,  "Apprentice",  6),
(2500,  "Journeyman",  7),
(5000,  "Grandmaster", 8),
(12000, "Legendary",   9);

/*
 * |-------------------|
 * | Stored Procedures |
 * |-------------------|
 */

/*
 * LOADING USERS
 */
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

CREATE PROCEDURE get_friends_of_friend_with_common(IN _me VARCHAR(255), IN _friend VARCHAR(255))
    SELECT f_friends.friend_name, fullname, NOT ISNULL(my_friends.friend_name) mutual FROM (
        SELECT user_1 AS friend_name FROM friend WHERE user_2 = _friend
        UNION
        SELECT user_2 FROM friend WHERE user_1 = _friend
    ) AS f_friends LEFT JOIN (
        SELECT user_1 AS friend_name FROM friend WHERE user_2 = _me
        UNION
        SELECT user_2 FROM friend WHERE user_1 = _me
    ) AS my_friends ON f_friends.friend_name = my_friends.friend_name
    LEFT JOIN user u ON f_friends.friend_name = u.username
    WHERE f_friends.friend_name <> _me;

/*
 * CREDENTIALS
 */
CREATE PROCEDURE verify_credentials(IN _username VARCHAR(255), IN _password VARCHAR(255))
    SELECT COUNT(*) AS is_valid FROM user WHERE username = _username and pwd = sha1(_password);

CREATE PROCEDURE user_exists(IN _username VARCHAR(255))
    SELECT COUNT(*) AS user_exists FROM user WHERE username = _username;

/*
 * UPDATING USERS
 */
DELIMITER $$
CREATE PROCEDURE add_user(IN _username VARCHAR(255), IN _fullname VARCHAR(255), IN _pwd VARCHAR(255))
BEGIN
    INSERT INTO user (username, fullname, pwd) VALUES (_username, _fullname, sha1(_pwd));
    INSERT INTO farmer (username, xp, wealth) VALUES (_username, 0, 50);
END$$
DELIMITER ;

CREATE PROCEDURE unfriend(IN _current_user VARCHAR(255), IN _to_remove VARCHAR(255))
    DELETE FROM friend WHERE (user_1 = _current_user AND user_2 = _to_remove) OR (user_2 = _current_user AND user_1 = _to_remove);

/*
 * FRIEND REQUESTS
 */
DELIMITER $$
CREATE TRIGGER validate_request BEFORE INSERT ON request
FOR EACH ROW BEGIN
    SET @is_friend := (
        SELECT COUNT(*) FROM (
            SELECT user_1 FROM friend WHERE user_1 = NEW.sender AND user_2 = NEW.recipient
            UNION
            SELECT user_2 FROM friend WHERE user_2 = NEW.sender AND user_1 = NEW.recipient
        ) AS f
    );
    IF (@is_friend = TRUE) THEN
        SIGNAL SQLSTATE '45000' SET message_text = 'Cannot request existing friend.';
    END IF;
END$$
DELIMITER ;

CREATE PROCEDURE make_request(IN _sender VARCHAR(255), IN _recipient VARCHAR(255))
    INSERT INTO request (sender, recipient) VALUES (_sender, _recipient);

CREATE PROCEDURE get_requests(IN _recipient VARCHAR(255))
    SELECT sender FROM request WHERE recipient = _recipient;

DELIMITER $$
CREATE PROCEDURE accept_request(IN _sender VARCHAR(255), IN _recipient VARCHAR(255))
BEGIN
    IF _sender < _recipient THEN
        INSERT INTO friend (user_1, user_2) VALUES (_sender, _recipient);
    ELSE
        INSERT INTO friend (user_1, user_2) VALUES (_recipient, _sender);
    END IF;
    DELETE FROM request WHERE sender = _sender AND recipient = _recipient;
END$$
DELIMITER ;

CREATE PROCEDURE reject_request(IN _sender VARCHAR(255), IN _recipient VARCHAR(255))
    DELETE FROM request WHERE sender = _sender AND recipient = _recipient;

/*
 * LOADING THREADS
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
    WHERE th.thread_id = _thread_id ;

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

/*
 * UPDATING THREADS
 */
DELIMITER $$
CREATE PROCEDURE add_thread_return_id(IN _author VARCHAR(255), IN _recipient VARCHAR(255), IN _content TEXT(65535))
BEGIN
    INSERT INTO thread (author, recipient, posted_on, content) VALUES (_author, _recipient, NOW(), _content);
    SELECT LAST_INSERT_ID() AS new_id;
END$$
DELIMITER ;

CREATE PROCEDURE delete_thread(IN _thread_id INT, IN _username VARCHAR(255))
    DELETE FROM thread WHERE thread_id = _thread_id AND (author = _username OR recipient = _username);

DELIMITER $$
CREATE PROCEDURE reply_to_thread(IN _thread_id INT, IN _username VARCHAR(255), IN _content TEXT(65535))
BEGIN
    SET @next_num = (
        SELECT MAX(comment_num) + 1 FROM comment
        WHERE thread_id = _thread_id
    );
    INSERT INTO comment (comment_num, thread_id, commenter, commented_on, content) VALUES (@next_num, _thread_id, _username, NOW(), _content);
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE toggle_like_thread(IN _thread_id INT, IN _username VARCHAR(255))
BEGIN
	SET @liked := (
        SELECT COUNT(*)
	    FROM liker l NATURAL JOIN user u
	    WHERE l.thread_id = _thread_id
	    AND l.username = _username
    );
	IF (@liked = 0) THEN
		INSERT INTO liker (username, thread_id) VALUES (_username, _thread_id);
	ELSE
	    DELETE FROM liker WHERE username = _username AND thread_id = _thread_id;
   	END IF;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE toggle_dislike_thread(IN _thread_id INT, IN _username VARCHAR(255))
BEGIN
	SET @disliked := (
        SELECT COUNT(*)
	    FROM disliker l NATURAL JOIN user u
	    WHERE l.thread_id = _thread_id
	    AND l.username = _username
    );
	IF (@disliked = 0) THEN
		INSERT INTO disliker (username, thread_id) VALUES (_username, _thread_id);
	ELSE
	    DELETE FROM disliker WHERE username = _username AND thread_id = _thread_id;
   	END IF;
END$$
DELIMITER ;

/*
 * TAGGING THREADS
 */
DELIMITER $$
CREATE TRIGGER validate_tag BEFORE INSERT ON tag
FOR EACH ROW BEGIN
    SET @is_friend := (
        SELECT COUNT(*) FROM (
            SELECT user_1 FROM friend WHERE user_2 = NEW.tagged_user
            UNION
            SELECT user_2 FROM friend WHERE user_1 = NEW.tagged_user
        ) AS f
        WHERE user_1 IN (
            SELECT author FROM thread WHERE thread_id = NEW.thread_id
        )
    );
    IF (@is_friend = FALSE) THEN
        SIGNAL SQLSTATE '45000' SET message_text = 'Cannot tag non-friends.';
    END IF;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE add_tag(IN _thread_id INT, IN _username VARCHAR(255))
BEGIN
    SET @is_friend = (
        SELECT COUNT(*) FROM (
            SELECT user_1 FROM friend WHERE user_2 = _username
            UNION
            SELECT user_2 FROM friend WHERE user_1 = _username
        ) AS f
        WHERE user_1 IN (
            SELECT author FROM thread WHERE thread_id = _thread_id
        )
    );
    IF (@is_friend = TRUE) THEN
        INSERT INTO tag (thread_id, tagged_user) VALUES (_thread_id, _username);
    END IF;
END$$
DELIMITER ;

CREATE PROCEDURE remove_tag(IN _thread_id INT, IN _username VARCHAR(255))
    DELETE FROM tag WHERE thread_id = _thread_id AND tagged_user = _username;

/*
 * LOADING FARM DETAILS
 */
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

CREATE PROCEDURE get_plots(IN _username VARCHAR(255))
    SELECT plot_num, p.crop_name, time_planted, yield_of_crop, yield_stolen,
    cost, minutes_to_harvest, xp, min_yield, max_yield, sale_price
    FROM plot p
    JOIN crop c ON p.crop_name = c.crop_name
    WHERE owner = _username;

CREATE PROCEDURE get_inventory(IN _username VARCHAR(255))
    SELECT crop_name, quantity FROM inventory
    WHERE owner = _username
    ORDER BY crop_name;

/*
 * UPDATING FARM DETAILS
 */
DELIMITER $$
CREATE TRIGGER verify_crop_num BEFORE INSERT ON plot
FOR EACH ROW BEGIN
    SET @max_plots := (
        SELECT num_plots FROM (
            SELECT MAX(rank_level) max_rank_level FROM ranking
            WHERE rank_min_xp < (
                SELECT xp FROM farmer WHERE username = NEW.owner
            )
        ) AS r1 JOIN ranking r2
        ON r1.max_rank_level = r2.rank_level
    );
    IF (NEW.plot_num > @max_plots) THEN
        SIGNAL SQLSTATE '45000' SET message_text = 'You do not have access to so many plots.';
    END IF;
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER verify_crop_yield BEFORE INSERT ON plot
FOR EACH ROW BEGIN
    SET @min_yield_of_crop := ( SELECT min_yield FROM crop WHERE crop_name = NEW.crop_name);
    SET @max_yield_of_crop := ( SELECT max_yield FROM crop WHERE crop_name = NEW.crop_name);
    IF (NEW.yield_of_crop > @max_yield_of_crop OR NEW.yield_of_crop < @min_yield_of_crop) THEN
        SIGNAL SQLSTATE '45000' SET message_text = 'Invalid crop yield.';
    END IF;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE plant_crop_auto_yield(IN _username VARCHAR(255), IN _plot_num INT, IN _crop_name VARCHAR(255))
BEGIN
    -- Generate random yield
    SET @min_yield_of_crop := ( SELECT min_yield FROM crop WHERE crop_name = _crop_name );
    SET @max_yield_of_crop := ( SELECT max_yield FROM crop WHERE crop_name = _crop_name );
    SET @random_yield := FLOOR(RAND() * (@max_yield_of_crop - @min_yield_of_crop + 1)) + @min_yield_of_crop;
    -- Update plot
    INSERT INTO plot (owner, plot_num, crop_name, time_planted, yield_of_crop, yield_stolen) VALUES 
    (_username, _plot_num, _crop_name, NOW(), @random_yield, 0);
    -- Update inventory
    SET @new_quantity := ( SELECT quantity - 1 FROM inventory WHERE owner = _username AND crop_name = _crop_name );
    IF (@new_quantity = 0) THEN
        DELETE FROM inventory WHERE owner = _username AND crop_name = _crop_name;
    ELSE
        UPDATE inventory SET quantity = @new_quantity WHERE owner = _username AND crop_name = _crop_name;
    END IF;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE plant_crop(IN _username VARCHAR(255), IN _plot_num INT, IN _crop_name VARCHAR(255), IN _yield INT)
BEGIN
    -- Update plot
    INSERT INTO plot (owner, plot_num, crop_name, time_planted, yield_of_crop, yield_stolen) VALUES 
    (_username, _plot_num, _crop_name, NOW(), _yield, 0);
    -- Update inventory
    SET @new_quantity := ( SELECT quantity - 1 FROM inventory WHERE owner = _username AND crop_name = _crop_name );
    IF (@new_quantity = 0) THEN
        DELETE FROM inventory WHERE owner = _username AND crop_name = _crop_name;
    ELSE
        UPDATE inventory SET quantity = @new_quantity WHERE owner = _username AND crop_name = _crop_name;
    END IF;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE clear_plot_update_wealth(IN _username VARCHAR(255), IN _plot_num INT)
BEGIN
    SET @is_wilted := (
        SELECT COUNT(*) FROM plot p
        JOIN crop c ON p.crop_name = c.crop_name
        WHERE owner = _username AND plot_num = _plot_num
        AND TIMESTAMPDIFF(MINUTE, p.time_planted, NOW()) >= minutes_to_harvest * 2
    );
    SET @farmer_wealth := (
        SELECT wealth FROM farmer WHERE username = _username
    );
    IF @is_wilted THEN 
        IF @farmer_wealth < 5 THEN
            SIGNAL SQLSTATE '45000' SET message_text = 'Insufficient funds to clear wilted crop.';
        ELSE
            UPDATE farmer SET wealth = @farmer_wealth - 5 WHERE username = _username;
            DELETE FROM plot WHERE owner = _username AND plot_num = _plot_num;
        END IF;
    ELSE
        DELETE FROM plot WHERE owner = _username AND plot_num = _plot_num;
    END IF;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE harvest(IN _username VARCHAR(255))
BEGIN
    -- get total sale price and xp gained
    SELECT IFNULL(SUM(sale_price * (yield_of_crop - yield_stolen)), 0),
    IFNULL(SUM(xp * (yield_of_crop - yield_stolen)), 0) INTO @total_sale, @total_xp FROM (
        SELECT plot_num FROM plot p
        JOIN crop c ON p.crop_name = c.crop_name
        WHERE owner = _username
        -- check if ready to harvest
        AND TIMESTAMPDIFF(MINUTE, p.time_planted, NOW()) >= minutes_to_harvest
        -- check if wilted
        AND TIMESTAMPDIFF(MINUTE, p.time_planted, NOW()) < minutes_to_harvest * 2
    ) AS h JOIN plot p ON h.plot_num = p.plot_num AND p.owner = _username
    JOIN crop c ON p.crop_name = c.crop_name;

    -- clear plots that were harvested
    DELETE p FROM plot p
    JOIN (
        SELECT plot_num FROM plot p
        JOIN crop c ON p.crop_name = c.crop_name
        WHERE owner = _username
        -- check if ready to harvest
        AND TIMESTAMPDIFF(MINUTE, p.time_planted, NOW()) >= minutes_to_harvest
        -- check if wilted
        AND TIMESTAMPDIFF(MINUTE, p.time_planted, NOW()) < minutes_to_harvest * 2
    ) AS h ON p.plot_num = h.plot_num AND p.owner = _username;

    -- add sale and xp to user account
    UPDATE farmer SET wealth = wealth + @total_sale, xp = xp + @total_xp
    WHERE username = _username;
END$$
DELIMITER ;

/*
 * STORE
 */

CREATE PROCEDURE get_store_items()
    SELECT * 
    FROM crop
    ORDER BY crop_name;

DELIMITER $$
CREATE PROCEDURE purchase_crop(IN _username VARCHAR(255), IN _crop_name VARCHAR(255), IN amount INT)
BEGIN
    IF amount <= 0 THEN
        SIGNAL SQLSTATE '45000' SET message_text = 'Please choose a quantity bigger than 0.';
    END IF;

	SET @farmer_wealth := (
        SELECT wealth FROM farmer WHERE username = _username
    );
    
    SET @purchase_cost := (
		SELECT cost * amount FROM crop WHERE crop_name = _crop_name
	);
    
    IF @farmer_wealth < @purchase_cost THEN
        SELECT FALSE AS purchase_success;
	ELSE
		UPDATE farmer SET wealth = wealth - @purchase_cost WHERE username = _username;
        SET @crop_exist := (
			SELECT count(*) FROM inventory WHERE owner = _username AND crop_name = _crop_name
		);
		IF @crop_exist THEN 
			UPDATE inventory SET quantity = quantity + amount WHERE owner = _username;
        ELSE
			INSERT INTO inventory (owner, crop_name, quantity) VALUES 
			(_username, _crop_name, amount);
		END IF;
        SELECT TRUE AS purchase_success;
	END IF;
END$$
DELIMITER ;