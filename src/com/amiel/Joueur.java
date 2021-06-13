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
    boolean regleVerifie = false;
    boolean premierTour = true;


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

    public ArrayList<Domino> chooseDomino(ArrayList<Domino> listeDomino, Domino domino, int coordx, int coordy, int coordx2, int coordy2 , boolean premierTour) {
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

    public ArrayList<Domino> chooseDomino(ArrayList<Domino> listeDomino, Domino Domino, int coordx, int coordy, int coordx2, int coordy2){
        return chooseDomino(listeDomino, Domino, coordx, coordy, coordx2, coordy2, false );
    }

    public void updateListePlateau(Domino domino, int coordx, int coordy, int coordx2, int coordy2) {

        plateau.printPlateau();
        domino.afficheDomino();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez choisir les coordonnées haut");
        //coordx = scanner.nextInt();
        //coordy = scanner.nextInt();


        System.out.println("Veuillez choisir les coordonnées bas");
        //coordx2 = scanner.nextInt();
        //coordy2 = scanner.nextInt();
        System.out.println("Défausser la carte ? O/N");
        String defauce = scanner.toString();
        if(!defauce.equals("O")){
            regleVerifie = verifierRegles(coordx, coordy, coordx2, coordy2, domino.getType1(), domino.getType2());
            if(regleVerifie) {
                plateau.plateau[coordy2][coordx2] = domino.getType2();
                plateau.plateau[coordy][coordx] = domino.getType1();
                plateau.printPlateau();
                premierTour = false;
            }
        }


    }



    public boolean verifierRegles(int coordx, int coordy, int coordx2, int coordy2, int type1 , int type2) {
        if(dansLesLimitesDuPlateau(coordx) && dansLesLimitesDuPlateau(coordy) && dansLesLimitesDuPlateau(coordx2) && dansLesLimitesDuPlateau(coordy2) ) {
            if (!(coteACoteType(coordx, coordy, type1) || coteACoteType(coordx2, coordy2, type2))) {
                System.out.println("La case a coté n'est pas du même type");
            }else {
                return verifierCaseNonUtilisee(coordx, coordy) &&
                        verifierCaseNonUtilisee(coordx2, coordy2) &&
                        coteACote(coordx, coordy, coordx2, coordy2) &&
                        tailleMax(coordx, coordy) &&
                        tailleMax(coordx2, coordy2);
            }
        }
        return false;
    }

    public boolean verifierCaseNonUtilisee(int x, int y) {
        System.out.println(x);
        System.out.println(y);
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

    public boolean coteACoteType(int x, int y, int typeVerifie) {
        //Si permier tout regarder si a coté du roi de type 1
        if(premierTour){
            typeVerifie = 1;
        }
        boolean result = false;

        if( x != 8){
            result = plateau.plateau[y][x+1] == typeVerifie;

        }
        if(y != 8 ){
            result = result || plateau.plateau[y+1][x] == typeVerifie;

        }
        if(x != 0 ){
            result = result || plateau.plateau[y][x-1] == typeVerifie;

        }
        if( y != 0 ){
            result = result || plateau.plateau[y-1][x] == typeVerifie;

        }

        return result;
    }

    public boolean coteACote(int coordx, int coordy, int coordx2, int coordy2) {
        if((Math.abs(coordx - coordx2) == 1) == (Math.abs(coordy - coordy2) == 1)){
            System.out.println("Les deux coordonnees ne sont pas a cote");
        }
        return Math.abs(coordx-coordx2) == 1 ^ Math.abs(coordy-coordy2) == 1;
    }

    public boolean tailleMax (int coordx, int coordy){
        // coté gauche x
        int plusLoinGauche = coordx;
        int plusLoinDroite = coordx;
        for(var x = 1; x <= coordx; x++){
            if(plateau.plateau[coordx-x][coordy] != 0 ){
                plusLoinGauche = coordx - x;
            }
        }
        // coté droit x
        for(var x = coordx + 1; x >= plateau.taille-1; x++){
            if(plateau.plateau[x][coordy] != 0 ){
                plusLoinDroite = x;
            }
        }
        // coté haut y
        int plusLoinHaut = coordy;
        int plusLoinBas = coordy;
        for(var y = 1; y <= coordy; y++){
            if(plateau.plateau[coordx][coordy-y] != 0 ){
                plusLoinHaut = coordy - y;
            }
        }

        //coté bas y
        for(var y = coordy + 1; y >= plateau.taille-1; y++){
            if(plateau.plateau[coordx][y] !=  0 ){
                plusLoinBas = y ;
            }
        }
        return (plusLoinDroite - plusLoinGauche <= 5) && (plusLoinHaut - plusLoinBas <= 5);
    }

    public void compteScore(){
        //compte couronnes
        for (Domino domino : listeDominosChoisi){
            score += domino.getnbCouronne2();
            score += domino.getnbCouronne();

        }


    }
}

