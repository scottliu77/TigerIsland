package com.tigerisland;

import com.tigerisland.messenger.Message;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ManualTournamentTest {

    private Tournament tournament;
    private GlobalSettings globalSettings;

    private String[] tournamentSettings = new String[]{"--offline", "false", "--turnTime", "1.5", "--ipaddress", "localhost", "--port", "6539", "--username", "username", "--password", "password", "--manual", "false", "--dummyFeed", "true"};

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
        setupTournamentMessages();
        tournament = new Tournament(globalSettings);
        tournament.run();
    }

    private void setupTournamentMessages() {
        addMessage(new Message("WELCOME TO ANOTHER EDITION OF THUNDERDOME!"));
        addMessage(new Message("TWO SHALL ENTER, ONE SHALL LEAVE!"));
        addMessage(new Message("WAIT FOR THE TOURNAMENT TO BEGIN 7"));
        addMessage(new Message("NEW CHALLENGE 1 YOU WILL PLAY 1 MATCHES"));
        addMessage(new Message("BEGIN ROUND 1 OF 1"));
        addMessage(new Message("NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER 1"));
        addMessage(new Message("MAKE YOUR MOVE IN GAME X WITHIN 1.5 SECONDS: MOVE 1 PLACE GRASSLANDS+ROCKY"));
    }

    private void addMessage(Message message) {
        globalSettings.inboundQueue.add(message);
    }

}
