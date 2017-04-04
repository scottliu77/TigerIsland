package com.tigerisland;

import com.tigerisland.messenger.Message;
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
            GlobalSettings globalSettings = new GlobalSettings(ServerSettings.defaultOffline, GlobalSettings.defaultTurnTime, ServerSettings.defaultIPaddress,
                    ServerSettings.defaultPort, ServerSettings.defaultTournamentPassword, ServerSettings.defaultUsername, ServerSettings.defaultPassword, false, parser);
        } catch (ArgumentParserException e) {
            e.printStackTrace();
        }
        assertTrue(globalSettings != null);

    }
    @Test
    public void testCanGetOfflineStatus() {
        assertTrue(globalSettings.getServerSettings().offline);
    }

    @Test
    public void testCannotSetTooShortOfRounds() {
        try {
            GlobalSettings globalSettings = new GlobalSettings(ServerSettings.defaultOffline, GlobalSettings.minTurnTime - 1);
            assertTrue(false);
        } catch (ArgumentParserException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCannotSetTooLongOfRounds() {
        try {
            GlobalSettings globalSettings = new GlobalSettings(ServerSettings.defaultOffline, GlobalSettings.maxTurnTime + 1);
            assertTrue(false);
        } catch (ArgumentParserException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCanGetAllowedTimePerTurn() {
        assertTrue(globalSettings.turnTime == GlobalSettings.defaultTurnTime);
    }

    @Test
    public void testCanUseInboundQueue() {
        try {
            globalSettings.inboundQueue.add(new Message("New inbound string"));
            assertTrue(globalSettings.inboundQueue.remove().message.equals("New inbound string"));
        } catch (Exception exception) {
            assert false;
        }
    }

    @Test
    public void testCanUseOutboundQueue() {
        try {
            globalSettings.outboundQueue.add(new Message("New outbound string"));
            assertTrue(globalSettings.outboundQueue.remove().message.equals("New outbound string"));
        } catch (Exception exception) {
            assert false;
        }
    }
}


