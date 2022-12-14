package it.unimol.monopoli;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.Color;

import org.junit.Test;

import it.unimol.monopoli.app.Contract;
import it.unimol.monopoli.app.MoneyException;
import it.unimol.monopoli.app.Player;

public class PlayerTest {

    @Test
    public void testCostruttoreUI() {
        Player player = new Player("nome");
        assertEquals("nome", player.getName());
        assertEquals(0, player.getBox());
        assertNotNull(player.getContracts());
        assertEquals(false, player.getPrison());
    }

    @Test
    public void testCostruttoreGUI() {
        Player player = new Player("nome", Color.red);
        assertEquals("nome", player.getName());
        assertEquals(0, player.getBox());
        assertNotNull(player.getContracts());
        assertEquals(false, player.getPrison());
        assertEquals(Color.red, player.getColor());
    }

    @Test
    public void testSetMoney() throws MoneyException {
        Player player = new Player("nome");
        player.setMoney(100);
        assertNotEquals(0, player.getMoney());
    }

    @Test
    public void tesAddMoney() throws MoneyException {
        Player player = new Player("nome");
        player.setMoney(100);
        player.addMoney(100);
        assertEquals(200, player.getMoney());
    } 

    @Test
    public void testAdvancesBox() {
        Player player = new Player("nome");
        player.advancesBox(3);
        assertEquals(3, player.getBox());
        player.advancesBox(40);
        assertEquals(3, player.getBox());
    }

    @Test
    public void testAddContract() throws MoneyException {
        Player player = new Player("nome");
        player.setMoney(100);
        Contract contract = new Contract("contratto", 100, 20);
        player.addContract(contract);
        assertNotNull(player.getContracts());
        assertEquals(contract, player.getContracts().get(0));
    }

    @Test
    public void testPrison() {
        Player player = new Player("name");
        player.goPrison();
        assertEquals(true, player.getPrison());
        assertEquals(10, player.getBox());
        player.exitPrison();
        assertEquals(false, player.getPrison());
        assertEquals(10, player.getBox());
    }
}
