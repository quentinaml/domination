package com.amiel;

import java.util.*;

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

    public int[] melangeDomino(int[] arrayDomino){
        for(int position=arrayDomino.length-1; position>=1; position--){


            var hasard= (int)Math.floor(Math.random()*(position+1));


            var sauve=arrayDomino[position];
            arrayDomino[position]=arrayDomino[hasard];
            arrayDomino[hasard]=sauve;

        }
        return arrayDomino;
    }

    public void afficheDomino(){
        System.out.println(this.nbCouronne1 + " " + this.type1 + " " + this.nbCouronne2 + " " + this.type2 + " " + this.numeroDomino);
    }

    public int getNumeroDomino() {
        return numeroDomino;
    }
}
