FROM openjdk:8-jdk-alpine
VOLUME /tmp
ENV JAVA_OPTS ""
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT exec java -Djava.security.egd=file:/dev/./urandom $JAVA_OPTS -jar /app.jar
