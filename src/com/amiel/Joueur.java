package com.amiel;

import java.util.ArrayList;
import java.util.Scanner;

public class Joueur {
    public final Plateau plateau;
    public String name;
    public int score;
    public int king;
    public int taillePlusGrandBiome = 0;
    public ArrayList<Domino> listeDominosChoisi;
    private int[][] plateauCoordonnee;
    boolean regleVerifie = false;
    boolean premierTour = true;
    boolean[][] coordoneeCheck;


    public Joueur(String name) {
        this.name = name;
        this.score = 0;
        this.plateau = new Plateau(1, 9);
        this.listeDominosChoisi = new ArrayList<Domino>();
        //System.out.println(name);
    }

    public int getScore() {
        return score;
    }

    public ArrayList<Domino> chooseDomino(ArrayList<Domino> listeDomino, Domino domino, int coordx, int coordy, int coordx2, int coordy2, boolean premierTour) {
        boolean dansDominoListe = false;
        do {
            System.out.println("Veuillez choisir un domino");
            for (int i = 0; i < listeDomino.size(); i++) {
                if (listeDomino.get(i).getNumeroDomino() == domino.numeroDomino) {
                    dansDominoListe = true;
                    domino.numeroDomino = i;
                    break;
                }
            }
        } while (!dansDominoListe);
        listeDominosChoisi.add(listeDomino.get(domino.numeroDomino));
        listeDomino.remove(domino.numeroDomino);
        return listeDomino;
    }

    public ArrayList<Domino> chooseDomino(ArrayList<Domino> listeDomino, Domino Domino, int coordx, int coordy, int coordx2, int coordy2) {
        return chooseDomino(listeDomino, Domino, coordx, coordy, coordx2, coordy2, false);
    }

    public void updateListePlateau(Domino domino, int coordx, int coordy, int coordx2, int coordy2) {

        //plateau.printPlateau();
        //domino.afficheDomino();
        Scanner scanner = new Scanner(System.in);
        //System.out.println("Veuillez choisir les coordonnées haut");
        //coordx = scanner.nextInt();
        //coordy = scanner.nextInt();


        //System.out.println("Veuillez choisir les coordonnées bas");
        //coordx2 = scanner.nextInt();
        //coordy2 = scanner.nextInt();
        //System.out.println("Défausser la carte ? O/N");
        String defauce = scanner.toString();
        if (!defauce.equals("O")) {
            regleVerifie = verifierRegles(coordx, coordy, coordx2, coordy2, domino.getType1(), domino.getType2());
            if (regleVerifie) {
                plateau.plateau[coordy][coordx][0] = domino.getType1();
                plateau.plateau[coordy][coordx][1] = domino.getnbCouronne();

                plateau.plateau[coordy2][coordx2][0] = domino.getType2();
                plateau.plateau[coordy2][coordx2][1] = domino.getnbCouronne2();
                //plateau.printPlateau();
                premierTour = false;
            }
        }


    }


    public boolean verifierRegles(int coordx, int coordy, int coordx2, int coordy2, int type1, int type2) {
        if (dansLesLimitesDuPlateau(coordx) && dansLesLimitesDuPlateau(coordy) && dansLesLimitesDuPlateau(coordx2) && dansLesLimitesDuPlateau(coordy2)) {

            if (!(coteACoteType(coordx, coordy, type1) || coteACoteType(coordx2, coordy2, type2))) {
                System.out.println("La case a coté n'est pas du même type");
            } else {
                return verifierCaseNonUtilisee(coordx, coordy) &&
                        verifierCaseNonUtilisee(coordx2, coordy2) &&
                        coteACote(coordx, coordy, coordx2, coordy2) &&
                        tailleMax(coordx, coordy, coordx2, coordy2);
            }
        }
        return false;
    }

    public boolean verifierCaseNonUtilisee(int x, int y) {
        if (!(plateau.plateau[y][x][0] == 0)) {
            System.out.println("La case est déjà utilsée");
        }
        return plateau.plateau[y][x][0] == 0;
    }

    public boolean dansLesLimitesDuPlateau(int coord) {
        if (!(coord <= plateau.taille && coord >= 0)) {
            System.out.println("La case n'est pas dans le plateau");
        }
        return coord <= plateau.taille && coord >= 0;
    }

    public boolean coteACoteType(int x, int y, int typeVerifie) {
        //Si permier tout regarder si a coté du roi de type 1
        if (premierTour) {
            typeVerifie = 1;
        }
        boolean result = false;

        if (x != 8) {
            result = (plateau.plateau[y][x + 1][0] == typeVerifie);
            result = plateau.plateau[y][x + 1][0] == typeVerifie;

        }
        if (y != 8) {
            result = result || plateau.plateau[y + 1][x][0] == typeVerifie;

        }
        if (x != 0) {
            result = result || plateau.plateau[y][x - 1][0] == typeVerifie;

        }
        if (y != 0) {
            result = result || plateau.plateau[y - 1][x][0] == typeVerifie;

        }

        return result;
    }

    public boolean coteACote(int coordx, int coordy, int coordx2, int coordy2) {
        if ((Math.abs(coordx - coordx2) == 1) == (Math.abs(coordy - coordy2) == 1)) {
            System.out.println("Les deux coordonnees ne sont pas a cote");
        }
        return Math.abs(coordx - coordx2) == 1 ^ Math.abs(coordy - coordy2) == 1;
    }

    public boolean tailleMax(int coordx, int coordy, int coordx2, int coordy2) {
        int coordxPlusPetit = coordx2;
        int coordxPlusGrand = coordx;
        int coordyPlusPetit = coordy2;
        int coordyPlusBas = coordy;

        if (coordx <= coordx2) {
            coordxPlusPetit = coordx;
            coordxPlusGrand = coordx2;
        }
        if (coordy <= coordy2) {
            coordyPlusPetit = coordy;
            coordyPlusBas = coordy2;
        }
        int plusLoinGauche = coordxPlusPetit;
        int plusLoinDroite = coordxPlusGrand;
        int plusLoinHaut = coordyPlusPetit;
        int plusLoinBas = coordyPlusBas;

        for (int y = 0; y < plateau.taille - 1; y++) {
            //cote gauche
            for (int x = 1; x <= coordxPlusPetit; x++) {
                if (plateau.plateau[y][coordxPlusPetit - x][0] != 0 && coordxPlusPetit - x < plusLoinGauche) {

                    plusLoinGauche = coordxPlusPetit - x;
                }
            }
            // coté droit x
            for (int x = coordxPlusGrand + 1; x <= plateau.taille - 1; x++) {
                if (plateau.plateau[y][x][0] != 0 && x > plusLoinDroite) {
                    plusLoinDroite = x;
                }
            }
        }
        // calcul taille hauteur
        ;
        for (int x = 0; x < plateau.taille - 1; x++) {
            // coté haut
            for (int y = 1; y <= coordyPlusPetit; y++) {
                if (plateau.plateau[coordyPlusPetit - y][x][0] != 0 && coordyPlusPetit - y < plusLoinHaut) {
                    plusLoinHaut = coordyPlusPetit - y;
                }
            }

            //coté bas
            for (int y = coordyPlusBas; y <= plateau.taille - 1; y++) {
                if (plateau.plateau[y][x][0] != 0 && y > plusLoinBas) {
                    plusLoinBas = y;
                }

            }
        }
        boolean b = (plusLoinDroite - plusLoinGauche + 1 <= 5) && (plusLoinBas - plusLoinHaut + 1 <= 5);
        if (!b) {
            System.out.println("Taille du terrain dépasse les 5x5");
        }
        return b;
    }

    public void compteScore() {
        //compte couronnes
        score = 0;
        boolean[][] coordoneeCheck = new boolean[plateau.taille][plateau.taille];
        for (int i = 0; i < plateau.taille - 1; i++) {
            for (int j = 0; j < plateau.taille - 1; j++) {
                coordoneeCheck[i][j] = false;
            }
        }

        for (int i = 0; i < plateau.taille - 1; i++) {
            for (int j = 0; j < plateau.taille - 1; j++) {

                if (!coordoneeCheck[i][j] && plateau.plateau[i][j][0] != 0) {
                    boolean[][] coordoneeCheck2 = new boolean[plateau.taille][plateau.taille];
                    for (int x = 0; x < plateau.taille - 1; x++) {
                        for (int y = 0; y < plateau.taille - 1; y++) {
                            coordoneeCheck2[x][y] = false;
                        }
                    }
                    coordoneeCheck2[i][j] = true;
                    int type = plateau.plateau[i][j][0];
                    int tailleBiome = 1;
                    int tailleBiomeAvant = 0;
                    while (tailleBiome > tailleBiomeAvant) {
                        for (int x = 0; x < plateau.taille; x++) {
                            for (int y = 0; y < plateau.taille; y++) {
                                if (coordoneeCheck2[x][y]) {
                                    if (x > 0) {
                                        if (!coordoneeCheck2[x - 1][y] && plateau.plateau[x - 1][y][0] == type) {
                                            coordoneeCheck2[x - 1][y] = true;
                                        }
                                    }
                                    if (x < 8) {
                                        if (!coordoneeCheck2[x + 1][y] && plateau.plateau[x + 1][y][0] == type) {
                                            coordoneeCheck2[x + 1][y] = true;
                                        }
                                    }
                                    if (y > 0) {
                                        if (!coordoneeCheck2[x][y - 1] && plateau.plateau[x][y - 1][0] == type) {
                                            coordoneeCheck2[x][y - 1] = true;
                                        }
                                    }
                                    if (y < 8) {
                                        if (!coordoneeCheck2[x][y + 1] && plateau.plateau[x][y + 1][0] == type) {
                                            coordoneeCheck2[x][y + 1] = true;
                                        }
                                    }
                                }
                            }
                        }
                        tailleBiomeAvant = tailleBiome;
                        tailleBiome = compteTailleBiome(coordoneeCheck2);
                    }
                    int nbCouronneBiome = compteCouronneBiome(coordoneeCheck2);
                    score += nbCouronneBiome * tailleBiome;
                    for (int x = 0; x < plateau.taille; x++) {
                        for (int y = 0; y < plateau.taille; y++) {
                            if (coordoneeCheck2[x][y]) {
                                coordoneeCheck[x][y] = true;
                            }
                        }
                    }
                }
            }
        }
    }

    public int compteTailleBiome(boolean[][] coordoneeCheck2) {
        int taille = 0;
        for (int x = 0; x < plateau.taille - 1; x++) {
            for (int y = 0; y < plateau.taille - 1; y++) {
                if (coordoneeCheck2[x][y]) {
                    taille++;
                }
            }
        }
        if (taille > taillePlusGrandBiome){
            taillePlusGrandBiome = taille;
        }
        return taille;
    }

    public int compteCouronneBiome(boolean[][] coordoneeCheck2) {
        int total = 0;
        for (int x = 0; x < plateau.taille - 1; x++) {
            for (int y = 0; y < plateau.taille - 1; y++) {
                if (coordoneeCheck2[x][y] && plateau.plateau[x][y][1] != 0) {
                    total += plateau.plateau[x][y][1];
                }
            }
        }
        return total;
    }


}

