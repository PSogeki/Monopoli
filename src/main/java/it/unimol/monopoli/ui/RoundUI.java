package it.unimol.monopoli.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import it.unimol.monopoli.app.Contract;
import it.unimol.monopoli.app.MoneyException;
import it.unimol.monopoli.app.Player;
import it.unimol.monopoli.app.Round;

public class RoundUI {
    Player player;
    Round round;
    List<Contract> contracts;

    public RoundUI(Player player, List<Contract> contracts) {
        this.player = player;
        this.round = new Round(player);
        this.contracts = new ArrayList<Contract>();
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
        System.out.println("\nTurno di " + player.getName() + ", denaro posseduto: " + player.getMoney() + "\n\n");
        System.out.println("1. Visualizza contratti posseduti");
        System.out.println("2. Acquista contratto");
        System.out.println("3. Paga affitto");
        System.out.println("4. Paga tassa");
        System.out.println("5. Passa al via");
        System.out.println("6. Vai in prigione");
        System.out.println("0. Termina turno");
        System.out.println("Insierisci un numero per selezionare l'opzione: ");
    }

    private boolean input() {
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch(choice) {
            case 1: 
                this.showContracts();
                return true;
            case 2:
                this.buyContract();
                return true; 
            case 3:
                this.payRent();
                return true;
            case 4:
                this.payTax();
                return true;
            case 5:
                this.goStart();
                return true;
            case 6:
                this.goPrison();
                return false;
            case 0:
                return false;  
            default:
                System.out.println("Scelta non valida");
                return true;             
        }
    }

    private void showContracts() {
        if(player.getContracts().size() == 0)
            System.out.println("Nessun contratto posseduto ");
        else {
            System.out.println("\n");
            for (Contract contract : player.getContracts()) {
                System.out.println("nome: " + contract.getName() + ", prezzo: " + contract.getPrice() + ", affitto: " + contract.getRent());
            }
        }
    }

    private void buyContract() {
        int i = 1;
        for (Contract contract : contracts) {
            if(contract.getOwner() == null)
                System.out.println(i + " - nome: " + contract.getName() + ", prezzo: " + contract.getPrice() + ", affitto: " + contract.getRent());
            i++;
        }
        int choice;
        System.out.println("Inserire il numero corrispondente al contratto da comprare: ");
        Scanner scanner = new Scanner(System.in);
        choice = scanner.nextInt();
        if(contracts.get(choice-1).getOwner() == null) {
            try {
                round.buyContract(contracts.get(choice-1));
            } catch (MoneyException e) {
                System.out.println("Errore, non è possibile comprare il contratto: " + e.getMessage());
                e.printStackTrace();
            }
            System.out.println("Contratto acquistato\n ");
        }
        else {
            System.out.println("Contratto già posseduto da " + contracts.get(choice-1).getOwner().getName() + " riprovare\n ");
        }
    }

    private void payRent() {
        int i = 1;
        for (Contract contract : contracts) {
            if(contract.getOwner() != null && contract.getOwner() != this.player) 
                System.out.println(i + " - nome: " + contract.getName() + ", affitto: " + contract.getRent());
            i++;
        }
        int choice;
        System.out.println("Inserire il numero corrispondente al contratto di cui pagare l'affitto: ");
        Scanner scanner = new Scanner(System.in);
        choice = scanner.nextInt();
        if(contracts.get(choice-1).getOwner() != null && contracts.get(choice-1).getOwner() != this.player) {
            try {
                round.payRent(contracts.get(choice-1));
            } catch (MoneyException e) {
                System.out.println("Errore, non è possibile pagare completamente l'affitto: " + e.getMessage());
                contracts.get(choice-1).getOwner().addMoney(player.getMoney());
                try {
                    player.removeMoney(player.getMoney());
                } catch (MoneyException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
            System.out.println("Affitto pagato\n ");
        }
        else {
            System.out.println("Inserire un numero di contratto tra quelli presenti sopra, riprovare\n ");
        }
    }

    private void payTax() {
        System.out.println("Inserire importo da pagare ");
        Scanner scanner = new Scanner(System.in);
        int money = scanner.nextInt();
        try {
            round.payTax(money);
        } catch (MoneyException e) {
            System.out.println("Errore, non è possibile pagare completamente la tassa: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Tassa pagata\n ");
    }

    private void goStart() {
        round.goStart();
    }

    private void goPrison() {
        round.goPrison();
    }
}
