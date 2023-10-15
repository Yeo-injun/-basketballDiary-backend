FROM gradle:7.3-jdk11-alpine as builder
WORKDIR /build
COPY build.gradle settings.gradle /build/
RUN gradle build -x test --parallel --continue > /dev/null 2>&1 || true
COPY . /build
RUN gradle build -x test --parallel

FROM openjdk:11
RUN mkdir /app
WORKDIR /app

ADD ./build/libs/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
