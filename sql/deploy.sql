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
    is_gift     BOOLEAN             NOT NULL DEFAULT FALSE,
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
    username   VARCHAR(255) BINARY NOT NULL,
    thread_id  INT                 NOT NULL,
    PRIMARY KEY (username, thread_id),
    FOREIGN KEY (username)  REFERENCES user (username),
    FOREIGN KEY (thread_id) REFERENCES thread (thread_id) ON DELETE CASCADE
);

CREATE TABLE disliker (
    username   VARCHAR(255) BINARY NOT NULL,
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
    stealer         VARCHAR(255) BINARY NOT NULL,
    victim          VARCHAR(255) BINARY NOT NULL,
    plot_num INT                 NOT NULL,
    PRIMARY KEY (victim, plot_num, stealer),
    FOREIGN KEY (victim, plot_num) REFERENCES plot (owner, plot_num) ON DELETE CASCADE,
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
    sender      VARCHAR(255) BINARY NOT NULL,
    recipient   VARCHAR(255) BINARY NOT NULL,
    gifted_on   DATE                NOT NULL,
    gifted_time TIME                NOT NULL,
    crop_name   VARCHAR(255)        NOT NULL,
    -- If null, represents the gift has been accepted or rejected.
    thread_id   INT,
    PRIMARY KEY (sender, recipient, gifted_on),
    FOREIGN KEY (sender)    REFERENCES farmer (username),
    FOREIGN KEY (recipient) REFERENCES farmer (username),
    FOREIGN KEY (crop_name) REFERENCES crop (crop_name),
    FOREIGN KEY (thread_id) REFERENCES thread (thread_id) ON DELETE SET NULL
);

/*
 * |-----------------------|
 * | Load City Farmer Data |
 * |-----------------------|
 */

INSERT INTO crop
    (crop_name,  cost, minutes_to_harvest, xp, min_yield, max_yield, sale_price) VALUES
    ("Papaya",     20,                 30,  8,        75,       100,         15),
    ("Pumpkin",    30,                 60,  5,         5,       200,         20),
    ("Sunflower",  40,                120, 20,        15,        20,         40),
    ("Watermelon", 50,                240,  1,         5,       800,         10);

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
CREATE PROCEDURE user_exists(IN _username VARCHAR(255))
    SELECT COUNT(*) user_exists FROM user WHERE username = _username;

CREATE PROCEDURE login(IN _username VARCHAR(255), IN _pwd VARCHAR(255))
    SELECT username, fullname FROM user
    WHERE username = _username AND pwd = SHA1(_pwd);

DELIMITER $$
CREATE PROCEDURE register(IN _username VARCHAR(255), IN _fullname VARCHAR(255), IN _pwd VARCHAR(255))
BEGIN
    INSERT INTO user (username, fullname, pwd) VALUES (_username, _fullname, SHA1(_pwd));
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

DELIMITER $$
CREATE PROCEDURE make_request(IN _sender VARCHAR(255), IN _recipient VARCHAR(255))
BEGIN
    IF (_sender = _recipient) THEN
        SIGNAL SQLSTATE '45000' SET message_text = 'Cannot request self.';
    ELSE
        INSERT INTO request (sender, recipient) VALUES (_sender, _recipient);
    END IF;
END$$
DELIMITER ;

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

CREATE PROCEDURE get_all_thread_comments_latest_last(IN _thread_id INT)
    SELECT commenter, content FROM comment
    WHERE thread_id = _thread_id
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
            -- Load tagging information
            IF(tag.tagged_user = _username, TRUE, FALSE) AS is_tagged,
            -- Load comment count
            com.comment_count
    FROM thread th
    LEFT JOIN tag ON th.thread_id = tag.thread_id AND tagged_user = _username
    LEFT JOIN (
        SELECT thread_id, IFNULL(COUNT(*), 0) comment_count
        FROM comment
        GROUP BY thread_id
    ) AS com ON th.thread_id = com.thread_id
    -- No gift threads
    WHERE NOT th.is_gift AND (
        -- Include threads posted to my friends
        recipient IN (
            SELECT user_1 AS friend_name FROM friend WHERE user_2 = _username
            UNION
            SELECT user_2 FROM friend WHERE user_1 = _username
        )
        -- Same as posts to my wall
        -- Include threads posted to me
        OR recipient = _username
        -- Include threads I am tagged in
        OR th.thread_id IN (
            SELECT thread_id FROM tag WHERE tagged_user = _username
        )
    )
    ORDER BY posted_on DESC
    LIMIT _limit;

CREATE PROCEDURE get_wall_threads(IN _username VARCHAR(255), IN _limit INT)
    SELECT th.thread_id AS thread_id, author, recipient, content,
            -- Load tagging information
            IF(tag.tagged_user = _username, TRUE, FALSE) AS is_tagged,
            -- Load comment count
            com.comment_count
    FROM thread th
    LEFT JOIN tag ON th.thread_id = tag.thread_id AND tagged_user = _username
    LEFT JOIN (
        SELECT thread_id, IFNULL(COUNT(*), 0) comment_count
        FROM comment
        GROUP BY thread_id
    ) AS com ON th.thread_id = com.thread_id
    -- No gift threads
    WHERE NOT th.is_gift AND (
        -- Include threads posted to me
        recipient = _username
        -- Include threads I am tagged in
        OR th.thread_id IN (
            SELECT thread_id FROM tag WHERE tagged_user = _username
        )
    -- Only gift threads posted to me
    ) OR th.is_gift AND recipient = _username
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
        SELECT IFNULL(MAX(comment_num), 0) + 1 FROM comment
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
 * LOADING FARMER DETAILS
 */
DELIMITER $$
CREATE PROCEDURE get_farmer_detail(IN _username VARCHAR(255))
BEGIN
    SET @rank := 0;
    SELECT u.username, u.fullname, xp, r.wealth, r.wealth_rank
    FROM farmer f LEFT JOIN (
        SELECT (@rank := @rank + 1) AS wealth_rank, wealth
        FROM (
            -- Get all possible wealth levels of friends
            SELECT DISTINCT wealth FROM farmer
            WHERE username = _username OR username IN (
                SELECT user_1 FROM friend WHERE user_2 = _username
                UNION
                SELECT user_2 FROM friend WHERE user_1 = _username
            )
        ) AS w
        ORDER BY wealth DESC
    ) AS r ON f.wealth = r.wealth
    JOIN user u ON f.username = u.username
    WHERE u.username = _username;
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
 * UPDATING FARMER DETAILS
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
        SIGNAL SQLSTATE '45000' SET message_text = 'Plot inaccessible.';
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

/*
 * Ensures stealing records for a given plot are reset when the plot is cleared.
 */
DELIMITER $$
CREATE TRIGGER clear_steal_record BEFORE DELETE ON plot
FOR EACH ROW BEGIN
    DELETE FROM stealing
    WHERE victim = OLD.owner AND plot_num = OLD.plot_num;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE plant_crop_auto_yield(IN _username VARCHAR(255), IN _plot_num INT, IN _crop_name VARCHAR(255))
BEGIN
    -- Update inventory
    SET @quantity := ( SELECT IFNULL(SUM(quantity), 0) FROM inventory WHERE owner = _username AND crop_name = _crop_name );
    IF (@quantity = 0) THEN
        SIGNAL SQLSTATE '45000' SET message_text = 'Not enough seeds in inventory.';
    ELSEIF (@quantity = 1) THEN
        DELETE FROM inventory WHERE owner = _username AND crop_name = _crop_name;
    ELSE
        UPDATE inventory SET quantity = @quantity - 1 WHERE owner = _username AND crop_name = _crop_name;
    END IF;

    -- Generate random yield
    SET @min_yield_of_crop := ( SELECT min_yield FROM crop WHERE crop_name = _crop_name );
    SET @max_yield_of_crop := ( SELECT max_yield FROM crop WHERE crop_name = _crop_name );
    SET @random_yield := FLOOR(RAND() * (@max_yield_of_crop - @min_yield_of_crop + 1)) + @min_yield_of_crop;
    -- Update plot
    INSERT INTO plot (owner, plot_num, crop_name, time_planted, yield_of_crop, yield_stolen) VALUES
    (_username, _plot_num, _crop_name, NOW(), @random_yield, 0);
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE clear_plot(IN _username VARCHAR(255), IN _plot_num INT)
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
    DROP TEMPORARY TABLE IF EXISTS harvested_crop;

    CREATE TEMPORARY TABLE harvested_crop
    SELECT plot_num FROM plot p
    JOIN crop c ON p.crop_name = c.crop_name
    WHERE owner = _username
    -- Check if ready to harvest
    AND TIMESTAMPDIFF(MINUTE, p.time_planted, NOW()) >= minutes_to_harvest
    -- Check if wilted
    AND TIMESTAMPDIFF(MINUTE, p.time_planted, NOW()) < minutes_to_harvest * 2;

    -- Get total sale price and xp gained
    SELECT IFNULL(SUM(sale_price * (yield_of_crop - yield_stolen)), 0),
    IFNULL(SUM(xp * (yield_of_crop - yield_stolen)), 0) INTO @total_sale, @total_xp
    FROM harvested_crop h
    JOIN plot p ON h.plot_num = p.plot_num AND p.owner = _username
    JOIN crop c ON p.crop_name = c.crop_name;

    -- Clear plots that were harvested
    DELETE p FROM plot p
    JOIN harvested_crop h ON p.plot_num = h.plot_num AND p.owner = _username;

    -- Add sale and xp to user account
    UPDATE farmer SET wealth = wealth + @total_sale, xp = xp + @total_xp
    WHERE username = _username;

    DROP TEMPORARY TABLE harvested_crop;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE steal(IN _stealer VARCHAR(255), IN _victim VARCHAR(255))
BEGIN
    DROP TEMPORARY TABLE IF EXISTS stolen_crop;

    CREATE TEMPORARY TABLE stolen_crop
    -- Generate random quantity of stolen crops
    SELECT @yield_stolen := LEAST(
        -- Limit stolen yield to the remaining yield available
        FLOOR(yield_of_crop * 0.2) - yield_stolen,
        -- Random yield of percentage ~ [1, 5] rounded down, with minimum 1 yield
        GREATEST(1, FLOOR((FLOOR(RAND() * 5) + 1) * yield_of_crop / 100))
    ) yield_stolen,
    -- Get the XP gained from yield stolen
    @yield_stolen * c.xp xp_gained,
    -- Get the wealth gained from yield stolen
    @yield_stolen * c.sale_price wealth_gained,
    p.plot_num, c.crop_name FROM (
        -- Set of harvestable crops
        SELECT plot_num FROM plot p
        JOIN crop c ON p.crop_name = c.crop_name
        WHERE owner = _victim
        -- Check if ready to harvest
        AND TIMESTAMPDIFF(MINUTE, p.time_planted, NOW()) >= minutes_to_harvest
        -- Check if wilted
        AND TIMESTAMPDIFF(MINUTE, p.time_planted, NOW()) < minutes_to_harvest * 2
    ) AS h JOIN plot p ON h.plot_num = p.plot_num AND p.owner = _victim
    JOIN crop c ON p.crop_name = c.crop_name
    -- Only allow crops that were not previously stolen
    WHERE p.plot_num NOT IN (
        SELECT plot_num FROM stealing
        WHERE stealer = _stealer AND victim = _victim
    );

    -- Update victim's plots
    UPDATE plot p
    JOIN stolen_crop s ON p.plot_num = s.plot_num AND p.owner = _victim
    SET p.yield_stolen = p.yield_stolen + s.yield_stolen;

    -- Update stealer's XP and wealth
    SELECT IFNULL(SUM(wealth_gained), 0), IFNULL(SUM(xp_gained), 0) INTO @total_wealth_gained, @total_xp_gained FROM stolen_crop;
    UPDATE farmer
    SET
        wealth = wealth + @total_wealth_gained,
        xp = xp + @total_xp_gained
    WHERE username = _stealer;

    -- Add stealing record
    INSERT INTO stealing (stealer, victim, plot_num)
    SELECT _stealer, _victim, plot_num FROM stolen_crop;

    -- Return all stolen crops classified by crop name
    SELECT crop_name, SUM(yield_stolen) quantity, SUM(xp_gained) total_xp_gained, SUM(wealth_gained) total_wealth_gained FROM stolen_crop
    GROUP BY crop_name
    HAVING SUM(yield_stolen) > 0
    ORDER BY crop_name;

    DROP TEMPORARY TABLE stolen_crop;
END$$
DELIMITER ;

/*
 * GIFTING
 */

CREATE PROCEDURE get_gift_count_today(IN _username VARCHAR(255))
    SELECT COUNT(*) gift_count FROM gift
    WHERE sender = _username AND gifted_on = CURDATE();

CREATE PROCEDURE sent_gift_today(IN _sender VARCHAR(255), IN _recipient VARCHAR(255))
    SELECT COUNT(*) sent FROM gift
    WHERE sender = _sender AND recipient = _recipient AND gifted_on = CURDATE();

DELIMITER $$
CREATE PROCEDURE send_gift(IN _sender VARCHAR(255), IN _recipient VARCHAR(255), IN _crop_name VARCHAR(255))
BEGIN
    -- Add the corresponding gift thread.
    INSERT INTO thread (author, recipient, posted_on, is_gift, content) VALUES
        (_sender, _recipient, NOW(), TRUE,
        CONCAT("Here is a bag of ", LOWER(_crop_name), " seeds for you. - City Farmers"));
    -- Add the gift record with the corresponding gift thread ID
    INSERT INTO gift (sender, recipient, gifted_on, gifted_time, crop_name, thread_id) VALUES
        (_sender, _recipient, CURDATE(), CURTIME(), _crop_name, LAST_INSERT_ID());
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE accept_gifts(IN _username VARCHAR(255))
BEGIN
    -- Add quantity of gift to inventory
    UPDATE inventory i
    JOIN (
        SELECT crop_name, COUNT(*) quantity FROM gift
        WHERE recipient = _username AND thread_id IS NOT NULL
        GROUP BY crop_name
    ) g ON i.crop_name = g.crop_name
    SET i.quantity = i.quantity + g.quantity
    WHERE owner = _username;

    -- Add new crops to inventory
    INSERT INTO inventory (owner, crop_name, quantity)
        SELECT _username, crop_name, COUNT(*) FROM gift
        WHERE recipient = _username AND thread_id IS NOT NULL
        GROUP BY crop_name
        HAVING crop_name NOT IN (
            SELECT crop_name FROM inventory WHERE owner = _username
        );

    -- Delete gift threads of current user
    DELETE th FROM thread th
    JOIN gift g ON th.thread_id = g.thread_id AND g.recipient = _username;
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
CREATE PROCEDURE purchase_crop(IN _username VARCHAR(255), IN _crop_name VARCHAR(255), IN _amount INT)
BEGIN
    IF _amount <= 0 THEN
        SIGNAL SQLSTATE '45000' SET message_text = 'Please choose a quantity bigger than 0.';
    END IF;

    SET @farmer_wealth := (
        SELECT wealth FROM farmer WHERE username = _username
    );

    SET @purchase_cost := (
        SELECT cost * _amount FROM crop WHERE crop_name = _crop_name
    );

    IF @farmer_wealth < @purchase_cost THEN
        SELECT FALSE AS purchase_success;
    ELSE
        UPDATE farmer SET wealth = wealth - @purchase_cost WHERE username = _username;
        SET @crop_exist := (
            SELECT count(*) FROM inventory WHERE owner = _username AND crop_name = _crop_name
        );
        IF @crop_exist THEN
            UPDATE inventory SET quantity = quantity + _amount WHERE owner = _username AND crop_name = _crop_name;
        ELSE
            INSERT INTO inventory (owner, crop_name, quantity) VALUES
            (_username, _crop_name, _amount);
        END IF;
        SELECT TRUE AS purchase_success;
    END IF;
END$$
DELIMITER ;
