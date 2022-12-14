package it.unimol.monopoli.gui;

import javax.swing.JFrame;

import it.unimol.monopoli.app.Game;

public class GameFrame extends JFrame{
    private static GameFrame instance;
    
    private Game game;
    private GameBoard gameBoard;

    public GameFrame(Game game) {
        this.setTitle("Monopoli");
        this.setSize(800,800);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        GameFrame.instance = this;

        this.game = game;

        this.gameBoard = GameBoard.getInstance();
        gameBoard.setPlayers(game.getPlayers());
    }

    public void startRound(int index) {
        GamePanel round = new GamePanel(index, game);
        this.add(round);
        round.setVisible(true);
        if(game.getPlayers().get(index).getPrison() == false)
            round.dice();
    }

    public static GameFrame getInstance() {
        return GameFrame.instance;
    }
}