package it.unimol.monopoli.app;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe per la gestione delle meccaniche di inizializzazione del gioco e per il salvataggio del gioco.
 * Implementa Serializable per la serializzazione del gioco, andando a salvare la lista dei giocatori e dei contratti.
 */
public class Game implements Serializable{
    private static final long serialVersionUID = 1L;

    private List<Player> players;
    private List<Contract> contracts;

    public Game() {
        this.players = new ArrayList<Player>();
        this.contracts = new ArrayList<Contract>();
    }

    /**
     * Metodo per l'aggiunta di un nuovo giocatore. 
     * Lancia PlayerException se si inserisce un giocatore con un nome o un colore uguale ad uno già presente.
     * 
     * @param player il giocatore sa inserire
     * @throws PlayerException se {@code player1.getName().equals(player.getName()) || player1.getColor().equals(player.getColor())}
     */
    public void addPlayer(Player player) throws PlayerException {
        for (Player player1 : players) {
            if (player1.getName().equals(player.getName())) {
                throw new PlayerException("Nome già inserito");
            }
            if(player.getColor() != null && player1.getColor().equals(player.getColor())) {
                throw new PlayerException("Colore già inserito");
            }
        }
        players.add(player);
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public List<Contract> getContracts() {
        return this.contracts;
    }

    /**
     * Metodo per la ricerca di partite precedentemente salvate.
     * Lancia IOException se c'è un errore nella lettura del file.
     * Cattura FileNotFoundException se il tentativo di apertura del file fallisce.
     * 
     * @return {@code false}, se cattura una FileNotFoundException; {@code true}, se non ci sono errori
     * @throws IOException se ci sono errori di input/output
     */
    public static boolean searchFile() throws IOException {
        try (FileInputStream f = new FileInputStream("Game.sr")) {
            return true;
        } 
        catch (FileNotFoundException e) {
            return false;
        }
    }

    /**
     * Metodo che permette di ottenere un'instanza di gioco a partire dal file salvato.
     * Lancia ClassNotFoundException se non si riesce a trovare la classe di un oggetto serializzato.
     * Lancia IOException se c'è un errore nella lettura del file.
     * 
     * @return l'istanza del gioco
     * @throws ClassNotFoundException se {@code readObject()} non riece a trovare la classe relativa all'oggetto serializzato
     * @throws IOException se nella creazione di {@code ObjectInputStrem} ci sono errori di input/output
     */
    public static Game getInstance() throws ClassNotFoundException, IOException{
        try (
                FileInputStream f = new FileInputStream("Game.sr");
                ObjectInputStream o = new ObjectInputStream(f)
        ) {
            Object obj = o.readObject();
            return (Game) obj;
        }
    }

    /**
     * Metodo che gestisce l'inizializzazione del gioco.
     * In base al numero di giocatori presenti, assegna ad essi il denaro e i contratti, quest'ultimi in ordine casuale.
     * Infine mischia la lista dei giocatori per permettere di giocare in un ordine casuale rispetto quello di inserimento dei giocatori.
     * 
     * @throws ListSizeException se {@code players.size() < 2 || players.size() > 6}
     * @throws MoneyException se {@code player.getMoney <= 0}
     */
    public void readyGame() throws ListSizeException, MoneyException {
        getMoney();
        generateContracts();
        giveContracts();
        Collections.shuffle(players);
    }

    private void getMoney() throws ListSizeException, MoneyException{
        if(players.size() < 2 || players.size() > 6)
            throw new ListSizeException("Numero giocatori errato");
        
        int money = 0;
        if(players.size() == 2)
            money = 8750;
        if(players.size() == 3)
            money = 7500;
        if(players.size() == 4)
            money = 6250;
        if(players.size() == 5)
            money = 5000;
        if(players.size() == 6)
            money = 3750;
        for(Player player : players) {
            player.setMoney(money);
        }
    }

    private void giveContracts() throws ListSizeException{
        if(players.size() < 2 || players.size() > 6)
            throw new ListSizeException("Numero giocatori errato");
        
        Collections.shuffle(contracts);
        
        int contr = 0;
        if(players.size() == 2)
            contr = 7;
        if(players.size() == 3)
            contr = 6;
        if(players.size() == 4)
            contr = 5;
        if(players.size() == 5)
            contr = 4;
        if(players.size() == 6)
            contr = 3;
        int cont = 0;
        for (int i = 0; i < contr; i++) {
            for (Player player : players) {
                contracts.get(cont).setOwer(player);
                player.addContract(contracts.get(cont));
                cont++;
            }
        }
    }

    private void generateContracts() {
        Contract vicoloCorto = new Contract("Vicolo Corto", 60, 2);
        this.contracts.add(vicoloCorto);
        Contract vicoloStretto = new Contract("Vicolo Stretto", 60, 4);
        this.contracts.add(vicoloStretto);
        Contract bastioniGranSasso = new Contract("Bastioni Gran Sasso", 100, 6);
        this.contracts.add(bastioniGranSasso);
        Contract vialeMonterosa = new Contract("Viale Monterosa", 100, 6);
        this.contracts.add(vialeMonterosa);
        Contract vialeVesuvio = new Contract("Viale Vesuvio", 120, 8);
        this.contracts.add(vialeVesuvio);
        Contract viaAccademia = new Contract("Via Accademia", 140, 10);
        this.contracts.add(viaAccademia);
        Contract corsAteneo = new Contract("Corso Ateneo", 140, 10);
        this.contracts.add(corsAteneo);
        Contract piazzaUniversita = new Contract("Piazza Università", 160, 12);
        this.contracts.add(piazzaUniversita);
        Contract ViaVerdi = new Contract("Via Verdi", 180, 14);
        this.contracts.add(ViaVerdi);
        Contract corsoRaffaello = new Contract("Corso Raffaello", 180, 14);
        this.contracts.add(corsoRaffaello);
        Contract piazzaDante = new Contract("Piazza Dante", 200, 16);
        this.contracts.add(piazzaDante);
        Contract viaMarcoPolo = new Contract("Via Marco Polo", 220, 18);
        this.contracts.add(viaMarcoPolo);
        Contract corsoMagellano = new Contract("Corso Magellano", 220, 18);
        this.contracts.add(corsoMagellano);
        Contract largoColombo = new Contract("Largo Colombo", 240, 20);
        this.contracts.add(largoColombo);
        Contract vialeCostantino = new Contract("Viale Costantino", 260, 22);
        this.contracts.add(vialeCostantino);
        Contract vialeTraiano = new Contract("Viale Traiano", 260, 22);
        this.contracts.add(vialeTraiano);
        Contract piazzaGiulioCesare = new Contract("Piazza Giulio Cesare", 280, 24);
        this.contracts.add(piazzaGiulioCesare);
        Contract viaRoma = new Contract("Via Roma", 300, 26);
        this.contracts.add(viaRoma);
        Contract corsoImpero = new Contract("Corso Impero", 300, 26);
        this.contracts.add(corsoImpero);
        Contract largoAugusto = new Contract("Largo Augusto", 320, 28);
        this.contracts.add(largoAugusto);
        Contract vialeDeiGiardini = new Contract("Viale dei Giardini", 350, 35);
        this.contracts.add(vialeDeiGiardini);
        Contract parcoDellaVittoria = new Contract("Parco della Vittoria", 400, 40);
        this.contracts.add(parcoDellaVittoria);
        Contract stazioneSud = new Contract("Stazione sud", 200, 25);
        this.contracts.add(stazioneSud);
        Contract stazioneNord = new Contract("Stazione nord", 200, 25);
        this.contracts.add(stazioneNord);
        Contract stazioneEst = new Contract("Stazione est", 200, 25);
        this.contracts.add(stazioneEst);
        Contract stazioneOvest = new Contract("Stazione ovest", 200, 25);
        this.contracts.add(stazioneOvest);
    }

    /**
     * Metodo che permette la serializzazione della classe.
     * Lancia IOException in caso di errori di scrittura.
     * 
     * @throws IOException se ci sono errori durante la scrittura dell'oggetto
     */
    public void saveGame() throws IOException{
        try (
            FileOutputStream f = new FileOutputStream("Game.sr");
            ObjectOutputStream o = new ObjectOutputStream(f);
        ){
            o.writeObject(this);
        }
    }

    /**
     * Metodo per la rimozione di un giocatore dopo l'eliminazione.
     * Il metodo fa in modo che tutti i contratti posseduti dal giocatore siano disponibili per l'acquisto dopo la sua eliminazione.
     */
    public void removePlayer(Player player) {
        players.remove(player);
        for (Contract contract : player.getContracts()) {
            contract.setOwer(null);
        }
    }
}