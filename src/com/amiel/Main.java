package com.amiel;

public class Main {

    public static void main(String[] args) {

        Plateau plateau1 = new Plateau(1,9);
        plateau1.printPlateau();

        Affichage fenetre = new Affichage();
        fenetre.initAffichage();
        fenetre.updateAffichage(plateau1.taille,plateau1.plateau);
        System.out.println("hello");

    }
}
