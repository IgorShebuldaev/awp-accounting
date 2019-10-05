# Automated workplace: Accounting
## What is it?
The program to work with accounting as you may see.
## How do you make it work?
To mount a database, create a file database.properties and fill in the example 
src/main/resources/database.properties.example.
Then use the file seed.sql to fill the system tables in the program, 
otherwise you will not be able to log in.
## Gradle task
* Loads database schema.
```
load
```
* Creates dump of database's structure.
```
dump
```
* Destroys database.
```
destroy
```
* Creates empty database.
```
create
```
* Applies seeds to database.
```
seed
```
* Creates new database and applies schema.
```
setup_db
```
