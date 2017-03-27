package com.tigerisland.messenger;

import com.tigerisland.GlobalSettings;
import com.tigerisland.ServerSettings;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertTrue;

public class LocalServerTest {

    private GlobalSettings globalSettings;
    private LocalServer localServer;
    private Messenger messenger;

    @Before
    public void createLocalServer() throws IOException {
        this.globalSettings = new GlobalSettings();
        this.localServer = new LocalServer(this.globalSettings);
    }

    @Ignore("Skipping tes for creation of local server") @Test
    public void testCanCreateLocalServer() {
        assertTrue(localServer != null);
    }

    @Ignore("Skipping direct write to local server test") @Test
    public void testCanWriteToLocalServer() throws InterruptedException {
        Thread localServerThread = new Thread(localServer);
        localServerThread.start();

        try {
            Socket socket = new Socket(ServerSettings.defaultIPaddress, ServerSettings.defaultPort);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("Hello");
            writer.println("END");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        localServerThread.join();

        assertTrue(globalSettings.messagesReceived.remove().equals("Hello"));
    }

    @Ignore("Skipping direct pass END_CODE to local server test") @Test
    public void testCanShutoffLocalServerWithEndCode() throws InterruptedException {
        Thread localServerThread = new Thread(localServer);
        localServerThread.start();

        try {
            Socket socket = new Socket(ServerSettings.defaultIPaddress, ServerSettings.defaultPort);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("END");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        localServerThread.join();

        assertTrue(localServerThread.isAlive() == false);
    }

    @Ignore("Skipping multiple writes with END_CODE to local server test") @Test
    public void testCanShutoffLocalServerWithEndCodeAfterWriting() throws InterruptedException {
        Thread localServerThread = new Thread(localServer);
        localServerThread.start();

        try {
            Socket socket = new Socket(ServerSettings.defaultIPaddress, ServerSettings.defaultPort);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("Hello");
            writer.println("END");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        localServerThread.join();

        assertTrue(localServerThread.isAlive() == false);
    }

    @Ignore("Skipping success messages to local server test") @Test
    public void testCanRetrieveSuccessiveMessages() throws InterruptedException {
        Thread localServerThread = new Thread(localServer);
        localServerThread.start();

        try {
            Socket socket = new Socket(ServerSettings.defaultIPaddress, ServerSettings.defaultPort);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("Hello");
            writer.println("Goodbye");
            writer.println("END");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        localServerThread.join();

        ArrayList<String> messagesProcessed = new ArrayList<String>();
        for(Message message : globalSettings.messagesReceived) {
            messagesProcessed.add(message.toString());
            System.out.println(message.toString());
        }

        assertTrue(messagesProcessed.size() == 2);
    }


    @Ignore("Skipping passing of messages to local server from messager") @Test
    public void testLocalServerCanReceiveMessagesFromMessager() throws InterruptedException, IOException {

        Thread localServerThread = new Thread(localServer);
        localServerThread.start();

        globalSettings.outboundQueue.add(new Message("Hello"));
        globalSettings.outboundQueue.add(new Message("END"));

        messenger = new Messenger(globalSettings);

        Thread messengerThread = new Thread(messenger);

        messengerThread.start();

        while(globalSettings.outboundQueue.size() > 0) {
            sleep(5);
        }

        messengerThread.interrupt();
        messengerThread.join();

        localServerThread.join();

//        ArrayList<String> messagesProcessed = new ArrayList<String>();
//        for(String message : globalSettings.messagesReceived) {
//            messagesProcessed.add(message);
//            System.out.println(message);
//        }

        assertTrue(globalSettings.messagesReceived.remove().equals("Hello"));
    }
}
