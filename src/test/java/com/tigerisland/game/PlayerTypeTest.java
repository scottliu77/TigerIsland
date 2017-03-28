package com.tigerisland.game;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PlayerTypeTest {

    @Test
    public void testCanCreatePlayerType() {
        PlayerType playerType = PlayerType.BasicAI;
        assertTrue(playerType != null);
    }

    @Test
    public void testCanGetPlayerTypeString() {
        assertTrue(PlayerType.BasicAI.getTypeString().equals("BasicAI"));
    }
}
