package com.amiel;

import java.io.*;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collections;


public class Pioche {
    public ArrayList dominos;

    public Pioche(String file) throws FileNotFoundException, IOException {
        ArrayList<Domino> pioche = new ArrayList<Domino>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String ligne = null;
        while ((ligne = br.readLine()) != null)
        {
            // Retourner la ligne dans un tableau
            String[] data = ligne.split(",");

            // Afficher le contenu du tableau
            for (int val=0; val < data.length; val += 5)
            {
                Domino domino = new Domino(Integer.valueOf(data[val]),data[val+1],Integer.valueOf(data[val+2]),data[val+3],Integer.valueOf(data[val+4]));
                pioche.add(domino);
            }
        }
        br.close();
        this.dominos = pioche;
    }

    public void melangePioche(){
        Collections.shuffle(this.dominos);
    }

    public ArrayList<Domino> nouvellePiocheDuTour (int nbDominosPioche){
        melangePioche();
        ArrayList<Domino> listeDomino = new ArrayList<Domino>(nbDominosPioche);
        for (int i=0; i < nbDominosPioche; i++){
            Domino domino = (Domino) dominos.get(i);
            listeDomino.add(domino);
        }
        for (int i= nbDominosPioche-1 ; i >= 0 ; i--){
            dominos.remove(i);
        }


        return listeDomino;

    }
}
