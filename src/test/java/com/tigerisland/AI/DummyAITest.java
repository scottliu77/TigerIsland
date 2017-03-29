package com.tigerisland.AI;

import com.tigerisland.GameSettings;
import com.tigerisland.GlobalSettings;
import com.tigerisland.game.*;
import com.tigerisland.messenger.Message;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DummyAITest {

    private Turn turnState;
    private TurnInfo turnInfo;
    private GameSettings gameSettings;
    private BlockingQueue<Message> inboundMessages;
    private InputStream testMessage;
    private InputStream old = System.in;

    @Before
    public void setupMocks() {
        turnState = new Turn(new Player(Color.BLACK), new Board());
        gameSettings = new GameSettings(new GlobalSettings());
        inboundMessages = gameSettings.getGlobalSettings().inboundQueue;
        turnInfo = new TurnInfo(1, gameSettings);
    }

    @After
    public void cleanup() {
        System.setIn(old);
    }

//    @Test
//    public void testCanPassBuildMessageWithDummyAI() throws UnsupportedEncodingException {
//        String message = "GAME 1 MOVE 1 PLACE RG AT 0 0 60";
//        testMessage = new ByteArrayInputStream(message.getBytes("UTF-8"));
//        DummyAI.pickTilePlacement(turnInfo, turnState);
//    }
}
