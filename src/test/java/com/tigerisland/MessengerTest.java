package com.tigerisland;

import cucumber.api.java8.Gl;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import static org.junit.Assert.assertTrue;

public class MessengerTest {

    private GlobalSettings globalSettings;
    private Match match;
    private Messenger messenger;
    private Thread messengerThread;

    @Before
    public void createMessenger() throws ArgumentParserException {
         globalSettings = new GlobalSettings(true, 0, 0, 0);
         match = new Match(globalSettings);
         messenger = new Messenger(globalSettings);
    }

    @Test
    public void testCanStartAndStopMessengerThread() throws InterruptedException {
        messengerThread = new Thread(messenger);
        messengerThread.start();
        messengerThread.interrupt();
        messengerThread.join();

        assertTrue(messengerThread.isAlive() == false);
    }
    
    @Test
    public void testCanRemoveMessageFromQueue() throws InterruptedException {
        messenger.outboundQueue.add("New message");
        assertTrue(messenger.removeMessageFromQueue().equals("New message"));
    }

    @Test
    public void testCanWriteToMessageQueueFromOutsideProcess() throws InterruptedException {
        match.globalSettings.outboundQueue.add("New message");
        assertTrue(messenger.removeMessageFromQueue().equals("New message"));
    }

}
