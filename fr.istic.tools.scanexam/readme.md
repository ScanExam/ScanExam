java -jar target/scanexam-0.1.0-SNAPSHOT-shaded.jar scanexam-0.1.0-SNAPSHOT-shaded.jar examples/sample.xmi# What is scanexam

ScanExam is a tool to help grading scanned paper exams. 

It allows for fast navigation among examination papers and quick grading through predefined grades.


Beware this is an very preliminary version.  

# How to compile/install ?

```
cd fr.istic.tools.scanexam
mvn compile assembly:single
java -jar target/ScanExam-jar-with-dependencies.jar
```
 