# Create your own .env file
# Look at .env-sample for an example of how to set it up
source .env;
export CLASSPATH="target/social-magnet-0.0.1.jar"
export CLASSPATH="$CLASSPATH:$HOME/.m2/repository/mysql/mysql-connector-java/8.0.19/mysql-connector-java-8.0.19.jar"
java -cp $CLASSPATH com.g1t11.socialmagnet.App;
