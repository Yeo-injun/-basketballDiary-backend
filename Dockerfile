FROM gradle:7.3-jdk11-alpine as builder
WORKDIR /build
COPY build.gradle settings.gradle /build/
RUN gradle build -x test --parallel --continue > /dev/null 2>&1 || true
COPY . /build
RUN gradle build -x test --parallel

FROM openjdk:11
WORKDIR /app
COPY --from=builder /build/build/libs/basketballDiary-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
USER nobody
ENTRYPOINT ["java", "-jar", "basketballDiary-0.0.1-SNAPSHOT.jar"]
