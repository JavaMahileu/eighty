rem test: yes (jUnit tests for java and javascript, integration tests)
rem code quality checks: yes (findbugs, pmd, checkstyle)
rem deploy: no deploy
rem intent of using: production
rem minification: yes
rem we can modify port for embedded tomcat 7 server by passing argument "-Dtomcat.port=_port_number_"
rem this can be useful to suppress port conflict during executing 2 synchronous Jenkins jobs
mvn clean verify -Pprod -Dspring.profiles.active=test %1
