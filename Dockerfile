FROM maven:alpine as build
ENV HOME=/search-engine
RUN mkdir -p $HOME
WORKDIR $HOME
COPY pom.xml $HOME
COPY . $HOME
RUN mvn clean install

FROM openjdk:8-jdk-alpine
ENV ES_LB=es01:9200
COPY --from=build /search-engine/target/search-engine-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]