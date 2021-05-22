package com.amiel;

public class Plateau {
    public int joueur;
    public int taille;
    public int[][] plateau;

    public Plateau(int joueur, int taille) {
        this.joueur = joueur;
        this.taille = taille;
        this.plateau = new int[taille][taille];
        this.plateau[taille/2][taille/2] = 1;
    }

    public void printPlateau() {
        for(int ligne = 0; ligne < this.taille; ++ligne) {
            StringBuilder line = new StringBuilder();

            for(int colonne = 0; colonne < this.taille; ++colonne) {
                line.append(" ").append(this.plateau[ligne][colonne]);
            }

            System.out.println(line);
        }

    }

}

