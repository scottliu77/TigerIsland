package com.tigerisland.AI;

import com.tigerisland.Deck;
import com.tigerisland.GameSettings;
import com.tigerisland.GlobalSettings;
import com.tigerisland.game.*;
import com.tigerisland.messenger.Message;
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
    private Deck deck;

    @Before
    public void createMocks() {
        gameSettings = new GameSettings(new GlobalSettings());
        gameSettings.getPlayerSet().setCurrentPlayer(1);
        testPlayer = new Player(Color.BLACK, 1);
        testPlayerType = PlayerType.SAFEAI;
        testPlayer.setPlayerType(testPlayerType);
        gameSettings.setGameID("A");
        turnState = new Turn(gameSettings, new Board());
        testAI = new AI(testPlayerType);
        inboundMessages = turnState.inboundMessages;
    }

    @Test
    public void testCanCreateAI() {
        assertTrue(testAI != null);
    }

}
