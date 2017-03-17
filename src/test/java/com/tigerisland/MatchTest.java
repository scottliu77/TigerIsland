package com.tigerisland;

import net.sourceforge.argparse4j.inf.ArgumentParserException;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class MatchTest {

    private Match match;
    private GlobalSettings globalSettings;

    @Before
    public void createMatch() {
        this.globalSettings = new GlobalSettings();
        this.match = new Match(globalSettings);
    }

    @Test
    public void testCanCreateOfflineMatch() {
        assertTrue(match != null);
    }

    @Test
    public void testCanCreateOnlineMatchWithoutThrowingAnException() {
        try {
            GlobalSettings globalSettings = new GlobalSettings(true, GlobalSettings.defaultGames, GlobalSettings.defaultPlayers, GlobalSettings.defaultTurnTime);
            Match onlineMatch = new Match(globalSettings);
        } catch (ArgumentParserException e) {
            assertTrue(false);
        }
        assertTrue(true);
    }

    @Test
    public void testMatchCreatesTheCorrectNumberOfGames() {
        assertTrue(match.games.size() == globalSettings.gameCount);
    }
}
