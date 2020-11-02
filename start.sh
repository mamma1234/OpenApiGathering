PROJECT_HOME=`pwd`
JAVA_HOME=/usr/local/jdk/jdk1.8.0_121/jre
CLASSPATH=.

CLASSPATH=$PROJECT_HOME/resource/:$CLASSPATH
CLASSPATH=$JAVA_HOME:$CLASSPATH

export CLASSPATH


nohup $JAVA_HOME/bin/java -Dname=CnediThread -jar CnediThread-0.0.1-SNAPSHOT.jar -Xmss5m -Xmx2048m &

#$JAVA_HOME/bin/java -Dname=KmcsSpringStarter -jar KmcsSpringStarter-0.0.1-SNAPSHOT.jar --logging.config=file:$PROJECT_HOME/resource/log4j2-spring.xml

#$JAVA_HOME/bin/java -Dname=KmcsSpringStarter -jar KmcsSpringStarter-0.0.1-SNAPSHOT.jar -Xmss5m -Xmx2048m
