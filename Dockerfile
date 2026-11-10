FROM gradle:jdk21 AS build

WORKDIR /app

COPY --chown=gradle:gradle . .

RUN chmod +x gradlew && ./gradlew clean bootWar --no-daemon

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/build/libs/discografia-1.war app.war

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.war"]