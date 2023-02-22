# Introduction

This project is a Java application that demonstrates how to use the JDBC API to interact with a PostgreSQL 
database. Using Java version 1.8, we implement the standard CRUD operations (Create, Read, Update, Delete) 
on the database. To simplify deployment, we use Docker to create a container image and run PostgreSQL within it. 
The application uses the java.sql packages to establish connections with the database and execute SQL statements 
to perform CRUD operations. We use Maven to manage dependencies, such as the org.postgresql driver, and Git and 
GitHub to manage our project code.

# Implementaiton
## ER Diagram
![This is the ER Diagram of the Database](./assets/hplussport%20-%20public.png
)

## Design Patterns
The DAO design pattern involves creating an object that provides an abstract interface to the underlying 
database or data source. The DAO is responsible for managing the connection to the database, executing SQL 
queries or other data operations, and mapping the results to Java objects or data structures. By abstracting 
the data access logic behind a DAO interface, the rest of the application can work with higher-level objects 
or abstractions without worrying about the details of the underlying data storage.

On the other hand, the Repository design pattern provides a higher-level interface to a collection of objects
or entities, rather than directly to a database or data source. Repositories act as a mediator between the 
application code and the data storage layer, providing an easy-to-use interface for retrieving, creating, 
updating, and deleting entities. The Repository pattern can also help to simplify testing, as it enables you
to swap out different implementations of the repository interface (e.g., in-memory, database-backed, or mock 
implementations) for different testing scenarios.

# Test
The application was manually tested to ensure its functionality. To set up the testing environment, I deployed the 
PostgreSQL database on a Docker container using the official PostgreSQL Docker image and the docker run 
command. I then loaded a test data set into the database using the psql command with the -f flag, followed 
by SQL files.

To verify the results of the application, I ran queries manually in the terminal and compared the changes 
with the expected results. This was done to confirm that the CRUD operations were executed correctly and 
that the data was stored and retrieved accurately.

Additionally, automated testing can be implemented to ensure that the application functions as expected 
in a more systematic and repeatable manner. This can include unit testing, integration testing, and 
end-to-end testing, which can be performed using testing frameworks such as JUnit.

