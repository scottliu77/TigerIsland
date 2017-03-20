package com.tigerisland;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MoveTypeTest {

    @Test
    public void testCanCreateMoveType() {
        assertTrue(MoveType.TILEPLACEMENT instanceof MoveType );
    }

    @Test
    public void testCanGetMoveTypeString() {
        assertTrue(MoveType.TILEPLACEMENT.getMoveString().equals("TilePlacement"));
    }
}
