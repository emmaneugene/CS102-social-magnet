source load-env.sh;

export CLASSPATH="target/classes"
export CLASSPATH="$CLASSPATH:$HOME/.m2/repository/mysql/mysql-connector-java/8.0.19/mysql-connector-java-8.0.19.jar"

java -cp $CLASSPATH com.g1t11.socialmagnet.App;
