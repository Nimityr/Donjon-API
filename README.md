## 	RAPPORT TECHNIQUE
 

 

Nous avons créé un dossier test où sont tous nos fichiers permettant l'interface graphique et un dossier MP où sont tous les fichiers du modèle persistant. 

 

## PARTIE TEST

 

## Fenetre.java : 

Ce fichier correspond à l'affichage de la fenêtre principale avec l'instanciation de nos objets comme l'inventaire du joueur, du coffre, la mini-map, le chargement des images... 

La fonction run est exécutée en permanence pour mettre à jour la fenêtre en redessinant les différents composants graphiques, comme l'inventaire, la mini-map... 

 

## Textures.java : 

Ce fichier permet de charger toutes les images dans le dossier res/images, que ce soit l'image d'une porte ouverte, fermée, les différents objets (clé, bouteille d'eau, de rhum et trésor). 

 

## Menu.java : 

Nous pouvons accéder à un menu en appuyant sur échap. Ce menu contient les différentes actions que l'on peut faire avec leurs commandes respectives. Par exemple, Ouvrir l'inventaire de la pièce (les différents objets que contient la pièce dans laquelle on se trouve : appuyer sur I. 

 

## InventaireSac.java : 

Ce fichier correspond à l'inventaire du joueur, situé en bas au centre de l'écran. 

Nous pouvons naviguer dans notre inventaire en scrollant (en bougeant la molette de la souris). 

Lorsque l'on clique droit sur notre souris, nous pouvons utiliser l'objet sélectionné à l'aide du scroll. 

 

## Coffre.java : 

Ce fichier correspond à l'inventaire du coffre. Lorsque l'on appuie sur I, l'inventaire de la pièce dans laquelle on se trouve est affiché. Ainsi, grâce au drag and drop (glisser-déposer), nous pouvons déplacer les objets d'un inventaire à un autre (de l'inventaire du joueur à l'inventaire de la pièce et inversement). 

 

## Map.java : 

Ce fichier permet de créer la map du jeu (positionner les murs, les portes à un endroit précis...). 

 

## ActionDoor.java : 

Ce fichier permet d'agir sur une porte. Lorsque le joueur regarde une porte, une annonce est affichée (par exemple : Appuyer sur E pour fermer la porte). 

Ces annonces sont gérées automatiquement par 3 fichiers que sont Indication.java, IndicationUpdate.java et enfin IndicationHandler.java. 

 

## Camera.java : 

Ce fichier permet de mettre en place une caméra (l'écran que l'on voit en permanence qui correspond à la vision du joueur) et de la faire bouger selon les touches du clavier. 

 

## Coord.java : 

Ce fichier correspond aux coordonnées (x, y) d'un objet (par exemple une porte). Il permettra entre autres de savoir si le joueur (la caméra) regarde bien en direction d'une porte. 

 

## Mini-map.java : 

Ce fichier permet d'afficher la mini-map situé en haut à droite de la fenêtre. 

Lorsque l'on quitte une pièce, la pièce qui a été visité est affiché dans la mini-map. Si l'on utilise une bouteille de rhum, l'ensemble des pièces visitées ne sont plus affichées dans la mini-map. 


## Controller.java

Les listeners sont placés dans ce fichier.

 

## PARTIE API ET MODELE PERSISTANT

 

 

Nous avons créé 3 tables dans notre base de données : 

-Pieces

-Passages

-Trucs
 

Dans la table Piece, sont stockés les identifiants de chaque pièce, un booléen (visited) qui vaut 1 si la pièce a été visité par le joueur et un booléen (joueur) qui vaut 1 si le joueur se trouve actuellement dans cette pièce.
 
Dans la table Passage, sont stockés les identifiants (id) de chaque passage, ainsi que leur direction (dir1 et dir2) et leur état (etat) comme OPEN, CLOSED ou LOCKED.

Dans la table Truc, seront stockées les identifiants (contientTruc_id) de chaque truc, leur type (typeTruc) comme CLE, ALCOOL, EAU ou GOODIES et leur description (description). 

 
## ConnexionBDD.java : 

Ce fichier permet de se connecter à la base de données et de créer les 3 tables si elles n'existent pas.

 

## JoueurP.java : 

Ce fichier permet d'afficher une boîte de dialogue qui nous demande si l'on souhaite reprendre notre partie précédente. Si c'est votre première partie, cette boîte de dialogue ne s'affichera donc pas. Pour réaliser le fait de pouvoir reprendre une partie, nous interagissons avec la base de données. 

Nous avons redéfinis la méthode addTruc pour pouvoir insérer dans la base de données les items qui sont dans l'inventaire du joueur, et la méthode removeTruc pour supprimer dans la base de données les items devant être supprimés. En effet, lorsque l'on utilise un item en cliquant droit sur notre souris, alors il est supprimé de l'inventaire du joueur. 

Dans la méthode loadPartie (méthode permettant de récupérer la partie) , nous séléctionnons toutes les données des 3 tables pour créer les trucs (newTruc), les pièces (newPiece) et les passages (newPassage).

Si le joueur ne souhaite pas reprendre sa partie, Nous effaçons tous les tuples des 3 tables de la base de données.

Dans la méthode isVisited, nous sélectionnons les pièces qui ont été visitées. 

Dans la méthode setPiece, nous mettons à 1 le champ joueur permettant de détecter la pièce actuelle dans laquelle se trouve le joueur. 

Dans la méthode agir, nous avons rajouté une requête SQL pour mettre toutes les pièces visitées à false lorsque le joueur utilise une bouteille de rhum. 

 

## PieceP.java : 

Nous avons redéfinis la méthode addTruc pour pouvoir insérer dans la base de données les items qui sont dans l'inventaire de chaque pièce, et la méthode removeTruc pour supprimer dans la base de données les items devant être supprimés. En effet, lorsque l'on prend un objet qui se trouvait dans une pièce pour le mettre dans l'inventaire du joueur, il faut alors supprimer l'objet en question de l'inventaire de la pièce. 

 

## PassageP.java :

Dans la méthode SetEtatPassage, nous mettons à jour le champ etat de la table Passages dans la base de données.



## PassagePieceFactoryP.java :

Dans la méthode newPiece, nous insérons dans la base de données l'identifiant de la pièce qui doit être créer.

Dans la méthode newPassage, nous insérons l'identifiant, la direction et l'état du passage qui doit être créer.



## CAS DE TESTS

 

## Premier test : 


-Appuyer sur “i” pour ouvrir l’inventaire de la pièce 

-Prendre la clé de porte en la déplaçant dans votre inventaire (inventaire du joueur) 

-Appuyer sur “i” pour fermer l’inventaire 

-Bouger le joueur à l’aide des flèches directionnelles afin qu’il regarde la porte fermée à clé 

-Naviguer dans votre inventaire à l’aide de la molette de la souris jusqu’à ce que la clé de porte soit sélectionnée 

-Utiliser la clé en appuyant sur clique droit 

-Appuyer sur E pour ouvrir la porte 

-Faire avancer le joueur pour qu’il puisse rentrer dans la porte et donc changer de pièce 

-Fermer la fenêtre 

 

## Deuxième test : 

-Cliquer sur oui pour reprendre votre partie 

-Appuyer sur “i” pour ouvrir l’inventaire de la pièce 

-Déposer un item de votre inventaire dans la pièce en la déplaçant dans l’inventaire de la pièce 

-Appuyer sur “i” pour fermer l’inventaire 

-Bouger le joueur à l’aide des flèches directionnelles afin qu’il regarde une porte fermée 

-Appuyer sur E pour ouvrir la porte 

-Faire avancer le joueur pour qu’il puisse rentrer dans la porte et donc changer de pièce 

-Fermer la fenêtre 

 

## Troisième test : 

-Cliquer sur oui pour reprendre votre partie 

-Naviguer dans votre inventaire à l’aide de la molette de la souris jusqu’à ce que la bouteille de rhum soit sélectionnée 

-Utiliser la bouteille de rhum en appuyant sur clique droit 

-Fermer la fenêtre 



## Quatrième test : 

-Cliquer sur non pour ne pas reprendre votre partie 

-Appuyer sur “i” pour ouvrir l’inventaire de la pièce 

-Prendre la clé de porte en la déplaçant dans votre inventaire (inventaire du joueur) 

-Appuyer sur “i” pour fermer l’inventaire

-Bouger le joueur à l’aide des flèches directionnelles afin qu’il regarde la porte fermée à clé 

-Naviguer dans votre inventaire à l’aide de la molette de la souris jusqu’à ce que la clé de porte soit sélectionnée 

-Utiliser la clé en appuyant sur clique droit 

-Appuyer sur E pour ouvrir la porte 

-Faire avancer le joueur pour qu’il puisse rentrer dans la porte et donc changer de pièce 

-Fermer la fenêtre 



## Commandes à exécuter

-make

-make jar

Pour exécuter la version avec le modèle non persistant :

java -jar projet.jar MNP

Pour exécuter la version avec le modèle persistant :

java -jar projet.jar MP