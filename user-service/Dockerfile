FROM gradle:8.4.0-jdk17 AS build

WORKDIR /app

COPY . .

RUN gradle clean :user-service:build -x test --no-daemon

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/user-service/build/libs/user-service.jar /app/service.jar

EXPOSE 3568

ENTRYPOINT ["java","-jar","/app/service.jar"]
