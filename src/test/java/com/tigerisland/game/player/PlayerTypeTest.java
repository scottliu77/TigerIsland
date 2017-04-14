package com.tigerisland.game.player;

import com.tigerisland.game.player.PlayerType;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PlayerTypeTest {

    @Test
    public void testCanCreatePlayerType() {
        PlayerType playerType = PlayerType.SAFEAI;
        assertTrue(playerType != null);
    }

    @Test
    public void testCanGetPlayerTypeString() {
        assertTrue(PlayerType.SAFEAI.getTypeString().equals("SAFEAI"));
    }
}
