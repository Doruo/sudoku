# Sudoku

Implémentation du Sudoku en Java. Jouable contre un ordinateur. 

Ce projet a eu lieu dans le cadre des Situations d'Apprentissage et Évaluation de l'IUT Montpellier-Sête. 

## Les contributions des différents membres du groupe :

### Global : 

- Marc Haye - 60%
- Esteban Rémond - 40%

### SudokuBase : 

- main : Marc Haye
- saisirEntierMinMax : Marc Haye
- copieMatrice : Esteban Rémond
- ensPlein : Marc Haye et Esteban Rémond
- supprime : Marc Haye
- uneValeur : Marc Haye
- afficheGrille : Marc Haye
- debCarre : Esteban Rémond
- initGrilleComplete : Marc Haye
- initGrilleIncomplete : Marc Haye
- saisirGrilleIncomplete: Esteban Rémond et Marc Haye
- initPleines : Esteban Rémond
- suppValPoss : Esteban Rémond
- initPossibles : Marc Haye
- initPartie : Marc Haye
- tourHumain : Marc Haye
- chercheTrou : Esteban Rémond et Marc Haye
- tourOrdinateur : Esteban Rémond
- partie : Marc Haye

### SudokuExtensions : 

- 3.1 (1 point): Marc Haye
- 3.2 (1 point): Esteban Rémond et Marc Haye
- 3.4 (2 points): Esteban Rémond

### Débugage et test 

Rémond Esteban et Marc Haye

Le contenu des différents fichiers :

- SudokuBase : Contient la version completé du sudoku de base.
- SudokuExtensions : Contient les extensions 3.1, 3.2 et 3.4

Les stratégies utilisées ou les raisonnements effectués dans certaines extensions :

A l'extension 3.4 pour réaliser "l'échange de 2 lignes passant par les mêmes carrés, paramétré par les deux numéros de ligne". Nous avons choisi la ligne1 de manière aléatoire et ensuite, nous avons déterminé la position de cette ligne dans son carré (en haut, milieu ou en bas) à l'aide de modulo 3. 
- Si la ligne1 est en haut du carré, la ligne2 prendra donc comme valeur soit : ligne1+1 ou ligne1+2.
- Si la ligne1 est au milieu du carré, la ligne2 prendra donc comme valeur soit : ligne1+1 ou ligne1-1.
- Si la ligne1 est en bas du carré, la ligne2 prendra donc comme valeur soit : ligne1-1 ou ligne1-2.
- La ligne1 et la ligne2 sont ensuite placé en paramètre de la fonction "echange".