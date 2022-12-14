package it.unimol.monopoli.gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.*;

import it.unimol.monopoli.app.Game;
import it.unimol.monopoli.app.Player;
import it.unimol.monopoli.app.PlayerException;

public class NewPlayerScreen extends JFrame{
    private Player player;
    private Game game;
    private JPanel panel;
    private JLabel name;
    private JTextField textField;
    private JLabel pawn;
    private JComboBox<String> comboBox;
    private JButton send;
    private JButton cancel;
    private String[] colors = {"Rosso", "Giallo", "Blu", "Verde", "Grigio", "Arancione"};

    public NewPlayerScreen(Game game) {
        this.game = game;

        this.setTitle("Monopoli: Nuovo giocatore");
        this.setMinimumSize(new Dimension(350,110));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        this.panel = new JPanel();
        this.name = new JLabel("Nome");
        this.textField = new JTextField(10);
        this.pawn = new JLabel("Pedina");
        this.comboBox = new JComboBox<>(colors);
        this.send = new JButton("Inserisci");
        this.cancel = new JButton("Annulla");

        this.addAction();

        this.buildPanel();
    }

    private void buildPanel() {
        this.add(panel);
        panel.setBackground(Color.green.darker());
        this.panel.add(name);
        this.panel.add(textField);
        this.panel.add(pawn);
        this.panel.add(comboBox);
        this.panel.add(cancel);
        this.panel.add(send);
        panel.setVisible(true);
    }

    private void addAction() {
        this.send.addActionListener(
            actionEvent -> {
                try {
                    buidPlayer();
                } catch (PlayerException e) {
                    JOptionPane.showMessageDialog(
                        null, 
                        "Impossibile aggiungere il giocatore: " + e.getMessage(), 
                        "Errore",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        );

        this.cancel.addActionListener(
            actionEvent -> this.dispose()
        );
    }

    private void buidPlayer() throws PlayerException{
        player = new Player(textField.getText(), stringToColor());
        game.addPlayer(player);
        JOptionPane.showMessageDialog(
            null, 
            "Giocatore aggiunto: " + player.getName(), 
            "Giocatore aggiunto",
            JOptionPane.INFORMATION_MESSAGE
        );
        this.dispose();
    }

    private Color stringToColor() {
        String color = comboBox.getItemAt(comboBox.getSelectedIndex());
        switch (color) {
            case "Rosso":
                return Color.red;
            case "Giallo":
                return Color.yellow;
            case "Blu":
                return Color.blue;
            case "Verde":
                return Color.green;
            case "Grigio":
                return Color.gray;
            case "Arancione":
                return Color.orange;
            default:
                return Color.white;
        }
    }
}
