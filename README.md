# ACDC-project

## Static / dynamic website

Ce projet a été réalisé dans le cadre de la formation d'ingénieur spécialité ingénierie logicielle lors de la première année.

***
## Introduction
##### Contexte
Ce projet a été mené dans le cadre de de l'UV ACDC du semestre 1 en Filière Ingénièries Logcielle. Le but du projet est de permettre l'ajout de publication à un site web statique de façon dynamique. Le rendu final attendu est une interface facilitant la création de publication. Le projet est découpé en deux phases, dans la première une version on se concentre sur une API en proposant simplement une interface en ligne de commande. A l'issus de cette phase, nous échangeons le code au sein du groupe, afin de débuter la deuxième phase: à partir d'un code qui nous est inconnu, nous réalisons une interface graphique.
##### Technologie
Le projet est développé en Java, le site statique est directement hébergé sur Gitlab et utilise Gitlab pages, une fonctionnalité permettant de publier un site web statique directement depuis un répertoire gitlab. Le site web statique est produit grâce à l'outil jekyll.

##### Organisation et roadmap
Afin de suivre l'avancement du projet, il est attendu chaque semaine un fichier log permettatn de rendre compte de l'activité effectuée sur le projet. A ceci s'ajoute un tableau d'avancement permettant d'évaluer les fonctionnalités faites et celles encore à faire.


## A propos du projet

## Prérequis
Afin que le programme fonctionne correctement il faut vérifier plusieurs choses :

- Avoir une installation de **java 8**.
- Etre connecté en SSH à Git sur votre terminal ou tout du moins avoir stocké ses identifiants. (Plus de détails [ici](https://help.github.com/articles/generating-a-new-ssh-key-and-adding-it-to-the-ssh-agent/)).
- Avoir installé jekyll (Plus de détails [ici](https://jekyllrb.com/docs/installation/)).
- Créer un fichier Categories.txt dans le dossier category/ du blog jekyll contenant les catégories souhaitées pour les publications. L'interface permet de mettre à jour ces catégories par la suite.

***

## Lancement du programme

1. Récupérer le jar du dossier rendu_final/ et le placer directement dans le dossier du site.
3. Ouvrir votre shell, se placer dans le dossier du site et executer `java -jar ACDC.jar`

#### Logs ####

__28/11/2018 :__
Réunion premier bilan avec tout le monde, débrief et orientation pour la suite du projet. 

__05/12/2018 :__
Evaluation du code de Jordan. Découverte du diagramme de classe et fonctions de bases.

__12/12/2018 :__
Evaluation des solutions possibles. Choix de JavaFX pour l'efficacité et la gestion de HTML. De plus, c'est l'occasion de découvrir JFX pour moi. 
Création d'une interface de base.

__19/12/2018 :__
Ajout des fonctions d'édition de texte de base:
- Bold
- Italic
- Ajout d'image
- Ajout de lier
- Ajout de titre


__28/12/2018 :__
Semaine sans activité.

__08/01/2019 :__
- Ajout d'un rendu HTML du markdown.
- Ajout de la gestion de jekyll
- Ajout de la gestion de Git
- Finalisation de l'interface

__016/01/2019 :__
- Gestion des dépedances par Maven pour une meilleure portabilité
- Gestion du path sans besoin d'intervention de l'utilisateur.
