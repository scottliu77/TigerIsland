package com.tigerisland;


import net.sourceforge.argparse4j.inf.ArgumentParserException;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class TournamentTest {

    private Tournament tournament;
    private GlobalSettings globalSettings;

    @Before
    public void createTournament() {
        this.globalSettings = new GlobalSettings();
        this.tournament = new Tournament(globalSettings);
    }

    @Test
    public void testCanCreateOfflineTournament() {
        assertTrue(tournament != null);
    }

    @Test
    public void testCanCreateOnlineTournamentWithoutThrowingAnException() {
        try {
            GlobalSettings globalSettings = new GlobalSettings(true, GlobalSettings.defaultTurnTime);
            Tournament onlineTournament = new Tournament(globalSettings);
        } catch (ArgumentParserException e) {
            assertTrue(false);
        }
        assertTrue(true);
    }

}
