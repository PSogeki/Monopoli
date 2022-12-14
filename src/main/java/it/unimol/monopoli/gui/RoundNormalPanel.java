package it.unimol.monopoli.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import it.unimol.monopoli.app.Contract;
import it.unimol.monopoli.app.MoneyException;
import it.unimol.monopoli.app.Player;
import it.unimol.monopoli.app.Round;

public class RoundNormalPanel extends JPanel{
    private static RoundNormalPanel instance;

    private GamePanel roundPanel;

    private Player player;
    private List<Contract> contracts;
    private Round round;

    private JButton buyContract;
    private JButton payRent;
    private JButton payTax;
    private JButton endRound;

    public RoundNormalPanel(Player player, List<Contract> contracts) {
        RoundNormalPanel.instance = this;

        this.roundPanel = GamePanel.getInstance();

        this.player = player;
        this.contracts = contracts;
        this.round = new Round(this.player);

        this.buyContract = new JButton("Acquista contratto");
        this.payRent = new JButton("Paga affitto");
        this.payTax = new JButton("Paga tassa");
        this.endRound = new JButton("Termina turno");

        this.addAction();
        this.buildPanel();
    }

    private void addAction() {
        this.buyContract.addActionListener(
            actionEvent -> buyContract()
        );
        this.payRent.addActionListener(
            actionEvent -> payRent()
        );
        this.payTax.addActionListener(
            actionEvent -> payTax()
        );
        this.endRound.addActionListener(
            actionEvent -> roundPanel.endRound(this)
        );
    }

    private void buildPanel() {
        this.setBackground(null);
        this.add(buyContract);
        this.add(payRent);
        this.add(payTax);
        endRound.setBackground(Color.red.darker());
        this.add(endRound);
    }


    private void buyContract() {
        JFrame frame = new JFrame();
        frame.setTitle("Contratti");
        frame.setSize(new Dimension(550,500));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setBackground(Color.green.darker());

        for (Contract contract : contracts) {
            if(contract.getOwner() == null) {
                JLabel contractName = new JLabel("Nome: " + contract.getName() + ", prezzo: " + contract.getPrice() + ", affitto: " + contract.getRent());
                panel.add(contractName);
                JButton buy = new JButton("Acquista " + contract.getName());
                panel.add(buy);

                buy.addActionListener(
                    actionEvent -> {
                        try {
                            round.buyContract(contract);
                            JOptionPane.showMessageDialog(
                            null,
                            "" + player.getName() + " ha acquistato " + contract.getName(),
                            "Contratto acquistato",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                        } catch (MoneyException e) {
                            JOptionPane.showMessageDialog(
                                null,
                                "Impossibile acquistare il contratto: " + e.getMessage(),
                                "Errore",
                                JOptionPane.INFORMATION_MESSAGE
                            );
                        }
                        frame.dispose();
                    }
                );
            }
        }
        frame.add(panel);
        panel.setVisible(true);
        frame.setVisible(true);
    }

    private void payRent() {
        JFrame frame = new JFrame();
        frame.setTitle("Contratti");
        frame.setSize(new Dimension(500,500));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setBackground(Color.green.darker());

        for (Contract contract : contracts) {
            if(contract.getOwner() != null && contract.getOwner() != this.player) {
                JLabel contractName = new JLabel("Nome: " + contract.getName() + ", affitto: " + contract.getRent());
                panel.add(contractName);
                JButton buy = new JButton("Paga affitto di " + contract.getName());
                panel.add(buy);

                buy.addActionListener(
                    actionEvent -> {
                        try {
                            round.payRent(contract);
                            JOptionPane.showMessageDialog(
                            null,
                            "" + player.getName() + " ha pagato l'affitto di " + contract.getName(),
                            "Affitto pagato",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                        } catch (MoneyException e) {
                            JOptionPane.showMessageDialog(
                                null,
                                "Impossibile pagare totalmente affitto: " + e.getMessage(),
                                "Attenzione",
                                JOptionPane.WARNING_MESSAGE
                            );
                        }
                        frame.dispose();
                    }
                );
            }
        }
        frame.add(panel);
        panel.setVisible(true);
        frame.setVisible(true);
    }

    private void payTax() {
        int tax;
        tax = Integer.parseInt(
            JOptionPane.showInputDialog(
            null, 
            "Inserire importo tassa"
        ));
        try {
            round.payTax(tax);
            JOptionPane.showMessageDialog(
            null,
            "Tassa pagata",
            "Tassa pagata",
            JOptionPane.INFORMATION_MESSAGE
        );
        } catch (MoneyException e) {
            JOptionPane.showMessageDialog(
                null,
                "Impossibile pagare totalmente la tassa: " + e.getMessage(),
                "Attenzione",
                JOptionPane.WARNING_MESSAGE
            );
        }
    }
    
    public static RoundNormalPanel getInstance() {
        return RoundNormalPanel.instance;
    }
}
