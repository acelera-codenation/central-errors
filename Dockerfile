FROM adoptopenjdk/openjdk11:alpine-jre
VOLUME /tmp
ARG JAR_PATH
COPY $JAR_PATH app.jar

ENTRYPOINT java -jar app.jar