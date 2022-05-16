FROM maven:alpine as build
ENV HOME=/search-engine
RUN mkdir -p $HOME
WORKDIR $HOME
COPY pom.xml $HOME
RUN mvn verify --fail-never
COPY . $HOME
RUN mvn package

FROM openjdk:8-jdk-alpine
ENV ELASTIC_HOSTNAME=es01
ENV ELASTIC_PORT=9200
COPY --from=build /search-engine/target/search-engine-1.0-SNAPSHOT-jar-with-dependencies.jar /app/runner.jar
ENTRYPOINT java -jar /app/runner.jar