# Introduction
This project implements a Java application that allows users to post, show, 
and delete tweets on Twitter using the Twitter REST API. The TwitterApp 
follows the MVC architecture, where the models encapsulate and manage Tweet 
objects that are displayed in JSON format. The controller layer interacts 
with client input (args), parses it, and calls the service layer to return 
results. The service layer handles the business logic and interacts with the 
DAO layer to communicate with the Twitter REST API. The data access layer (DAO) 
interacts with the Twitter REST API to perform post, show, and delete actions 
on tweets.

To implement this design, we use Twitter REST API v2 and combine it with HTTP requests to 
perform the actions. We also use the OAuth 1.0a protocol to create an additional HTTP Authorization header
to authorize HTTP requests. To convert JSON strings from HTTP responses to Tweet objects and vice versa,
we use the fasterXML Jackson package. Springboot framework is used to manage dependencies and set dependencies
via the class constructor. For testing, we utilize integration and unit testing with the libraries Mockito and
JUnit4. To manage all the above dependency packages, we use Maven.

Finally, we deploy the Twitter application within a Docker container and upload it to Dockerhub for easy use.
# Quick Start
Retrieve the Docker image from Docker Hub
```shell
#Syntax
docker run --rm \
-e consumerKey=YOUR_VALUE \
-e consumerSecret=YOUR_VALUE \
-e accessToken=YOUR_VALUE \
-e tokenSecret=YOUR_VALUE \
en44zo/twitter show|post|depete [options]
```

```shell
#Exampple show tweet
docker run --rm \
-e consumerKey=YOUR_VALUE \
-e consumerSecret=YOUR_VALUE \
-e accessToken=YOUR_VALUE \
-e tokenSecret=YOUR_VALUE \
en44zo/twitter show 1633074624056508418

#Example post tweet
docker run --rm \
-e consumerKey=YOUR_VALUE \
-e consumerSecret=YOUR_VALUE \
-e accessToken=YOUR_VALUE \
-e tokenSecret=YOUR_VALUE \
en44zo/twitter post "test text"
```

# Design
## UML diagram
![This is the UML diagram of the program](./assets/UML%20Relationship-Page-1.drawio.png)
## Components
- TwitterHttpHelper 
  - TwitterHttpHelper is an implementation of HttpHelper that is specifically designed to establish a 
    connection with the Twitter REST API. It does so by accepting a Uniform Resource Identifier (URI) and 
     JSON String as arguments, which are then used to execute an HTTP request. 
    Upon execution, TwitterHttpHelper returns the response obtained from the Twitter API.
- TwitterDao
  - TwitterDao implements three interfaces: createDao, readDao, and deleteDao. These functions 
    utilize HttpHelper by passing Uniform Resource Identifiers (URI) as arguments. Once HttpHelper returns 
    a response, TwitterDao converts the data into a tweet object(Root), delete message object(deleteRoot), or create message 
    object(create Root), depending on the specific function being called.
- TwitterService
  - TwitterService is an implementation of the service interface. Within the service layer, TwitterService 
    is responsible for validating arguments received from the controller and calling the appropriate methods 
    within the DAO layer. Depending on the specific method called, TwitterService returns a tweet object (Root),
    delete tweet object (delete Root), or create tweet object (create Root).
- TwitterController 
  - Twitter Controller is an implementation of the controller interface. Its function is to pass command
    line arguments into the appropriate methods within the service layer. Depending on which method is called,
    Twitter Controller returns a tweet object (Root), delete tweet object (delete Root), or create tweet object
    (create Root).
- TwitterCLIApp
  - The TwitterCLIApp parses  input args and calls the controller methods. It also prints tweet(s) returned by controller methods.
- TwitterCLIBean
  - Define dependency relationship using @Bean and pass dependencies through method arguments.
    Create an IoC container/context which will automatically instantiate all Beans base on the relationship you specified in the previous step.
    Get the main entry point (TwitterCLIApp) from the IoC container and start the program.
- TwitterCLISpringBoot
  - TwitterCLISpringBoot configure Spring automatically.
## Models
- The Root model is a fundamental tweet object that contains key information such as the tweet's ID, text, creation timestamp, entities, geo location data, and public metrics. The PublicMetrics, Geo, and Entities are separate objects that contain additional data such as hashtags, mentions, coordinates, and retweets.

- The Delete Root model is a basic delete message object that is returned when a tweet has been successfully deleted. It contains a boolean value indicating whether the deletion was successful.

- The Create Root model is a basic create message object that is returned when a tweet has been successfully created. It contains information such as the tweet's edit history, ID, and text.
## Spring
The @Beans approach is used to manage dependencies in the application. The @Configuration annotation is used to mark the Spring Configure file that defines the dependency relationships. The dependency relationships are defined using the @Bean annotation, which allows dependencies to be passed through method arguments.

An Inversion of Control (IoC) container/context is created, which automatically instantiates all beans based on the relationship specified in the previous step. The IoC container/context is responsible for managing the lifecycle of these beans, as well as their dependencies.

The main entry point (TwitterCLIApp) is obtained from the IoC container/context, and the program is started. This approach allows for a more modular and flexible design, as dependencies can be easily managed and updated without affecting the overall structure of the application.
# Test
## Deployment
1. Login Docker
2. Set the base image to be "openjdk:8-alpine".
3. Copy the compiled JAR file into the Docker image using the "COPY" command.
4. Set the entry point to execute the JAR file using the "ENTRYPOINT" command.
5. Compile and build the project using mvn.
6. Build docker image and push to docker hub.
```shell
docker login

cat > Dockerfile << EOF
FROM openjdk:8-alpine
COPY target/java_apps-1.0-SNAPSHOT.jar /usr/local/app/twitter/lib/twitter.jar
ENTRYPOINT ["java","-jar","/usr/local/app/twitter/lib/twitter.jar"]
EOF

#Package the java app 
mvn clean package

#Build docker image locally
docker build -t ${docker_user}/twitter .

#Push to dockerhub
docker push ${docker_user}/twitter
```
# Improvements
1. One approach is to investigate how to pass coordinates as an argument to a tweet post in the Tweet REST API v2. By allowing users to specify a location for their tweets, the application could offer location-based services that add value to the user experience.

2. Another option is to leverage the Tweet API rather than creating custom HTTP requests from scratch. This API provides a higher-level interface for working with the Twitter platform, which could streamline development and simplify the codebase.

3. Additionally, expanding the range of objects beyond tweets - to include users, media, and other entities - could create a richer user experience. Users could explore media associated with tweets or discover new users based on location or other criteria. Incorporating additional objects and functionality could help the application stand out from competitors and attract a wider user base.