#! /bin/sh

cmd=$1
db_username=$2
db_password=$3

#start the docker container
sudo systemctl status docker || systemctl start docker

#check the container status
docker container inspect jrvs-psql
container_status=$?

#switch case to create, start or stop
case $cmd in
  create)
  #check if container already exist
  if [ $container_status -eq 0 ]; then
    echo 'Container already exists'
    exit 1;
  fi
  #check number of CLI commands
  if [ $# -ne 3 ]; then
    echo 'Create requires username and password'
    exit1
  fi
  #create container
  #get latest postgres image
  docker pull postgres
  #create a new volume if not exist
  docker volume create $db_username

  #start the container
  #create a container using psql image with name = jrvs-psql
  docker run --name jrvs-psql -e POSTGRES_PASSWORD=$db_password -d\
    -v $db_username:/var/lib/postgresql/data -p 5432:5432 postgres:9.6-alpine

  ##check `jrvs-psql` is powered on
  docker ps -f name=jrvs-psql
  exit $?
  ;;

  start|stop)
  #check instance status; exit 1 if container has not been created
  if [ $container_status -eq 1 ]; then
    echo 'Container has not been create yet, please create the container first'
    exit 1;
  fi;

  docker container $cmd jrvs-psql
  exit $?
  ;;

  *)
  echo 'Illegal command'
  echo 'Commands: start|stop|create'
  exit 1
  ;;
esac

