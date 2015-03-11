#!/bin/sh
echo "test: yes (jUnit tests for java and javascript, integration tests)"
echo "code quality checks: yes (findbugs, pmd, checkstyle)"
echo "deploy: no deploy"
echo "intent of using: production"
echo "minification: yes"
mvn clean verify -Pprod $1
