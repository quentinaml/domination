package com.amiel;

import java.util.ArrayList;
import java.util.Scanner;

public class Joueur {
    public final Plateau plateau;
    public String name;
    public int score;
    public int king;
    public ArrayList<Domino> listeDominosChoisi;
    private int[][] plateauCoordonnee;


    public Joueur(String name) {
        this.name = name;
        this.score = 0;
        this.plateau = new Plateau(1, 9);
        this.listeDominosChoisi = new ArrayList<Domino>();
        System.out.println(name);
    }

    public int getScore() {
        return score;
    }

    public ArrayList<Domino> chooseDomino(ArrayList<Domino> listeDomino, boolean premierTour ) {
        int numeroDomino = 0;
        boolean dansDominoListe = false;

        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Veuillez choisir un domino");
            numeroDomino = scanner.nextInt();
            for (int i = 0; i < listeDomino.size(); i++) {
                if (listeDomino.get(i).getNumeroDomino() == numeroDomino) {
                    dansDominoListe = true;
                    numeroDomino = i;
                    break;
                }
            }
        } while (!dansDominoListe);
        updateListePlateau(listeDomino.get(numeroDomino), premierTour);
        listeDomino.remove(numeroDomino);
        return listeDomino;
    }

    public ArrayList<Domino> chooseDomino(ArrayList<Domino> listeDomino){
        return chooseDomino(listeDomino, false );
    }

    public void updateListePlateau(Domino domino, boolean premierTour) {
        boolean regleVerifie = false;
        int coordx;
        int coordy;
        int coordx2;
        int coordy2;

        do {
            plateau.printPlateau();
            domino.afficheDomino();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Veuillez choisir les coordonnées haut");
            coordx = scanner.nextInt();
            coordy = scanner.nextInt();


            System.out.println("Veuillez choisir les coordonnées bas");
            coordx2 = scanner.nextInt();
            coordy2 = scanner.nextInt();
            regleVerifie = verifierRegles(coordx, coordy, coordx2, coordy2, domino.getType2(), domino.getType1(),premierTour);

        } while (!regleVerifie);
        plateau.plateau[coordx2][coordy2] = domino.getType2();
        plateau.plateau[coordx][coordy] = domino.getType1();
        plateau.printPlateau();

    }



    public boolean verifierRegles(int coordx, int coordy, int coordx2, int coordy2, int type1 , int type2, boolean premierTour) {
        if(dansLesLimitesDuPlateau(coordx) && dansLesLimitesDuPlateau(coordy) && dansLesLimitesDuPlateau(coordx2) && dansLesLimitesDuPlateau(coordy2) ) {
            if (!(coteACoteType(coordx, coordy, type1, premierTour) || coteACoteType(coordx2, coordy2, type2, premierTour))) {
                System.out.println("La case a coté n'est pas du même type");
            }else {
                return verifierCaseNonUtilisee(coordx, coordy) &&
                        verifierCaseNonUtilisee(coordx2, coordy2) &&
                        coteACote(coordx, coordy, coordx2, coordy2);
            }
        }
        return false;
    }

    public boolean verifierCaseNonUtilisee(int x, int y) {
        if(!(plateau.plateau[x][y] == 0) ){
            System.out.println("La case est déjà utilsée");
        }
        return plateau.plateau[x][y] == 0;
    }

    public boolean dansLesLimitesDuPlateau(int coord) {
        if(!(coord <= plateau.taille && coord >= 0)) {
            System.out.println("La case n'est pas dans le plateau");
        }
        return coord <= plateau.taille && coord >= 0;
    }

    public boolean coteACoteType(int x, int y, int typeVerifie, boolean premierTour) {
        //Si permier tout regarder si a coté du roi de type 1
        if(premierTour){
            typeVerifie = 1;
        }
        boolean result = false;

        if( x != 8){
            result = plateau.plateau[x + 1][y] == typeVerifie;
        }
        if(y != 8 ){
            result = result || plateau.plateau[x][y + 1] == typeVerifie;
        }
        if(x != 0 ){
            result = result || plateau.plateau[x - 1][y] == typeVerifie;
        }
        if( y != 0 ){
            result = result || plateau.plateau[x][y - 1] == typeVerifie;
        }

        return result;
    }

    public boolean coteACote(int coordx, int coordy, int coordx2, int coordy2) {
        if((Math.abs(coordx - coordx2) == 1) == (Math.abs(coordy - coordy2) == 1)){
            System.out.println("Les deux coordonnees ne sont pas a cote");
        }
        return Math.abs(coordx-coordx2) == 1 ^ Math.abs(coordy-coordy2) == 1;
    }
}