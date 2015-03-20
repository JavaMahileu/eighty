rem test: no
rem code quality checks: no
rem deploy: deploy on embedded server tomcat 7
rem intent of using: development with starting embedded tomcat 7 server
rem minification: no
rem we can modify port for embedded tomcat 7 server by passing argument "-Dtomcat.port=_port_number_"
mvn clean tomcat7:run-war -Pdev -DskipTests -Dspring.profiles.active=dev %1
