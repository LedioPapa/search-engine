# Console Search engine with ElasticSearch
This is a simple search engine which can add documents to an index and query by their contents.

It uses ElasticSearch's library to make possible all the operations in the server node. 

This implementation was chosen in order to make way for future iterations of the solution with scalability in mind. 
## Requirements
JDK 8+

Apache Maven 3+

Docker

## Compile the Application
Using a terminal go to the root folder where the file pom.xml is an run the following command:
```
mvn clean package
```
## Running the Application
There are 2 steps to run the application:
- Start an ElasticSearch node locally with Docker using the command below
  - ```docker run -d -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.17.3```
- Start the application
  - ```java - jar search-engine-1.0-SNAPSHOT-jar-with-dependencies.jar```