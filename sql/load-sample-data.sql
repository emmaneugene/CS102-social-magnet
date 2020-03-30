USE magnet;

INSERT INTO user (username, fullname, pwd) VALUES
-- testGetUser
("adam",    "Adam Levine",    SHA1("maroon5")),
-- testGetNoRequests
("britney", "Britney Spears", SHA1("itsbritney")),
("charlie", "Charlie Puth",   SHA1("attention")),
("danny",   "Danny DeVito",   SHA1("iasip")),
("elijah",  "Elijah Wood",    SHA1("mordor")),
("frank",   "Frank Sinatra",  SHA1("flymetothemoon")),
-- testGetNoFriends, testGetRequestUsernames
("gary",    "Gary Oldman",    SHA1("alone")),
-- testUnfriend
("howard",  "Howard Duck",    SHA1("quack")),
("icarus",  "Icarus",         SHA1("flytoohigh"));

INSERT INTO friend (user_1, user_2) VALUES
-- testGetFriends
("adam",    "charlie"),
-- testGetFriendsOfFriendNoCommon
("adam",    "elijah"),
("britney", "elijah"),
-- testGetFriendsOfFriend
("charlie", "danny"),
("charlie", "frank"),
("danny",   "elijah"),
("danny",   "frank");

INSERT INTO thread (thread_id, author, recipient, posted_on, content) VALUES
-- testSetCommentsOnlyThree, testToggleLikeThread
(1,  "adam",    "adam",    "2019-03-02 08:30:00", "Hello, world!"),
-- testSetCommentsFewerThanThree, testSetDislikersNone
(2,  "adam",    "adam",    "2019-05-02 10:30:00", "I'm going crazy!!"),
-- testSetCommentsZero, testSetLikers, testToggleDislikeThread
(3,  "britney", "elijah",  "2019-05-15 20:30:00", "Don't know what you're talking about"),
-- testSetLikersNone, testSetDislikers, testReplyToThread
(4,  "charlie", "charlie", "2019-09-03 20:30:00", "Where is City Farmer??"),
-- testDeleteThreadNoAttributes
(5,  "charlie", "adam",    "2019-09-04 09:30:00", "Who are you talking to?"),
-- testDeleteThreadWithLikesDislikesCommentsTags
(6,  "danny",   "frank",   "2019-09-02 15:30:00", "Look out, @charlie! Social Magnet is going to get more users!"),
-- testGetThread, testSetCommentsMoreThanThree, testGetTaggedUsernames
(7,  "elijah",  "elijah",  "2019-09-15 08:30:00", "Had a great night with @adam, @britney, and @charlie"),
-- testGetTaggedUsernamesNone, testReplyToThreadMoreThanThree
(8,  "adam",    "elijah",  "2019-10-02 12:30:00", "Where did you go?"),
-- testDeleteUnauthorized
(9,  "britney", "charlie", "2019-10-03 13:30:00", "We should meet up again! @elijah @adsm"),
-- testAddAndRemoveTags, testReplyToThreadNoComments
(10, "britney", "britney", "2019-10-04 14:30:00", "I'm so lonely...");

INSERT INTO tag (thread_id, tagged_user) VALUES
-- testDeleteThreadWithLikesDislikesCommentsTags
(6, "charlie"),
-- testGetTaggedUsernames
(7, "adam"),
(7, "britney"),
(9, "elijah");

INSERT INTO comment (thread_id, comment_num, commenter, commented_on, content) VALUES
-- testSetCommentsOnlyThree
(1, 1, "charlie", "2019-03-02 16:30:00", "Good job! You started programming."),
(1, 2, "elijah",  "2019-03-04 12:15:00", "Bye!"),
(1, 3, "charlie", "2019-03-05 12:15:00", "Goodbye!"),
-- testSetCommentsFewerThanThree
(2, 1, "charlie", "2019-05-02 10:32:00", "Same!!! Too many things to do!"),
-- testReplyToThread
(4, 1, "danny",   "2019-09-03 20:35:00", "I can't find it too!"),
-- testDeleteThreadWithLikesDislikesCommentsTags
(6, 1, "frank",   "2019-09-03 16:30:00", "Not sure about that."),
-- testSetCommentsMoreThanThree
(7, 1, "adam",    "2019-09-15 10:00:00", "You were a blast!"),
(7, 2, "britney", "2019-09-15 15:00:00", "How did you guys wake up so early??"),
(7, 3, "adam",    "2019-09-15 16:15:00", "Early bird gets the worm!"),
(7, 4, "elijah",  "2019-09-15 16:30:00", "Maybe you shouldn't stay out too late!"),
-- testReplyToThreadMoreThanThree
(8, 1, "adam",    "2019-09-15 10:00:00", "First time!"),
(8, 2, "britney", "2019-09-15 15:00:00", "Second."),
(8, 3, "adam",    "2019-09-15 16:15:00", "Third time!"),
(8, 4, "elijah",  "2019-09-15 16:30:00", "Stop!!");


INSERT INTO liker (username, thread_id) VALUES
-- testSetLikers
("adam",    3),
("britney", 3),
("britney", 7),
("charlie", 1),
-- testDeleteThreadWithLikesDislikesCommentsTags
("charlie", 6),
("elijah",  2);

INSERT INTO disliker (username, thread_id) VALUES
("adam",    3),
-- testSetDislikers
("adam",    4),
("britney", 4),
("elijah",  1),
-- testDeleteThreadWithLikesDislikesCommentsTags
("frank",   6);

INSERT INTO request (sender, recipient) VALUES
("charlie", "gary"),
("adam",    "gary");

INSERT INTO farmer (username, xp, wealth) VALUES
-- testGetFarmer, testPlantAndClearCrops
("adam",    11000,  15000),
-- testGetPlots, testGetInventory
("britney", 2000,  1500),
-- testHarvest
("charlie", 2450,  1500),
-- testSteal
("danny",   1300, 1500),
("elijah",  100,   1000),
("frank",   200,   3);

INSERT INTO plot (owner, plot_num, crop_name, time_planted, yield_of_crop, yield_stolen) VALUES
-- testPlantAndClearCrops
("adam",    1, "Papaya",     DATE_SUB(NOW(), INTERVAL  30 MINUTE), 75,  0),
("adam",    2, "Pumpkin",    DATE_SUB(NOW(), INTERVAL  25 MINUTE), 100, 0),
("adam",    3, "Watermelon", DATE_SUB(NOW(), INTERVAL 480 MINUTE), 400, 0),
("adam",    6, "Watermelon", DATE_SUB(NOW(), INTERVAL 250 MINUTE), 400, 0),
-- testGetPlots
("britney", 1, "Papaya",     DATE_SUB(NOW(), INTERVAL  25 MINUTE), 75,  0),
("britney", 2, "Pumpkin",    DATE_SUB(NOW(), INTERVAL  25 MINUTE), 100, 0),
("britney", 3, "Watermelon", DATE_SUB(NOW(), INTERVAL  25 MINUTE), 400, 0),
-- testHarvest
("charlie", 1, "Papaya",     DATE_SUB(NOW(), INTERVAL  30 MINUTE), 75,  0),
("charlie", 2, "Pumpkin",    DATE_SUB(NOW(), INTERVAL  25 MINUTE), 100, 0),
("charlie", 3, "Watermelon", DATE_SUB(NOW(), INTERVAL 480 MINUTE), 400, 0),
("charlie", 4, "Papaya",     DATE_SUB(NOW(), INTERVAL  30 MINUTE), 75,  0),
("charlie", 6, "Watermelon", DATE_SUB(NOW(), INTERVAL 250 MINUTE), 400, 0),
-- testSteal (no more yield to steal)
("danny", 1,   "Papaya",     DATE_SUB(NOW(), INTERVAL  30 MINUTE), 75,  15),
-- testSteal (1 to 3 yield (1-5%))
("elijah", 1,  "Papaya",     DATE_SUB(NOW(), INTERVAL  30 MINUTE), 75,  0),
-- testSteal (4 to 20 yield (1-5%))
("elijah", 2,  "Watermelon", DATE_SUB(NOW(), INTERVAL 250 MINUTE), 400, 0);

INSERT INTO inventory (owner, crop_name, quantity) VALUES
-- tesGetInventory
("britney", "Papaya", 1),
("britney", "Watermelon", 2);

INSERT INTO gift (sender, recipient, gifted_on, crop_name) VALUES
("adam",    "britney", "2019-12-03", "Sunflower"),
("adam",    "elijah",  "2019-12-03", "Sunflower"),
("adam",    "frank",   "2019-12-03", "Sunflower"),
("charlie", "adam",    CURDATE(),    "Watermelon"),
("charlie", "britney", CURDATE(),    "Papaya"),
("charlie", "danny",   CURDATE(),    "Papaya"),
("charlie", "elijah",  CURDATE(),    "Papaya"),
("charlie", "frank",   CURDATE(),    "Papaya");
