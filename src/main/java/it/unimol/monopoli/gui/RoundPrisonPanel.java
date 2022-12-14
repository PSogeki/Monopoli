package it.unimol.monopoli.gui;

import java.awt.Color;

import javax.swing.*;

import it.unimol.monopoli.app.MoneyException;
import it.unimol.monopoli.app.Player;
import it.unimol.monopoli.app.Round;

public class RoundPrisonPanel extends JPanel{
    private GamePanel roundPanel;

    private Player player;
    private Round round;

    private JButton exitFree;
    private JButton exitPay;
    private JButton endRound;

    public RoundPrisonPanel(Player player) {

        this.roundPanel = GamePanel.getInstance();

        this.player = player;
        this.round = new Round(this.player);

        this.exitFree = new JButton("Esci gratis");
        this.exitPay = new JButton("Esci pagando");
        this.endRound = new JButton("Resta in prigione");

        this.addAction();
        this.buildPanel();
    }

    private void addAction() {
        this.exitFree.addActionListener(
            actionEvent -> this.exitFree()
        );
        this.exitPay.addActionListener(
            actionEvent -> this.exitPay()
        );
        this.endRound.addActionListener(
            actionEvent -> roundPanel.endRound(this)
        );
    }

    private void exitFree() {
        round.exitFreePrison();
        JOptionPane.showMessageDialog(
            null,
            "Giocatore uscito di prigione",
            "Esci di prigione",
            JOptionPane.INFORMATION_MESSAGE
        );
        this.startRound();
    }

    private void exitPay() {
        try {
            this.round.exitPayPrison();
            JOptionPane.showMessageDialog(
                null, 
                "Giocatore uscito di prigione", 
                "Esci di prigione",
                JOptionPane.INFORMATION_MESSAGE
            );
        } catch (MoneyException e) {
            JOptionPane.showMessageDialog(
                null,
                "Impossibile uscire di prigione: " + e.getMessage(),
                "Errore",
                JOptionPane.ERROR_MESSAGE
            );
        }
        this.startRound();
    }    

    private void buildPanel() {
        this.add(exitFree);
        this.add(exitPay);
        endRound.setBackground(Color.red.darker());
        this.add(endRound);
    }

    private void startRound() {
        roundPanel.endRoundPrison(this);
    }
}
