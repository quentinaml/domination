package com.amiel;

import java.util.ArrayList;
import java.util.Scanner;

public class Joueur {
    public final Plateau plateau;
    public String name;
    public int score;
    public int king;
    public ArrayList<Domino> listeDominosChoisi;



    public Joueur(String name) {
        this.name = name;
        this.score = 0;
        this.plateau = new Plateau(1,9);
        this.listeDominosChoisi = new ArrayList<Domino>();
        System.out.println(name);
    }

    public int getScore() {
        return score;
    }

    public int chooseDomino(ArrayList<Domino> listeDomino){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez choisir un domino");
        int domino = scanner.nextInt();
        this.listeDominosChoisi.add(listeDomino.get(domino));
        System.out.println(domino);
        return domino;

    }

    public ArrayList<Domino> getListeDominosChoisi() {
        return listeDominosChoisi;
    }

    public <domino> Plateau upadteListePlateau(domino){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez choisir les coordonnées haut");
        int coordx = scanner.nextInt();
        int coordy = scanner.nextInt();
        this.plateau[coordx][coordy] = domino.getType1();

        System.out.println("Veuillez choisir les coordonnées bas");
        int coordx2 = scanner.nextInt();
        int coordy2 = scanner.nextInt();
        this.plateau[coordx2][coordy2] = domino.getType2();

        return plateau;
    }
}
