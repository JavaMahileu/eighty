rem test: no
rem code quality checks: no
rem deploy: deploy on local server tomcat 7
rem intent of using: development with deploy on local server tomcat 7 that should be started before deploy
rem minification: no
mvn clean package tomcat7:deploy -DskipTests -DskipITs -Dfindbugs.skip=true -Dpmd.skip=true -Dcheckstyle.skip=true
