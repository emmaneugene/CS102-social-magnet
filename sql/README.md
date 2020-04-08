# Social Magnet Database

#### `deploy.sql`

`deploy.sql` creates the database for the application.

It will first create all necessary tables, then all the required stored procedures and triggers for the server application.

#### `load-sample-data.sql`

`load-sample-data.sql` will load sample data required for the test cases.

Sample data has been designed to minimise conflicts between test cases, by partitioning sets of data for independent test cases.

#### Creating the database with sample data

If the goal is simply to test the database, use the `reset-db.sh` or `reset-db.bat` scripts provided.

* Make sure to provide database credentials through a `env.sh` or `env.bat` file. A sample of these scripts are provided as `env-sample.sh` and `env-sample.bat`.

#### Creating the database without sample data

To load `deploy.sql` independently, simply run:

```bash
$ mysql -u"myadmin" -p"mysupersecretkey" < deploy.sql
```

