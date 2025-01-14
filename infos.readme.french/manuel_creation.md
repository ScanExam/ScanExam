# Manuel d'utilisation de ScanExam : Création d'un examen

Cette page détaille la création d'un examen sur ScanExam. Pour revenir à l'introduction du manuel de ScanExam, cliquez <a href="https://github.com/ScanExam/ScanExam/blob/master/infos.readme.french/manuel_intro.md">ici</a>.

## Introduction

La première étape de la correction d'examen asistée par ScanExam, est la préparation du sujet maître. En important votre sujet d'examen dans ScanExam, vous pourrez :
- Définir le barème de l’examen en spécifiant les points de chaque question
- Indiquer la position de chaque question
- Indiquer la position où seront insérer les QR codes

Ces étapes assurent le bon déroulement des futures corrections via ScanExam et faciliteront la navigation entre les copies d'étudiants et leurs réponses.

<img src="https://raw.githubusercontent.com/ScanExam/ScanExam/master/infos.readme.french/creation_schema.png">

***Figure 1** : Schéma illustrant les étapes importantes de la création d'un examen dans ScanExam*

## Onglet Sujet

<img src="https://raw.githubusercontent.com/ScanExam/ScanExam/master/infos.readme.french/onglet_sujet.png">

***Figure 2** : Onglet Sujet*

### Création d'un nouveau sujet d'examen

Pour créer un nouveau sujet d'examen dans ScanExam, ouvrez le menu **Fichier** ![#ed1d25](https://via.placeholder.com/15/ed1d25/000000?text=+) (voir figure 2) et cliquez sur le bouton **Nouveau sujet d'examen**. Dans la nouvelle fênetre, cliquez sur le bouton **Parcourir...** et sélectionner le sujet souhaité au format PDF. Définissez ensuite le nom de votre examen dans le champ juste en dessous. Une fois ces deux étapes réalisées, vous pouvez valider.

### Navigation

Une fois le sujet chargé, vous pouvez déplacer la page affichée en la glissant en maintenant le clic droit de votre souris. Vous pouvez également zoomer à l'aide de la molette. Pour changer de page, utilisez les boutons **Précédent** et **Suivant** en bas à gauche ou le **menu entre** ![#037f04](https://via.placeholder.com/15/037f04/000000?text=+) (voir figure 2).

### Ajouter une question

Afin de les afficher lors de la phase de correction, vous devez définir les zones où sont placées vos questions. Pour cela, cliquez sur le bouton **Ajouter question** ![#fe8028](https://via.placeholder.com/15/fe8028/000000?text=+) (voir figure 2), puis sur le sujet, clic gauche et glisser. Une fois définie, vous pouvez déplacer la zone en cliquant en son center ou la redimensionner en cliquant sur ces bords.

### Gestion des questions

La colonne de gauche "**Questions**" ![#b6e620](https://via.placeholder.com/15/b6e620/000000?text=+) (voir figure 2) affiche toutes les zones de questions crées. Cliquer sur un item vous permettra de sélection de la zone souhaitée, vous pouvez également sélectionner une zone en cliquant directement sur elle. Une fois sélectionnée vous pouvez, dans la colonne de droite "**Détails de la question**" ![#feafc9](https://via.placeholder.com/15/feafc9/000000?text=+) (voir figure 2), modifier le nom et le barème de la question. Des boutons pour supprimer la zone sont également présents dans les deux colonnes.

### Ajouter zone QR code

ScanExam insère des QR codes sur les sujets d'examen afin de reconnaitre les copies à la correction. Vous pouvez choisir où seront insérer les qr codes grâce au bouton **Ajouter zone QR code** ![#4049cc](https://via.placeholder.com/15/4049cc/000000?text=+) (voir figure 2). Il ne peut y avoir qu'une zone par sujet et pour toutes les pages. Si lors de l'export aucune zone n'a été définie, une zone sera placé automatiquement en bas à gauche du sujet.

### Exporter le sujet d'examen en PDF

Une fois toutes les questions définies et le fichier enregistré vous pouvez exporter votre sujet pour l'imprimer. Pour cela, ouvrez le menu **Fichier** ![#ed1d25](https://via.placeholder.com/15/ed1d25/000000?text=+) (voir figure 2) et cliquez sur le bouton **Exporter le sujet d'examen en PDF**. Dans la nouvelle fenêtre, une option vous permet de différencier les copies ou non. Si vous choisissez cette option, chaque page aura son propre QR code, ce qui permettera à l'application de remettre les copies dans l'ordre à la correction et donc à vous, de ne pas faire ce tri. Vous devrez indiquer le nombre de sujet souhaité, vous pouvez indiquer un nombre plus élevé que nécessaire. Si vous ne différencier pas les copies, assurez-vous que des copies d'élèves de sont pas mélangées entre elles à l'importation.

### Exporter les identifiants des identifiants en qr codes

Vous pouvez exporter les identifiants des étudiants sous forme de QR codes. En les imprimant sur des étiquettes autocollantes et les collant sur les sujets, les identifiants des étudiants seront automatiquement complétés. Pour obtenir le document PDF contenant les QR codes, ouvrez le menu **Fichier** ![#ed1d25](https://via.placeholder.com/15/ed1d25/000000?text=+) (voir figure 2) et cliquez sur **Exporter les identifiants des étudiants en qr codes**. Dans la nouvelle fenêtre, chargez d'abord la liste des étudiants. La liste doit être sous la forme d'un fichier excel et les informations contenues dans les cellules doivent correspondre aux identifiant, nom, et prénom de chaque étudiant. Les champs des noms et prénoms sont facultatifs.

<img src="https://raw.githubusercontent.com/ScanExam/ScanExam/master/infos.readme.french/liste-etudiants-exemple1.png">

***Figure 3** : Exemple de liste d'étudiants*

Vous pouvez indiquer la première cellule, c'est-à-dire la plus haut à gauche, à prendre en compte en dessous. Pour l'export, vous pouvez conserver l'ordre des étudiants tel qu'il est dans votre fichier excel ou ordonner par ordre alphabétique en prenant en compte l'identifiant.
