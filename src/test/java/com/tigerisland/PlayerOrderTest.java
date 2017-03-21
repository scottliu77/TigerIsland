package com.tigerisland;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class PlayerOrderTest {

    private PlayerOrder playerOrder;

    @Before
    public void createPlayerOrder() {
        this.playerOrder = new PlayerOrder(new GlobalSettings());
    }

    @Test
    public void testCanCreatePlayerOrder() {
        assertTrue(playerOrder != null);
    }

    @Test
    public void testCanShuffleOrderOfPlayers() {
        playerOrder.shufflePlayerOrder();
        assertTrue(true);
    }

    @Test
    public void testCanGetCurrentPlayer() {
        assertTrue(playerOrder.getCurrentPlayer() != null);
    }

    @Test
    public void testCanSetNextPlayer() {
        Color nextPlayerString = playerOrder.getPlayerList().get(1).getPlayerColor();
        playerOrder.setNextPlayer();
        Color currentPlayerString = playerOrder.getCurrentPlayer().getPlayerColor();
        assertTrue(nextPlayerString.equals(currentPlayerString));
    }

    @Test
    public void testThatPlayerOrderLoopsAfterLastPlayer() {
        for(int turns = 0; turns < 9; turns ++) {
            playerOrder.setNextPlayer();
        }
        assertTrue(playerOrder.getCurrentPlayer().getPlayerColor() == Color.BLACK);
    }

    @Test
    public void testCanGetPlayerList() {
        ArrayList<Player> players = playerOrder.getPlayerList();
        GlobalSettings globalSettings = new GlobalSettings();
        assertTrue(players != null && players.size() ==  globalSettings.playerCount);
    }

    @Test
    public void testCanUpdatePlayerState() {
        Player updatedPlayer = playerOrder.getCurrentPlayer();
        updatedPlayer.addPoints(20);
        playerOrder.updatePlayerState(updatedPlayer);
    }

}