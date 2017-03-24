package com.tigerisland;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertTrue;

public class LocalServerTest {

    private GlobalSettings globalSettings;
    private Socket socket;
    private LocalServer localServer;
    private Messenger messenger;

    @Before
    public void createLocalServer() throws IOException {
        this.globalSettings = new GlobalSettings();
        this.localServer = new LocalServer(this.globalSettings);
    }

    @Test
    public void testCanCreateLocalServer() {
        assertTrue(localServer != null);
    }

    @Test
    public void testCanWriteToLocalServer() throws InterruptedException {
        Thread localServerThread = new Thread(localServer);
        localServerThread.start();

        try {
            Socket socket = new Socket(globalSettings.defaultIPaddress, globalSettings.defaultPort);
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

    @Test
    public void testCanShutoffLocalServerWithEndCode() throws InterruptedException {
        Thread localServerThread = new Thread(localServer);
        localServerThread.start();

        try {
            Socket socket = new Socket(globalSettings.defaultIPaddress, globalSettings.defaultPort);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("END");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        localServerThread.join();

        assertTrue(localServerThread.isAlive() == false);
    }

    @Test
    public void testCanShutoffLocalServerWithEndCodeAfterWriting() throws InterruptedException {
        Thread localServerThread = new Thread(localServer);
        localServerThread.start();

        try {
            Socket socket = new Socket(globalSettings.defaultIPaddress, globalSettings.defaultPort);
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

    @Test
    public void testCanRetrieveSuccessiveMessages() throws InterruptedException {
        Thread localServerThread = new Thread(localServer);
        localServerThread.start();

        try {
            Socket socket = new Socket(globalSettings.defaultIPaddress, globalSettings.defaultPort);
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
        for(String message : globalSettings.messagesReceived) {
            messagesProcessed.add(message);
            System.out.println(message);
        }

        assertTrue(messagesProcessed.size() == 2);
    }


    @Test
    public void testLocalServerCanReceiveMessagesFromMessager() throws InterruptedException, IOException {

        Thread localServerThread = new Thread(localServer);
        localServerThread.start();

        globalSettings.outboundQueue.add("Hello");
        globalSettings.outboundQueue.add("END");

        messenger = new Messenger(globalSettings);

        Thread messengerThread = new Thread(messenger);

        messengerThread.start();

        sleep(5);

        messengerThread.interrupt();
        messengerThread.join();

        localServerThread.join();

        ArrayList<String> messagesProcessed = new ArrayList<String>();
        for(String message : globalSettings.messagesReceived) {
            messagesProcessed.add(message);
            System.out.println(message);
        }

        assertTrue(globalSettings.messagesReceived.remove().equals("Hello"));
    }
}
