FROM eclipse-temurin:17-jre
WORKDIR /app
# This looks into your local target folder
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]