USE magnet;

INSERT INTO user (username, fullname, pwd) VALUES
("adam",    "Adam Levine",    "maroon5"),
("britney", "Britney Spears", "itsbritney"),
("charlie", "Charlie Puth",   "attention"),
("danny",   "Danny DeVito",   "iasip"),
("elijah",  "Elijah Wood",    "mordor"),
("frank",   "Frank Sinatra",  "flymetothemoon");

INSERT INTO friend (user_1, user_2) VALUES
("adam",    "charlie"),
("adam",    "elijah"),
("britney", "charlie"),
("britney", "elijah"),
("charlie", "danny"),
("charlie", "frank"),
("danny",   "frank");

INSERT INTO thread (author, recipient, posted_on, content) VALUES
("adam",    "adam",    "2019-03-02 08:30:00", "Hello, world!"),
("adam",    "adam",    "2019-05-02 10:30:00", "I'm going crazy!!"),
("britney", "elijah",  "2019-05-15 20:30:00", "Don't know what you're talking about"),
("charlie", "charlie", "2019-09-03 20:30:00", "Where is City Farmer??"),
("charlie", "adam",    "2019-09-04 09:30:00", "Who are you talking to?"),
("danny",   "frank",   "2019-09-02 15:30:00", "Look out, Fakebook! Social Magnet is going to get more users!"),
# elijah and charlie are not friends, therefore the tag does not work.
("elijah",  "elijah",  "2019-09-15 08:30:00", "Had a great night with @adam, @britney, and @charlie"),
("adam",    "elijah",  "2019-10-02 12:30:00", "Where did you go?"),
# adam is spelt wrongly, therefore the tag does not work.
("britney", "charlie", "2019-10-03 13:30:00", "We should meet up again! @elijah @adsm"),
("britney", "britney", "2019-10-04 14:30:00", "I'm so lonely...");

INSERT INTO tag (thread_id, tagged_user) VALUES
(7, "adam"),
(7, "britney"),
(9, "elijah");

INSERT INTO comment (comment_num, thread_id, commenter, commented_on, content) VALUES
(1, 1, "charlie", "2019-03-02 16:30:00", "Good job! You started programming."),
(2, 1, "elijah",  "2019-03-04 12:15:00", "Bye!"),
(1, 2, "charlie", "2019-05-02 10:32:00", "Same!!! Too many things to do!"),
(1, 3, "elijah",  "2019-05-15 21:00:00", "Same here."),
(1, 4, "danny",   "2019-09-03 20:35:00", "I can't find it too!"),
(1, 6, "frank",   "2019-09-03 16:30:00", "Not sure about that."),
# More than 3 comments on one post
(1, 7, "adam",    "2019-09-15 10:00:00", "You were a blast!"),
(2, 7, "britney", "2019-09-15 15:00:00", "How did you guys wake up so early??"),
(3, 7, "adam",    "2019-09-15 16:15:00", "Early bird gets the worm!"),
(4, 7, "elijah",  "2019-09-15 16:30:00", "Maybe you shouldn't stay out too late!");


INSERT INTO liker (username, thread_id) VALUES
("adam",    3),
("britney", 3),
("britney", 7),
("charlie", 1),
("charlie", 5),
("danny",   4),
("elijah",  2);

INSERT INTO disliker (username, thread_id) VALUES
("adam",    3),
("adam",    4),
("britney", 4),
("elijah",  1);

INSERT INTO request (sender, recipient) VALUES
("adam",    "danny"),
("britney", "adam"),
("danny",   "britney");

INSERT INTO farmer (username, xp, wealth) VALUES
("adam",    1500,  3),
("britney", 2000,  1500),
("charlie", 4000,  1500),
("danny",   13000, 15000),
("elijah",  100,   1000),
("frank",   200,   500);

INSERT INTO plot (owner, plot_num, crop_name, time_planted, yield_of_crop, yield_stolen) VALUES
("adam", 1, "Papaya",        DATE_SUB(NOW(), INTERVAL  30 MINUTE), 75,  13),
("adam", 2, "Pumpkin",       DATE_SUB(NOW(), INTERVAL  29 MINUTE), 100, 20),
("adam", 3, "Watermelon",    DATE_SUB(NOW(), INTERVAL 480 MINUTE), 400, 20),
("adam", 4, "Papaya",        DATE_SUB(NOW(), INTERVAL  30 MINUTE), 75,  13),
("adam", 6, "Watermelon",    DATE_SUB(NOW(), INTERVAL 250 MINUTE), 400, 20),
("britney", 1, "Papaya",     DATE_SUB(NOW(), INTERVAL  29 MINUTE), 75,  13),
("britney", 2, "Pumpkin",    DATE_SUB(NOW(), INTERVAL  29 MINUTE), 100, 20),
("britney", 3, "Watermelon", DATE_SUB(NOW(), INTERVAL  29 MINUTE), 400, 20);

INSERT INTO stealing (victim, stolen_plot_num, stealer) VALUES
("adam", 1, "britney"),
("adam", 1, "charlie"),
("adam", 1, "danny"),
("adam", 2, "britney"),
("adam", 2, "charlie"),
("adam", 2, "danny"),
("adam", 2, "elijah"),
("adam", 3, "britney"),
("adam", 3, "charlie");

INSERT INTO inventory (owner, crop_name, quantity) VALUES
("adam", "Papaya", 1),
("adam", "Watermelon", 2);
