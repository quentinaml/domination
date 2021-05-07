package com.amiel;

import java.util.ArrayList;
import java.util.Scanner;

public class Joueur {
    public final Plateau plateau;
    public String name;
    public int score;
    public int king;


    public Joueur(String name) {
        this.name = name;
        this.score = 0;
        this.plateau = new Plateau(1,9);
        System.out.println(name);
    }

    public int getScore() {
        return score;
    }

    public int chooseDomino(ArrayList<Domino> listDomino){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez saisir le premier domino");
        int domino = scanner.nextInt();
        System.out.println(domino);
        return domino;
    }
}
