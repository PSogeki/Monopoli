package it.unimol.monopoli.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import it.unimol.monopoli.app.Player;
import it.unimol.monopoli.app.Round;

public class GameBoard extends JPanel{
    private static GameBoard instance;

    private List<Player> players;
    private JLabel imageLabel;
    private List<JLabel> playersLabels;

    private GameBoard() {    
        GameBoard.instance = this;
        
        this.playersLabels = new ArrayList<>();

        this.setLayout(null);

        imageLabel = new JLabel(new ImageIcon("images\\Tabellone500.jpg"));
        imageLabel.setBounds(0,0,500,500);
    }

    public void setPlayers(List<Player> players) {
        this.players = players;

        int y = 435;
        for (Player player : this.players) {
            JLabel playerLabel = new JLabel(player.getName());
            playerLabel.setForeground(player.getColor());
            playerLabel.setBounds(435,y,50,12);
            y += 10;
            playersLabels.add(playerLabel);
            this.add(playerLabel);
            this.inizializeBox(player);
        }
        this.add(imageLabel);
    }

    private void inizializeBox(Player player) {
        JLabel playerLabel = null;
        for (JLabel label : playersLabels) {
            if(player.getName().equals(label.getText()))
                playerLabel = label;
        }
        int box = player.getBox();
        while(box > 0) {
            for(int i = 0; i < 10 && box > 0; i++) {
                playerLabel.setLocation((int)playerLabel.getLocation().getX() - 41, (int)playerLabel.getLocation().getY());
                box--;
            }
            for(int i = 0; i < 10 && box > 0; i++) {
                playerLabel.setLocation((int)playerLabel.getLocation().getX(), (int)playerLabel.getLocation().getY() - 40);
                box--;
            }
            for(int i = 0; i < 10 && box > 0; i++) {
                playerLabel.setLocation((int)playerLabel.getLocation().getX() + 41, (int)playerLabel.getLocation().getY());
                box--;
            }
            for(int i = 0; i < 10 && box > 0; i++) {
                playerLabel.setLocation((int)playerLabel.getLocation().getX(), (int)playerLabel.getLocation().getY() + 40);
                box--;
            }
        }
    }

    public void boxManager(Player player, int dice) {
        int prevBox = player.getBox();

        Round round = new Round(player);

        this.moveBox(player, dice);

        if(prevBox > player.getBox()) {
            round.goStart();
            JOptionPane.showMessageDialog(
                null,
                "Giocatore passato per il via",
                "Via",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
        
        if(player.getBox() == 30) {
            this.movePrison(player);
            round.goPrison();
            JOptionPane.showMessageDialog(
                null,
                "Giocatore in prigione",
                "Prigione",
                JOptionPane.INFORMATION_MESSAGE
            );
            GamePanel.getInstance().endRound(RoundNormalPanel.getInstance());
        }

    }

    private void moveBox(Player player, int dice) {
        JLabel playerLabel = null;
        for (JLabel label : playersLabels) {
            if(player.getName().equals(label.getText()))
                playerLabel = label;
        }
        
        for (int i = 0; i < dice; i++) {
            player.advancesBox(1);
            if(player.getBox() > 0 && player.getBox() < 11) 
                playerLabel.setLocation((int)playerLabel.getLocation().getX() - 41, (int)playerLabel.getLocation().getY());
            else {
                if(player.getBox() > 0 && player.getBox() < 21)
                    playerLabel.setLocation((int)playerLabel.getLocation().getX(), (int)playerLabel.getLocation().getY() - 40);
                    else {
                        if(player.getBox() > 0 && player.getBox() < 31)
                            playerLabel.setLocation((int)playerLabel.getLocation().getX() + 41, (int)playerLabel.getLocation().getY());
                            else {
                                playerLabel.setLocation((int)playerLabel.getLocation().getX(), (int)playerLabel.getLocation().getY() + 40);
                            }
                    }
            }
        }
    }

    private void movePrison(Player player) {
        this.moveBox(player, (40-player.getBox())+10);
    }

    public static GameBoard getInstance() {
        if(GameBoard.instance == null)
            GameBoard.instance = new GameBoard();
        return GameBoard.instance;
    }
}
