FROM gradle:8.4.0-jdk17 AS build

WORKDIR /app

COPY . .

RUN gradle clean :friendship-service:build -x test --no-daemon

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/friendship-service/build/libs/friendship-service.jar /app/service.jar

EXPOSE 3570

ENTRYPOINT ["java","-jar","/app/service.jar"]
