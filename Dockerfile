# Stage 1: Build environment (JDK)
FROM maven:3.9.8-eclipse-temurin-21-alpine AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
WORKDIR /usr/src/app
RUN mvn -f pom.xml clean package -DskipTests

# Stage 2: Runtime environment (JRE)
FROM eclipse-temurin:21.0.4_7-jre
RUN mkdir /db
COPY --from=build /usr/src/app/target/app.jar /usr/app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/app/app.jar"]