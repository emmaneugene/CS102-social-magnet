cd -P -- "$(dirname -- "$0")"
source env.sh;
source reset-db.sh;

mvn clean && mvn test;
