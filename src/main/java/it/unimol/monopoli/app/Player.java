package it.unimol.monopoli.app;

import java.awt.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe per la gestione dei vari attributi che caratterizzano un giocatore e dei relativi metodi che possono essere lanciati.
 * Implementa Serializable per permettere la serializzazione del giocatore.
 * 
 * @author Paolo Di Biase
 */
public class Player implements Serializable{
    private static final long serialVersionUID = 1L;

    private String name;
    private int money;
    private Color color;
    private int box;
    private List<Contract> contracts;
    private Boolean prison;

    /**
     * Costruttore di un nuovo giocatore utilizzato nell'ui.
     * 
     * @param name nome del giocatore passato dall'ui
     */
    public Player(String name) {
        this.name = name;
        this.money = 0;
        this.contracts = new ArrayList<Contract>();
        this.prison = false;
    }

    /**
     * Costruttore di un nuovo giocatore utilizzato nella gui.
     * 
     * @param name nome del giocatore passato dalla gui
     * @param color colore della pedina del giocatore passato dalla gui
     */
    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
        this.money = 0;
        this.box = 0;
        this.contracts = new ArrayList<Contract>();
        this.prison = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return this.color;
    }

    public int getMoney() {
        return money;
    }

    public int getBox() {
        return this.box;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public Boolean getPrison() {
        return this.prison;
    }

    /**
     * Metodo utilizzato per inizializzare il denaro posseduto dal giocatore.
     * Lancia MoneyExcception se si imposta a 0 il valore iniziale del denaro.
     * 
     * @param money il denaro da assegnare al giocaore
     * @throws MoneyException se {@code money <= 0}
     */
    public void setMoney(int money) throws MoneyException{
        if(money <= 0)
            throw new MoneyException("Giocatore inizializzato con 0 denaro");
        this.money = money;
    }

    /**
     * Metodo utilizzato per aggiungere denato al giocatore.
     * 
     * @param money la quantità di denaro da aggiungere
     */
    public void addMoney(int money) {
        this.money += money;
    }

    /**
     * Metodo utilizzato per rimuovere denaro dal giocatore.
     * Lancia MoneyException se la quantità di denaro da ruovere è maggiore di quella posseduta.
     * 
     * @param money la quantità di denaro da rimuovere.
     * @throws MoneyException se {@code money > this.money}
     */
    public void removeMoney(int money) throws MoneyException{
        if(this.money - money < 0)
            throw new MoneyException("Soldi insufficienti");
        this.money -= money;
    }

    /**
     * Metodo che permette al giocatore di avanzare di casella.
     * 
     * @param n numero di caselle da avanzare
     */
    public void advancesBox(int n) {
        if(this.box + n < 40)
            this.box += n;
        else
            this.box = this.box + n - 40;
    }
     /**
      * Metodo che permette di aggiungere il contratto indicato, alla lista dei contratti posseduti dal giocatore.

      * @param contract il contratto da aggiungere
      */
    public void addContract(Contract contract) {
        this.contracts.add(contract);
    }

    /**
     * Metodo che permette al giocatore di andare in prigione, 
     * impostando il relativo flag a true e la cassella corrispondente a quella della prigione.
     */
    public void goPrison() {
        this.prison = true;
        this.box = 10;
    }

    /**
     * Metodo che permette al giocatore di uscire di prigione, 
     * impostando il relativo flag a false.
     */
    public void exitPrison() {
        this.prison = false;
    }
}
