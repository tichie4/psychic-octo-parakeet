# psychic-octo-parakeet

This application implements a queue populated with Request objects. Objects can be added and removed. Other operations are also available

## Compatibility
 JDK 8
 
## Build and Deploy
### Build
To build the jar simply run `./gradlew build` in the base directory of the repository. This will generate a in the build/libs directory called psychic-octo-parakeet-{version}.jar

### Deploy
For simplicity this jar provides it's own tomcat container. To run the application use java to run the jar: `java -jar build/libs/psychic-octo-parakeet-{version}.jar`

## REST services
Base application URL: http://localhost:8080/queue-app

| Resource        | Method           | Parameters  | Request Body   | Function |
| ------------- |:-------------:|:-----:|:-------------:| --------:|
| requests/enqueue      | PUT |   | {"customerId": 5, "date": "2017-06-19 09:00:00"} | Add Request to the queue |
| requests/delete/{id}      | DELETE |       |       | Delete Request from the queue |
| requests/dequeue      | GET     |      |      | Get highest priority Request and remove from the queue | 
| requests             | GET   |      |       | Get all Requests sorted by priority (Highest to Lowest) |
| requests/index/{id}  | GET  |       |      | Get index of Request with given {id} |
| requests/meanwait    | GET  | date  |      | Get the average wait time for all Requests in the queue |
