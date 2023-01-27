#!/bin/sh

psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

#check # of args
if [ "$#" -ne 5 ]; then
    echo "Illegal number of parameters"
    exit 1
fi

#save machine statistics in MB and current machine hostname to variables
vmstate_mb=$(vmstat --unit M)
lscpu_out=`lscpu`
hostname=$(hostname -f)

#id, hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, l2_cache, "timestamp", total_mem)
#Retrieve hardware specification variables
cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out"  | egrep "^Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out"  | egrep "^Model:" | awk '{print $2}' | xargs)
cpu_mhz=$(echo "$lscpu_out" | egrep "^CPU MHz:" | awk '{print $3}' | xargs)
l2_cache=$(echo "$lscpu_out" | egrep "^L2 cache:" | awk '{print $3}' | xargs)
total_mem=$(echo "$vmstate_mb" | tail -1 | awk '{print $4}')
timestamp=$(date +"%Y-%m-%d %T")


#Insert server usage into host_info table
insert_stmt="INSERT INTO host_info(
                  hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz,
                  l2_cache, timestamp, total_mem
                )
                VALUES
                (
                  '$hostname', '$cpu_number', '$cpu_architecture', '$cpu_model',
                  '$cpu_mhz', '${l2_cache%%K}', '$timestamp', '$total_mem'
                )";

#set up env var
export PGPASSWORD=$psql_password
#Insert data into a database
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?
