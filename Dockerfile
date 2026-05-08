# Stage 1: Build
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
# Sửa dòng này để lấy file .war thay vì .jar
COPY --from=build /app/target/*.war app.war
EXPOSE 8080
# Chạy file .war
ENTRYPOINT ["java", "-jar", "app.war"]