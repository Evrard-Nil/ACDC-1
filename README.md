# ACDC-project

## Static / dynamic website

Ce projet a été réalisé dans le cadre de la formation d'ingénieur spécialité ingénierie logicielle lors de la première année.

***

## A propos du projet

## Prérequis
Afin que le programme fonctionne correctement il faut vérifier plusieurs choses :

- Avoir une installation de java 8 ou plus.
- Avoir configurer dans le fichier properties (dossier resources) le dossier local du site web.
- Etre connecté en SSH à Git sur votre terminal ou tout du moins avoir stocké ses identifiants. (Plus de détails [ici](https://help.github.com/articles/generating-a-new-ssh-key-and-adding-it-to-the-ssh-agent/)).
- Avoir installé jekyll (Plus de détails [ici](https://jekyllrb.com/docs/installation/)).

***

## Lancement du programme

1. Récupérer les fichiers du dossier rendu_final/ et les placer directement dans le dossier du site.
2. Changer les chemins `localRepo` dans le fichier config.properties
3. Ouvrir votre shell, se placer dans le dossier du site et executer `java -jar jekyll_article_builder`

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