package com.tigerisland;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class BuildActionTypeTest {

    @Test
    public void testCanCreateMoveType() {
        assertTrue(BuildActionType.TOTOROPLACEMENT instanceof BuildActionType);
    }

    @Test
    public void testCanGetMoveTypeString() {
        assertTrue(BuildActionType.TOTOROPLACEMENT.getMoveString().equals("TotoroPlacement"));
    }
}
