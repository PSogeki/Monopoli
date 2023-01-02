package it.unimol.monopoli.gui;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.io.File;

import it.unimol.monopoli.app.Contract;
import it.unimol.monopoli.app.Game;
import it.unimol.monopoli.app.Player;

public class RoundPanel extends JPanel {
    private final RoundPanel panel;
    private static RoundPanel instance;

    private GameBoard gameBoard;
    private Player player;
    private List<Player> players;
    private JFrame frame;
    private Game game;
    private int index;
    private int dice;
    private int time;

    private JLabel roundName;
    private JLabel money;
    private JLabel timer;
    private JTextPane contractText;
    private BufferedImage bgImage;

    public RoundPanel(JFrame frame, int index, Game game, GameBoard gameBoard) {
        this.panel = this;
        RoundPanel.instance = this;
        
        this.setLayout(null);

        try {
            bgImage = ImageIO.read(new File("images\\sfondo800.jpg"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                null, 
                "Errore caricamento immagine " + e.getMessage(), 
                "Errore", 
                JOptionPane.ERROR_MESSAGE
            );
        }

        this.frame = frame;
        this.game = game;
        this.players = game.getPlayers();
        this.time = 180;

        this.gameBoard = gameBoard;

        this.player = players.get(index);
        this.index = index;

        if(player.getPrison()) 
            this.roundName = new JLabel("Turno in prigione di " + player.getName());
        else
            this.roundName = new JLabel("Turno di " + player.getName());
        this.money = new JLabel("€ " + player.getMoney());
        this.timer = new JLabel("Tempo rimanente: " + time + " secondi");
        this.contractText = new JTextPane();
        this.contractText.setText(buildcontractText());

        buildPanel();
        update();
    }
    

    private void buildPanel() {
        this.setSize(800,800);

        gameBoard.setBounds(10,150,500,500);
        this.add(gameBoard);
        
        roundName.setFont(new Font("Kabel",Font.BOLD,30));
        roundName.setBounds(0,20,800,30);
        roundName.setHorizontalAlignment(JLabel.CENTER);
        this.add(roundName);
        money.setFont(new Font("Kabel",Font.BOLD,30));
        money.setBounds(0,60,800,30);
        money.setHorizontalAlignment(JLabel.CENTER);
        this.add(money);
        contractText.setBounds(525,150,250,430);
        contractText.setBackground(Color.green.darker());
        this.add(contractText);

        JPanel round;
        if(player.getPrison() == false) {
            timer.setFont(new Font("Kabel",Font.BOLD,13));
            timer.setBounds(550,30,200,20);
            timer.setHorizontalAlignment(JLabel.RIGHT);
            this.add(timer);
            round = new RoundNormalPanel(player, game.getContracts());

            Thread thread = new Thread(
                () -> {
                    while(time > 0) {
                        if(time == 60)
                            timer.setForeground(Color.red);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            JOptionPane.showMessageDialog(
                                null, 
                                "Errore thread " + e.getMessage(), 
                                "Errore", 
                                JOptionPane.ERROR_MESSAGE
                            );
                        }
                        time--;
                    }
                    JOptionPane.showMessageDialog(
                        null,
                        "Tempo terminato",
                        "Nuovo turno",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    this.endRound(round);
                }
            );
        thread.start();
        }
        else {
            round = new RoundPrisonPanel(player);
        }
        round.setBounds(525,580,250,70);
        round.setBackground(Color.green.darker());
        this.add(round);

        gameBoard.setVisible(true);
        round.setVisible(true);
    }


    private String buildcontractText() {
        String label = "Contratti Posseduti: \n\n";
        for (Contract contract : game.getContracts()) {
            if(contract.getOwner() != null && contract.getOwner().equals(player))
                label += contract.getName() + "\n";
        }
        return label;
    }

    public void dice() {
        Random random = new Random();
        this.dice = random.nextInt(12-2)+2;
        JOptionPane.showMessageDialog(
            null,
            "Turno di " + player.getName() + ":\n Lancio dadi: " + dice,
            "Nuovo turno",
            JOptionPane.INFORMATION_MESSAGE
        );
        
        gameBoard.boxManager(player, dice);
    }

    public void nextRound() {
        GameFrame.getInstance().startRound(index);
    }

    public void endRound(JPanel round) {
        if(player.getMoney() <= 0) {
            game.removePlayer(player);
            index--;
            JOptionPane.showMessageDialog(
                null,
                "Il giocatore " + player.getName() + " è stato eliminato!",
                "Eliminato",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
        this.remove(round);
        frame.remove(panel);

        if(players.size() <= 1 || index >= players.size()-1) {
            if(players.size() <= 1) {
                this.winner();
            }
            else {
                index = 0;
                try {
                    game.saveGame();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(
                        null, 
                        "Impossibile salvare il gioco: " + e.getMessage(), 
                        "Errore",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
                int result = JOptionPane.showConfirmDialog(
                    null, 
                    "Round finito, continuare? ",
                    "Round finito",
                    JOptionPane.YES_NO_OPTION);
                if(result == 0)
                    nextRound();
                else
                    System.exit(0);
            }
        }
        else {
            index++;
            nextRound();
        }
    }

    public void endRoundPrison(JPanel round) {
        if(player.getMoney() <= 0) {
            //players.remove(player);
            game.removePlayer(player);
            JOptionPane.showMessageDialog(
                null,
                "Il giocatore " + player.getName() + " è stato eliminato!",
                "Eliminato",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
        round.setVisible(false);
        this.remove(round);
        panel.setVisible(false);
        frame.remove(panel);

        if(players.size() <= 1) {
            this.winner();
        }
        else {
            nextRound();
        }
    }

    private void winner() {
        String winner = "";
        if(players.size() == 1)
            winner = players.get(0).getName();
        else {
            int max = 0;
            for (Player player1 : players) {
                if(player1.getMoney() > max)
                    winner = player1.getName();
            }
        }
        JOptionPane.showMessageDialog(
            null,
            "Il vincitore è " + winner + "!",
            "Game Over",
            JOptionPane.INFORMATION_MESSAGE
        );
        System.exit(0);
    }

    public void update() {
        Thread thread = new Thread( 
            () -> {
                while(true) {
                    if(player.getPrison()) 
                        this.roundName.setText("Turno in prigione di " + player.getName());
                    else {
                        this.roundName.setText("Turno di " + player.getName());
                        this.timer.setText("Tempo rimanente: " + time + " secondi");
                    }
                    this.money.setText("€ " + player.getMoney());
                    this.contractText.setText(buildcontractText());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        JOptionPane.showMessageDialog(
                            null, 
                            "Errore thread " + e.getMessage(), 
                            "Errore", 
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        );
        thread.start();
    }

    public static RoundPanel getInstance() {
        return RoundPanel.instance;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(bgImage, 0, 0, this);
      }

}
