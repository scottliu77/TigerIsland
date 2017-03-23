package com.tigerisland;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MessengerTest {

    private Match match = new Match(new GlobalSettings());
    private Messenger messenger;
    private Thread messagerThread;

    @Before
    public void createMessager() {
         messenger = new Messenger(new GlobalSettings());
    }

    @Test
    public void testCanStartAndStopMessagerThread() throws InterruptedException {

        messagerThread = new Thread(messenger);
        messagerThread.start();
        messagerThread.interrupt();
        messagerThread.join();
        assertTrue(messagerThread.isAlive() == false);
    }
}
