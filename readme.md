# DAT251-company-presentation
School project for improving the system for signing up to company presentations.

## Setup
git clone https://github.com/EirinS/DAT251-company-presentation.git

### Connect to the google cloud database
#### Connect to MySQL workbench
  - In mySQL workbench create a new connection with these parameters
  - HostName: `35.228.69.153`, Port: `3306`, Username: `dat251`, Password: ask Even or Eivind
#### Connect project to DB
In resources/application.properties add the following parameters:
`spring.cloud.gcp.sql.database-name=[YOUR_DB_NAME]
spring.cloud.gcp.sql.instance-connection-name=[PROJECT_ID]:[ZONE]:[INSTANCE-NAME]
spring.datasource.password=[YOUR_PWD]
spring.datasource.username=[YOUR_USERNAME]
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQL5InnoDBDialect

You also need to authenticate to Google cloud with:
`gcloud auth application-default login`