### Carina's BackEnd

- Build backend with Spring Boot (OpenJDK v11.0.4 and Junit 4)
- Build Spring Boot with Gradle (v5.2.1)
- Store data in MYSQL (v5.5)
- Pull live data from LTA DataMall with AWS Lambda
- Host on AWS
- Test and build on Ubuntu 18

### Setup of MySQL
- Enter MySQL
```$xslt
sudo mysql
```
- Create user
```$xslt
CREATE USER 'lta'@'localhost' IDENTIFIED BY 'lta';
GRANT ALL PRIVILEGES ON * . * TO 'lta'@'localhost';
FLUSH PRIVILEGES;
```
- Create database
```
CREATE DATABASE lta;
```
- Upload MySQL dump
```
mysql -u lta -p lta < carina.sql
```

### Run the backend
- Install Java 11 and Gradle 5.6.2
- Execute the command
```$xslt
./gradlew bootRun
```

### Swagger API
- Point browser to http://localhost:8080/swagger-ui.html to view endpoints

### Unit and Integeration Test
- Run integration and unit test
```$xslt
./gradlew test
```
- Check the code style
```$xslt
./gradlew check
```

### Resources
- https://www.tutorialspoint.com/spring_boot/spring_boot_enabling_swagger2.htm
- https://www.nexsoftsys.com/articles/how-to-deploy-spring-boot-application-in-aws-ec2-server-quickly.html
- https://geekyneha.wordpress.com/2018/11/17/how-to-deploy-spring-boot-application-on-aws-ec2/
- https://www.callicoder.com/spring-boot-spring-security-jwt-mysql-react-app-part-1/
- https://www.callicoder.com/spring-boot-security-oauth2-social-login-part-1/
- https://spring.io/guides/gs/accessing-data-mysql/
