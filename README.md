# Shop With Me - Backend: Wall

[![Build status](https://ci.cloudlab.zhaw.ch/api/projects/status/b57hibsns0gosquq?svg=true)](https://ci.cloudlab.zhaw.ch/project/swm/backend)

## Requirements

For building and running the application you need:
- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [MongoDB](https://www.mongodb.com/download-center/community) Running locally on Port 27017 

## Running the application locally

First of all ensure you have a local mongodb instance running. That can be done with docker in an ephemeral way: 
```shell
docker run --name mongodb --expose 27017 mongo:4.2.3
```

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `ch.zhaw.swm.wall.WallApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

### Run and build it locally
There is also a way to run and build everything locally on docker with an volume attached, so the data is persistent. Just run the script `build_and_run_complete.sh` 
