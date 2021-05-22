package com.amiel;

public class Domino {
    public int nbCouronne1;
    public String type1;
    public int nbCouronne2;
    public String type2;
    public int numeroDomino;

    public Domino(int nbCouronne1, String type1, int nbCouronne2, String type2, int numeroDomino) {
        this.nbCouronne1 = nbCouronne1;
        this.type1 = type1;
        this.nbCouronne2 = nbCouronne2;
        this.type2 = type2;
        this.numeroDomino = numeroDomino;
    }

    public boolean afficheDomino(){
        System.out.println(this.nbCouronne1 + " " + this.type1 + " " + this.nbCouronne2 + " " + this.type2 + " " + this.numeroDomino);
        return false;
    }

    public String getType1(){return this.type1;}
    public String getType2(){return this.type2;}

    public int getNumeroDomino() {
        return numeroDomino;
    }
}
