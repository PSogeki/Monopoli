package it.unimol.monopoli.gui;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import it.unimol.monopoli.app.Game;

public class MainFrame extends JFrame{
    private final MainFrame frame;
    
    private Game game;
    private MainPanel panel;

    public MainFrame() {
        this.frame = this;
        this.panel = new MainPanel(frame);

        this.setTitle("Monopoli");
        this.setSize(new Dimension(500,500));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    public void start() {
        try {
            if(Game.searchFile()) {
                int result = JOptionPane.showConfirmDialog(
                    null, 
                    "Trovata partita in corso, continuare? ",
                    "Continuare partita?",
                    JOptionPane.YES_NO_OPTION
                );
                if(result == 0) {
                    this.dispose();
                    game = Game.getInstance();
                    panel.newGame(game);
                } else {
                    newGame();
                }
            }
            else {
                newGame();
            }
        } catch (ClassNotFoundException | IOException e) {
            JOptionPane.showMessageDialog(
                null, 
                "Errore caricamento scorsa partita: " + e.getMessage(), 
                "Errore",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /*
     * Questa finestra verrà resa visibile solo nel momento in cui si decide di inziare una nuova partita.
     * Con essa verrà reso visibile anche il pannello relativo.
     * Questo per fare in modo che se si continua una vecchia partita verrà mostrata direttamente la schemata di gioco.
     */
    private void newGame() {
        this.add(panel);
        panel.setVisible(true);
        this.setVisible(true);
    }

}
