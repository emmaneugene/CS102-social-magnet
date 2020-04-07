# CS102 Final Project

Social Magnet, City Farmers

## Functional Requirements

Social networking sites such as Facebook have become ubiquitous platforms for communication. A social network is a means of keeping track of a set of people and the relationships between them.

For this project, we are going to write a simple social networking application called **Social Magnet**.

In Social Magnet, a member has a wall containing his personal information, and the last few messages to the wall. 

All members are automatically players of an integrated farming game called **City Farmers**. In the game, the farmer (i.e. the player) manages a virtual farm by planting, growing and harvesting virtual crops.

> **Note**: You are expected to perform input validation, and show an appropriate error message if the user enters an invalid option.

## Entities

### Social Magnet

The social network that you are building.

A person has to register as a member of Social Magnet in order to participate in the network.

Each member has a wall and shares posts with his friends.

In the Social Magnet network, relationships are **reciprocal** (i.e. bi-directional). 

* If Alice is a friend of Bob, Bob is a friend of Alice as well.

#### My Wall

A member's wall displays:

* 5 **most-recent** posts
* Full name
* Rank (e.g. novice)
* Wealth ranking among his friends.
  * This is the amount of gold the member has amassed in the City Farmers game.

Your recent posts will not be visible to non-friend members. However, your full name, rank and wealth ranking will be visible to everyone.

The owner or his friends can post to the wall.

Each post on "My Wall" shows the number of likes and dislikes.

```
== Social Magnet :: My Wall ==
About kenny
Full Name: Kenny LEE
Novice Farmer, 2nd richest


1. Abi: Let's meet up for lunch?
  1.1. Kenny: Sure. When?
[ 0 likes, 0 dislike ]

2. charlie: Here is a bag pineapple seeds for you. – City Farmers
[ 0 likes, 0 dislike ]

[M]ain | [T]hread | [A]ccept Gift | [P]ost >
```

#### Post

A post provides a member with an outlet to share every aspect of his life with his friends.

A member can either post on his own wall or a friend's wall.

When posting, a member can tag his friend(s) in his post so that this message appears on multiple walls, using the `@` sign. For example, if a member posts the following message on his wall:

```
Until now, @evelyn, @adeline, @marilyn's laptops have bitten the
dust. See, OOP is tough on laptops as well :/
```

The following post will automatically appear on the wall of the member who made this post, as well as the walls of `evelyn`, `adeline` and `marilyn`.

```
Until now, evelyn, adeline, marilyn's laptops have bitten the dust. 
See, OOP is tough on laptops as well :/
```

In the event that an invalid username is tagged, then the text will appear as it is (with the `@` sign).

For example, if `ninjaturtle` is not a valid username, the following message will be rendered as it is:

```
@ninjaturtle is mutable
```

* All valid user tags will be rendered without `@`, and all invalid user tags will be rendered with the `@` sign.

When a post is removed, the associated information (who likes and dislikes) and the replies are deleted permanently.

Tagging will work only for the original post. **A member cannot tag other members in replies.**

For a post in which multiple friends are tagged, it should appear on the wall of all friends who are tagged.

* A reply from any wall will be visible for everyone who sees the posts (either from the **News Feed** or **My Wall**).
* Anyone tagged in the post can remove the post and the associated replies.

Deleting a post (using the **kill** functionality) will only affect the user's wall.

* If person A tags person B in a post to his own wall, when person B deletes the post from his wall (i.e. person B's wall), he will only delete it from his own.

When a post is deleted, all the associated replies are deleted too.

### City Farmer

This is the game that is made available to all members of Social Magnet.

#### Farmer

In the City Farmer game, a member is a farmer.

At the start, a farmer will be given 50 gold.

A farmer has rank.

* Rank is an indication of his seniority in the game, and is based on the experience points that he has gained through farming.
* As the farmer gets promoted in rank, he will unlock more plot of land for farming.

#### Land

Each farmer has his own plot(s) of land.

The farmer will use a bag of **seeds** to plant his **crop** on a plot of land.

* The farmer buys the bags of seeds from **My Seed Store**.
* The farmer may only plant one type of seed at any one time on a plot of land.

#### Crop

For each plot of land, only 1 crop can be planted at a time.

Each crop will grow to maturity in a fixed interval, T (in minutes).

The farmer is given another T minutes to harvest the crop, after which the crop wilt.

For example, if the current time is 1pm, (assuming that the T value for papaya is 30 minutes):

* The plot of land planted with a papaya seed will mature at 1:30pm.
* The farmer has to harvest it between 1:30pm and 2pm.
* After 2pm, the crop will wilt.

When the crop has wilt, the farmer will have to pay 5 gold to clear the plot.

* The farmer can only plant seeds on a cleared plot.

A bag of seeds planted on a plot of land will generate different yield (i.e. different number of units of produce).

Each unit of produce will generate a fixed number of experience points (XP), which may differ for different produce types. 

* For example, one bag of papaya seeds produces 10 units of papaya, and each papaya generates 5 XP.
  * The total XP gained by the player will be 50 XP (10 X 5).

The formula to calculate the total number of units produced: 

$$
\text{# units} \sim U(\mathrm{yield_{min}},\mathrm{yield_{max}})
$$

#### Visit Friend

In the City Farmer game, a farmer can visit a friend's farmland.

During the visit, he can **steal** a small quantity (up to 5% inclusive) of his friend's produce (if they are not harvested yet).

* For each plot of land, generate a random number(between 1 and 5) to determine the amount available for theft.

The total amount of produce available that all your friends (the total) can steal from you is 20% of your produce.

In the event that the remaining quantity available for theft is lesser than the random number generated, use the smaller of the two values.

## Data Files

The following data files will be provided:

* `rank.csv`
* `crop.csv`

`rank.csv`

| Field | Description                                                  |
| ----- | ------------------------------------------------------------ |
| Rank  | The description of the rank                                  |
| XP    | The amount of experience points required to obtain this rank |
| Plot  | The number of plots of land owned at this rank               |

`crop.csv`

| Field     | Description                                                  |
| --------- | ------------------------------------------------------------ |
| Name      | The name of the crop                                         |
| Cost      | The purchase price of **a bag of seed** of the specified crop |
| Time      | The time it takes for the crop to mature (to be ready for harvest) |
| XP        | The experience points gained **for 1 unit** of the crop produced |
| Min Yield | The minimum unit of the crop produced                        |
| Max Yield | The maximum unit of the crop produced                        |
| Sell      | The selling price **for 1 unit** of the crop produced        |

## Sample Run

### Social Magnet

#### Welcome Page

The application starts by showing the player the welcome page.

An error message will be displayed for invalid inputs. After which, the user will press enter, and the page will be redisplayed.

```
== Social Magnet :: Welcome ==
Good morning, anonymous!
1. Register
2. Login 
3. Exit
Enter your choice > 4
Please enter a choice between 1 & 3!
```

#### Registration

If the user enters `1`, the application prompts the player to register:

```
== Social Magnet :: Registration ==
Enter your username > kenny
Enter your Full name> Kenny LEE
Enter your password > secret
Confirm your password > secret
kenny, your account is successfully created!
```

* A username should only contain alphanumeric characters.
* The Welcome Page will be re-displayed.

#### Login

If the user enters `2`, the application prompts the player for his/her username and password:

```
== Social Magnet :: Login ==
Enter your username > kenny
Enter your password > secret
```

* The application displays an error if either the username or password is entered incorrectly
* **It is not a requirement to mask the password**

#### Main Menu

The player sees the following menu when he logs in.

```
== Social Magnet :: Main Menu ==
Welcome, Kenny LEE!
1. News Feed
2. My Wall
3. My Friends
4. City Farmers
5. Logout
Enter your choice >
```

#### News Feed

The user sees the **latest 5** (the ones with the latest timestamp) posts on his own wall or his friend's wall. 

##### Cases

| Poster | Posted on whose wall              | Display on my newsfeed |
| ------ | --------------------------------- | ---------------------- |
| Me     | Mine                              | Yes                    |
| Me     | Friend                            | Yes                    |
| Friend | Mine                              | Yes                    |
| Friend | His friend (not a friend of mine) | No                     |

The latest status update should be at the top.

* If there are more than 3 replies, show the latest 3 (the replies with the latest timestamp).

```
== Social Magnet :: News Feed ==
1 tommi: Got a hair cut yesterday and look as awesome as before 
[ 1 like, 3 dislikes ]
  1.1 apple: then why need to cut 
  1.2 tommi: Easier to maintain the awesomeness!
	
2 adrian: "A user interface is like a joke. If you have to explain it, it's not that good."
[ 10 likes, 0 dislike ]
  2.2 duke: Explain your face.
  2.3 charlie: LOL
  2.4 david: LOL
[M]ain | [T]hread > T1
```

* The user enters `T<ID>` to read the specific thread (point 6).
* There is no way that a user can view older threads.

#### View a thread

The system displays:

* the thread (original status post and replies)
* who likes or dislikes the post

```
== Social Magnet :: View a Thread ==
tommi: Got a hair cut yesterday and look as awesome as before
  1.1 apple: then why need to cut 
  1.2 tommi: Easier to maintain the awesomeness!

Who likes this post:
  1. Jerry LEE (jerri)
  
Who dislikes this post:
  1. Effendy LEE (effendy)
  2. Daisy LEE (daisy)
  3. Timothy LEE (timothy)
[M]ain | [K]ill | [R]eply | [L]ike | [D]islike > 	
```

* The user selects `R` to reply to the post.
* The user selects `L/D` to like/dislike the post.
* The user selects `K` to remove any post from his own wall.
  * `K` should not show up if a thread is not on the user's wall

#### My Wall

My wall contains the **last 5 status updates** of the current user and the following information:

* User's full name
* His rank (e.g. novice)
* His wealth (the amount of gold he has in the City Farmers game)
* Ranking among his friends

```
== Social Magnet :: My Wall ==
About kenny
Full Name: Kenny LEE
Novice Farmer, 2nd richest

1 Tommi: Got a hair cut yesterday and look as awesome as before
  1.1 Apple: then why need to cut 
  1.2 Tommi: Easier to maintain the awesomeness!

2 charlie: Here is a bag pineapple seeds for you. – City Farmers 
[M]ain | [T]hread | [A]ccept Gift | [P]ost >
```

* The user enters `T<ID>` to display a specific message thread. 
* The user sees the details of thread (who likes/dislikes the thread), and is able to reply to the thread (See point 6).
* To accept a City Farmer gift sent by a friend, the user will enter `A`.
  * The bag of seeds will be added to the player inventory. 
  * You can only accept gifts that are posted to your wall.
  * This will accept **all** gifts that are posted to your wall (and have yet to be accepted before).
* To post to the wall, the user will enter `P`. 

#### My Friends

The user sees his/her friends in the list.

```
== Social Magnet :: My Friends ==
Welcome, Kenny LEE!

My Friends:
1. apple
2. billy
3. charlie

My Requests:
4. danny
5. effendy
[M]ain | [U]nfriend | re[Q]uest | [A]ccept | [R]eject | [V]iew > A4
danny is now your friend.
```

* The user selects `M` to go back to the main menu.
* The user selects `U<ID>` to remove a friend from his/her circle.
* The user enters `Q` to send a friend request.

```
== Social Magnet :: My Friends ==
Welcome, Kenny LEE!

My Friends:
1. apple
2. billy
3. charlie

My Requests:
4. danny
5. effendy
[M]ain | [U]nfriend | re[Q]uest | [A]ccept | [R]eject | [V]iew > Q
Enter the username > powerpuffgirl
A friend request is sent to powerpuffgirl.
```

* The user enters `A<ID>` to accept a Friend Request.
* The user enters `R<ID>` to reject a Friend Request.
* The user enters `V` to view a friend's wall.

```
== Social Magnet :: My Friends ==
Welcome, Kenny LEE!

My Friends:
1. apple
2. billy
3. charlie

My Requests:
4. danny
5. effendy
[M]ain | [U]nfriend | re[Q]uest | [A]ccept | [R]eject | [V]iew > V
Enter the username > zac
```

```
== Social Magnet :: Zac's Wall ==
Welcome, Kenny LEE!

About Zac
Full Name: Chek Arc LEE
Novice Farmer, 2nd richest

1 Zac: My resolution for 2011 is 1024 x 768.
[ 0 like, 0 dislike ]
  1.1 brendon: -_- 

Zac's Friend
1. Brendon LEE (Common Friend)
2. Eddy LEE
3. Fred LEE
4. Gary LEE

[M]ain | [T]hread | [P]ost > 	P
Enter your message >  sm-jacked by kenny. :) 
Message successfully posted.
```

### City Farmers

This page provides access to the user's virtual farmland application called City Farmers.

```
== Social Magnet :: City Farmers ==
Welcome, Kenny LEE!
Title: Novice             Gold: 800 gold

1. My Farmland
2. My Store
3. My Inventory
4. Visit Friend
5. Send Gift
[M]ain | Enter your choice >
```

#### My Farmland

This page provides access to the user's virtual farmland.

```
== Social Magnet :: City Farmers :: My Farmland ==
Welcome, Kenny LEE!
Title: Novice             Gold: 800 gold

You have 5 plots of land.
1. Papaya    [  wilted  ]
2. <empty>
3. Papaya    [###-------] 37%
4. Papaya    [##########] 100%
5. Papaya    [##########] 100%
[M]ain | City [F]armers | [P]lant | [C]lear | [H]arvest > H 
You have harvested 243 Papaya for 1944 XP and 4860 gold.
```

* The number of `#` to display is the growth progress. This number is rounded down to the nearest ten percent. For example, 37% or 32% will display the same number of `#` signs (3 of them).

The user selects `P<ID>` to plant a crop.

```
== Social Magnet :: City Farmers :: My Farmland ==
Welcome, Kenny LEE!
Title: Novice             Gold: 800 gold

You have 5 plots of land.
1. <empty>
2. <empty>
3. Papaya    [##########] 100%
3. Papaya    [##########] 100%
3. Papaya    [##########] 100%
[M]ain | City [F]armers | [P]lant | [C]lear | [H]arvest > P1

Select the crop:
1. Papaya     
2. Pumpkin  
[M]ain | City [F]armers | Select Choice > 1
Papaya planted on plot 1.
```

The user selects `C<ID>` to clear away wilted crop.

The user selects `H` to harvest the crop. Crops ready for harvest on any plot of land will be harvested.

The user selects `F` to go back to the City Farmers menu (See point 9), and M to go back main menu.

#### My Store

The store sells all seeds that the user needs.

Once a bag of seed is bought, you cannot refund it back for gold.

```
== Social Magnet :: City Farmers :: My Store ==
Welcome, Kenny LEE!
Title: Novice             Gold: 800 gold

Seeds Available:
1. Papaya     – 20 gold
Harvest in: 30 mins
XP Gained: 175
2. Pumpkin    - 30 gold 
Harvest in: 60 mins
XP Gained: 200
3. Sunflower  – 40 gold
Harvest in: 2 hours
XP Gained: 300
4. Watermelon – 50 gold
Harvest in: 4 hours
XP Gained: 500

[M]ain | City [F]armers | Select choice > 1
Enter quantity > 10
20 bags of seeds purchased for 200 gold.
```

#### My Inventory

It lists the variety of seeds and the associated quantity

```
== Social Magnet :: City Farmers :: My Inventory ==
Welcome, Kenny LEE!
Title: Novice             Gold: 800 gold

My Seeds:
1. 20 Bags of Papaya     
2. 50 Bags of Pumpkin  

[M]ain | City [F]armers | Select choice > F
```

#### Visit

During a visit, you can choose to **steal** your friend's harvest if they are ready for harvest (i.e. 100% growth).

All crops that are ready for harvest (100% growth) are eligible for theft.

```
== Social Magnet :: City Farmers :: Visit Friend ==
Welcome, Kenny LEE!
Title: Novice             Gold: 800 gold

My Friends:
1. Effendy TAN (effendy)
2. Daisy LEE (daisy)
3. Timothy LIM (timothy)

[M]ain | [C]ity Farmer Main | Select choice > 1

Name: Effendy TAN
Title: Novice
Gold: 12,000 
1. Pumpkin    [######----] 60%
2. Sunflower  [##--------] 23%
3. Papaya     [##########] 100%
4. Pumpkin    [#---------] 30%
5. Watermelon [----------] 5%
[M]ain | City [F]amers | [S]teal > S
You have successfully stolen 4 papayas for 32 XP, and 300 gold.
```

#### Send a Gift

In the game, you can send up to 5 free gifts per day to your friends but not yourself. It will not cost you any gold.

Once a friend accepts it, the bag of seeds will be added to his inventory.

```
== Social Magnet :: City Farmers :: Send a Gift ==
Welcome, Kenny LEE!
Title: Novice             Gold: 800 gold

Gifts Available:
1. 1 Bag of Papaya Seeds
2. 1 Bag of Pumpkin Seeds
3. 1 Bag of Sunflower Seeds
4. 1 Bag of Watermelon Seeds
[R]eturn to main | Select choice > 1
Send to> apple,billy
Gift posted to your friends' wall.
```

## Database Guidelines

Only MySQL is allowed (no MS Access, MS SQL Server, Oracle etc).

Initialize the relevant tables with data from the CSV files.

Prepare a human-readable script called `deploy.sql` and place it in the `sql` directory of your submission.

* `deploy.sql` should contain your `CREATE` and `INSERT` SQL commands to create the necessary database and tables which your application uses.
* The instructor will run the script on their laptops during assessment using this command...

```bash
$ mysql -u root -p < deploy.sql
```

...so do ensure that this script works.

Include the necessary JDBC driver(s) in the lib directory of your submission.

Include instructions in a `README.txt` file in the `sql` directory on what other configuration files should be changed in order for your application to work with the database.

* You will be **penalized** if unclear or incorrect instructions provided results in your database not being set up properly on the instructors’ laptop, or your application not being able to connect to the database correctly. 

The database name should be `magnet`. A sample `deploy.sql` is shown below:

```mysql
-- drop database if exists and recreate it
drop database if exists magnet;
create database magnet;

-- use this database to create and populate the tables
use magnet;

-- create commands (this is just an example)
CREATE TABLE contact (
    contact_id  INT NOT NULL AUTO_INCREMENT,
    given_name  VARCHAR(128) NOT NULL,
    family_name VARCHAR(128) NOT NULL,
    email       VARCHAR(128) NOT NULL,
    PRIMARY KEY(contact_id)
);

-- insert commands (needed for your application to work)
INSERT INTO contact (contact_id, given_name, family_name, email)
VALUES
(1, 'Dukester', 'Lee', 'geek@smu.edu.sg');
```

