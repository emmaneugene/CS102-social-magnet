cd -P -- "$(dirname -- "$0")";
source env.sh;
source reset-db.sh;
cd ..;
mvn clean && mvn test;
