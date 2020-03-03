USE magnet;

INSERT INTO user (username, fullname, pwd) VALUES
("adam",    "Adam Levine",    "maroon5"),
("britney", "Britney Spears", "itsbritney"),
("charlie", "Charlie Puth",   "attention"),
("danny",   "Danny DeVito",   "iasip"),
("elijah",  "Elijah Wood",    "mordor"),
("frank",   "Frank Sinatra",  "flymetothemoon");

INSERT INTO post (author, recipient, posted_on, content) VALUES
("adam",    "adam",    "2019-03-02 15:30:00", "Hello, world!"),
("adam",    "adam",    "2019-03-02 15:30:00", "I'm going crazy!!"),
("britney", "elijah",  "2019-03-02 15:30:00", "Don't know what you're talking about"),
("charlie", "charlie", "2019-03-02 15:30:00", "Where is City Farmer??"),
("charlie", "adam",    "2019-03-02 15:30:00", "Who are you talking to?"),
("danny",   "frank",   "2019-03-02 15:30:00", "Look out, Fakebook! Social Magnet is going to get more users!"),
("elijah",  "elijah",  "2019-03-02 15:30:00", "Had a great night with adam, britney, and @charkie");

INSERT INTO tag (post_id, tagged_user) VALUES
(7, "adam"),
(7, "britney");

INSERT INTO friend (user_1, user_2) VALUES
("adam",    "charlie"),
("adam",    "elijah"),
("britney", "charlie"),
("britney", "elijah"),
("charlie", "danny"),
("danny",   "frank");

INSERT INTO comment (post_id, commenter, commented_on, content) VALUES
(1, "charlie", "2019-03-02 16:30:00", "Good job! You started programming."),
(1, "elijah",  "2019-03-02 16:30:00", "Bye!"),
(3, "elijah",  "2019-03-02 16:30:00", "Same here."),
(4, "danny",   "2019-03-02 16:30:00", "I can't find it too!"),
(6, "frank",   "2019-03-02 16:30:00", "Not sure about that.");

INSERT INTO likes (username, post_id) VALUES
("adam",    3),
("britney", 3),
("britney", 7),
("charlie", 1),
("charlie", 5),
("danny",   4),
("elijah",  2);

INSERT INTO dislikes (username, post_id) VALUES
("adam",    3),
("adam",    4),
("britney", 4),
("elijah",  1);

INSERT INTO requests (sender, recipient) VALUES
("adam",    "danny"),
("britney", "adam"),
("danny",   "britney");
