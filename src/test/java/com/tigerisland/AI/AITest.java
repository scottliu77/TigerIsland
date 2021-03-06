package com.tigerisland.AI;

import com.tigerisland.client.OfflineDeck;
import com.tigerisland.game.board.Board;
import com.tigerisland.game.pieces.Color;
import com.tigerisland.game.player.Player;
import com.tigerisland.game.player.PlayerType;
import com.tigerisland.settings.GameSettings;
import com.tigerisland.settings.GlobalSettings;
import com.tigerisland.game.*;
import com.tigerisland.client.Message;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;

import static org.junit.Assert.assertTrue;

public class AITest {

    private Player testPlayer;
    private PlayerType testPlayerType;
    private Turn turnState;
    private AI testAI;
    private GameSettings gameSettings;
    private BlockingQueue<Message> inboundMessages;
    private OfflineDeck offlineDeck;

    @Before
    public void createMocks() {
        gameSettings = new GameSettings(new GlobalSettings());
        gameSettings.getPlayerSet().setCurrentPlayer("1");
        testPlayer = new Player(Color.BLACK, "1", PlayerType.SAFEAI);
        testPlayerType = PlayerType.SAFEAI;
        testPlayer.setPlayerType(testPlayerType);
        gameSettings.setGameID("A");
        turnState = new Turn(gameSettings, new Board());
        testAI = new SafeAI();
        inboundMessages = turnState.inboundMessages;
    }

    @Test
    public void testCanCreateAI() {
        assertTrue(testAI != null);
    }

}
