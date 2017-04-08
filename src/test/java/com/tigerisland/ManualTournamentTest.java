package com.tigerisland;

import net.sourceforge.argparse4j.inf.ArgumentParserException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ManualTournamentTest {

    private Tournament tournament;
    private GlobalSettings globalSettings;

    private String[] tournamentSettings = new String[]{"--offline", "false", "--turnTime", "1.5", "--ipaddress", "localhost", "--port", "6539", "--username", "username", "--password", "password", "--manual", "true"};

    @Before
    public void setupManualMockOnlineTournament() {
        TigerIsland tigerIsland = new TigerIsland();
        try {
            tigerIsland.parseArguments(tournamentSettings);
            globalSettings = tigerIsland.globalSettings;
        } catch (ArgumentParserException exception) {
            assertTrue(false);
        }

    }

    @Test
    public void testCouldParseTournamentSettings() {
        assertTrue(globalSettings != null);
    }

    @Ignore("Ignoring, need to differentiate LocalServer for manual and auto testing") @Test
    public void testRunMockTournament() {
        tournament = new Tournament(globalSettings);
        tournament.run();
    }

}
