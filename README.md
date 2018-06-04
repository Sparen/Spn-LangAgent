# Sparen LangAgent

Welcome to the master repository for Sparen LangAgent, a project by Andrew Fan.

[![Build Status](https://travis-ci.com/Sparen/Spn-LangAgent.svg?branch=master)](https://travis-ci.com/Sparen/Spn-LangAgent)

Spn-LangAgent is a basic language agent that performs simple analysis tasks. It is meant to be embedded into a running web application, where it times requests and logs the amount of strings + memory each page/REST request takes. Each request is logged with a different ID so that they can be displayed in order. 

This project was made using Spring Boot via the Initializr, and was tested on WebGoat.

### Usage

### Build Commands

This project uses Maven and Java 1.8. If running locally, the application will launch on localhost:8081

This project consists of a main parent pom.xml, as there are two seperate components in this project - the agent and the runtime. Each of these has its own pom.xml and builds to a seperate .jar.

Use `mvn package` from the parent directory (the one with this README) to build the .jar

Test suites consist of all java files prefixed with 'Test' located in runtime/src/test/java/com/spnlangagent/

Run the tests with:

```
mvn -DfailIfNoTests=false -Dtest=Test* test
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
Creates documentation for runtime (not the agent) based off of Javadoc

