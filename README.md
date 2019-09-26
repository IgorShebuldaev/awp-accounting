# Automated workplace: Accounting
## What is it?
The program to work with accounting as you may see.
## How do you make it work?
To mount a database, create a file database.properties and fill in the example 
src/main/resources/database.properties.example
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
* Creates new database and applies schema.
```
setup_db
```
