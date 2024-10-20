Project Cleaning Room Rest API

Project uses Java 11 , maven 3.9.5 and Spring Boot


1. in order to build the application and also run the tests use the following command
   `mvn clean install`
2. In order to run the project use the following command 

    `cd ../robot  java -jar target/robot-0.0.1-SNAPSHOT.jar`


In order to access the API implemented you can use the postman collection
(Cleaning Room.postman_collection.json) included in ..the robot/soapui folder.


The api url is :  `http://localhost:8080/api/cleanRoom` 

sample request 

` {
        "room": [5, 5],
        "coords": [1, 2],
        "patches": [[1, 0], [2, 2], [2, 3]],
        "instructions": "NNESEESWNWW"
    } `

Sample response 


`{
    "coords": [1,3],
    "patches": 1
}`

            



