# psychic-octo-parakeet

This application implements a queue populated with Request objects. Objects can be added and removed. Other operations are also available

## Compatibility
 JDK 8
 
## Build and Deploy
### Build
To build the jar simply run `./gradlew build` in the base directory of the repository. This will generate a in the build/libs directory called psychic-octo-parakeet-{version}.jar

## Deploy
For simplicity this jar provides it's own tomcat container. To run the application use java to run the jar: `java -jar build/libs/psychic-octo-parakeet-{version}.jar`

## REST service
Base application URL: http://localhost:8080/queue-app

| Resource        | Method           | Parameters  | Request Body   |
| ------------- |:-------------:|:-----:| -------------:| 
| requests/enqueue      | PUT |   | {"customerId": 5, "date": "2017-06-19 09:00:00"} |
| requests/delete/{id}      | DELETE |       |       |
| requests/dequeue      | GET     |      |      |
| requests             | GET   |      |       |
| requests/index/{id}  | GET  |       |      |
| requests/meanwait    | GET  | date  |      |
