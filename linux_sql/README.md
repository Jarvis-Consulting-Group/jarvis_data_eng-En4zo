#Linux Cluster Monitoring Agent
## Introduction
#### What does this project do?
In this project, we are writing several shell script files that record the 
server's hardware information and monitor the server's resource usage in real-time(once a minute).
After that, the data will be stored in an RDBMS(PpstgreSQL in this project) for future planning purpose.

#### Who are the users?
Originally,the project is designed for the *Jarvis Linux Cluster Administration(LCA)* team which
manages a Linux cluster of 10 servers. The LCA team would like to collect the hardware specification
of each server and monitor the real-time states of the server's resource usage. The LCA team will
use the data to generate reports for future resource planning purposes.

#### Technologies used?
In this project, we use *git* to manage the code and use *git repository* to store the code.
The project was built and tested on *JRV(Jarvis Remote Desktop)*. We create *PSQL* Instance using
*docker* and connect the PSQL instance using *PSQL CLI tools*. We also write *bash script* and *ddl sql
script* to make the program automatically create docker instance, create table and insert date into
 the database.
- **Git&Git Repository:** manage and store the code.
- **Docker:** provision the PSQL instance.
- **JRV:** a remote desktop running centos 7.
- **Bash script&Linux Command:** implement the project.
- **PostgreSQL:** store the server's hardware and real-time usage data.

## Quick Start

## Implementation

### Architecture

### Scripts

### Database Modeling

## Test

## Deployment

## Improvements