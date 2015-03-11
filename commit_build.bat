rem test: yes (jUnit tests for java and javascript, integration tests)
rem code quality checks: yes (findbugs, pmd, checkstyle)
rem deploy: no deploy
rem intent of using: production
rem minification: yes
mvn clean verify -Pprod %1
