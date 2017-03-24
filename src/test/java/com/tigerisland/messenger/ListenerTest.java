package com.tigerisland.messenger;

import com.tigerisland.GlobalSettings;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertTrue;

public class ListenerTest {

    private GlobalSettings globalSettings;
    private Listener listener;
    private Thread listenerThread;

    @Before
    public void createListener() throws ArgumentParserException {
        globalSettings = new GlobalSettings(true, 0, 0, 0);
        listener = new Listener(globalSettings);
    }

    @Ignore("Exceptionally slow") @Test
    public void testCanStartAndStopListenerThread() throws InterruptedException {
        listenerThread = new Thread(listener);
        listenerThread.start();

        sleep(5);

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
        globalSettings.inboundQueue.add("New message");
        assertTrue(listener.inboundQueue.remove().equals("New message"));
    }
}
