package com.tigerisland.game;

import com.tigerisland.GameSettings;
import com.tigerisland.GlobalSettings;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TurnInfoTest {

    private GameSettings gameSettings;
    private TurnInfo turnInfo;

    @Before
    public void createTurnInfo() {
        gameSettings = new GameSettings(new GlobalSettings());
        turnInfo = new TurnInfo("A", gameSettings);
    }

    @Test
    public void testCanCreateTurnInfo() {
        assertTrue(turnInfo != null);
    }

    @Test
    public void testCanIncrementAndGetMoveID() {
        int initialMoveID = turnInfo.getMoveID();
        turnInfo.incrementMoveNumber();
        assertTrue(turnInfo.getMoveID() == initialMoveID + 1);
    }

    @Test
    public void testCanGetGameSettings() {
        assertTrue(turnInfo.getGameSettings() == gameSettings);
    }
}
