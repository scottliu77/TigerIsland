package com.tigerisland.settings;

import com.tigerisland.settings.GameSettings;
import com.tigerisland.settings.GlobalSettings;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GameSettingsTest {

    private GameSettings gameSettings;
    private GlobalSettings globalSettings;

    @Before
    public void createGameSettings() {
        this.globalSettings = new GlobalSettings();
        this.gameSettings = new GameSettings(this.globalSettings);
    }

    @Test
    public void testCanCreateGameSettings() {
        assertTrue(gameSettings != null);
    }

    @Test
    public void testCanCreateGameSettingsWithDefaults() {
        gameSettings = new GameSettings();
        assertTrue(gameSettings != null);
    }

    @Test
    public void testCanCreateGameSettingsViaCopyConstructor() {
        GameSettings copyGameSettings = new GameSettings(gameSettings);
        assertTrue(copyGameSettings != gameSettings);
    }

    @Test
    public void testCanCreateAndGetDeckOffline() {
        gameSettings.setDeck();
        assertTrue(gameSettings.getDeck() != null);
    }

    @Test
    public void testCanCreateAndGetPlayOrderOffline() {
        gameSettings.constructPlayerSet();
        assertTrue(gameSettings.getPlayerSet() != null);
    }

    @Test
    public void testCanSetMoveID() {
        gameSettings.setMoveID("1");
        assertTrue(gameSettings.getMoveID().equals("1"));
    }
}
