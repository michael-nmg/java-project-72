FROM eclipse-temurin:20-jdk

WORKDIR /app

COPY /app .

RUN ./gradlew --no-daemon dependencies

RUN ./gradlew --no-daemon build

ENV JAVA_OPTS "-Xmx512M -Xms512M"
EXPOSE 7070

CMD java -jar build/libs/app-1.0-SNAPSHOT-all.jar
