package it.unimol.monopoli.ui;

import java.util.List;
import java.util.Scanner;

import it.unimol.monopoli.app.Contract;
import it.unimol.monopoli.app.MoneyException;
import it.unimol.monopoli.app.Player;
import it.unimol.monopoli.app.Round;

public class RoundPrisonUI {
    Player player;
    Round round;
    List<Contract> contracts;

    public RoundPrisonUI(Player player, List<Contract> contracts) {
        this.player = player;
        this.round = new Round(player);
        this.contracts = contracts;
    }

    public void start() {
        boolean exit;
        do {
            printMenu();
            exit = input();
        } while (exit && player.getMoney() > 0);
        
        System.out.println("Fine turno di " + player.getName() + "\n\n");
    }
    
    private void printMenu() {
        System.out.println("\nTurno in prigione di " + player.getName() + "Denaro posseduto: " + player.getMoney() + "\n\n");
        System.out.println("1. Esci gratis");
        System.out.println("2. Esci pagando $125");
        System.out.println("3. Rimani in prigione");
        System.out.println("Insierisci un numero per selezionare l'opzione: ");
    }

    private boolean input() {
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch(choice) {
            case 1: 
                this.exitFree();
                return false;
            case 2:
                this.exitPay();
                return false; 
            case 3:
                return false;  
            default:
                System.out.println("Scelta non valida");
                return true;             
        }
    }

    private void exitFree() {
        round.exitFreePrison();
        RoundUI roundUI = new RoundUI(player, contracts);
        roundUI.start();
    }

    private void exitPay() {
        try {
            round.exitPayPrison();
        } catch (MoneyException e) {
            System.out.println("Errore, non è possibile uscire di prigione: " + e.getMessage());
            e.printStackTrace();
        }
        RoundUI roundUI = new RoundUI(player, contracts);
        roundUI.start();
    }
}
