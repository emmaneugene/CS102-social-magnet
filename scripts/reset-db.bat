call env.bat

mysql -u"%DB_USER%" -p"%DB_PASS%" < ../sql/deploy.sql
mysql -u"%DB_USER%" -p"%DB_PASS%" < ../sql/load-sample-data.sql
