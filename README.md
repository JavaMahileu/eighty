# Eighty

## About Eighty

Backgrounds of the project were to learn to use new technologies, to participate in development of real applications, to gain experience of work in team.

Eighty is a single page web-application, that works through REST-service with graph database. This is a base of knowledge with questions and answers on different topics that lets to use the application as self-knowledge, testing and as questions for interview.
 
Goals at the beginning of the project:

  *  All the questions are divided by topics that are saved in the tree.
  *  User can add new questions, topics, update and delete existing questions and topics.
  *  There is a filter by tags, contents.
  *  Every user can increase question rating.

## Used technologies
  
A lot of technologies from the widely used to highly specialized are used in project. Graph-oriented database Neo4j, Spring Framework (REST-service and Spring Data) are used as back-end. Bootstrap, jQuery, AngularJS are used as front-end. Application testing is based on JUnit, Karma, Protractor. Project building performs using Maven and Grunt. Package management is performed by Npm and Bower. Swagger is used for API project documentation.  

Client works with data saved in Neo4j, through the client part provided by AngularJS and the server part of Spring (REST-service and Spring Data). Pattern [MVC] (http://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller), implemented in AngulrJS is used on the client side. Server-side caching for recoverable data in queries is implemented too.

Neo4j is a high-performance, NoSQL database with oriented graph structure. It is not a such thing as a table with strictly specified fields it operates flexible structure as nodes and links between them. As a result of project development, we have a central repository of questions on various topics with the ability to filter by tags, words and answers. All questions are divided into specific topics and can change their rating. User can create list of questions to export and then print it. 

Messages about controllers methods calls are written in file `eighty.log`, that is located in directory `logs`, and output to the console. There is collect Codahale Metrics and you can view their values using jconsole, selecting tab `MBeans metrics`, the metric that you interesting in and then selecting Attributes.

Used technologies: 

1. [AngularJS](https://angularjs.org/) - javascript-framework of Google team.
2. [Neo4j](http://neo4j.com/) - database. 
3. [Spring](http://spring.io/) - Java-framework to create a REST-service and database access.
4. [Bootstrap](http://getbootstrap.com/) - style framework for html-pages.

## Environment

To run the project, you must install [Maven 3](http://maven.apache.org/download.cgi) and [msysgit](http://msysgit.github.io/) .

Version control is carried out by Github. You need to install [HubFlow](https://github.com/datasift/gitflow) to work with Github project. To work with HubFlow you should download [cygwin](https://www.cygwin.com/) or use `Git Bash`. 

You should download sources from Github after environment installation, using command 

`git clone https://github.com/EPAMMogilev/eighty`

There are [demo](http://evbymogsd0030.minsk.epam.com:7080/eighty/)-version and [sonar](http://evbymogsd0030/sonar/dashboard/index/1908) of the project. Each pull request building is performed automatically on [CI server] (http://evbymogsd0030.minsk.epam.com/ci/view/eighty/) . 

To run integration tests you should download and install [Firefox Portable 34.0.5](http://mozilla-firefox-portable.en.uptodown.com/download/92825) and add environment variable: 

`FF_PORTABLE = path_to_Firefox_Portable_directory/App/firefox.exe`

## Building Eighty

There are following .bat-files for project building:
* dirty_build.bat - development version is built, unit-tests and integration tests, Code Quality checks using findbugs, pmd, checkstyle are not performed. 
* commit_build.bat - production version is built, all the tests, jUnit and integration, Code Quality checks using findbugs, pmd, checkstyle are performed. 
* dirty_deploy.bat - development version is built, unit-tests and integration tests, Code Quality checks using findbugs, pmd, checkstyle are not performed. Build is deployed on the local server tomcat 7.
 
