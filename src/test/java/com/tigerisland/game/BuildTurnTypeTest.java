package com.tigerisland.game;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class BuildTurnTypeTest {

    @Test
    public void testCanCreateMoveType() {
        assertTrue(BuildActionType.TOTOROPLACEMENT instanceof BuildActionType);
    }

    @Test
    public void testCanGetMoveTypeString() {
        assertTrue(BuildActionType.TOTOROPLACEMENT.getBuildString().equals("TotoroPlacement"));
    }
}
