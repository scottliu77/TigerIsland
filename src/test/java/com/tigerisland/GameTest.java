package com.tigerisland;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

public class GameTest {

    private GlobalSettings globalSettings = new GlobalSettings();
    private GameSettings gameSettings;
    private Game game;

    @Before
    public void createGame() {
        this.gameSettings = new GameSettings(globalSettings);
        this.game = new Game(gameSettings);
    }

    @Test
    public void testCanCreateGame() {
        assertTrue(game != null);
    }
}
