package com.tigerisland;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MatchTest {

    private int playerID = 7;
    private int opponentID = 13;
    private GlobalSettings globalSettings;
    private Match match;

    @Before
    public void createMatch() {
        globalSettings = new GlobalSettings();
        globalSettings.getServerSettings().setPlayerID(playerID);
        globalSettings.getServerSettings().setOpponentID(opponentID);
        match = new Match(globalSettings);
    }

    @Test
    public void testCanCreateMatch() {
        assertTrue(match != null);
    }

    @Test
    public void testCanRunMatch() {
        match.run();
    }
}