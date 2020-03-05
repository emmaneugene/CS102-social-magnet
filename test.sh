mvn clean;
source .env;
mysql -u"$DB_USER" -p"$DB_PASS" < sql/deploy.sql &&
mysql -u"$DB_USER" -p"$DB_PASS" < sql/load-sample-data.sql;
mvn test;
