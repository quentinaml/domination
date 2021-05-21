package com.amiel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) throws Exception{

        int nbJoueurs=0;
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

        for (int i = 1; i < nbJoueurs+1; i++){
            listeJoueurs[i-1] = new Joueur("Joueur" + i);
        }

        Pioche pioche = new Pioche("dominos.csv");
        pioche.melangePioche();
        if(nbJoueurs == 2){
            for (int i=0; i<24; i++){
                pioche.dominos.remove(0);
            }
        }
        else if(nbJoueurs == 3){
            for (int i=0; i<12; i++){
                pioche.dominos.remove(0);
            }
        }

        //tour 1
        //Définir ordre des joueurs


        ArrayList<Integer> maliste = new ArrayList<Integer>();
        for(int i =1;i<nbJoueurs+1;i++){
            maliste.add(i);
        }

        ArrayList<Integer> listePassage = new ArrayList<Integer>();
        Random rand = new Random();

        do{
            int next = maliste.get(rand.nextInt(maliste.size()));
            maliste.remove(maliste.indexOf(next));
            listePassage.add(next);
            //System.out.println("next : " + next);
            //System.out.println("maliste : "+maliste);
            //System.out.println("listePassage : "+listePassage);
        }while (maliste.size()!=0);


        //Piocher les dominos

        ArrayList<Domino> listDomino = pioche.affichePioche(nbJoueurs);
        System.out.println(listDomino);
        System.out.println(listePassage);

        //chaque joueur choisi son premier domino

        for (int i=0; i<nbJoueurs;i++){
            //Problème d'indexation ça me casse les couilles
            //J'arrive pas a trouver y'a moyen que ça soit la formule du dessous

            listeJoueurs[listePassage.get(i)].chooseDomino(listDomino);
        }


        fenetre.initAffichage();
        fenetre.updateAffichage(listeJoueurs[0].plateau.taille,listeJoueurs[0].plateau.plateau);

        // autres tours

    }

    private static ArrayList<Integer> generateRandomArray(int n) {
        ArrayList<Integer> list = new ArrayList<Integer>(n);
        Random random = new Random();

        for (int i = 0; i < n; i++)
        {
            list.add(random.nextInt(n));
        }
        return list;
    }
}
