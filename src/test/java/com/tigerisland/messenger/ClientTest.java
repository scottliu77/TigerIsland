package com.tigerisland.messenger;

import com.tigerisland.settings.GlobalSettings;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertTrue;

public class ClientTest {

    private GlobalSettings globalSettings;
    private Client client;
    private Thread messengerThread;

    @Before
    public void createMessenger() throws ArgumentParserException {
         globalSettings = new GlobalSettings(true, 0);
         client = new Client(globalSettings);
    }

    @Ignore("Skipping start/stop client thread test") @Test
    public void testCanStartAndStopMessengerThread() throws InterruptedException {
        Thread localServerThread = new Thread(new MockServer(globalSettings));
        localServerThread.start();

        messengerThread = new Thread(client);
        messengerThread.start();
        messengerThread.interrupt();
        messengerThread.join();

        assertTrue(messengerThread.isAlive() == false);
    }

    @Ignore("Skipping can cleanup processed messages in queue") @Test
    public void testCanCleanupProcessedMessagesInQueue() throws InterruptedException {
        globalSettings.inboundQueue.add(new Message("Processed message"));
        globalSettings.inboundQueue.take().setProcessed();
        sleep(1);
        assertTrue(globalSettings.inboundQueue.size() == 0);

    }

    @Ignore("Skipping can listen for messages test") @Test
    public void testCanListenForMessages() throws InterruptedException {


        Thread localServerThread = new Thread(new MockServer(globalSettings));
        localServerThread.start();

        messengerThread = new Thread(client);
        messengerThread.start();
        messengerThread.interrupt();
        messengerThread.join();

        assertTrue(messengerThread.isAlive() == false);
    }

}
