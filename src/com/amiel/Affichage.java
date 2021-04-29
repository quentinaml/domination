package com.amiel;

import javax.swing.*;
import java.awt.*;

public class Affichage {

    JFrame frame = new JFrame("jeu en cours");

    public void initAffichage() {
        frame.setSize(new Dimension(800, 800));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void affichagePlateau(int taille, int[][] plateau){
        frame.setLayout(new GridLayout(taille, taille));

        for (int x = 0; x < taille; x++) {
            for (int y = 0; y < taille; y++) {
                if (plateau[x][y] == 0) { //vide
                    JLabel label = new JLabel();
                    label.setOpaque(true);
                    label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    label.setBackground(Color.white);
                    frame.add(label);
                }
                else if (plateau[x][y] == 1){ //royaume

                    JLabel label = new JLabel();
                    label.setIcon(new ImageIcon("./images/chateau.jpg"));

                    frame.add(label); // Ajout à la JFrame
                }
            }
        }
        frame.setVisible(true);
    }
}
