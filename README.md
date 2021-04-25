# What is scanexam

ScanExam is a tool to help grading scanned paper exams. 

It allows for fast navigation among examination papers and quick grading through predefined grades.


Beware this is an very preliminary version.  

# How to compile/install ?

```
cd fr.istic.tools.scanexam
mvn compile assembly:single
Move file pfo_example.pdf to `target` directory
java -jar target/ScanExam-jar-with-dependencies.jar
```

# Useful commands

## Compile

```maven
mvn compile
```

## Execute program

```maven
mvn exec:java
```

## Execute tests

```maven
mvn test
```

## Build to jar
```maven
mvn compile assembly:single
```
