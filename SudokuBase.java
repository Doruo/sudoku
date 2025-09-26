import java.util.*;
import java.io.IOException; // MODIFICI
import java.io.BufferedReader; // MODIFICI
import java.io.FileReader; // MODIFICI

public class SudokuBase {

    /** pré-requis : aucun
     *  action :     effectue une partie de Sudoku entre le joueur humain et l'ordinateur
     *               et affiche qui a gagné
     */
    public static void main(String[] args){

        /* ------------ PROGRAMME PRINCIPALE ------------ */

        int gagnant =  (partie());

        if (gagnant==0)System.out.println("\nMatch Nul !\n");
        else if (gagnant==1) System.out.println("\nLe joueur humain a gagné !\n");
        else if (gagnant==2) System.out.println("\nLe joueur ordinateur a gagné !\n");

    }  // fin main


    //.........................................................................
    // Fonctions utiles
    //.........................................................................

    /** pré-requis : min <= max
     *  résultat :   un entier saisi compris entre min et max, avec re-saisie éventuelle jusqu'à ce qu'il le soit
     */
    public static int saisirEntierMinMax(int min, int max){

        Scanner input = new Scanner(System.in);
        int val;

        System.out.println("Saisissez un nombre entre "+min+" et "+max);
        val = input.nextInt();

        while (val < min || val > max) {
            System.out.println("Resaisissez un nombre entre "+min+" et "+max);
            val=input.nextInt();
        }

        input.close();
        return val;
    }  // fin saisirEntierMinMax

    //.........................................................................



    /** MODIFICI
     *  pré-requis : mat1 et mat2 ont les mêmes dimensions
     *  action : copie toutes les valeurs de mat1 dans mat2 de sorte que mat1 et mat2 soient identiques
     */
    public static void copieMatrice(int[][] mat1, int[][] mat2){
        for(int i=0;i<mat1.length;i++){
            for (int j=0;j<mat1[0].length;j++){
                mat2[i][j]=mat1[i][j];
            }
        }
    }  // fin copieMatrice

    //.........................................................................


    /** pré-requis :  n >= 0
     *  résultat : un tableau de booléens représentant le sous-ensemble de l'ensemble des entiers 
     *             de 1 à n égal à lui-même 
     */

    public static boolean[] ensPlein(int n){

       boolean[] ens = new boolean[n+1];

        for (int i=1; i<=n;i++){
            ens[i]=true;
        }
        return ens;
    }
       // fin ensPlein

    //.........................................................................


    /** pré-requis : 1 <= val < ens.length
     *  action :     supprime la valeur val de l'ensemble représenté par ens, s'il y est
     *  résultat :   vrai ssi val était dans cet ensemble
     */
    public static boolean supprime(boolean[] ens, int val){

        if (ens[val]) {
            ens[val] = false;
            return true;
        }
        return false;
    }  // fin supprime

    //.........................................................................

    /** pré-requis : l'ensemble représenté par ens n'est pas vide
     *  résultat :   un élément de cet ensemble
     */
    public static int uneValeur(boolean[] ens){

        for (int i=ens.length-1;i>0;i++){
                if (ens[i]) return i;
        }
        return 0;
    }  // fin uneValeur

    //.........................................................................

    /**

     1 2 3 4 5 6 7 8 9
     -------------------
     1 |6 2 9|7 8 1|3 4 5|
     2 |4 7 3|9 6 5|8 1 2|
     3 |8 1 5|2 4 3|6 9 7|
     -------------------
     4 |9 5 8|3 1 2|4 7 6|
     5 |7 3 2|4 5 6|1 8 9|
     6| 1 6 4|8 7 9|2 5 3|
     -------------------
     7 3 8 1|5 2 7|9 6 4
     8 |5 9 6|1 3 4|7 2 8|
     9 |2 4 7|6 9 8|5 3 1|
     -------------------

     1 2 3 4 5 6 7 8 9
     -------------------
     1 |6 0 0|0 0 1|0 4 0|
     2 |0 0 0|9 6 5|0 1 2|
     3 |8 1 0|0 4 0|0 0 0|
     -------------------
     4 |0 5 0|3 0 2|0 7 0|
     5 |7 0 0|0 0 0|1 8 9|
     6||0 0 0|0 7 0|0 0 3|
     -------------------
     7 |3 0 0|0 2 0|9 0 4|
     8 |0 9 0|0 0 0|7 2 0|
     9 |2 4 0|6 9 0|0 0 0|
     -------------------

     * pré-requis : 0<=k<=3 et g est une grille k^2xk^2 dont les valeurs sont comprises
     *              entre 0 et k^2 et qui est partitionnée en k sous-carrés kxk
     * action : affiche la  grille g avec ses sous-carrés et avec les numéros des lignes
     *          et des colonnes de 1 à k^2.
     * Par exemple, pour k = 3, on obtient le dessin d'une grille de Sudoku
     *
     */
    public static void afficheGrille(int k,int[][] g){

        for (int i=1;i<=k;i++){System.out.print(" ");}

        for (int i=1;i<=k*k ;i++){
            System.out.print(i);
            if (i!=0 && i!= k*k-1 && i%k==0) System.out.print(" ");
        }

        System.out.println();

        for (int i=0;i<k*k ;i++){

            if (i!=0 && i!= k*k-1 && i%k==0) {
                for (int j=0; j< k*k + 2*k;j++){System.out.print("-");}
                System.out.println();
            }

            System.out.print( i+1 + " |");
            for (int j=0; j<k*k;j++){
                if (j!=0 && j!= k*k-1 && j%k==0) System.out.print("|");
                System.out.print(g[i][j]);
            }
            System.out.println("|");
        }

    } // fin afficheGrille
    //.........................................................................

    /** pré-requis : k > 0, 0 <= i< k^2 et 0 <= j < k^2
     *  résultat : (i,j) étant les coordonnées d'une case d'une grille k^2xk^2 partitionnée 
     *             en k sous-carrés kxk, retourne les coordonnées de la case du haut à gauche
     *             du sous-carré de la grille contenant cette case.
     *  Par exemple, si k=3, i=5 et j=7, la fonction retourne (3,6).
     */
    public static int[] debCarre(int k,int i,int j){

        return new int[]{i-i%k, j-j%k};

    }  // fin debCarre

    //.........................................................................

    // Initialisation
    //.........................................................................

    /** MODIFICI
     *  pré-requis : gComplete est une matrice 9X9
     *  action   :   remplit gComplete pour que la grille de Sudoku correspondante soit complète
     *  stratégie :  les valeurs sont données directement dans le code et on peut utiliser copieMatrice pour mettre à jour gComplete
     */
    public static void initGrilleComplete(int [][] gComplete){

        int[][] mat={
                {6,2,9, 7,8,1, 3,4,5},
                {4,7,3, 9,6,5, 8,1,2},
                {8,1,5, 2,4,3, 6,9,7},

                {9,5,8, 3,1,2, 4,7,6},
                {7,3,2, 4,5,6, 1,8,9},
                {1,6,4, 8,7,9, 2,5,3},

                {3,8,1, 5,2,7, 9,6,4},
                {5,9,6, 1,3,4, 7,2,8},
                {2,4,7, 6,9,8, 5,3,1}
        };
        copieMatrice(mat,gComplete);
    }

    // fin initGrilleComplete

    //.........................................................................


    /** MODIFICI
     *  pré-requis : gSecret est une grille de Sudoku complète de mêmes dimensions que gIncomplete et 0 <= nbTrous <= 81
     *  action :     modifie gIncomplete pour qu'elle corresponde à une version incomplète de la grille de Sudoku gSecret (gIncomplete peut être complétée en gSecret),
     *               avec nbTrous trous à des positions aléatoires
     */
    /* SAISIE PAR ORDI*/

    public static void initGrilleIncomplete(int nbTrous, int[][] gSecret, int[][] gIncomplete) {
        copieMatrice(gSecret, gIncomplete);
        int i, j;
        Random random = new Random();

        while (nbTrous > 0) {
            i = random.nextInt(gIncomplete.length);
            j = random.nextInt(gIncomplete[0].length);

            // Vérifier que la valeur initiale n'est pas déjà un zéro
            if (gIncomplete[i][j] != 0) {
                gIncomplete[i][j] = 0; // Crée un trou à la case (i, j)
                nbTrous--;
            }
        }
    } // fin initGrilleIncomplete


    /** MODIFICI
     *  pré-requis : 0 <= nbTrous <= 81 ; g est une grille 9x9 (vide a priori) ;
     *               fic est un nom de fichier de ce répertoire contenant des valeurs de Sudoku
     *  action :   remplit g avec les valeurs lues dans fic. Si la grille ne contient pas des valeurs
     *             entre 0 et 9 ou n'a pas exactement nbTrous valeurs nulles, la méthode doit signaler l'erreur,
     *             et l'utilisateur doit corriger le fichier jusqu'à ce que ces conditions soient vérifiées.
     *             On suppose dans la version de base que la grille saisie est bien une grille de Sudoku incomplète.
     */
    public static void saisirGrilleIncompleteFichier(int nbTrous, int [][] g, String fic){
        //_________________________________________________

        try (BufferedReader lecteur = new BufferedReader(new FileReader(fic))) {
            for (int i = 0 ; i < 9 ; i++){
                String ligne = lecteur.readLine();
                String [] valeurs = ligne.split("\\s+");
                for (int j = 0 ; j < 9 ; j++) {
                    g[i][j] = Integer.parseInt(valeurs[j]);
                    // Des tests d'erreur sont à ajouter quelque part !
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // fin saisirGrilleIncompleteFichier


    //.........................................................................


    /** pré-requis : 0 <= nbTrous <= 81
     *  résultat :   une grille  9x9 saisie dont les valeurs sont comprises ente 0 et 9
     *               avec exactement  nbTrous valeurs nulles
     *               et avec re-saisie jusqu'à ce que ces conditions soient vérifiées.
     *               On suppose dans la version de base que la grille saisie est bien une grille de Sudoku incomplète.
     *  stratégie : utilise la fonction saisirEntierMinMax
     */

    /* SAISIE PAR HUMAIN */
    public static int [][] saisirGrilleIncomplete(int nbTrous){

        int [][] gIncomplete = new int [9][9];
        initGrilleComplete(gIncomplete);

        int i,j;

        while (nbTrous>0) {

            System.out.println("Saisissez les points i,j d'où vous souhaitez y ajouter un trou");
            System.out.println("Nombre de trous restant à saisir : "+ nbTrous);

            i=saisirEntierMinMax(1, gIncomplete.length) - 1; // l'ordi choisi aléatoirement la pos(i,j)
            j=saisirEntierMinMax(1, gIncomplete[0].length) - 1;

            if (gIncomplete[i][j]==0) System.err.println("Il y a déjà un trou à ("+(i+1)+','+(j+1)+')');
            else {
                nbTrous--;

                gIncomplete[i][j]=0; // crée un trou à la case (i,j)

                afficheGrille(3,gIncomplete);
                System.out.println("\nTrou ajouté à ("+(i+1)+','+(j+1)+")\n");
            }
        }
        return gIncomplete;
    }  // fin saisirGrilleIncomplete

    //.........................................................................



    /** pré-requis : gOrdi est une grille de Sudoku incomplète,
     *               valPossibles est une matrice 9x9 de tableaux de 10 booléens
     *               et nbValPoss est une matrice 9x9 d'entiers
     *  action : met dans valPossibles l'ensemble des entiers de 1 à 9 pour chaque trou de gOrdi
     *           et leur nombre dans nbValPoss       
     */
    public static void initPleines(int [][] gOrdi, boolean[][][] valPossibles, int [][] nbValPoss){

        for (int i=0;i<gOrdi.length;i++){
            for (int j=0;j<gOrdi.length;j++){
                valPossibles[i][j]=ensPlein(10);
                nbValPoss[i][j]=9;
            }
        }
        //________________________________________________________________________________________________

    }  // fin initPleines

    //.........................................................................


    /** pré-requis : gOrdi est une grille de Sudoku incomplète,
     *               0<=i<9, 0<=j<9,g[i][j]>0,
     *               valPossibles est une matrice 9x9 de tableaux de 10 booléens
     *               et nbValPoss est une matrice 9x9 d'entiers
     *  action : supprime dans les matrices valPossibles et nbValPoss la valeur gOrdi[i][j] pour chaque case de la ligne,
     *           de la colonne et du carré contenant la case (i,j) correspondant à un trou de gOrdi.
     */

    public static void suppValPoss(int [][] gOrdi, int i, int j, boolean[][][] valPossibles, int [][]nbValPoss){

        /* Test ligne */
        for(int ligne=0; ligne<gOrdi.length;ligne++){
                if(supprime(valPossibles[i][ligne],gOrdi[i][j])) nbValPoss[i][ligne]--;
        }

        /* Test colonne */
        for(int colonne=0; colonne<gOrdi[0].length;colonne++){
            if(supprime(valPossibles[colonne][j],gOrdi[i][j])) nbValPoss[colonne][j]--;
        }

        /*Test valeurs carré 3x3*/
        int[] debCarre= debCarre(3,i,j);

        for(int k=debCarre[0];k<debCarre[0] + 3; k++){
            for (int l=debCarre[1]; l<debCarre[1] + 3; l++) {
                if (supprime(valPossibles[k][l], gOrdi[i][j])) nbValPoss[k][l]--;
            }
        }

    }  // fin suppValPoss

    //.........................................................................

    /** pré-requis : gOrdi est une grille de Sudoju incomplète,
     *               valPossibles est une matrice 9x9 de tableaux de 10 booléens
     *               et nbValPoss est une matrice 9x9 d'entiers
     * action :      met dans valPossibles l'ensemble des valeurs possibles de chaque trou de gOrdi
     *               et leur nombre dans nbValPoss       
     */
    public static void initPossibles(int [][] gOrdi, boolean[][][] valPossibles, int [][]nbValPoss){

        for(int i=0; i<gOrdi.length;i++){
            for(int j=0; j<gOrdi[0].length;j++){
                if (gOrdi[i][j]!=0) suppValPoss(gOrdi,i,j,valPossibles,nbValPoss);
            }
        }

        //________________________________________________________________________________________________

    }  // fin initPossibles

    //.........................................................................


    /** pré-requis : gSecret, gHumain et gOrdi sont des grilles 9x9
     *  action :     demande au joueur humain de saisir le nombre nbTrous compris entre 0 et 81,
     *               met dans gSecret une grille de Sudoku complète,
     *               met dans gHumain une grille de Sudoku incomplète, pouvant être complétée en gSecret
     *               et ayant exactement nbTrous trous de positions aléatoires,
     *               met dans gOrdi une grille de Sudoku incomplète saisie par le joueur humain
     *               ayant  nbTrous trous,
     *               met dans valPossibles l'ensemble des valeurs possibles de chaque trou de gOrdi
     *               et leur nombre dans nbValPoss.
     * retour : la valeur de nbTrous
     */
    public static int initPartie(int [][] gSecret, int [][] gHumain, int [][] gOrdi, boolean[][][] valPossibles, int [][]nbValPoss){

        System.out.println("INITIALISATION PARTIE \n Nombre de trou");
        int nbTrous = saisirEntierMinMax(0,81);

        initGrilleComplete(gSecret);

        initGrilleIncomplete(nbTrous,gSecret,gHumain);

        initGrilleIncomplete(nbTrous,gSecret,gOrdi);

        initPleines(gOrdi,valPossibles,nbValPoss);
        initPossibles(gOrdi,valPossibles,nbValPoss);

        return nbTrous;
    }  // fin initPartie

    //...........................................................
    // Tour du joueur humain
    //...........................................................

    /** pré-requis : gHumain est une grille de Sudoku incomplète pouvant se compléter en
     *               la  grille de Sudoku complète gSecret
     *  résultat :   le nombre de points de pénalité pris par le joueur humain pendant le tour de jeu
     *  action :     effectue un tour du joueur humain   
     */
    public static int tourHumain(int [][] gSecret, int [][] gHumain){

        int choix, valeur = 0, i,j, nbMalus=0;

        System.out.println("/-/-/-/-/-/-/ DEBUT TOUR HUMAIN /-/-/-/-/-/-/\n");

        afficheGrille(3,gHumain);


        // Saisie coordonees trou et verification trou
        do {
            System.out.println("\nChoix de la position du trou que vous souhaitez remplir\n");
            i = saisirEntierMinMax(1,9) - 1;
            j = saisirEntierMinMax(1,9) - 1;

            if (gHumain[i][j]!=0) System.err.println("La case"+'('+i+1+","+j+1+") n'est pas un trou !\n");
        }
        while (gHumain[i][j]!=0);

        System.out.println("    Choix valeur dans ce trou  :                      [1]");
        System.out.println(" ");
        System.out.println("    Utiliser un joker pour ce trou :                 [2]");
        System.out.println(" ");

        System.out.print("   Saisissez le chiffre correspondant à l'action souhaité : ");
           
        do {

            choix = saisirEntierMinMax(1, 2);

            if (choix == 1) {
                System.out.println("Choix de la valeur du trou que vous souhaitez remplir\n");

                valeur = saisirEntierMinMax(1, 9);

                if (gSecret[i][j] != valeur) { // Cas Valeur Incorrect
                    System.err.println("Valeur incorrect !\n");
                    nbMalus++;
                }

                else {
                    System.out.println("Valeur correct !\n");
                    gHumain[i][j] = valeur;
                }

            }
            else if (choix == 2) { // Choix Joker
                System.out.println("Vous utilisez un joker !\n");
                gHumain[i][j] = gSecret[i][j];
                return nbMalus + 1;
            }

        }
        while (gSecret[i][j] != valeur);

        System.out.println("/-/-/-/-/-/-/ FIN TOUR HUMAIN /-/-/-/-/-/-/");

        return nbMalus;
    }  // fin  tourHumain

    /** pré-requis : gOrdi et nbValPoss sont des matrices 9x9
     *  résultat :   le premier trou (i,j) de gOrdi (c'est-à-dire tel que gOrdi[i][j]==0)
     *               évident (c'est-à-dire tel que nbValPoss[i][j]==1) dans l'ordre des lignes,
     *                s'il y en a, sinon le premier trou de gOrdi dans l'ordre des lignes
     *
     */
    public static int[] chercheTrou(int[][] gOrdi,int [][]nbValPoss){

        // Cherche le premier trou evident
        for (int i=0;i<gOrdi.length;i++){
                for (int j=0;j<gOrdi[0].length;j++){
                    if (gOrdi[i][j]==0 && nbValPoss[i][j]==1) return new int[]{i,j};
                }
        }

        // sinon le premier trou de gOrdi
        for (int i=0;i<gOrdi.length;i++){
            for (int j=0;j<gOrdi[0].length;j++) {
                if (gOrdi[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[2];
    }  // fin chercheTrou

    //.........................................................................

    /** pré-requis : gOrdi est une grille de Sudoju incomplète,
     *               valPossibles est une matrice 9x9 de tableaux de 10 booléens
     *               et nbValPoss est une matrice 9x9 d'entiers
     *  action :     effectue un tour de l'ordinateur      
     */


    //.........................................................................

    // Tour de l'ordinateur
    //.........................................................................

    public static int tourOrdinateur(int [][] gOrdi, boolean[][][] valPossibles, int [][]nbValPoss){

        int nbMalus=0;

        System.out.println("\n/-/-/-/-/-/-/ DEBUT TOUR ORDI /-/-/-/-/-/-/\n");

        int [] trou = chercheTrou(gOrdi, nbValPoss);

        // Le trou est evident
        if (nbValPoss [trou[0]] [trou[1]] == 1){

            gOrdi[trou[0]][trou[1]] = uneValeur(valPossibles[trou[0]] [trou[1]]);
            initPleines(gOrdi, valPossibles, nbValPoss); //met tout à true
            initPossibles(gOrdi,valPossibles, nbValPoss); // met à false les valeurs impossibles + la nouvelle valeur ajoutée

            return 0;
        }
        else { // Utilisation du Joker
            System.out.println("\nL'ordinateur utilise un joker\n");

            afficheGrille(3, gOrdi);
            gOrdi[trou[0]][trou[1]] = saisirEntierMinMax(1,9);
            initPleines(gOrdi, valPossibles, nbValPoss); //met tout à true
            initPossibles(gOrdi,valPossibles, nbValPoss); // met à false les valeurs impossibles + la nouvelle valeur ajoutée

            return 1;
        }
    }  // fin tourOrdinateur

    //.........................................................................
    // Partie
    //.........................................................................

    /** pré-requis : aucun
     *  action :     effectue une partie de Sudoku entre le joueur humain et l'ordinateur
     *  résultat :   0 s'il y a match nul, 1 si c'est le joueur humain qui gagne et 2 sinon
     */
    public static int partie(){

        int[][] gSecret = new int[9][9],
                gOrdi = new int[9][9],
                gHumain = new int[9][9],
                nbValPoss = new int[9][9];

        boolean[][][] valPossibles = new boolean [10][10][9];

        int nbTrous = initPartie(gSecret,gHumain,gOrdi,valPossibles,nbValPoss),
                nbMalusHumain=0,
                nbMalusOrdi=0;

        do {

            nbMalusHumain += tourHumain(gSecret,gHumain);

            nbMalusOrdi += tourOrdinateur(gOrdi,valPossibles,nbValPoss);

            nbTrous--;

        }
        while (nbTrous > 0);

        System.out.println("\nPartie Terminée !\n");

        if (nbMalusHumain==nbMalusOrdi) return 0; // Match Nul

        else if (nbMalusHumain < nbMalusOrdi) return 1; // Victoire Humain

        else return 2; // Victoire Ordi

    }  // fin partie

    //........................................................................

} // fin SudokuBase