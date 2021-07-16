# Manuel d'utilisation de ScanExam : correction de l'examen

Dans cette partie, nous allons aborder la correction d'un examen dans l'application, ainsi que toutes les fonctionnalités qui sont associées à la correction.
<a href="https://github.com/ScanExam/ScanExam/blob/dev/infos.readme.french/manuel_intro.md">Retour</a>

<img src="https://github.com/ScanExam/ScanExam/blob/master/infos.readme.french/correction_schema.png?raw=true">

***Figure 1** : Schéma illustrant les étapes importantes de la correction d'un examen dans ScanExam*

Comme le montre la **figure 1** ci-dessus, la correction se déroule en plusieurs étapes; cela commence avec l'import des copies des étudiants. Il faudra donc au préalable avoir créé un examen dans l'application et l'avoir fait passer à vos élèves, pour numériser leurs copies. Une fois les copies numérisées, vous pouvez commencer la correction dans ScanExam.

<img src="https://github.com/ScanExam/ScanExam/blob/master/infos.readme.french/correction_vue.png?raw=true">

***Figure 2** : Vue de la fenêtre principale de correction. Cette image sera une référence dans le texte plus tard par le biais d'un code couleur.*

### Commencer la correction

Pour commencer, il faut importer vos copies numérisées ainsi que le modèle que vous avez créé lors de la création de l'examen dans l'application. Cet import se trouve dans le menu **Fichier -> Nouvelle correction** ![#f03c15](https://via.placeholder.com/15/f03c15/000000?text=+) (voir figure 2). Vous pourrez alors sélectionner dans une nouvelle fenêtre, votre modèle au format XMI et vos copies d'étudiants au format d'un unique PDF. Dans la fenêtre de sélection, vous aurez 2 choix, soit vous importez votre modèle, soit, si le modèle est déjà chargé depuis l'onglet de création, vous n'aurez pas besoin de le charger et le modèle sera déjà reconnu.
De plus, vous avez la possiblité de charger une correction déjà commencée. Il faudra alors vous rendre dans **Fichier -> Charger une correction** ![#f03c15](https://via.placeholder.com/15/f03c15/000000?text=+) (voir figure 2). Tout comme la nouvelle correction, il vous faudra charger le modèle de l'examen si il n'est pas déjà chargé depuis l'onglet de création, mais au lieu de chercher vos copies, vous allez directement charger un deuxième fichier au format XMI qui sera votre fichier de sauvegarde.

Une fois fait, vous serez automatiquement renvoyé sur l'onglet **Copies** ![#c5f015](https://via.placeholder.com/15/c5f015/000000?text=+)

### Identification des étudiants

Comme vu dans la partie création, ScanExam propose plusieurs types d'examens.
En effet, vous pouvez choisir en amont de placer un QRCode, directement sur le sujet ou via des autocollants, qui contiendra les informations disponibles d'un étudiant; son nom, prénom, numéro d'étudiant ou bien son numéro d'anonymat par exemple. Si c'est le cas, lors du chargement des copies, l'application sera en mesure de lier chaque étudiant à sa copie. Au cas échéant, il vous faudra importer la liste des étudiants en allant dans **Fichier -> Charger la liste des étudiants** ![#f03c15](https://via.placeholder.com/15/f03c15/000000?text=+) (voir figure 2) et lier chaque copie à son étudiant. La liste des étudiants se présente sous la forme d'un tableau Excel et reprend le nom d'un étudiant et son adresse mail. Pour effectuer la liaison, vous trouverez un petit **symbole de crayon** ![#964b00](https://via.placeholder.com/15/964b00/000000?text=+) en dessous de la liste des copies (voir figure 2 ou 3).

<img src="https://github.com/ScanExam/ScanExam/blob/master/infos.readme.french/correction_name.PNG?raw=true">

***Figure 3** : Exemple de placement du symbole du crayon afin de d'attribuer un étudiant à sa copie*

Plus tard, lors de la fin de votre correction, il est important d'importer la liste des étudiants si vous souhaitez envoyer la correction d'une copie à son étudiant, par mail.

### Déplacement dans les copies et entre les copies

Une fois l’import des informations des étudiants réalisé (automatiquement ou manuellement), tout est en place pour commencer la correction. Grâce aux boutons **Précédent** ![#0000d1](https://via.placeholder.com/15/0000d1/000000?text=+) et **Suivant** ![#0000d1](https://via.placeholder.com/15/0000d1/000000?text=+) (voir figure 2), il est possible de parcourir l’ensemble des questions des copies numérisées. La sélection d’une question spécifique d’une copie est également possible en cliquant sur la question voulue dans la **colonne de droite** ![#0000d1](https://via.placeholder.com/15/0000d1/000000?text=+) (voir figure 2) de l’application. 

La **colonne de gauche** ![#964b00](https://via.placeholder.com/15/964b00/000000?text=+) (voir figure 2), quant à elle, permet de se déplacer entre les copies. Un déplacement de la sorte garde la question courante sélectionnée, bien que la réponse affichée soit celle de la nouvelle copie. Par exemple, si la question 1 de la 1ère copie est sélectionnée, alors passer à la copie suivante mettra en avant la question 1 de la 2e copie. Un autre moyen de se déplacer entre les copies est d’utiliser les boutons **Précédent** ![#800080](https://via.placeholder.com/15/800080/000000?text=+) et **Suivant** ![#800080](https://via.placeholder.com/15/800080/000000?text=+) (voir figure 2).

Lors de la sélection d’une question, l’application isolera celle-ci, ainsi le PDF complet disparaîtra. Pour revenir au PDF, utilisez le bouton **Réinitialiser la fenêtre** ![#ffb6c1](https://via.placeholder.com/15/ffb6c1/000000?text=+) (voir figure 2) dans la barre d’outils.
Quelques informations concernant la copie de l’étudiant sont présentes en bas de la **colonne de gauche** ![#964b00](https://via.placeholder.com/15/964b00/000000?text=+) (voir figure 2). C’est aussi ici que la copie peut être attribuée à un étudiant en y saisissant son identifiant.

### Grille de correction et notation

La grille de correction et le barème sont totalement dynamiques dans ScanExam. Cela permet de pouvoir à tout moment modifier rétroactivement le poids de certaines questions que l’on pourrait juger trop dures ou trop faciles au cours de la correction.

En effet, chaque question possède une **grille d’évaluation** ![#ffff00](https://via.placeholder.com/15/ffff00/000000?text=+) (voir figure 2) auquel on peut ajouter différents critères d’évaluation en fonction des attentes du correcteur. Il est aussi possible de modifier le texte correspondant à chaque critère à l’aide d’un éditeur de texte riche, permettant ainsi de mettre de la couleur, du gras, etc (voir figure 4).

<img src="https://github.com/ScanExam/ScanExam/blob/master/infos.readme.french/correction_grader.PNG?raw=true">

***Figure 4** : Exemple d'une grille de notation*

### Superviser la correction

Lors de votre correction, ou bien à la fin de celle ci, vous aurez sûrement envire de revoir votre correction dans sa généralité. Cela sera possible grade à l'**onglet Etudiants** ![#ffa500](https://via.placeholder.com/15/ffa500/000000?text=+) (voir figure 2). En vous rendant dans cet onglet, vous aurez un aperçu des copies avec un récapitulatif de chaque question par étudiant ainsi que leur note finale (exemple en figure 5).

<img src="https://github.com/ScanExam/ScanExam/blob/master/infos.readme.french/correction_students_vue.PNG?raw=true">

***Figure 5** : Visuel de l'onglet Etudiants d'une correction terminée*

Il vous sera également possible de vous rendre à une copie précise directement depuis ce tableau. En effet, il vous suffira d'ouvrir un menu contextuel (clic droit) sur la copie qui vous intéresse, et de cliquer sur **Aller à la copie** (voir figure 5).

### Annotations

Ajouter un commentaire sur un examen est parfois nécessaire pour expliquer à l’étudiant la raison de son erreur, c’est pourquoi un panel de commentaires est disponible. 
Vous pouvez ajouter des annotations en cliquant sur le bouton **Ajouter Annotation** ![#ffb6c1](https://via.placeholder.com/15/ffb6c1/000000?text=+) (voir figure 2) dans la barre d’outils en haut de l’écran. 

<img src="https://github.com/ScanExam/ScanExam/blob/master/infos.readme.french/correction_annotations.png?raw=true">

***Figure 6** : Exemple d'une annotation et de ses boutons utiles*

De plus, pour ne pas gêner le correcteur, il est possible grâce au bouton **Visualiser Annotations** ![#ffb6c1](https://via.placeholder.com/15/ffb6c1/000000?text=+) (voir figure 2) d’afficher ou de masquer les annotations déjà présentes sur la question en cours de correction. Pour plus de flexibilité, les boîtes de texte des annotations ainsi que l’endroit où elles pointent sont déplaçables afin de s’adapter à l’espace disponible autour de la réponse (voir figure 6).


### Export et diffusion des notes

Une fois l’examen corrigé, vous pouvez exporter votre correction en allant dans **Edition -> Exporter la correction en PDF** ![#006400](https://via.placeholder.com/15/006400/000000?text=+) (voir figure 2). Cela exportera chaque copie des étudiants dans le répertoire cible en prenant en compte la note que vous aurez attribuée ainsi que les annotations.
Il est également possible d’exporter toutes les notes dans un fichier tableur excel en allant dans **Édition -> Exporter les notes** ![#006400](https://via.placeholder.com/15/006400/000000?text=+) (voir figure 2). Ces notes seront rattachées aux étudiants que vous aurez importé avec la liste des étudiants.

L’outil peut également envoyer les copies numérisées par mail aux étudiants via **Édition -> Envoyer la correction par mail** ![#006400](https://via.placeholder.com/15/006400/000000?text=+) (voir figure 2). L’envoi des mails se réfère aux adresses mails fournies lors de l’import de la liste des étudiants si toutefois vous l'avez importée.

Il est également possible de modifier vos paramètres de configuration d’envoi par mails en allant dans **Édition -> Mettre à jour la configuration** ![#006400](https://via.placeholder.com/15/006400/000000?text=+) (voir figure 2). Vous pourrez ainsi renseigner votre adresse mail et configurer le SMTP (la configuration SMTP est automatique pour les opérateurs connus). Il est recommandé de tester (par le bouton “Tester l’email”) avant de valider sous peine de ne pas pouvoir envoyer les copies aux étudiants.