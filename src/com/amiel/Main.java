package com.amiel;

import java.util.*;

import static java.util.Arrays.*;


public class Main {

    public static void main(String[] args) throws Exception {

        int nbJoueurs = 0;
        boolean commencer = false;
        Affichage fenetre = new Affichage();
        fenetre.initAffichage();
        while (!commencer) {
            fenetre.afficheAccueil();
            commencer = fenetre.commencer;
            nbJoueurs = fenetre.nbJoueurs;
            Thread.sleep(100);
        }

        Joueur[] listeJoueurs = new Joueur[nbJoueurs];

        for (int i = 1; i < nbJoueurs + 1; i++) {
            listeJoueurs[i - 1] = new Joueur("Joueur" + i);
        }
        //établissement des règles
        Pioche pioche = new Pioche("dominos.csv");
        pioche.melangePioche();

        //retire le nombre de carte en fonction du nombre de joueur
        if (nbJoueurs == 2) {
            for (int i = 0; i < 24; i++) {
                pioche.dominos.remove(0);
            }
        } else if (nbJoueurs == 3) {
            for (int i = 0; i < 12; i++) {
                pioche.dominos.remove(0);
            }
        }

        ArrayList<King> listeKing = creerListeKing(nbJoueurs);

        //tour 1
        //Définir ordre des joueurs

        Joueur[] listePassage = creerListePassage(nbJoueurs, listeJoueurs);

        //Piocher les dominos
        ArrayList<Domino> piocheDuTour = pioche.nouvellePiocheDuTour(listeKing.size());

        //chaque joueur choisi son premier domino
        for (Joueur joueur : listePassage) {
            afficheNomsDominoDansListe(piocheDuTour);
            System.out.println(joueur.name);
            piocheDuTour = joueur.chooseDomino(piocheDuTour, true);

        }


        fenetre.initAffichage();
        fenetre.updateAffichage(listeJoueurs[0].plateau.taille,listeJoueurs[0].plateau.plateau);

        // autres tours

    }

    private static ArrayList<Integer> generateRandomArray(int n) {
        ArrayList<Integer> liste = new ArrayList<Integer>(n);
        Random random = new Random();

        for (int i = 0; i < n; i++)
        {
            liste.add(random.nextInt(n));
        }
        return liste;
    }

    public static ArrayList<King> creerListeKing(int nbJoueurs){
        ArrayList<King> listeKing = new ArrayList<King>(3);
        if (nbJoueurs == 2){
            listeKing.add(new King("rouge"));
            listeKing.add(new King("rouge"));
            listeKing.add(new King("bleu"));
            listeKing.add(new King("bleu"));
        } else if (nbJoueurs == 3){
            listeKing.add(new King("rouge"));
            listeKing.add(new King("bleu"));
            listeKing.add(new King("vert"));
        }
        else if (nbJoueurs == 4) {
            listeKing.add(new King("rouge"));
            listeKing.add(new King("bleu"));
            listeKing.add(new King("vert"));
            listeKing.add(new King("jaune"));
        }
        return listeKing;
    }

    public static Joueur[] creerListePassage(int nbJoueurs, Joueur[] listeJoueurs){
        Joueur[] listePassage = new Joueur[4];
        if( nbJoueurs == 3){
            listePassage = new Joueur[3];
        }

        for ( int i = 0; i < nbJoueurs; i++){
            listePassage[i] = listeJoueurs[i];
            if(nbJoueurs == 2){
                listePassage[i+2] = listeJoueurs[i];
            }
        }
        Collections.shuffle(asList(listePassage));
        for (int i = 0; i < listePassage.length; i++){
            System.out.println(listePassage[i].name);
        }
        return listePassage;
    }

    public static void afficheNomsDominoDansListe (ArrayList<Domino> piocheDuTour){
        for (int i = 0; i < piocheDuTour.size();i++) {
            System.out.println(piocheDuTour.get(i).getNumeroDomino());
        }
    }
}
