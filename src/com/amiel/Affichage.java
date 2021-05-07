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

    JFrame frame;
    GridBagConstraints gbc = new GridBagConstraints();
    JPanel menu = new JPanel();
    JPanel panel_plateau = new JPanel();
    int nbJoueurs = 2;
    boolean commencer;

    public void initAffichage() {
        frame = new JFrame("jeu en cours");
        frame.setSize(new Dimension(1500, 900));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public void afficheAccueil(){

        JLabel accueil = new JLabel("Bienvenue sur Domination, veuillez choisir le nombre de joueurs :");
        accueil.setOpaque(true);
        accueil.setBackground(Color.BLACK);
        accueil.setForeground(Color.WHITE);
        accueil.setPreferredSize(new Dimension(1500, 300));

        JLabel afficheNbJoueurs = new JLabel("nombre de joueurs : " + nbJoueurs);
        afficheNbJoueurs.setOpaque(true);
        afficheNbJoueurs.setBackground(Color.BLACK);
        afficheNbJoueurs.setForeground(Color.WHITE);
        afficheNbJoueurs.setPreferredSize(new Dimension(1100, 300));

        JButton minus = new JButton("-");
        minus.setPreferredSize(new Dimension(300, 300));
        minus.addActionListener(actionEvent -> {
            if(nbJoueurs > 2) {
                nbJoueurs--;
            }
            afficheNbJoueurs.setText("nombre de joueurs : " + nbJoueurs);
        });


        JButton plus = new JButton("+");
        plus.setPreferredSize(new Dimension(300, 300));
        plus.addActionListener(actionEvent -> {
            if(nbJoueurs < 4 ) {
                nbJoueurs++;
            }
            afficheNbJoueurs.setText("nombre de joueurs : " + nbJoueurs);
        });

        JButton buttonCommencer = new JButton("Cliquez pour commencer la partie");
        buttonCommencer.setPreferredSize(new Dimension(1500, 300));
        buttonCommencer.addActionListener(actionEvent -> {
            if(nbJoueurs > 1) {
                frame.dispose();
                commencer = true;
            }

        });


        frame.add(afficheNbJoueurs, BorderLayout.CENTER);
        frame.add(accueil, BorderLayout.PAGE_START);
        frame.add(minus, BorderLayout.WEST);
        frame.add(plus, BorderLayout.EAST);
        frame.add(buttonCommencer, BorderLayout.PAGE_END);
        frame.setVisible(true);
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
                JButton label = new JButton(x+";"+y);
                label.setOpaque(true);
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                if (plateau[x][y] == 0) { //Vide
                    label.setBackground(Color.PINK);
                }
                else if (plateau[x][y] == 1) { //Chateau
                    label.setBackground(Color.BLACK);
                }
                else if (plateau[x][y] == 2) { //Champs
                    label.setBackground(Color.YELLOW);
                }
                else if (plateau[x][y] == 3) { //Foret
                    label.setBackground(Color.GREEN);
                }
                else if (plateau[x][y] == 4) { //Mer
                    label.setBackground(Color.BLUE);
                }
                else if (plateau[x][y] == 5) { //Prairie
                    label.setBackground(Color.ORANGE);
                }
                else if (plateau[x][y] == 6) { //Mine
                    label.setBackground(Color.GRAY);
                }
                else if (plateau[x][y] == 7) { //Montagne
                    label.setBackground(Color.WHITE);
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
        frame.setLayout(new GridBagLayout());
        frame.setVisible(true);
        gbc.weightx = 6;
        gbc.weighty = 1;
        updateMenu();
        frame.add(menu, gbc);

        updatePlateau(taille, plateau);
        frame.add(panel_plateau, gbc);
        frame.setVisible(true);

    }
}
