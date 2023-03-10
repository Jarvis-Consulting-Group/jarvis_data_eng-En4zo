#!/bin/sh

psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

#check # of args
if [ "$#" -ne 5 ]; then
    echo "Illegal number of parameters: [psql host] [psql port] [database name] [user name] [user password]"
    exit 1
fi

#save machine statistics in MB and current machine hostname to variables
vmstat_mb=$(vmstat --unit M)
hostname=$(hostname -f)
#run top once to get current cpu usage
cpu_info=$(top -bn1)

#Retrieve hardware usage specification variables
memory_free=$(echo "$vmstat_mb" | awk '{print $4}' | tail -n1 | xargs)
cpu_idle=$(echo "$cpu_info" | grep "Cpu(s)" | awk '{print $8}')
cpu_kernel=$(echo "$cpu_info" | grep "Cpu(s)" | awk '{print $4}')
disk_io=$(vmstat --unit M -d | tail -1 | awk -v col="10" '{print $col}')
disk_available=$(df -BM / | tail -1 | awk '{print $4}')
timestamp=$(date +"%Y-%m-%d %T")

#Insert server usage into host_usage table
#using insertion with subquery,also delete "M" in disk_available
#delect number after points in usage of cpu_kernel and cpu_idle
insert_stmt="INSERT INTO host_usage(
                "timestamp", host_id, memory_free,
                cpu_idle,cpu_kernel,disk_io,disk_available)
              SELECT
                '$timestamp', id, '$memory_free', '${cpu_idle%.*}',
                '${cpu_kernel%.*}', '$disk_io', '${disk_available%%M}'
              FROM host_info
              WHERE hostname='$hostname'
              ";

#set up env var
export PGPASSWORD=$psql_password
#Insert data into a database
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?
