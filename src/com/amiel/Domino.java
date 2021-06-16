package com.amiel;

import java.awt.*;

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

    public boolean afficheDomino() {
        System.out.println(this.nbCouronne1 + " Paysage :" + ConvertTypeToInt(this.type1) + " " + this.nbCouronne2 + "  Paysage :" + ConvertTypeToInt(this.type2) + " " + this.numeroDomino);
        return false;
    }

    public boolean afficheNumeroDomino() {
        //System.out.println(this.numeroDomino);
        return false;
    }

    public int ConvertTypeToInt(String type) {

        return switch (type) {
            case "Chateau" -> 1;
            case "Champs" -> 2;
            case "Foret" -> 3;
            case "Mer" -> 4;
            case "Prairie" -> 5;
            case "Mine" -> 6;
            case "Montagne" -> 7;
            default -> 0;
        };
    }

    public int getType1() {
        return ConvertTypeToInt(this.type1);
    }

    public int getType2() {
        return ConvertTypeToInt(this.type2);
    }

    public int getnbCouronne2() {
        return this.nbCouronne2;
    }

    public int getnbCouronne() {
        return this.nbCouronne1;
    }

    public int getNumeroDomino() {
        return this.numeroDomino;
    }

}
