#!/bin/bash
prop_file=src/main/resources/database.properties

if [ ! -f $prop_file ]; then
  echo No such file $prop_file
  exit 1
fi

declare -A connection

while IFS= read -r line; do
  IFS='=' read -ra config <<< $line
  if [ ! -z $config ]; then
    connection["${config}"]=${config[1]}
  fi
done < $prop_file

host=${connection["host"]}
port=${connection["port"]}
database=${connection["database"]}
user=${connection["user"]}
pass=${connection["pass"]}

if [ -z $host ] || [ -z $port ] || [ -z $database ] || [ -z $user ] || [ -z $pass ]; then
  echo Invalid config
  exit 1
fi

function load() {
  mysql -h$host -u $user -p$pass -P$port $database < src/main/resources/database_schema.sql
}

function dump() {
  mysqldump --no-data -h$host -u $user -p$pass -P$port $database > src/main/resources/database_schema.sql
}

function destroy() {
  mysql -h$host -u $user -p$pass -P$port -e "drop database $database;"
}

function create() {
  mysql -h$host -u $user -p$pass -P$port -e "create database $database;"
}

$1
