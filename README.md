# CS102 Final Project

Social Magnet, City Farmers

**Expected Effort**: *40 hours per person*

**Due Dates**: *8 Apr 11pm*

**Weightage**: *20% of final grade*

**Members**:

* Bryan Lee
* Charlie Angriawan
* Emmanuel Oh
* Zhi An Yuen

## Description

Social networking sites such as Facebook have become ubiquitous platforms for communication. A social network is a means of keeping track of a set of people and the relationships between them.

For this project, we are going to write a simple social networking application called **Social Magnet**.

In Social Magnet, a member has a wall containing his personal information, and the last few messages to the wall.

All members are automatically players of an integrated farming game called **City Farmers**. In the game, the farmer (i.e. the player) manages a virtual farm by planting, growing and harvesting virtual crops.

## Usage

### Tomcat Server

#### Compilation of servlet

Under the `server/social-magnet-service` directory, run `mvn clean package`.

```bash
$ mvn clean package
```

#### Loading database credentials

Confidential database credentials should be stored as a properties file on the web server.

After compiling the web servlet, create `my.properties` and place it in  `target/magnet/WEB-INF`.

A sample of `my.properties`:

```properties
db.user=admin
db.pass=mysupersecretpassword
```

#### Running the server

Once the servlet is compiled and the credentials file is placed under `WEB-INF`, copy the `magnet` directory into `$CATALINA_BASE/webapps`, and restart the Tomcat server.

> `$CATALINA_BASE` varies based on your installation of Tomcat.

The server should now run under `localhost:8080/magnet`. Send a GET request to the index link to check if the server is running. If the setup is successful, the message should appear:

```
Social Magnet Service running!
```

### Client Application

#### Compilation

Use `compile.bat` or `compile.sh` to compile the application.

#### Running

Use `run.bat` or `run.sh` to run the application.

By default, the Social Magnet Service should be running on `localhost:8080/magnet`.

However, should the server be on another location, simply modify `run.bat` or `run.sh` to update the server URL.

#### Testing

Because the tests require the database to be reset, ensure that database credentials are stored in `scripts/env.sh` or `scripts/env.bat` before running tests.

> Look at `scripts/env-sample.bat` or `scripts/env-sample.sh` for an example.

Then, `scripts/test.bat` or `scripts/test.sh` will use those credentials to reset the database, and run the necessary tests.

## Requirements

View our [requirements](docs/requirements.md).

## Design Considerations

View our [design considerations](docs/design_considerations.md).
