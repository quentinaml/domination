package com.amiel;

import javax.swing.*;
import java.awt.*;

/* notes :
0 = Vide
1 = Chateau
2 = Champs
3 = Foret
4 = Mer
5 = Prairie
6 = Mine
7 = Montagne
 */

public class Affichage {

    JFrame frame = new JFrame("jeu en cours");
    GridBagConstraints gbc = new GridBagConstraints();
    JPanel menu = new JPanel();
    JPanel panel_plateau = new JPanel();

    public void initAffichage() {
        frame.setSize(new Dimension(1500, 900));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLayout(new GridBagLayout());

        gbc.weightx = 6;
        gbc.weighty = 1;
    }
    public void updateMenu(){
        menu.setOpaque(true);
        menu.setBackground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
    }

    public void updatePlateau(int taille, int[][] plateau){
        panel_plateau.setOpaque(true);
        panel_plateau.setBackground(Color.WHITE);

        for (int x = 0; x < taille; x++) {
            for (int y = 0; y < taille; y++) {
                JButton label = new JButton(String.valueOf(x)+";"+String.valueOf(y));
                label.setOpaque(true);
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                if (plateau[x][y] == 0) { //Vide
                    label.setBackground(Color.white);
                }
                else if (plateau[x][y] == 1) { //chateau
                    label.setBackground(Color.black);
                }
                panel_plateau.add(label);
            }
        }
        panel_plateau.setLayout(new GridLayout(9,9));
        gbc.gridx = 1;
        gbc.gridwidth = 5;
        gbc.gridheight = 1;
        gbc.gridy = 0;
    }

    public void updateAffichage(int taille, int[][] plateau){

        //Panel du menu du joueur actuel
        updateMenu();
        frame.add(menu, gbc);
        //Panel du plateau du joueur actuel

        updatePlateau(taille, plateau);
        frame.add(panel_plateau, gbc);
        //frame.setLayout(new FlowLayout());
        frame.setVisible(true);

    }
}
