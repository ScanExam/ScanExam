# Manuel d'utilisation de ScanExam: correction de l'examen

Dans cette partie, nous allons aborder la correction d'un examen dans l'application, ainsi que toutes les fonctionnalités qui sont associées à la correction.

<img align="center" src="https://github.com/ScanExam/ScanExam/blob/master/infos.readme.french/correction_schema.png?raw=true">

***Figure 1** : Schéma illustrant les étapes importantes de la correction d'un examen dans ScanExam*

Comme le montre la **figure 1** ci dessus, la correction se déroule en plusieurs étapes; cela commence avec l'import des copies des étudiants. Il faudra donc au préalable avoir créé un examen dans l'application et l'avoir fait passer à vos élèves, pour numériser leurs copies. Une fois les copies numérisées, vous pouvez commencer la correction dans ScanExam.

<img align="center" src="https://github.com/ScanExam/ScanExam/blob/master/infos.readme.french/correction_vue.png?raw=true">

***Figure 2** : Vue de la fenêtre principale de correction*

### 1. Commencer la correction

Pour commencer, il faut importer vos copies numérisées ainsi que le modèle que vous avez créé lors de la création de l'examen dans l'application. Cet import se trouve dans le menu **Fichier -> Nouvelle correction** (figure 2). Vous pourrez alors sélectionner dans une nouvelle fenêtre, votre modèle au format XMI et vos copies d'étudiants au format d'un unique PDF. Dans la fenêtre de sélection, vous aurez 2 choix, soit vous importez votre modèle, soit, si le modèle est déjà chargé depuis l'onglet de création, vous n'aurez pas besoin de le charger et le modèle sera déjà reconnu.
De plus, vous avez la possiblité de charger une correction déjà commencée. Il faudra alors vous rendre dans **Fichier -> Charger une correction** (figure 2). Tout comme la nouvelle correction, il vous faudra charger le modèle de l'examen si il n'est pas déjà chargé depuis l'onglet de création, mais au lieu de chercher vos copies, vous allez directement charger un deuxième fichier au format XMI qui sera votre fichier de sauvegarde.

### 2. Identification des étudiants

Comme vu dans la partie création, ScanExam propose plusieurs types d'examens. En effet, vous pouvez choisir en amont de placer un QRCode, directement sur le sujet ou via des autocollants, qui contiendra les informations disponibles d'un étudiant; son nom, prénom, numéro d'étudiant ou bien son numéro d'anonymat par exemple. Si c'est le cas, lors du chargement des copies, l'application sera en mesure de lier chaque étudiant à sa copie. Au cas échéant, il vous faudra importer la liste des étudiants en allant dans **Fichier -> Charger la liste des étudiants** (figure 2)