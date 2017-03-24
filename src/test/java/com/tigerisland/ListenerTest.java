package com.tigerisland;

import net.sourceforge.argparse4j.inf.ArgumentParserException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ListenerTest {

    private GlobalSettings globalSettings;
    private Match match;
    private Listener listener;
    private Thread listenerThread;

    @Before
    public void createListener() throws ArgumentParserException {
        globalSettings = new GlobalSettings(true, 0, 0, 0);
        match = new Match(globalSettings);
        listener = new Listener(globalSettings);
    }

    @Test
    public void testCanStartAndStopListenerThread() throws InterruptedException {
        listenerThread = new Thread(listener);
        listenerThread.start();
        listenerThread.interrupt();
        listenerThread.join();
        assertTrue(listenerThread.isAlive() == false);
    }

    @Test
    public void testCanAddMessageToQueue() throws InterruptedException {
        listener.addMessageToQueue("New message");
        assertTrue(listener.inboundQueue.remove().equals("New message"));
    }

    @Test
    public void testCanWriteToMessageQueueFromOutsideProcess() throws InterruptedException {
        match.globalSettings.inboundQueue.add("New message");
        assertTrue(listener.inboundQueue.remove().equals("New message"));
    }
}
