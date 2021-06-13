package com.amiel;

import java.util.*;

import static java.util.Arrays.*;


public class Main {

    public static void main(String[] args) throws Exception {

        int nbJoueurs = 0;
        Affichage fenetre = new Affichage();
        fenetre.initAffichage();
        fenetre.afficheAccueil();
        while (!fenetre.commencer) {
            nbJoueurs = fenetre.nbJoueurs;
            Thread.sleep(100);
        }
        fenetre.frame.dispose();

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

        //TOUR 1
        //Définir ordre des joueurs

        Joueur[] listePassage = creerListePassage(nbJoueurs, listeJoueurs, true);

        //Piocher les dominos
        ArrayList<Domino> piocheDuTour = pioche.nouvellePiocheDuTour(listeKing.size());
        fenetre.initAffichage();
        //chaque joueur choisi son premier domino
        for (Joueur joueur : listePassage) {
            afficheNomsDominoDansListe(piocheDuTour);
            System.out.println(joueur.name);
            fenetre.updateAffichage(joueur, piocheDuTour);
            while(!fenetre.commencer){
                Thread.sleep(100);
            }
            fenetre.updateAffichage(joueur, piocheDuTour);
            fenetre.coordx = -1;
            fenetre.coordx2 = -1;
            fenetre.nbClique = 0;
            while(!joueur.regleVerifie) {

                while (fenetre.nbClique != 2) {

                    Thread.sleep(100);
                }
                joueur.updateListePlateau(fenetre.dominoChoisi, fenetre.coordx, fenetre.coordy, fenetre.coordx2, fenetre.coordy2);
                if(!joueur.regleVerifie){
                    fenetre.nbClique = 0;
                }
            }
            piocheDuTour = joueur.chooseDomino(piocheDuTour, fenetre.dominoChoisi, fenetre.coordx, fenetre.coordy, fenetre.coordx2, fenetre.coordy2);
            joueur.regleVerifie = false;
            fenetre.refresh();

        }
        //FIN TOUR 1
        // autres tours

        //while (!pioche.dominos.isEmpty()){
            //Piocher les dominos
            piocheDuTour = pioche.nouvellePiocheDuTour(listeKing.size());

            //liste de passage des joueurs
            listePassage = creerListePassage(nbJoueurs, listeJoueurs, false);
            listePassage[0] = listeJoueurs[0];
            System.out.println(listePassage);
            for (int i = 1; i < listeJoueurs.length; i++){
                int numeroDernierDomino = listeJoueurs[i].listeDominosChoisi.get(listeJoueurs[i].listeDominosChoisi.size() - 1 ).getNumeroDomino();
                listePassage[i] = listeJoueurs[i];
                int j = i;
                while(numeroDernierDomino < listeJoueurs[j-1].listeDominosChoisi.get(listeJoueurs[j-1].listeDominosChoisi.size() - 1 ).getNumeroDomino() ) {
                    Joueur copie = listePassage[j - 1];
                    listePassage[j - 1] = listeJoueurs[i];
                    listePassage[j] = copie;
                    if (j == 1){
                        break;
                    }
                    else{
                        j--;
                    }
                }
            }
            for (int i = 0; i < listePassage.length; i++){
                System.out.println(listePassage[i].name);
            }
            /*for (Joueur joueur : listeJoueurs){
                int numeroDernierDomino = joueur.listeDominosChoisi.get(joueur.listeDominosChoisi.size()-1).getNumeroDomino();
                if( listePassage.length == 0 ){
                    listePassage[0] = joueur;
                }
                else {
                    for (int i = 0; i < listePassage.length; i++) {
                        if (listePassage[i] == null || numeroDernierDomino > listePassage[i].listeDominosChoisi.get( listePassage[i].listeDominosChoisi.size() - 1).getNumeroDomino()) {
                            for (int j = listePassage.length - 1; i < j; j--) {
                                listePassage[j] = listePassage[j - 1];
                                System.out.println(listePassage);
                            }
                            listePassage[i] = joueur;
                        }
                    }
                }
                System.out.println("Voici l'ordre de passage :");
                for (int i = 0; i < listePassage.length; i++){
                    System.out.println(listePassage[i].name);
                }
            }*/







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

    public static Joueur[] creerListePassage(int nbJoueurs, Joueur[] listeJoueurs, boolean premierTour){
        Joueur[] listePassage = new Joueur[4];
        if( nbJoueurs == 3){
            listePassage = new Joueur[3];
        }

        if(premierTour){
            for ( int i = 0; i < nbJoueurs; i++){
                listePassage[i] = listeJoueurs[i];
                if(nbJoueurs == 2){
                    listePassage[i+2] = listeJoueurs[i];
                }
            }
            Collections.shuffle(asList(listePassage));
            System.out.println("Voici l'ordre de passage :");
            for (int i = 0; i < listePassage.length; i++){
                System.out.println(listePassage[i].name);
            }
        }
        return listePassage;
    }

    public static void afficheNomsDominoDansListe (ArrayList<Domino> piocheDuTour){
        for (int i = 0; i < piocheDuTour.size();i++) {
            System.out.println(piocheDuTour.get(i).getNumeroDomino());
        }
    }
}
