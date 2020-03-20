# DAT251-company-presentation
School project for improving the system for signing up to company presentations.

## Setup
 1. Maven: Make sure you have maven installed and setup in enviroment-variables.
 2. MySQL: http://bit.ly/2DB2T6D download mySQL from this link if you dont already have it.

 3. git clone https://github.com/EirinS/DAT251-company-presentation.git
 4. Import project to your IDE.
 5. Create database in mysql client:  https://spring.io/guides/gs/accessing-data-mysql/

### Connect to the google cloud database
#### Connect to MySQL workbench
  - In mySQL workbench create a new connection with these parameters
  - HostName: `35.228.69.153`, Port: `3306`, Username: `dat251`, Password: ask Even or Eivind
#### Connect project to DB
In src/main/resources/application.properties add the following parameters:


spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/db_example
spring.datasource.username=springuser
spring.datasource.password=ThePassword
