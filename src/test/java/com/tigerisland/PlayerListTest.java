package com.tigerisland;

import com.tigerisland.game.Color;
import com.tigerisland.game.Player;
import com.tigerisland.game.PlayerList;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class PlayerListTest {

    private PlayerList playerList;

    @Before
    public void createPlayerOrder() {
        this.playerList = new PlayerList(new GlobalSettings());
    }

    @Test
    public void testCanCreatePlayerOrder() {
        assertTrue(playerList != null);
    }

    @Test
    public void testCanShuffleOrderOfPlayers() {
        playerList.shufflePlayerList();
        assertTrue(true);
    }

    @Test
    public void testCanGetCurrentPlayer() {
        assertTrue(playerList.getCurrentPlayer() != null);
    }

    @Test
    public void testCanSetNextPlayer() {
        Color nextPlayerString = playerList.getPlayerList().get(1).getPlayerColor();
        playerList.setNextPlayer();
        Color currentPlayerString = playerList.getCurrentPlayer().getPlayerColor();
        assertTrue(nextPlayerString.equals(currentPlayerString));
    }

    @Test
    public void testThatPlayerOrderLoopsAfterLastPlayer() {
        for(int turns = 0; turns < 9; turns ++) {
            playerList.setNextPlayer();
        }
        assertTrue(playerList.getCurrentPlayer().getPlayerColor() == Color.BLACK);
    }

    @Test
    public void testCanGetPlayerList() {
        ArrayList<Player> players = playerList.getPlayerList();
        GlobalSettings globalSettings = new GlobalSettings();
        assertTrue(players != null && players.size() ==  globalSettings.playerCount);
    }

    @Test
    public void testCanUpdatePlayerState() throws InvalidMoveException {
        Player updatedPlayer = playerList.getCurrentPlayer();
        updatedPlayer.getScore().addPoints(20);
        playerList.updatePlayerState(updatedPlayer);
    }

}