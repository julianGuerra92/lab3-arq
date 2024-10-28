FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app/
COPY pom.xml /app/
RUN mvn dependency:go-offline -B
COPY src /app/src/
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app/
COPY --from=build /app/target/vuelosback-0.0.1-SNAPSHOT.jar /usr/local/lib/vuelosback.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/vuelosback.jar"]
