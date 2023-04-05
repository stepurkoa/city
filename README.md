# cityList

Enterprise-grade "city list" application which allows the user to do:
- browse through the paginated list of cities with the corresponding photos
- search by the name
- edit the city (both name and photo)

# System requirements:
[Java 17+](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

[Git](https://git-scm.com/downloads)

[Maven](https://maven.apache.org/download.cgi)

[Docker](https://www.docker.com/products/docker-desktop/)

[Postman](https://www.postman.com/downloads/)

Browser

P.S. select version for your OS

# Run application

- Get a Git Repository
      
        git clone https://github.com/stepurkoa/city.git

- Build appllication with test, for correct finish build, docker must be started

        mvn clean install  
 
     build application without tests
  
        mvn clean install -Dmaven.test.skip=true

- Build docker images using docker compose file

        docker compose build

- Run docker compose (first time need run docker compose twice, for correct init sql db)

        docker compose up

- Visit page for check service availability

        http://localhost:8080/index.html

- Stop docker compose 

      docker compose stop


# Test application
For test application functionality we can use 2 approach
 - [Open api webpage](http://localhost:8080/swagger-ui.html)

 - [Postman collection](CityList.postman_collection.json) - this file needs to import into postman
