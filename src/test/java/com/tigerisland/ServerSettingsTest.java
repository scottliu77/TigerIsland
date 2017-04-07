package com.tigerisland;

import com.tigerisland.ServerSettings;
import cucumber.api.java.eo.Se;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ServerSettingsTest {

    private ServerSettings serverSettings;

    @Before
    public void createServerSettings() {
        serverSettings = new ServerSettings();
    }

    @Test
    public void testCanCreateServerSettings() {
        assertTrue(serverSettings != null);
    }

    @Test
    public void testCanCreateServerSettingsWithArgs() {
        ServerSettings newServerSettings = new ServerSettings(ServerSettings.defaultIPaddress, ServerSettings.defaultPort, ServerSettings.defaultTournamentPassword, ServerSettings.defaultUsername, ServerSettings.defaultPassword, ServerSettings.defaultOffline);
        assertTrue(newServerSettings != null);
    }

    @Test
    public void testCanSetAndGetPlayerID() {
        serverSettings.setPlayerID("7");
        assertTrue(serverSettings.getPlayerID().equals("7"));
    }

    @Test
    public void testCanSetAndGetOpponentID() {
        serverSettings.setOpponentID("13");
        assertTrue(serverSettings.getOpponentID().equals("13"));
    }
}
