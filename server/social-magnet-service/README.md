# Social Magnet Web Service

A web service that provides a REST API for the Social Magnet application.

Written in Java using [Jersey](https://github.com/eclipse-ee4j/jersey) as the primary REST framework.

Most of the database logic was adapted from the initial Social Magnet application.

Initially, the client application would query the database directly through database access objects, which would call stored procedures on the database.

With the web service, an extra layer of abstraction is introduced. The client application no longer has to query the database directly. Instead, it uses a REST client to send HTTP requests to this web service, which then returns the relevant information. The database will only be accessed by this web service.

## Usage

### Compilation of servlet

Under this directory, `server/social-magnet-service`, run `mvn clean package`.

```bash
$ mvn clean package
```

### Loading database credentials

Confidential database credentials should be stored as a properties file on the web server.

After compiling the web servlet, create `my.properties` and place it in  `target/magnet/WEB-INF`.

A sample of `my.properties`:

```properties
db.user=admin
db.pass=mysupersecretpassword
```

### Running the server

Once the servlet is compiled and the credentials file is placed under `WEB-INF`, copy the `magnet` directory into `$CATALINA_BASE/webapps`, and restart the Tomcat server.

> `$CATALINA_BASE` varies based on your installation of Tomcat.

The server should now run under `localhost:8080/magnet`. Send a GET request to the index link to check if the server is running. If the setup is successful, the message should appear:

```
Social Magnet Service running!
```

