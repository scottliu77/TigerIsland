package com.tigerisland.game.moves;

import com.tigerisland.game.moves.BuildActionType;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class BuildActionTypeTest {

    @Test
    public void testCanCreateMoveType() {
        assertTrue(BuildActionType.TOTOROPLACEMENT instanceof BuildActionType);
    }

    @Test
    public void testCanGetMoveTypeString() {
        assertTrue(BuildActionType.TOTOROPLACEMENT.getBuildString().equals("TotoroPlacement"));
    }
}
