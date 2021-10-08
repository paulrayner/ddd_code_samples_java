# Sample code for domain-driven design (DDD) workshop exercises in Java

The goal of the coding exercises is to apply targeted refactoring towards a richer domain language and certain tactical domain patterns on a small amount of sample code.

We are going to pretend that we are members of the warranty application team for an extended warranty company, and that this repository contains some representative code for the core business logic handled by this system. You will need to imagine that the rest of the application exists, but is just not included here.

N.b.: This code has a lot of issues. This is deliberate. For example, the majority of the objects are devoid of behavior and there is little documentation. Also, the tests may be incomplete, poorly named, duplicated, and intermixed with the production code. Accept that we are not going to fix most of the issues. Instead, focus on applying the techniques and patterns taught in the workshop to see the impact they can have.

## Installation Guide

Load the project files from the `main` branch into a new project in your IDE, get the dependencies, then build the code and run all unit tests to make sure they pass.

This code has been tested successfully on IntelliJ IDEA Community Edition (build 2021.2.2) using Java 8 (OpenJDK 17). Unit tests use [Junit 5](https://junit.org/junit5/). 