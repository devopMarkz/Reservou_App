FROM maven:3.9.9-amazoncorretto-17-alpine AS builder
WORKDIR /builder

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

FROM amazoncorretto:17-alpine
WORKDIR /app

COPY --from=builder /builder/target/*.jar ./api-reservou.jar

ENTRYPOINT ["java", "-jar", "api-reservou.jar"]
