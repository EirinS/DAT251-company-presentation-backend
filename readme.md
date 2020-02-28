# DAT251-company-presentation
School project for improving the system for registrating for company presentations.

Use npm to do installs

## Setup
git clone https://github.com/EirinS/DAT251-company-presentation.git

### For eclipse:
1. Import projects.
2. Existing Maven projects
3. Locate DAT251-company-presentation
4. Wait for eclipse to download sources and javadocs, may take a little while.

### Connect to the google cloud database
#### Connect to MySQL workbench
  - In mySQL workbench create a new connection with these parameters
  - HostName: 35.228.69.153, Port: 3306, Username: dat251, Password: ask Even or Eivind
#### Connect project to DB
In resources/application.properties add the following parameters:
spring.cloud.gcp.sql.database-name=[YOUR_DB_NAME]
spring.cloud.gcp.sql.instance-connection-name=[PROJECT_ID]:[ZONE]:[INSTANCE-NAME]
spring.datasource.password=[YOUR_PWD]
spring.datasource.username=[YOUR_USERNAME]
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQL5InnoDBDialect
