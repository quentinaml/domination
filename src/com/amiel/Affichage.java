package com.amiel;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;


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
    boolean defaussage;
    Domino dominoChoisi;
    int numeroDominoChoisi = 0;
    int nbClique = 0;
    int coordx = -1;
    int coordy = -1;
    int coordx2 = -1;
    int coordy2 = -1;

    public void refresh(){
        menu.removeAll();
        panel_plateau.removeAll();
        coordx = -1;
        coordx2 = -1;
        nbClique = 0;
        commencer = false;
        numeroDominoChoisi = 0;
    }
    public void initAffichage() {
        frame = new JFrame("jeu en cours");
        frame.setSize(new Dimension(1500, 900));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        commencer = false;
        numeroDominoChoisi = 0;
        nbClique = 0;
        defaussage = false;
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

    public void updateMenu(Joueur joueur, ArrayList<Domino> piocheDuTour){
        menu.setOpaque(true);
        menu.setBackground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        JTextArea afficheDomino = new JTextArea("");
        if(numeroDominoChoisi == 0){
            JLabel afficheTour = new JLabel(joueur.name + " : choisis un domino ");
            afficheTour.setForeground(Color.WHITE);
            menu.add(afficheTour);

            for (int x = 0; x < piocheDuTour.size(); x++) {
                JButton label = new JButton(String.valueOf(piocheDuTour.get(x).numeroDomino));
                label.setOpaque(true);
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                label.setBackground(Color.WHITE);
                label.setPreferredSize(new Dimension(50, 50));
                int finalX = x;
                label.addActionListener(actionEvent -> {
                    dominoChoisi = piocheDuTour.get(finalX);
                    numeroDominoChoisi = dominoChoisi.numeroDomino;
                    commencer = true;
                });
                menu.add(label);
            }
            menu.add(afficheDomino);

        }
        else{
            menu.remove(piocheDuTour.size()+1);
            menu.repaint();
            afficheDomino.setText("Votre domino  est :\nnombre de couronne1 : " + dominoChoisi.nbCouronne2 + " \ntype1 :" + dominoChoisi.type1 + " \nnombre de couronne2 : " + dominoChoisi.nbCouronne2 + " \ntype 2 : " + dominoChoisi.type2 + " \nnumero du domino : " + dominoChoisi.numeroDomino);
            menu.add(afficheDomino);
            JTextArea instruction = new JTextArea("Cliquez sur la première case\nou vous voulez placer votre\ndomino, puis sur la deuxieme");
            menu.add(instruction);

            //défaussage
            JButton defausse = new JButton("Défausser la carte");
            defausse.setOpaque(true);
            defausse.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            defausse.setBackground(Color.WHITE);
            defausse.setPreferredSize(new Dimension(50, 50));
            defausse.addActionListener(actionEvent -> {
                numeroDominoChoisi = dominoChoisi.numeroDomino;
                defaussage = true;
            });
            menu.add(defausse);
        }
        menu.setLayout(new GridLayout(4, 2));

    }

    public void updatePlateau(int taille, int[][] plateau){

            panel_plateau.setOpaque(true);
            panel_plateau.setBackground(Color.WHITE);
            if(numeroDominoChoisi == 0) {
                for (int x = 0; x < taille; x++) {
                    for (int y = 0; y < taille; y++) {
                        JButton label = new JButton(x + ";" + y);
                        label.setOpaque(true);
                        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        if (plateau[x][y] == 0) { //Vide
                            label.setBackground(Color.PINK);
                            label.setText("vide");
                        } else if (plateau[x][y] == 1) { //Chateau
                            label.setBackground(Color.BLACK);
                            label.setText("Chateau");
                        } else if (plateau[x][y] == 2) { //Champs
                            label.setBackground(Color.YELLOW);
                            label.setText("Champs");
                        } else if (plateau[x][y] == 3) { //Foret
                            label.setBackground(Color.GREEN);
                            label.setText("Foret");
                        } else if (plateau[x][y] == 4) { //Mer
                            label.setBackground(Color.BLUE);
                            label.setText("Mer");
                        } else if (plateau[x][y] == 5) { //Prairie
                            label.setBackground(Color.ORANGE);
                            label.setText("Prairie");
                        } else if (plateau[x][y] == 6) { //Mine
                            label.setBackground(Color.GRAY);
                            label.setText("Mine");
                        } else if (plateau[x][y] == 7) { //Montagne
                            label.setBackground(Color.WHITE);
                            label.setText("Montagne");
                        }
                        int finalX = x;
                        int finalY = y;
                        label.addActionListener(actionEvent -> {
                            if(nbClique == 0) {
                                coordx = finalY;
                                coordy = finalX;
                            }
                            else if(nbClique == 1){
                                coordx2 = finalY;
                                coordy2 = finalX;
                            }
                            nbClique++;
                        });
                        panel_plateau.add(label);
                    }
                }
            }
            panel_plateau.setLayout(new GridLayout(9, 9));
            gbc.gridx = 1;
            gbc.gridwidth = 5;
            gbc.gridheight = 1;
            gbc.gridy = 0;
      //  }
    }

    public void updateAffichage(Joueur joueur, ArrayList<Domino> piocheDuTour){
        frame.setLayout(new GridBagLayout());
        frame.setVisible(true);
        gbc.weightx = 6;
        gbc.weighty = 1;
        updateMenu(joueur, piocheDuTour);
        frame.add(menu, gbc);
        updatePlateau(joueur.plateau.taille, joueur.plateau.plateau);
        frame.add(panel_plateau, gbc);
        frame.setVisible(true);

    }
}
