[![Codacy Badge](https://app.codacy.com/project/badge/Grade/e74fee0edb914eb5ba999080adba97bf)](https://www.codacy.com/gh/LedioPapa/search-engine/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=LedioPapa/search-engine&amp;utm_campaign=Badge_Grade)
# Console Search engine with ElasticSearch
This is a simple search engine which can add documents to an index and query by their contents.
It uses ElasticSearch's library to make possible all the operations in the server node.
This implementation was chosen in order to make way for future iterations of the solution with scalability in mind. 
## Requirements
JDK 8+

Apache Maven 3+

Docker

Docker Compose
## Running the Application
Navigate to the root folder of the project and run the following commands:
Start a cluster of three ElasticSearch nodes with Docker Compose
```
docker-compose up -d
```
Create the application image using Docker Build
```
docker build -t search-engine-app .
```
Start the application using Docker Run
```
docker run --rm -it --network search-engine_elastic search-engine-app
```
