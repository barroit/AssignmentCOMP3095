FROM gradle:8.4.0-jdk17 AS build

WORKDIR /app

COPY . .

RUN gradle clean :post-service:build -x test --no-daemon

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/post-service/build/libs/post-service.jar /app/service.jar

EXPOSE 3568

ENTRYPOINT ["java","-jar","/app/service.jar"]
