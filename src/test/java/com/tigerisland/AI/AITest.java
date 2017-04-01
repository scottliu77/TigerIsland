package com.tigerisland.AI;

import com.tigerisland.Deck;
import com.tigerisland.GameSettings;
import com.tigerisland.GlobalSettings;
import com.tigerisland.InvalidMoveException;
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
    private TurnInfo turnInfo;
    private AI testAI;
    private GameSettings gameSettings;
    private BlockingQueue<Message> inboundMessages;
    private Deck deck;

    @Before
    public void createMocks() {
        gameSettings = new GameSettings(new GlobalSettings());
        testPlayer = new Player(Color.BLACK);
        testPlayerType = PlayerType.BasicAI;
        testPlayer.setPlayerType(testPlayerType);
        turnState = new Turn(testPlayer, new Board());
        turnInfo = new TurnInfo(1, gameSettings);
        testAI = new AI(testPlayerType);
        inboundMessages = turnInfo.inboundMessages;
    }

    @Test
    public void testCanCreateAI() {
        assertTrue(testAI != null);
    }

    @Test
    public void testCanPickInitialTilePlacement() throws InvalidMoveException {
        turnInfo.drawANewTile();
        testAI.pickTilePlacement(turnInfo, turnState);
        Message message = inboundMessages.remove();
        assertTrue(message.message.contains("AT 0 0 0"));
    }

    @Test
    public void testCanPickInitialBuildAction() throws InvalidMoveException {
        turnInfo.drawANewTile();
        testAI.pickTilePlacement(turnInfo, turnState);
        Message message = inboundMessages.remove();
        turnState.getBoard().placeTile(new Tile(Terrain.GRASSLANDS, Terrain.GRASSLANDS), new Location(0, 0), 0);
        testAI.pickBuildAction(turnInfo, turnState);
        message = inboundMessages.remove();
        assertTrue(message.message.equals("GAME 1 MOVE 1 BUILD villager AT 1 0"));
    }
}
