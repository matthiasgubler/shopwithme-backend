FROM maven:3.6.3-jdk-8 as builder

WORKDIR /workspace/app

COPY pom.xml .
RUN mvn --batch-mode --errors --strict-checksums --threads 1C org.apache.maven.plugins:maven-dependency-plugin:3.1.1:go-offline

COPY checkstyle.xml .
COPY src ./src

RUN mvn --batch-mode --errors --offline --threads 1C verify

FROM openjdk:8-jre-alpine

VOLUME /tmp
WORKDIR /app
ENV JAVA_OPTS ""

RUN addgroup -S swm && adduser -S swm -G swm
USER swm

COPY --from=builder --chown=swm:swm /workspace/app/target/*.jar ./app.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar ${0} ${@}"]
