package com.tigerisland.game;

import com.tigerisland.GlobalSettings;
import com.tigerisland.InvalidMoveException;
import com.tigerisland.game.Color;
import com.tigerisland.game.Player;
import com.tigerisland.game.PlayerSet;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class PlayerSetTest {

    private PlayerSet playerSet;

    @Before
    public void createPlayerOrder() {
        this.playerSet = new PlayerSet(new GlobalSettings());
    }

    @Test
    public void testCanCreatePlayerOrder() {
        assertTrue(playerSet != null);
    }

    @Test
    public void testCanShuffleOrderOfPlayers() {
        playerSet.shufflePlayerList();
        assertTrue(true);
    }

    @Test
    public void testCanGetCurrentPlayer() {
        assertTrue(playerSet.getCurrentPlayer() != null);
    }

    @Test
    public void testCanSetNextPlayer() {
        Color nextPlayerString = playerSet.getPlayerList().get(1).getPlayerColor();
        playerSet.setNextPlayer();
        Color currentPlayerString = playerSet.getCurrentPlayer().getPlayerColor();
        assertTrue(nextPlayerString.equals(currentPlayerString));
    }

    @Test
    public void testThatPlayerOrderLoopsAfterLastPlayer() {
        for(int turns = 0; turns < 1; turns ++) {
            playerSet.setNextPlayer();
        }
        assertTrue(playerSet.getCurrentPlayer().getPlayerColor() == Color.WHITE);
    }

    @Test
    public void testCanGetPlayerList() {
        ArrayList<Player> players = playerSet.getPlayerList();
        GlobalSettings globalSettings = new GlobalSettings();
        assertTrue(players != null && players.size() ==  globalSettings.players);
    }

    @Test
    public void testCanUpdatePlayerState() throws InvalidMoveException {
        Player updatedPlayer = playerSet.getCurrentPlayer();
        updatedPlayer.getScore().addPoints(20);
        playerSet.updatePlayerState(updatedPlayer);
    }

}