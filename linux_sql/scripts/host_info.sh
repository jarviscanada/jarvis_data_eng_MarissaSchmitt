#!/bin/bash

psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

if [ $# -ne  5 ]; then
  echo 'Incorrect amount of arguments'
  echo 'Required arguments: host, port, dbname, psql user, password'
  exit 1
fi

hostname=$(hostname -f)
cpu_number=$(lscpu | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(lscpu | egrep "Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(lscpu  | egrep "Model name:" | awk '{$1=$2=""; print $0}' | xargs)
cpu_mhz=$(lscpu | egrep "CPU MHz:" | awk '{print $3}' | xargs)
l2_cache=$(lscpu  | egrep "L2 cache:" | awk '{print $3}' | xargs)
total_mem=$(grep MemTotal /proc/meminfo | awk '{print $2}')
timestamp=$(vmstat -t | awk '{print $18 " " $19}')

l2_cache=$(echo "$l2_cache" | sed 's/[A-Za-z]*//g')
timestamp=$(echo "$timestamp" | sed 's/[A-Za-z]*//g')

echo $cpu_architecture
echo $cpu_model

insert_stmt="INSERT INTO host_info(hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, l2_cache, total_mem, timestamp)
            VALUES('$hostname', '$cpu_number', '$cpu_architecture', '$cpu_model', $cpu_mhz, $l2_cache, '$total_mem', '$timestamp');"

export PGPASSWORD=$psql_password

psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?


