<img align="right" src="https://github.com/ScanExam/ScanExam/blob/master/fr.istic.tools.scanexam/src/main/resources/logo.png?raw=true">

# Qu'est-ce que ScanExam ?

ScanExam est un outil d'assistance à la correction d'examen papier, à destination d'enseignants.
Il permet une navigation rapide entre les copies, et une correction qui l'est tout autant suite à une grille de correction définissable et dynamique.


Attention, l'application est encore en phase de développement et peut donc avoir de (nombreux) bugs ! Si vous y êtes confrontés, n'hésitez pas à les reporter dans
l'onglet "Issues" de Github.

# Manuel d'utilisation de ScanExam

L'application ScanExam est fournie avec un manuel d'utilisation. Cela permet dans un premier temps de vous familiariser avec ses fonctionnalités, et également de vous montrer un déroulement classique d'utilisation.
Vous pouvez vous y rendre en cliquant juste <a href="https://github.com/ScanExam/ScanExam/blob/master/infos.readme.french/manuel_intro.md">ici</a> !

# Comment installer le programme ?

# Comment build le programme ?

```
cd fr.istic.tools.scanexam
mvn compile assembly:single
Move file pfo_example.pdf to `target` directory
java -jar target/ScanExam-jar-with-dependencies.jar
```

## Autres commandes utiles

### Compilation

```maven
mvn compile
```

### Exécuter directement le programme

```maven
mvn exec:java
```

### Exécuter les tests

```maven
mvn test
```

### Build le jar

```maven
mvn compile assembly:single
```
Le jar se trouvera alors dans le dossier ``target``.
