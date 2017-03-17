package com.tigerisland;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class PlayerOrderTest {

    private PlayOrder playOrder;

    @Before
    public void createPlayerOrder() {
        this.playOrder = new PlayOrder(new GlobalSettings());
    }

    @Test
    public void testCanCreatePlayerOrder() {
        assertTrue(playOrder != null);
    }

    @Test
    public void testCanShuffleOrderOfPlayers() {
        playOrder.shufflePlayerOrder();
        assertTrue(true);
    }

    @Test
    public void testCanGetCurrentPlayer() {
        assertTrue(playOrder.getCurrentPlayer() != null);
    }

    @Test
    public void testCanSetNextPlayer() {
        Color nextPlayerString = playOrder.players.get(1).getPlayerColor();
        playOrder.setNextPlayer();
        Color currentPlayerString = playOrder.getCurrentPlayer().getPlayerColor();
        assertTrue(nextPlayerString.equals(currentPlayerString));
    }

    @Test
    public void testThatPlayerOrderLoopsAfterLastPlayer() {
        for(int turns = 0; turns < 10; turns ++) {
            playOrder.setNextPlayer();
        }
        assertTrue(playOrder.getCurrentPlayer().getPlayerColor() == Color.WHITE);
    }
}
