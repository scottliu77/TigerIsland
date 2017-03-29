package com.tigerisland.AI;

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
    private BlockingQueue<Message> inboundMessages;

    @Before
    public void createMocks() {
        testPlayer = new Player(Color.BLACK);
        testPlayerType = PlayerType.BasicAI;
        testPlayer.setPlayerType(testPlayerType);
        turnState = new Turn(testPlayer, new Board());
        turnInfo = new TurnInfo(1, new GameSettings(new GlobalSettings()));
        testAI = new AI(testPlayerType);
        inboundMessages = turnInfo.inboundMessages;
    }

    @Test
    public void testCanCreateAI() {
        assertTrue(testAI != null);
    }

    @Test
    public void testCanPickInitialTilePlacement() {
        testAI.pickTilePlacement(turnInfo, turnState);
        Message message = inboundMessages.remove();
        assertTrue(message.toString().equals("GAME 1 MOVE 1 PLACE GG AT 0 0 0"));
    }

    @Test
    public void testCanPickInitialBuildAction() throws InvalidMoveException {
        turnState.getBoard().placeTile(new Tile(Terrain.GRASSLANDS, Terrain.GRASSLANDS), new Location(0, 0), 0);
        testAI.pickBuildAction(turnInfo, turnState);
        Message message = inboundMessages.remove();
        assertTrue(message.toString().equals("GAME 1 MOVE 1 BUILD villager AT 1 0"));
    }
}
