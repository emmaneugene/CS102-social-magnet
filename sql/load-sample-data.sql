USE magnet;

INSERT INTO user (username, fullname, pwd) VALUES
("adam", "Adam Levine", "maroon5"),
("britney", "Britney Spears", "itsbritney"),
("charlie", "Charlie Puth", "attention"),
("danny", "Danny DeVito", "iasip"),
("elijah", "Elijah Wood", "mordor"),
("frank", "Frank Sinatra", "flymetothemoon");

INSERT INTO post (author, recipient, content, posted_on) VALUES
("adam", "adam", "Hello, world!", "2019-03-02 15:30:00"),
("britney", "elijah", "Don't know what you're talking about", "2019-03-02 15:30:00"),
("charlie", "charlie", "Where is City Farmer??", "2019-03-02 15:30:00"),
("charlie", "adam", "Who are you talking to?", "2019-03-02 15:30:00"),
("danny", "frank", "Look out, Fakebook! Social Magnet is going to get more users!", "2019-03-02 15:30:00"),
("elijah", "elijah", "Had a great night with adam, britney, and @charkie", "2019-03-02 15:30:00");

INSERT INTO tag (post_id, tagged_user) VALUES
(6, "adam"),
(6, "britney");

INSERT INTO friend (user_1, user_2) VALUES
("adam", "charlie"),
("adam", "elijah"),
("britney", "charlie"),
("britney", "elijah"),
("charlie", "danny"),
("danny", "frank");

INSERT INTO comment (content, post_id, commenter, commented_on) VALUES
("Good job! You started programming.", 1, "charlie", "2019-03-02 16:30:00"),
("Bye!", 1, "elijah", "2019-03-02 16:30:00"),
("Same here.", 2, "elijah", "2019-03-02 16:30:00"),
("I can't find it too!", 3, "danny", "2019-03-02 16:30:00"),
("Not sure about that.", 5, "frank", "2019-03-02 16:30:00");

INSERT INTO likes (username, post_id) VALUES
("adam", 3),
("britney", 6),
("charlie", 5),
("elijah", 2),
("charlie", 1);

INSERT INTO dislikes (username, post_id) VALUES
("danny", 3),
("britney", 4),
("adam", 4),
("elijah", 1);

INSERT INTO requests (sender, recipient) VALUES
("adam", "danny"),
("britney", "adam"),
("danny", "britney");