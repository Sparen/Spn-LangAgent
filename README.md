# Sparen LangAgent

Welcome to the master repository for Sparen LangAgent, a project by Andrew Fan.

[![Build Status](https://travis-ci.com/Sparen/Spn-LangAgent.svg?branch=master)](https://travis-ci.com/Sparen/Spn-LangAgent)

Spn-LangAgent is a basic language agent that performs simple analysis tasks. It is meant to be embedded into a running web application, where it times requests and logs the amount of strings + memory each page/REST request takes. Each request is logged with a different ID so that they can be displayed in order. 

This project was made using Spring Boot via the Initializr, and was tested on WebGoat.

### Usage


### Build Commands

This project uses Maven and Java 1.8. If running locally, the application will launch on localhost:8081

Test suites consist of all java files prefixed with 'Test' located in src/test/java/com/spnlangagent/

Run the tests with:

```
mvn -Dtest=Test* test
```

Alternatively, running `mvn package` will run the tests as well. TravisCI has been setup with our application (see the badge at the top of the README).

### Demo



### Known Issues and Potential Features


### Makefile Commands

Current Makefile commands (for reference):

```
make clean
```
Removes all build files and emacs temporary files

```
make javadoc
```
Creates documentation based off of Javadoc

