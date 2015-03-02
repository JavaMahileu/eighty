rem test set: jUnit tests for java code
rem code quality checks: no
rem deploy: deploy on local server tomcat 7
rem intent of using: development
rem minification: no
mvn clean install tomcat7:deploy -DskipTests -DskipITs -Dfindbugs.skip=true -Dpmd.skip=true -Dcheckstyle.skip=true
