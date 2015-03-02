rem test set: jUnit tests for java code
rem code quality checks: no
rem deploy: no deploy
rem intent of using: development
rem minification: no
mvn clean install -DskipTests -DskipITs -Dfindbugs.skip=true -Dpmd.skip=true -Dcheckstyle.skip=true
