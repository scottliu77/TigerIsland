package com.tigerisland;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MessagerTest {

    private Messager messager;
    private Thread messagerThread;

    @Before
    public void createMessager() {
         messager = new Messager(new GlobalSettings());
    }

    @Test
    public void testCanStartAndStopMessagerThread() throws InterruptedException {
        messagerThread = new Thread(messager);
        messagerThread.start();
        messagerThread.interrupt();
        messagerThread.join();
        assertTrue(messagerThread.isAlive() == false);
    }
}
