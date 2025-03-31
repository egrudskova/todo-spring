FROM eclipse-temurin:21
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME

COPY build.gradle settings.gradle gradlew $APP_HOME
COPY gradle $APP_HOME/gradle
RUN ./gradlew bootJar || true
COPY . .
RUN ./gradlew bootJar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "build/libs/spring-todo-0.0.1-SNAPSHOT.jar"]
