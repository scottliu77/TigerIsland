package com.tigerisland;


import net.sourceforge.argparse4j.inf.ArgumentParserException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class TournamentTest {

    private Tournament tournament;
    private GlobalSettings globalSettings;

    @Test
    public void testCanCreateOfflineTournament() {
        Tournament tournament = new Tournament(new GlobalSettings());
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

    @Ignore("Ignoring can run tournament test") @Test
    public void testCanRunOfflineTournamentManually() {
        String[] manualTournamentSettings = new String[]{"--offline", "true", "--manual", "true"};

        TigerIsland tigerIsland = new TigerIsland();
        try {
            tigerIsland.parseArguments(manualTournamentSettings);
            globalSettings = tigerIsland.globalSettings;
        } catch (ArgumentParserException exception) {
            assertTrue(false);
        }
        tournament = new Tournament(globalSettings);
        tournament.run();
    }

    @Ignore("Ignoring can run tournament test") @Test
    public void testCanRunTournament() {
        Tournament tournament = new Tournament(new GlobalSettings());
        tournament.run();
    }

}
