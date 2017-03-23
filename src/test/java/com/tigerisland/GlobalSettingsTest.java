package com.tigerisland;

import gherkin.formatter.model.Examples;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GlobalSettingsTest {

    private GlobalSettings globalSettings;
    private ArgumentParser parser = ArgumentParsers.newArgumentParser("TigerParser");

    @Before
    public void createSettings() {
        this.globalSettings = new GlobalSettings();
    }

    @Test
    public void testCanCreateSettings() {
        assertTrue(globalSettings != null);
    }

    @Test
    public void testCanCreateSettingsWithFullConstructor() {
        try {
            GlobalSettings globalSettings = new GlobalSettings(GlobalSettings.defaultOffline, GlobalSettings.defaultGames, GlobalSettings.defaultPlayers, GlobalSettings.defaultTurnTime, "ABCD", parser);
        } catch (ArgumentParserException e) {
            e.printStackTrace();
        }
        assertTrue(globalSettings != null);

    }
    @Test
    public void testCanGetOfflineStatus() {
        assertTrue(globalSettings.offline);
    }

    @Test
    public void testCannotSetTooFewPlayers() {
        try {
            GlobalSettings globalSettings = new GlobalSettings(GlobalSettings.defaultOffline, GlobalSettings.defaultGames, GlobalSettings.minPlayers - 1, GlobalSettings.defaultTurnTime);
            assertTrue(false);
        } catch (ArgumentParserException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCannotSetTooManyPlayers() {
        try {
            GlobalSettings globalSettings = new GlobalSettings(GlobalSettings.defaultOffline, GlobalSettings.defaultGames, GlobalSettings.maxPlayers + 1, GlobalSettings.defaultTurnTime);
            assertTrue(false);
        } catch (ArgumentParserException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCannotSetTooFewGames() {
        try {
            GlobalSettings globalSettings = new GlobalSettings(GlobalSettings.defaultOffline, GlobalSettings.minGames - 1, GlobalSettings.defaultPlayers, GlobalSettings.defaultTurnTime);
            assertTrue(false);
        } catch (ArgumentParserException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCannotSetTooManyGames() {
        try {
            GlobalSettings globalSettings = new GlobalSettings(GlobalSettings.defaultOffline, GlobalSettings.maxGames + 1, GlobalSettings.defaultPlayers, GlobalSettings.defaultTurnTime);
            assertTrue(false);
        } catch (ArgumentParserException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCannotSetTooShortOfRounds() {
        try {
            GlobalSettings globalSettings = new GlobalSettings(GlobalSettings.defaultOffline, GlobalSettings.defaultGames, GlobalSettings.defaultPlayers, GlobalSettings.minTurnTime - 1);
            assertTrue(false);
        } catch (ArgumentParserException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCannotSetTooLongOfRounds() {
        try {
            GlobalSettings globalSettings = new GlobalSettings(GlobalSettings.defaultOffline, GlobalSettings.defaultGames, GlobalSettings.defaultPlayers, GlobalSettings.maxTurnTime + 1);
            assertTrue(false);
        } catch (ArgumentParserException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCanGetNumberOfGamesInEachMatch() {
        assertTrue(globalSettings.gameCount == GlobalSettings.defaultGames);
    }

    @Test
    public void testCanGetNumberOfPlayersInEachMatch() {
        assertTrue(globalSettings.playerCount == GlobalSettings.defaultPlayers);
    }

    @Test
    public void testCanGetAllowedTimePerTurn() {
        assertTrue(globalSettings.turnTime == GlobalSettings.defaultTurnTime);
    }

    @Test
    public void testCanUseInboundQueue() {
        try {
            globalSettings.inboundQueue.add("New inbound string");
            assertTrue(globalSettings.inboundQueue.remove().equals("New inbound string"));
        } catch (Exception exception) {
            assert false;
        }
    }

    @Test
    public void testCanUseOutboundQueue() {
        try {
            globalSettings.outboundQueue.add("New outbound string");
            assertTrue(globalSettings.outboundQueue.remove().equals("New outbound string"));
        } catch (Exception exception) {
            assert false;
        }
    }
}


