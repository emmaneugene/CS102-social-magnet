USE magnet;

INSERT INTO user (username, fullname, pwd) VALUES
("adam", "Adam Levine", "maroon5"),
("britney", "Britney Spears", "itsbritney"),
("charlie", "Charlie Puth", "attention"),
("danny", "Danny DeVito", "iasip"),
("elijah", "Elijah Wood", "mordor"),
("frank", "Frank Sinatra", "flymetothemoon");

INSERT INTO post (author, recipient, content) VALUES
("adam", "adam", "Hello, world!"),
("britney", "adam", "Don't know what you're talking about"),
("charlie", "charlie", "Where is City Farmer??"),
("charlie", "adam", "Who are you talking to?"),
("danny", "frank", "Look out, Fakebook! Social Magnet is going to get more users!")
("elijah", "elijah", "Had a great night with adam, britney, and @charkie");

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

INSERT INTO comment (content, post_id, commenter) VALUES
("Good job! You started programming.", 1, "charlie"),
("Bye!", 1, "elijah");
