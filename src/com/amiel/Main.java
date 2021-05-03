package com.amiel;

public class Main {

    public static void main(String[] args) throws Exception{

        Pioche pioche = new Pioche("dominos.csv");
        pioche.melangePioche();
        //pioche.affichePioche();

        int nbJoueurs;
        boolean commencer = false;
        Affichage fenetre = new Affichage();
        fenetre.initAffichage();
        while (!commencer) {
            fenetre.afficheAccueil();
            commencer = fenetre.commencer;
            nbJoueurs = fenetre.nbJoueurs;
            System.out.println(nbJoueurs);
            Thread.sleep(100);
        }
        fenetre.initAffichage();
        Plateau plateau1 = new Plateau(1,9);
        System.out.println("hello");


        fenetre.updateAffichage(plateau1.taille,plateau1.plateau);

    }
}
