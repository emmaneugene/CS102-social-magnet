cd /D "%~dp0"
call env.bat
call reset-db.bat

cd ..
mvn clean && mvn test