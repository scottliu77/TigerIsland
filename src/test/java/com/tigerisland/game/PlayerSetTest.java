package com.tigerisland.game;

import com.tigerisland.GlobalSettings;
import com.tigerisland.InvalidMoveException;
import com.tigerisland.game.Color;
import com.tigerisland.game.Player;
import com.tigerisland.game.PlayerSet;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class PlayerSetTest {

    private PlayerSet playerSet;

    @Before
    public void createPlayerOrder() {
        GlobalSettings globalSettings = new GlobalSettings();
        globalSettings.getServerSettings().setPlayerID("1");
        globalSettings.getServerSettings().setOpponentID("2");
        this.playerSet = new PlayerSet(globalSettings);
    }

    @Test
    public void testCanCreatePlayerOrder() {
        assertTrue(playerSet != null);
    }

    @Test
    public void testCanSetAndGetCurrentPlayer() {
        playerSet.setCurrentPlayer("1");
        assertTrue(playerSet.getCurrentPlayer() != null);
    }

    @Test
    public void testCanGetPlayerList() {
        HashMap<String, Player> players = playerSet.getPlayerList();
        GlobalSettings globalSettings = new GlobalSettings();
        assertTrue(players != null && players.size() ==  globalSettings.players);
    }

    @Test
    public void testCanUpdatePlayerState() throws InvalidMoveException {
        playerSet.setCurrentPlayer("1");
        Player updatedPlayer = playerSet.getCurrentPlayer();
        updatedPlayer.getScore().addPoints(20);
        playerSet.updatePlayerState("1", updatedPlayer);
    }

}