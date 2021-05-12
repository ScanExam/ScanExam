<img align="right" src="https://github.com/ScanExam/ScanExam/blob/master/fr.istic.tools.scanexam/src/main/resources/logo.png?raw=true">

# Qu'est-ce que ScanExam ?

ScanExam est un outil d'assistance à la correction d'examen papier, à destination d'enseignants.
Il permet une navigation rapide entre les copies, et une correction qui l'est tout autant suite à une grille de correction définissable et dynamique.


Attention, l'application est encore en phase de développement et peut donc avoir de (nombreux) bugs ! Si vous y êtes confrontés, n'hésitez pas à les reporter dans
l'onglet "Issues" de Github.

# Comment installer le programme ?

# Comment build le programme ?

```
cd fr.istic.tools.scanexam
mvn compile assembly:single
java -jar target/ScanExam-jar-with-dependencies.jar
```

## Autres commandes utiles

### Compilation

```maven
mvn compile
```

### Exécuter directement le programme

```maven
<<<<<<< HEAD
mvn compile exec:java
```

## Execute tests
=======
mvn exec:java
```

### Exécuter les tests
>>>>>>> branch 'master' of https://github.com/ScanExam/ScanExam.git

```maven
mvn test
```

### Build le jar

```maven
mvn compile assembly:single
```
<<<<<<< HEAD
=======
Le jar se trouvera alors dans le dossier ``target``.
>>>>>>> branch 'master' of https://github.com/ScanExam/ScanExam.git
