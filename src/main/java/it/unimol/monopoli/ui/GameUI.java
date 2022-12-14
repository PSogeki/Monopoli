package it.unimol.monopoli.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import it.unimol.monopoli.app.Game;
import it.unimol.monopoli.app.ListSizeException;
import it.unimol.monopoli.app.MoneyException;
import it.unimol.monopoli.app.Player;
import it.unimol.monopoli.app.PlayerException;

public class GameUI {
    private Game game;

    public GameUI() {
        game = new Game();
    }

    public void start() {
        String choice = "n";
        System.out.println("-----------Benvenuto------------");
        
        try {
            if(Game.searchFile()) {
                System.out.println("Trovata vecchia partita, riprenderla? (y/n)");
                Scanner scanner = new Scanner(System.in);
                choice = scanner.nextLine();
                if(choice.equals("y")) {
                    game = Game.getInstance();
                    startGame();
                }
                else {
                    newGame();
                }
            }
            else{
                newGame();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Ciao.");
    }

    private void newGame() {
        boolean exit;
        do {
            printMenu();
            exit = input();
        } while (!exit);
    }

    private void printMenu() {
        System.out.println("\n1. Nuovo giocatore");
        System.out.println("2. Avvia gioco");
        System.out.println("0. Esci");
        System.out.println("Insierisci un numero per selezionare l'opzione: ");
    }

    private boolean input() {
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch(choice) {
            case 1:
                if(game.getPlayers().size() <= 6) {
                    addPlayer();
                    return false;
                }
                else {
                    System.out.println("Ci possono essere al massimo 6 giocatori");
                    return false; 
                }
            case 2:
                if(game.getPlayers().size() >= 2) {
                    try {
                        game.readyGame();
                    } catch (ListSizeException | MoneyException e) {
                        System.out.println("Errore avvio gioco: " + e.getMessage());
                        e.printStackTrace();
                    }
                    startGame();
                    return true;
                }
                else {
                    System.out.println("Ci devono essere almeno 2 giocatori");
                    return false;
                }
            case 0:
                return true;  
            default:
                System.out.println("Scelta non valida");
                return false;             
        }
    }

    private void addPlayer() {
        System.out.println("\nInserisci nome giocatore: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();

        Player player = new Player(name);
        try {
            game.addPlayer(player);
            System.out.println("\nGiocatore " + player.getName() + " aggiunto ");
        } catch (PlayerException e) {
            System.out.println("\nErrore, " + player.getName() + " non aggiunto ");
            e.printStackTrace();
        }
    }

    private void startGame() {
        String exit = "n";
        List<Player> players = game.getPlayers();
        
        do {
            for (Player player : players) {
                if(player.getPrison() == false) {
                    RoundUI round = new RoundUI(player, game.getContracts());
                    round.start();
                }
                else {
                    RoundPrisonUI roundPrison = new RoundPrisonUI(player, game.getContracts());
                    roundPrison.start();
                }
                if(player.getMoney() <= 0)
                    game.removePlayer(player);
            }
            if(players.size() > 1) {
                try {
                    game.saveGame();
                } catch (IOException e) {
                    System.out.println("Errore salvataggio gioco");
                    e.printStackTrace();
                }

                System.out.println("Giocatori rimanenti: " + players.size() + ", uscire? (y/n) ");
                Scanner scanner = new Scanner(System.in);
                exit = scanner.nextLine();
            }
        } while(exit.equals("n") && players.size() >= 1);

        String winner = "";
        if(players.size() == 1)
            winner = players.get(0).getName();
        else {
            int max = 0;
            for (Player player : players) {
                if(player.getMoney() > max)
                    winner = player.getName();
            }
        }
        System.out.println("Il vincitore è " + winner);
    }
}
