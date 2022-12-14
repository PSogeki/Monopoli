package it.unimol.monopoli.app;

import java.io.Serializable;

/**
 * Classe per la gestione dei vari attributi che caratterizzano un contratto e dei relativi metodi che possono essere lanciati.
 * Implementa Serializable per permettere la serializzazione del contratto.
 * 
 * @author Paolo Di Biase
 */
public class Contract implements Serializable{
    private static final long serialVersionUID = 1L;

    private String name;
    private int price;
    private int rent;
    private Player owner;

    /**
     * Costruttore di un nuovo contratto.
     * 
     * @param name il nome del contratto
     * @param price il prezzo del contratto
     * @param rent l'affitto del contratto
     */
    public Contract(String name, int price, int rent) {
        this.name = name;
        this.price = price;
        this.rent = rent;
        this.owner = null;
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return this.price;
    }

    public int getRent() {
        return this.rent;
    }

    public Player getOwner() {
        return this.owner;
    }

    public void setOwer(Player player) {
        this.owner = player;
    }
}
