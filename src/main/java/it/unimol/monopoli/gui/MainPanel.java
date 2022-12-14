package it.unimol.monopoli.gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Graphics;

import it.unimol.monopoli.app.Game;
import it.unimol.monopoli.app.ListSizeException;
import it.unimol.monopoli.app.MoneyException;

public class MainPanel extends JPanel{
    private JButton newPlayer;
    private JButton newGame;
    private JButton exit;
    private Game game;
    private JFrame frame;
    private BufferedImage bgImage;

    public MainPanel(JFrame frame) {
        this.frame = frame;

        game = new Game();

        this.newPlayer = new JButton("Nuovo giocatore");
        this.newGame = new JButton("Avvia gioco");
        this.exit = new JButton("Esci");

        this.setLayout(null);

        try {
            bgImage = ImageIO.read(new File("src\\main\\java\\it\\unimol\\monopoli\\images\\screen500.jpg"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                null, 
                "Errore caricamento immagine " + e.getMessage(), 
                "Errore", 
                JOptionPane.ERROR_MESSAGE
            );
        }

        this.newPlayer.addActionListener(
            actionEvent -> this.newPlayer()
        );
        this.newGame.addActionListener(
            actionEvent -> this.readyGame()
        );
        this.exit.addActionListener(
            actionEvent -> System.exit(0)
        );
        
        newPlayer.setBounds(175,150,150,30);
        this.add(newPlayer);
        newGame.setBounds(175,200,150,30);
        this.add(newGame);
        exit.setBounds(175,250,150,30);
        this.add(exit);
        repaint();
    }

    private void newPlayer() {
        if(game.getPlayers().size() < 6) {
            NewPlayerScreen newPlayerScreen = new NewPlayerScreen(game);
            newPlayerScreen.setVisible(true);
        }
        else {
            JOptionPane.showMessageDialog(
                null, 
                "Non è possibile inserire più di 6 giocatori ", 
                "Attenzione", 
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void readyGame() {
        if(game.getPlayers().size() >= 2) {
            try {
                game.readyGame();
            } catch (ListSizeException | MoneyException e) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Errore caricamento gioco " + e.getMessage(), 
                    "Errore", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
            newGame(game);
            frame.dispose();
        }
        else {
            JOptionPane.showMessageDialog(
                null, 
                "Giocatori minimi: 2, giocatori inseriti:  " + game.getPlayers().size() + ", inserirne altri ", 
                "Attenzione", 
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    /*
     * Il gioco utilizza un indice nel round per individuare la posizione del giocatore che dovrà giocare, nella lista dei giocatori.
     */
    public void newGame(Game game) {
        GameFrame gameFrame = new GameFrame(game);
        gameFrame.setVisible(true);
        gameFrame.startRound(0);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(bgImage, 0, 0, this);
      }
}
