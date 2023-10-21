FROM gradle:8.4.0-jdk17 AS build

WORKDIR /app

COPY . .

RUN gradle clean :comment-service:build -x test --no-daemon

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/comment-service/build/libs/comment-service.jar /app/service.jar

EXPOSE 3569

ENTRYPOINT ["java","-jar","/app/service.jar"]
