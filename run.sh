# Create your own .env file
# Look at .env-sample for an example of how to set it up
FILE=.env
if [ ! -f "$FILE" ]; then
  RED='\033[0;31m'
  NOCOLOR='\033[0m'
  echo -e "\n${RED}File not found error: Please include config file (.env) in the project's root directory. See file .env-sample for reference${NOCOLOR}\n"
  exit 1
fi

source .env;
export CLASSPATH="target/classes"
export CLASSPATH="$CLASSPATH:$HOME/.m2/repository/mysql/mysql-connector-java/8.0.19/mysql-connector-java-8.0.19.jar"
java -cp $CLASSPATH com.g1t11.socialmagnet.App;
