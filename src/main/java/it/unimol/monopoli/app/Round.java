package it.unimol.monopoli.app;

/**
 * Classe per la gestione delle varie azioni che possono essere eseguite in un round da un giocatore.
 * Il giocatore di cui si gestiscono le azioni e inizializzato tramite il costruttore {@link #Round(Player)}
 * 
 * @author Paolo Di Biase
 */
public class Round {
    private Player player;

    /**
     * Costruttore di un nuovo round per il giocatore indicato.
     * 
     * @param player il giocatore per cui far partire un nuovo round
     */
    public Round(Player player) {
        this.player = player;
    }

    /**
     * Metodo per permettere al giocatore di acquistare il contratto indicato.
     * Lancia una MoneyException se il giocatore non possiede abbastanza denaro per acquistare il contratto.
     * 
     * @param contract il contratto da acquistare
     * @throws MoneyException se {@code player.getMoney() < contract.getPrice();}
     */
    public void buyContract(Contract contract) throws MoneyException{
        if(contract == null)
            throw new NullPointerException();
        player.removeMoney(contract.getPrice());
        contract.setOwer(player);
        player.addContract(contract);
    }

    /**
     * Metodo per permettere al giocatore di pagare l'affitto per un contratto posseduto da un altro giocatore.
     * Lancia una MoneyException se il giocatore non possiede abbastanza denaro per pagare l'affitto.
     * In quest'ultimo caso per garantire il pagamento almeno parziale dell'affitto, verrà trasferito l'intero importo posseduto dal giocatore.
     * 
     * @param contract il contratto di cui pagare l'affitto
     * @throws MoneyException se {@code player.getMoney() < contract.getRent();}
     */
    public void payRent(Contract contract) throws MoneyException{
        if(contract == null)
            throw new NullPointerException();
        if(player.getMoney() >= contract.getRent()) {
            player.removeMoney(contract.getRent());
            contract.getOwner().addMoney(contract.getRent());
        } else {
            player.removeMoney(player.getMoney());
            contract.getOwner().addMoney(player.getMoney());
        }
    }

    /**
     * Metodo che permette al giocatore di pagare una tassa.
     * Lancia una MoneyException se il giocatore non possiede abbastanza denaro per pagare la tassa.
     * In quest'ultimo caso verrà sottratto tutto il denaro restante del giocatore, per pagare almeno in parte la tassa
     * 
     * @param tax tassa da pagare
     * @throws MoneyException se {@code player.getMoney() < tax}
     */
    public void payTax(int tax) throws MoneyException{
        if(player.getMoney() >= tax)
            player.removeMoney(tax);
        else
            player.removeMoney(player.getMoney());
    }

    /**
     * Metodo per indicare il passaggio del giocatore per il via.
     * Aggiunge 500 al denaro del giocatore.
     */
    public void goStart() {
        player.addMoney(500);
    }

    /**
     * Metodo che indicare che il giocatore è andato in prigione.
     * Si ricollega a {@link goPrison()} per attivare il flag relativo alla prigione del giocatore.
     */
    public void goPrison() {
        player.goPrison();
    }

    /**
     * Metodo che permette al giocatore di uscire di prigione.
     * Si ricollega a {@link exitPrison()} per disattivare il flag relativo alla prigione del giocatore.
     */
    public void exitFreePrison() {
        player.exitPrison();
    }

    /**
     * Metodo che permette al giocatore d uscire di prigione pagando una somma di 125.
     * Lancia MoneyException se il giocatore non possiede abbastanza denaro per uscire di prigione.
     * In quest'ultimo caso non sarà possibile uscire di prigione pagando, ma bisogna aspettare di poter uscire gratis.
     * 
     * @throws MoneyException se {@code player.getMoney < 125}
     */
    public void exitPayPrison() throws MoneyException{
        player.removeMoney(125);
        player.exitPrison();
    }

}
