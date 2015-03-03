rem test: no
rem code quality checks: no
rem deploy: no deploy
rem intent of using: development without deploy
rem minification: no
mvn clean package -DskipTests -DskipITs -Dfindbugs.skip=true -Dpmd.skip=true -Dcheckstyle.skip=true
