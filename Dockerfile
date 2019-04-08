FROM openjdk:8-jdk-alpine

RUN mkdir /app
ADD . /app
VOLUME /app
VOLUME /tmp

EXPOSE 8080
WORKDIR /app
ENV GRADLE_OPTS -Dorg.gradle.native=false
CMD ./gradlew build --continuous & ./gradlew bootRun