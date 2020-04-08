cd -P -- "$(dirname -- "${BASH_SOURCE[0]}")";
source env.sh;
source reset-db.sh;

cd ..;
mvn clean && mvn test;
