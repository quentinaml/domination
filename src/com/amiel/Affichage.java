package com.amiel;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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

public class Affichage{

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
                JButton label = new JButton();
                int offset = label.getInsets().left;
                String imageName = "images/" + piocheDuTour.get(x).numeroDomino + ".png";
                label.setIcon(resizeIcon(new ImageIcon(getImage(imageName)), label.getWidth() - offset, label.getHeight() - offset));

                //label.setPreferredSize(new Dimension(50, 50));
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

            BufferedImage myPicture = null;
            //myPicture = ImageIO.read(new File("images/chateau.png"));
            JPanel preViz = new JPanel();
            preViz.setSize(menu.getWidth()/3,menu.getHeight()/3);
            int offset = preViz.getInsets().left;

            String partieDomino1 = "images/" + dominoChoisi.type1 + "_" + dominoChoisi.nbCouronne1 + ".png";
            String partieDomino2 = "images/" + dominoChoisi.type2 + "_" + dominoChoisi.nbCouronne2 + ".png";

            JLabel picLabel = new JLabel(resizeIcon(new ImageIcon(getImage(partieDomino1)), (preViz.getWidth() - offset)/2, (preViz.getHeight() - offset)/2));
            JLabel picLabel2 = new JLabel(resizeIcon(new ImageIcon(getImage(partieDomino2)), (preViz.getWidth() - offset)/2, (preViz.getHeight() - offset)/2));

            preViz.add(picLabel);
            preViz.add(picLabel2);
            menu.add(preViz);

            JTextArea instruction = new JTextArea("score :" + joueur.score);
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

    public void updatePlateau(int taille, int[][][] plateau){

            panel_plateau.setOpaque(true);
            panel_plateau.setBackground(Color.WHITE);
            if(numeroDominoChoisi == 0) {
                for (int x = 0; x < taille; x++) {
                    for (int y = 0; y < taille; y++) {
                        JButton label = new JButton();
                        String imageName = null;
                        //label.setOpaque(true);
                        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        label.setMargin(new Insets(0, 0, 0, 0));
                        int offset = label.getInsets().left;
                        label.setPreferredSize(new Dimension(70, 70));
                        //label.setBorder(null);
                        switch (plateau[x][y][0]) {
                            case 0 -> {
                                label.setBackground(Color.PINK);
                                label.setText("vide");
                            }
                            case 1 -> imageName = "images/chateau";
                            case 2 -> imageName = "images/Champs";
                            case 3 -> imageName = "images/Foret";
                            case 4 -> imageName = "images/Mer";
                            case 5 -> imageName = "images/Prairie";
                            case 6 -> imageName = "images/Mine";
                            case 7 -> imageName = "images/Montagne";
                        }
                        if(imageName != null){
                            imageName += "_" + plateau[x][y][1] + ".png";
                            label.setIcon(resizeIcon(new ImageIcon(getImage(imageName)), label.getWidth() - offset, label.getHeight() - offset));
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

    public BufferedImage getImage (String imageName){
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File(imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert myPicture != null;
        return myPicture;
    }

    private static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    public void afficheScore(String phraseResultat){

        JLabel accueil = new JLabel("Voici la liste des scores de chaques joueurs :");
        accueil.setOpaque(true);
        accueil.setBackground(Color.BLACK);
        accueil.setForeground(Color.WHITE);
        accueil.setPreferredSize(new Dimension(1500, 300));

        JTextArea afficheScore = new JTextArea(phraseResultat);

        JButton rejouer = new JButton("Cliquez pour rejouer");
        rejouer.setPreferredSize(new Dimension(300, 300));
        rejouer.addActionListener(actionEvent -> {
            try {
                Main.partie();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        frame.add(accueil, BorderLayout.NORTH);
        frame.add(afficheScore, BorderLayout.CENTER);
        frame.add(rejouer, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

}
