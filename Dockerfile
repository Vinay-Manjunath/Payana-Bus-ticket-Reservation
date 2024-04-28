FROM tomcat
COPY dockerproject.war /usr/local/tomcat/webapps/
ADD https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.28/mysql-connector-java-8.0.28.jar /usr/local/tomcat/lib/
