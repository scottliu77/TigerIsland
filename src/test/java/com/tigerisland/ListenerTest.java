package com.tigerisland;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ListenerTest {

    private Listener listener;
    private Thread listenerThread;

    @Before
    public void createListener() {
        listener = new Listener(new GlobalSettings());
    }

    @Test
    public void testCanStartAndStopListenerThread() throws InterruptedException {

        listenerThread = new Thread(listener);
        listenerThread.start();
        listenerThread.interrupt();
        listenerThread.join();
        assertTrue(listenerThread.isAlive() == false);
    }
}
