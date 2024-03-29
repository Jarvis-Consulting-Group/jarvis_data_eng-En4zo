# Linux Cluster Monitoring Agent
## Introduction
#### What does this project do?
In this project, we are creating several shell scripts to gather and record the hardware information of the server, 
as well as continuously monitor its resource usage on a minute-by-minute basis. The collected data will then be stored 
in a PostgreSQL RDBMS for future reference and planning.

#### Who are the users?
The project was initially created for the Jarvis Linux Cluster Administration (LCA) team, which is responsible for 
managing a Linux cluster of 10 servers. The LCA team needs to gather the hardware specifications of each server 
and continuously monitor their resource usage in real-time. The collected data will be used to generate reports for 
future resource planning and management.

#### Technologies used?
In this project, we utilize *git* for code management and utilize a *git repository* for code storage. The project was 
constructed and tested on *Jarvis Remote Desktop (JRV)*. We create a *PSQL* instance using *docker* and connect to it through 
the use of PSQL CLI tools. Additionally, we have written *bash scripts* and *DDL* SQL scripts to automate the process of 
creating a docker instance, creating tables, and inserting data into the database.
- **PostgreSQL:** Store the server's hardware and real-time usage data.
- **Docker:** Provision the PSQL instance.
- **JRV:** Remote desktop running centos 7.
- **Bash Script&Linux Command:** Implement the project.
- **Git&Git Repository:** Manage and stored the code.

## Quick Start
Using the psql_docker.sh file to create the PSQL instance(make sure docker installed).
```bash

#script usage
./scripts/psql_docker.sh start|stop|create [db_username][db_password]

#if first time, use create to create a psql docker container
./scripts/psql_docker.sh create db_username db_password

#to start the psql docker container
./scripts/psql_docker start

#to stop the psql docker container
./scripts/psql_docker stop

```
Create a database using psql CLI.
```bash

#make sure the docker container is running
docker ps -f name=jrvs-psql

#install psql CLI client
sudo yum install -y postgresql

#set password for default user `postgres`
export PGPASSWORD='yourpassword'

#connect to the psql instance without password
psql -h localhost -U postgres -w

#create database
postgres=# CREATE DATABASE host_agent;

#exit database
postgres=# \q;

```
Create tables to store hardware specification and resource usage data.
```bash

#create 'host_info' table and 'host_usage' table if not exist
#execute ddl.sql script on the host_agent database to create the table
psal -h localhost -U postgres -d host_agent -f sql/ddl.sql

```

Execute host_info.sh to collect hardware specification and then inserts the data into the psql instance.
```bash

#script usage
./scripts/host_info.sh [psql_host] [psql_port] [db_name] [psql_user] [psql_password]

#script example
./scripts/host_info.sh localhost 5432 host_agent postgres password

```

Execute host_usage.sh to collect server usage data and then inserts the data into the psql instance.
```bash
#script usage
./scripts/host_usage.sh [psql_host] [psql_port] [db_name] [psql_user] [psql_password]

#script example
./scripts/host_usage.sh localhost 5432 host_agent postgres password

```

Execute host_usage.sh every minute to generate real-time server source usage data and insert data into the database.
```bash

#edit crontab jobs
bash > crontab -e

#execute host_usage.sh every minute(add this to crontab)
* * * * * [absolute path of host_usage.sh] [psql_host] [psql_port] [db_name] [psql_user] [psql_password]

#example
* * * * * /home/centos/dev/jarvis_data_eng_Enzuo/linux_sql/scripts/host_usage.sh localhost 5432 host_agent postgres password

```

## Implementation
1. Create a Linux server running with centos 7 and install the docker.
2. Create a psql instance using docker.
3. Install psql CLI client and Setup RDBMS psql database.
4. Create 'host_info' table to stored the hardware information and 'host_usage' table to stored the real-time server usage data.
5. Create agent for collecting hardware specification and insert the data into database.
6. Create agent for collecting server usage data and then inserts the data into database.
7. Using crontab to automate 'host_usage' agent to generate real-time server usage data.

### Architecture
#### This is the architecture of the entire project.
![This is  the architecture](./assets/architecture.png)
### Scripts
This section will describe each shell script.
- *psql_docker.sh:*
  - The usage of psql_docker.sh is to create instance of psql database within docker container.\
    The script contains three input variables, input command, username and password.\
    The script first check the status of docker container, after that the shell script will switch case base to \
    create, start or stop the container base on the input command.
- *host_info.sh:*
  - The usage of host_info.sh is to collect hardware specification and then inserts the data into psql instance.\
    The script contains five input variables, psql_host, psql_port, db_name, psql_user, psql_password.\
    The script first check the numbers of the input arguments, then it collect the hardware information hostname,\
    cpu_number, cpu_architecture, cpu_model, cpu_mhz, l2_cache, timestamp, total_mem and insert the specification into\
    the psql database.
- *host_usage.sh:*
  - The usage of host_usage.sh is to collect server usage data and then inserts the data into the psql database.\
    The script contains five input variables, psql_host, psql_port, db_name, psql_user, psql_password.\
    The script fist check the number of the input arguments, then it collect the usage of hardware information\
    "timestamp", host_id, memory_free, cpu_idle, cpu_kernel, disk_io, disk_available and insert the usage specification\
    into the psql database.
- *crontab:*
  - The usage of crontab is to deploy the monitoring app on each server and collect data every minute.\
    The crontab run host_usage.sh every minute therefore the host_usage.sh will store hardware usage specification\
    into psql database every minute.
- *queries.sql:* (describe what business problem you are trying to resolve)

### Database Modeling
- *host_info:*
  - The usage of this table is to store the hardware data of each linux host.\
    The table stored nine types of information which are id, hostname, cpu_number, cpu_architecture, cpu_model,\
    cpu_mhz, l2_cache, timestamp and total_mem.
  - The table has primary key constraint named host_info_pk which is id.\
    The table has unique constraint named host_info_un which is hostname.
- *host_usage:*
  - The usage of this table is to store the usage of hardware data of each linux host.\
    The table stored seven types of information which are timestamp, host_id, memory_free, cpu_idle, cpu_kernel,\
    disk_io, disk_available.
  - The table has foreign key constraint named host_usage_info_fk which is host_id reference id in the host_info table.

## Test
- Test psql_docker.sh
  ```bash
  #check if `jrvs-psql` container is running
  docker container ls -a -f name=jrvs-psql
  
  ```
  Result:
  | CONTAINER ID | IMAGE | COMMAND | CREATED | STATUS |PORTS | NAMES |
  | --- | --- | --- | --- | --- | --- | --- |
  | 493232fb98d6 | postgres:9.6-alpine | "docker-entrypoint.s…" | 5 days ago | Up 35 hours | 0.0.0.0:5432->5432/tcp, :::5432->5432/tcp | jrvs-psql |

- Test host_info.sh
  ```sql
  --check the hardware info data in the database
  SELECT * FROM host_info;
  
  ```
  Result:
  |id | hostname | cpu_number | cpu_architecture | cpu_model | cpu_mhz  | l2_cache | timestamp | total_mem |
  | --- | --- | --- | --- | --- | --- | --- | --- | --- |
  |1 | jrvs-remote-desktop-centos7.us-east1-c.c.carbide-ratio-375000.internal | 2 | x86_64 | 49 | 2249.998 | 512 | 2023-01-24 04:16:23 | 3341 |

- Test host_usage.sh
  ```sql
  --check the usage data in the database
  SELECT * FROM host_usage;
  
  ```
  Result:
  | timestamp | host_id | memory_free | cpu_idle | cpu_kernel | disk_io | disk_available |
  | --- | --- | --- | --- | --- | --- | --- |
  | 2023-01-24 04:16:57 | 1 | 3340 | 87 | 3 | 0 | 22450 |
- Test crontab setup
  ```sql
  --check the usage data in the database
  SELECT * FROM host_usage;
  
  ```
  Result:
  | timestamp | host_id | memory_free | cpu_idle | cpu_kernel | disk_io | disk_available |
  | --- | --- | --- | --- | --- | --- | --- |
  | 2023-01-24 04:16:57 | 1 | 3340 | 87 | 3 | 0 | 22450 |
  | 2023-01-24 04:16:59 | 1 | 3340 | 93 | 0 | 0 | 22450 |
  | 2023-01-24 04:17:00 | 1 | 3340 | 90 | 3 | 0 | 22450 |
  | 2023-01-24 04:17:01 | 1 | 3340 | 93 | 0 | 0 | 22450 |

## Deployment
Please follow the steps and refer the quick start guide.
1. Downloading code from Github.
2. Installing docker.
3. Create psql instance using psql_docker.sh.
4. Create tables using ddl.sql.
5. Insert hardware specification into the database using host_info.sh.
6. Setup Crontab to execute host_usage.sh every minute to insert hardware usage data into the database every minute.

## Improvements
1. Collecting the input and output of the internal network(based on switch).
2. Create a GUI of the monitoring app.
3. Collecting the data of usage of hardware against battery usage.
