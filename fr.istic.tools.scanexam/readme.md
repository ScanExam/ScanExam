java -jar target/scanexam-0.1.0-SNAPSHOT-shaded.jar scanexam-0.1.0-SNAPSHOT-shaded.jar examples/sample.xmi# What is scanexam

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

You can specify Graphic Library to use by adding program argument:
```maven
mvn exec:java -D exec.args="-javafx"
```

## Execute tests

```maven
mvn test
```

## Build to jar
```maven
mvn compile assembly:single
```

# Program arguments


| Argument |  Description |
|----------|---------------------------------|
| -javafx | Run program with JavaFX library |
| -swing  | Run program with Swing library  |
 
