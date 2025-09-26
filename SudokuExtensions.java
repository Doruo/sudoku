import java.util.Random;

public class SudokuExtensions {

    public static void main(String[] args) {
        SudokuBase.main(args);
    }

    //.........................................................................

    // 3.1 & 3.3 - Joker versus choix aléatoire
    //.........................................................................


    public static int tourOrdinateur(int [][] gOrdi, boolean[][][] valPossibles, int [][]nbValPoss){

        int [] trou = SudokuBase.chercheTrou(gOrdi, nbValPoss);

        int nbVal = nbValPoss [trou[0]] [trou[1]];

        int nbMalus=0, valeur;

        boolean choixValide;

        System.out.println("\n/-/-/-/-/-/-/ DEBUT TOUR ORDI /-/-/-/-/-/-/\n");

        // Le trou est evident
        if (nbVal == 1){

            gOrdi[trou[0]][trou[1]] = SudokuBase.uneValeur(valPossibles[trou[0]] [trou[1]]);
            SudokuBase.suppValPoss(gOrdi,trou[0],trou[1],valPossibles,nbValPoss);

            return 0;
        }
        else if (nbVal >= 2 && nbVal <= 5){

            do {
                valeur = new Random().nextInt(nbVal);

                System.out.println("\nL'ordi tente de rentrer "+valeur+" dans le trou, est-il valide ? 1 pour vraie et 0 pour faux");

                choixValide = SudokuBase.saisirEntierMinMax(0,1) == 1;

                // Gestion Triche
                if ((choixValide && gOrdi[trou[0]][trou[1]]!=valeur) || (!choixValide && gOrdi[trou[0]][trou[1]]==valeur))  return -1;

                if (choixValide) {
                    gOrdi[trou[0]][trou[1]] = valeur;
                    SudokuBase.suppValPoss(gOrdi,trou[0],trou[1],valPossibles,nbValPoss);
                }
                else nbMalus++;
            }
            while (!choixValide);

            return nbMalus;
        }
        else { // Utilisation du Joker
            System.out.println("\nL'ordinateur utilise un joker\n");

            int valeurHumain = SudokuBase.saisirEntierMinMax(1,9);

            // Gestion Triche
            if (gOrdi[trou[0]][trou[1]] != valeurHumain) return -1;

            gOrdi[trou[0]][trou[1]] = valeurHumain;

            SudokuBase.suppValPoss(gOrdi,trou[0],trou[1],valPossibles,nbValPoss);
            return 1;
        }
    }  // fin tourOrdinateur



    // 3.2 - Gestion de la tricherie
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

        int nbTrous = SudokuBase.initPartie(gSecret,gHumain,gOrdi,valPossibles,nbValPoss),

                nbMalusHumain=0,
                nbMalusOrdi=0;

        do {

            nbMalusHumain += SudokuBase.tourHumain(gSecret,gHumain);

            int valeur = tourOrdinateur(gOrdi,valPossibles,nbValPoss);

            if (valeur == -1) return 2;
            else nbMalusOrdi = valeur;

            nbTrous--;
        }
        while (nbTrous > 0);

        System.out.println("\nPartie Terminée !\n");

        if (nbMalusHumain==nbMalusOrdi) return 0; // Match Nul

        else if (nbMalusHumain > nbMalusOrdi) return 1; // Victoire Humain

        else return 2; // Victoire Ordi

    }  // fin partie

    //........................................................................
    // 3.4
    //.........................................................................

    public static void copieMatrice(int[][] mat1, int[][] mat2){
        for(int i=0;i<mat1.length;i++){
            for (int j=0;j<mat1[0].length;j++){
                mat2[i][j]=mat1[i][j];
            }
        }
    }

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

    public static int[][] transformation (int [][] gComplete){
        Random random = new Random();
        int ligne1, ligne2 , signe;

        for (int i=1; i<8; i++){
            ligne1 = random.nextInt(gComplete.length); // choix aléatoire des lignes du même carré 3x3

            if (ligne1%3 == 0) ligne2 = ligne1 + random.nextInt(2) + 1; // la ligne est tout en haut du carré

            else if (ligne1%3 == 1) { // la ligne est au milieu du carré
                signe = random.nextInt(2); // 0=négatif et 1=positif
                if (signe==1) ligne2 = ligne1++;
                else ligne2 = ligne1 -1;
            }
            else ligne2 = ligne1 - random.nextInt(2) - 1; // la ligne est tout en bas du carré

            switch (random.nextInt(4)){
                case 0: rotation(gComplete);
                case 1: milieu(gComplete);
                case 2: diagonale(gComplete);
                case 3: echange(gComplete,ligne1,ligne2);
            }
        }
        return gComplete;
    }

    public static int[][] rotation(int[][] matrice) {
        int[][] matrice_modifiee = new int[matrice.length][matrice[0].length];

        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice.length; j++) {
                matrice_modifiee[matrice.length-1-j][i] = matrice[i][j];
            }
        }
        copieMatrice(matrice_modifiee, matrice);


        return matrice;
    }

    public static int [][] milieu (int[][] matrice){
        int[][] matrice_modifiee = new int[matrice.length][matrice[0].length];

        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[0].length; j++) {
                matrice_modifiee[i][j] = matrice[matrice.length-1-i][j];
            }
        }

        copieMatrice(matrice_modifiee, matrice);
        return matrice;
    }

    public static int [][] diagonale (int[][] matrice){
        int[][] matrice_modifiee = new int[matrice.length][matrice[0].length];

        for (int i = 0; i < matrice.length; i++) {
            for (int j = i; j < matrice.length; j++) {
                matrice_modifiee[i][j] = matrice[j][i];
                matrice_modifiee[j][i] = matrice[i][j];
            }
        }

        copieMatrice(matrice_modifiee, matrice);
        return matrice;
    }

    public static int [][] echange (int[][] matrice, int ligne1, int ligne2){
        int[][] matrice_modifiee = new int[matrice.length][matrice[0].length];
        copieMatrice(matrice, matrice_modifiee);

        for (int j=0; j<matrice.length; j++) {
            matrice_modifiee[ligne2][j] = matrice[ligne1][j];
        }
        for (int j=0; j<matrice.length; j++) {
            matrice_modifiee[ligne1][j] = matrice[ligne2][j];
        }

        copieMatrice(matrice_modifiee, matrice);
        return matrice;
    }
}