# Sparen LangAgent

Welcome to the master repository for Sparen LangAgent, a project by Andrew Fan.

[![Build Status](https://travis-ci.com/Sparen/Spn-LangAgent.svg?branch=master)](https://travis-ci.com/Sparen/Spn-LangAgent)

Spn-LangAgent is a basic language agent that performs simple analysis tasks. It is meant to be embedded into a running web application, where it times requests and logs the amount of strings + memory each page/REST request takes. Each request is logged with a different ID so that they can be displayed in order. 

This project was tested on WebGoat.

### Usage

### Build Commands

This project uses Maven and Java 1.8.

Use `mvn package` from the parent directory (the one with this README) to build the .jar

Test suites consist of all java files prefixed with 'Test' located in src/test/java/com/spnlangagent/

Run the tests with:

```
mvn -DfailIfNoTests=false -Dtest=Test* test
```

Alternatively, running `mvn package` will run the tests as well. TravisCI has been setup with our application (see the badge at the top of the README).

### Demo



### Known Issues and Potential Features

* Originally, the plan was to provide a webpage for viewing metrics but this was dropped from the project due to complications with Spring Boot overwriting manifest files.


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

### Random Things I Learned

* MANIFEST.MF files require a new line (\n) at the end or Maven freaks out and ignores your Manifest entirely  
