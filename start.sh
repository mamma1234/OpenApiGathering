PROJECT_HOME=`pwd`
JAVA_HOME=/usr/JAVA/jdk1.7.0_72
CLASSPATH=.

CLASSPATH=$PROJECT_HOME/resource/:$CLASSPATH
CLASSPATH=$JAVA_HOME:$CLASSPATH

export CLASSPATH

#nohup $JAVA_HOME/bin/java -Dname=OwnerFlowThread -jar OwnerFlowThread-0.0.1-SNAPSHOT.jar -Xmss5m -Xmx2048m &

$JAVA_HOME/bin/java -Dname=OwnerFlowThread -jar OwnerFlowThread-0.0.1-SNAPSHOT.jar -Xmss5m -Xmx2048m &

#$JAVA_HOME/bin/java -Dname=OwnerFlowThread -jar OwnerFlowThread-0.0.1-SNAPSHOT.jar --logging.config=file:$PROJECT_HOME/resource/log4j2-spring.xml

#$JAVA_HOME/bin/java -Dname=OwnerFlowThread -jar OwnerFlowThread-0.0.1-SNAPSHOT.jar -Xmss5m -Xmx2048m
