FROM openjdk:11-jdk
VOLUME /tmp
ARG JAR_PATH
COPY $JAR_PATH app.jar

ENTRYPOINT java -jar app.jar