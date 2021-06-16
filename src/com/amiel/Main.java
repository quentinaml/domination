package com.amiel;

import java.io.IOException;
import java.util.*;

import static java.util.Arrays.*;


public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        while(true) {
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

            //retire le nombre de dominos en fonction du nombre de joueur
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
            tour(listePassage, piocheDuTour, fenetre);
            //FIN TOUR 1

            // autres tours
            while (!pioche.dominos.isEmpty()) {
                //Piocher les dominos
                piocheDuTour = pioche.nouvellePiocheDuTour(listeKing.size());

                //liste de passage des joueurs
                //listePassage = creerListePassage(nbJoueurs, listeJoueurs, false);
                listePassage = creerListePassage(nbJoueurs, listeJoueurs, false);

                tour(listePassage, piocheDuTour, fenetre);
                //comptage des scores
                for (Joueur joueur : listeJoueurs) {
                    joueur.compteScore();
                    System.out.println(joueur.score);
                }
            }


            fenetre.frame.dispose();
            fenetre.initAffichage();

            String phraseResultat = classement(listeJoueurs);

            fenetre.afficheScore(phraseResultat);
            while (!fenetre.commencer) {
                Thread.sleep(100);
            }
        }
    }

    private static ArrayList<Integer> generateRandomArray(int n) {
        ArrayList<Integer> liste = new ArrayList<Integer>(n);
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            liste.add(random.nextInt(n));
        }
        return liste;
    }

    public static ArrayList<King> creerListeKing(int nbJoueurs) {
        ArrayList<King> listeKing = new ArrayList<King>(3);
        if (nbJoueurs == 2) {
            listeKing.add(new King("rouge"));
            listeKing.add(new King("rouge"));
            listeKing.add(new King("bleu"));
            listeKing.add(new King("bleu"));
        } else if (nbJoueurs == 3) {
            listeKing.add(new King("rouge"));
            listeKing.add(new King("bleu"));
            listeKing.add(new King("vert"));
        } else if (nbJoueurs == 4) {
            listeKing.add(new King("rouge"));
            listeKing.add(new King("bleu"));
            listeKing.add(new King("vert"));
            listeKing.add(new King("jaune"));
        }
        return listeKing;
    }

    public static Joueur[] creerListePassage(int nbJoueurs, Joueur[] listeJoueurs, boolean premierTour) {
        Joueur[] listePassage = new Joueur[4];
        if (nbJoueurs == 3) {
            listePassage = new Joueur[3];
        }

        if (premierTour) {
            for (int i = 0; i < nbJoueurs; i++) {
                listePassage[i] = listeJoueurs[i];
                if (nbJoueurs == 2) {
                    listePassage[i + 2] = listeJoueurs[i];
                }
            }
            Collections.shuffle(asList(listePassage));
        } else {
            listePassage[0] = listeJoueurs[0];
            if (nbJoueurs != 2) {
                for (int i = 1; i < listeJoueurs.length; i++) {
                    int numeroDernierDomino = listeJoueurs[i].listeDominosChoisi.get(listeJoueurs[i].listeDominosChoisi.size() - 1).getNumeroDomino();
                    listePassage[i] = listeJoueurs[i];
                    int j = i;
                    while (numeroDernierDomino < listePassage[j - 1].listeDominosChoisi.get(listePassage[j - 1].listeDominosChoisi.size() - 1).getNumeroDomino()) {
                        Joueur copie = listePassage[j - 1];
                        listePassage[j - 1] = listeJoueurs[i];
                        listePassage[j] = copie;
                        if (j == 1) {
                            break;
                        } else {
                            j--;
                        }
                    }
                }
            } else {
                int numeroDernierDomino = listeJoueurs[1].listeDominosChoisi.get(listeJoueurs[1].listeDominosChoisi.size() - 1).getNumeroDomino();
                listePassage[1] = listeJoueurs[1];
                int j = 1;
                while (numeroDernierDomino < listePassage[j - 1].listeDominosChoisi.get(listePassage[j - 1].listeDominosChoisi.size() - 1).getNumeroDomino()) {
                    Joueur copie = listePassage[j - 1];
                    listePassage[j - 1] = listeJoueurs[1];
                    listePassage[j] = copie;
                    break;
                }
                for (int i = 0; i < 2; i++) {
                    numeroDernierDomino = listeJoueurs[i].listeDominosChoisi.get(listeJoueurs[i].listeDominosChoisi.size() - 2).getNumeroDomino();
                    listePassage[i + 2] = listeJoueurs[i];
                    j = i + 2;
                    while (numeroDernierDomino < listePassage[j - 1].listeDominosChoisi.get(listePassage[j - 1].listeDominosChoisi.size() - 2).getNumeroDomino()) {
                        Joueur copie = listePassage[j - 1];
                        listePassage[j - 1] = listeJoueurs[i];
                        listePassage[j] = copie;
                        if (j == 1) {
                            break;
                        } else {
                            j--;
                        }
                    }
                }
            }
        }
        return listePassage;
    }

    public static void afficheNomsDominoDansListe(ArrayList<Domino> piocheDuTour) {
        for (Domino domino : piocheDuTour) {
            System.out.println(domino.getNumeroDomino());
        }
    }

    public static void tour(Joueur[] listePassage, ArrayList<Domino> piocheDuTour, Affichage fenetre) throws InterruptedException {
        for (Joueur joueur : listePassage) {
            //afficheNomsDominoDansListe(piocheDuTour);
            //System.out.printprintPlateauln(joueur.name);
            fenetre.updateAffichage(joueur, piocheDuTour);
            while (!fenetre.commencer) {
                Thread.sleep(100);
            }
            fenetre.updateAffichage(joueur, piocheDuTour);
            fenetre.coordx = -1;
            fenetre.coordx2 = -1;
            fenetre.nbClique = 0;
            while (!joueur.regleVerifie) {
                if (fenetre.defaussage) {
                    break;
                }
                while (fenetre.nbClique != 2) {
                    if (fenetre.defaussage) {
                        break;
                    }
                    Thread.sleep(100);
                }
                joueur.updateListePlateau(fenetre.dominoChoisi, fenetre.coordx, fenetre.coordy, fenetre.coordx2, fenetre.coordy2);
                if (!joueur.regleVerifie) {
                    fenetre.nbClique = 0;
                }
            }
            piocheDuTour = joueur.chooseDomino(piocheDuTour, fenetre.dominoChoisi, fenetre.coordx, fenetre.coordy, fenetre.coordx2, fenetre.coordy2);
            joueur.regleVerifie = false;
            fenetre.defaussage = false;
            fenetre.refresh();

        }
    }

    public static String classement(Joueur[] listeJoueurs){
        String message = "";
        Joueur[] classementListe = listeJoueurs;
        Joueur[] classementListeAvant = classementListe;
        do {
            classementListeAvant = classementListe;
            for (int x = 0; x < classementListe.length - 1; x++) {
                if (classementListe[x].score < classementListe[x + 1].score) {
                    Joueur copie = classementListe[x];
                    classementListe[x] = classementListe[x + 1];
                    classementListe[x + 1] = copie;
                }
            }

        }while(classementListe != classementListeAvant);

        int x = classementListe.length - 2;
        do {
            classementListeAvant = classementListe;
            while (x >= 0) {
                if (classementListe[x].score == classementListe[x + 1].score && classementListe[x].taillePlusGrandBiome < classementListe[x + 1].taillePlusGrandBiome) {
                    Joueur copie = classementListe[x];
                    classementListe[x] = classementListe[x + 1];
                    classementListe[x + 1] = copie;
                }
                x--;
            }
        }while(classementListe != classementListeAvant);

        if(classementListe[0].taillePlusGrandBiome == classementListe[1].taillePlusGrandBiome && classementListe[0].score == classementListe[1].score){
            message = "Egalite ! \n";
        }
        else{
            message = "Voici le classement : \n";
        }
        for (Joueur joueur : classementListe) {
            message +=  joueur.name + " : " + joueur.score + " points, plus grand biome : " + joueur.taillePlusGrandBiome + "\n";
        }
        return message;
    }


}
