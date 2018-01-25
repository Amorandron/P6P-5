# Project Big Movie - Group 5
---
## Getting Started
### Database
The database can be setup easily just by running a single  `.sql` file located in the project root. The file is called
`createdb.sql`. Don't forget to change passwords for security reasons.
```sh
$ psql -h <database host> -d <base datbase> -U <user name> -f <path to createdb.sql>  
```

### Parser
The parser takes in `.list`, `.gz` or `.zip` files from the IMDb database and parses them to structured `.csv` files. 
Additionaly, the parser will also insert the generated `.csv` files into the PostgreSQL database and structure the data.
To do this, connection details need to be provided in the `.env` file located in the resources folder.

To build parser execute
```sh
$ ./gradlew jar
```
Execute parser with:
```sh
$ java -jar <path to parser.jar> <list files directory> -a
```

To also insert into the database:
```sh
$ java -jar<path to parser.jar> <list files directory> -ai
```
To just parse a single list or gz file
```sh
$ java -jar <path to parser.jar> <list files directory> -f <filename>
```
_Note: you can not insert to the database with a single `.list` or `.gz` file._
### API (Dataprocessor)
The dataprocessor takes a connection to the database and queries it to answer questions and generate graphs. Both
the chatbot and the GUI use the API to connect to the database.

To install and run the API make sure you have your R_HOME environment variable set to your local R installation.
```sh
$ export R_HOME=/path/to/R
```
Also make sure you have all the libraries installed located in `resources/R/libs.R`, these are required to run all the 
other scripts.

Put your the path to the `lib/` folder located in the dataprocessor module as `Djava.library.path`.

Finally run the API by executing the `jooby:run` maven task.

The connection details to the database are located in the `application.conf` in the conf directory at the
root of the module.

### Chatbot
The chatbot is a discord chatbot, you can select a discord bot by passing the discord API token as the argument.

To build chatbot execute
```sh
$ ./gradlew jar
```
Run chatbot with:
```sh
$ java -jar <path to parser.jar> <discord token> 
```
### GUI
Our GUI provides a graphical way ot interact with the database, it connects the same way as the chatbot, via the API.
To build chatbot execute
```sh
$ ./gradlew jar
```
Run chatbot with:
```sh
$ java -jar <path to parser.jar> 
```
---
## Authors
#### Students
* Sven Mark Hofstra
* Lars Gerrits
* Yannick Kooistra 
* Everdien van der Werff
* Jan Wilts
#### Tutor
* Sibbele Oosterhaven 
---
