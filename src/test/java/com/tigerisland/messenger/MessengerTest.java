package com.tigerisland.messenger;

import com.tigerisland.GlobalSettings;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MessengerTest {

    private GlobalSettings globalSettings;
    private Messenger messenger;
    private Thread messengerThread;

    @Before
    public void createMessenger() throws ArgumentParserException {
         globalSettings = new GlobalSettings(true, 0);
         messenger = new Messenger(globalSettings);
    }

    @Ignore("Skipping start/stop messenger thread test") @Test
    public void testCanStartAndStopMessengerThread() throws InterruptedException {
        messengerThread = new Thread(messenger);
        messengerThread.start();
        messengerThread.interrupt();
        messengerThread.join();

        assertTrue(messengerThread.isAlive() == false);
    }
    
    @Ignore("Skipping messenger processes outbound queue test") @Test
    public void testCanRemoveMessageFromQueue() throws InterruptedException {
        messenger.outboundQueue.add(new Message("New message"));
        assertTrue(messenger.removeMessageFromQueue().equals("New message"));
    }

    @Ignore("Skipping write to messenger test") @Test
    public void testCanWriteToMessageQueueFromOutsideProcess() throws InterruptedException {
        globalSettings.outboundQueue.add(new Message("New message"));
        assertTrue(messenger.removeMessageFromQueue().equals("New message"));
    }

}
