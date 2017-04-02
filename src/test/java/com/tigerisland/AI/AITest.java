package com.tigerisland.AI;

import com.tigerisland.Deck;
import com.tigerisland.GameSettings;
import com.tigerisland.GlobalSettings;
import com.tigerisland.InvalidMoveException;
import com.tigerisland.game.*;
import com.tigerisland.messenger.Message;
import org.junit.Before;
import org.junit.Test;

import javax.xml.soap.Text;
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
        testAI.pickTilePlacementAndBuildAction(turnInfo, turnState);
        Message message = inboundMessages.remove();
        assertTrue(message.message.contains("AT 0 0 0"));
    }

    @Test
    public void testCanPickInitialBuildAction() throws InvalidMoveException {
        turnInfo.drawANewTile();
        testAI.pickTilePlacementAndBuildAction(turnInfo, turnState);
        Message message = inboundMessages.remove();
        System.out.println(message);
        assertTrue(message.message.contains("FOUND SETTLEMENT AT 0 1 -1"));
    }
}
