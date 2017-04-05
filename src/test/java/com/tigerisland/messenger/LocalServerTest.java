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
    private Client client;

    @Before
    public void createLocalServer() throws IOException {
        this.globalSettings = new GlobalSettings();
        this.localServer = new LocalServer(this.globalSettings);
    }

    @Ignore("Skipping test for creation of local server") @Test
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
            writer.println("THANK YOU FOR PLAYING! GOODBYE");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        localServerThread.join();

        assertTrue(globalSettings.messagesReceived.remove().message.equals("Hello"));
    }

    @Ignore("Skipping direct pass END_CODE to local server test") @Test
    public void testCanShutoffLocalServerWithEndCode() throws InterruptedException {
        Thread localServerThread = new Thread(localServer);
        localServerThread.start();

        try {
            Socket socket = new Socket(ServerSettings.defaultIPaddress, ServerSettings.defaultPort);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("THANK YOU FOR PLAYING! GOODBYE");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        localServerThread.join();

        assertTrue(localServerThread.isAlive() == false);
    }

    @Ignore("BROKEN Skipping multiple writes with END_CODE to local server test") @Test
    public void testCanShutoffLocalServerWithEndCodeAfterWriting() throws InterruptedException {
        Thread localServerThread = new Thread(localServer);
        localServerThread.start();

        try {
            Socket socket = new Socket(ServerSettings.defaultIPaddress, ServerSettings.defaultPort);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("Hello");
            writer.println("THANK YOU FOR PLAYING! GOODBYE");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        localServerThread.join();

        assertTrue(localServerThread.isAlive() == false);
    }

    @Ignore("Skipping successive messages to local server test") @Test
    public void testCanRetrieveSuccessiveMessages() throws InterruptedException {
        Thread localServerThread = new Thread(localServer);
        localServerThread.start();

        try {
            Socket socket = new Socket(ServerSettings.defaultIPaddress, ServerSettings.defaultPort);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("Hello");
            writer.println("Goodbye");
            writer.println("THANK YOU FOR PLAYING! GOODBYE");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        localServerThread.join();

        ArrayList<String> messagesProcessed = new ArrayList<String>();
        for(Message message : globalSettings.messagesReceived) {
            messagesProcessed.add(message.message);
            System.out.println(message.message);
        }

        assertTrue(messagesProcessed.size() == 2);
    }


    @Ignore("Skipping passing of messages to local server from messager") @Test
    public void testLocalServerCanReceiveMessagesFromMessager() throws InterruptedException, IOException {

        Thread localServerThread = new Thread(localServer);
        localServerThread.start();

        globalSettings.outboundQueue.add(new Message("ENTER THUNDERDOME " + ServerSettings.defaultTournamentPassword));

        client = new Client(globalSettings);

        Thread messengerThread = new Thread(client);

        messengerThread.start();

        while(globalSettings.outboundQueue.size() > 0) {
            sleep(5);
        }

        while(messengerThread.isAlive()) {
            messengerThread.interrupt();
            try {
                sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        localServerThread.join();

        assertTrue(localServerThread.isAlive() == false);
    }

    @Ignore("Skipping local server accepts tournament password test") @Test
    public void testLocalServerCanAcceptTournamentPassword() throws InterruptedException, IOException {

        Thread localServerThread = new Thread(localServer);
        localServerThread.start();

        globalSettings.outboundQueue.add(new Message("ENTER THUNDERDOME " + ServerSettings.defaultTournamentPassword));

        Thread messengerThread = new Thread(new Client(globalSettings));

        messengerThread.start();

        sleep(1000);

        closeMessenger(messengerThread);
        closeLocalServer(localServerThread);


        assertTrue(localServerThread.isAlive() == false);
    }

    @Ignore("Ignoring can authenticate team test") @Test
    public void testLocalServerCanAuthenticateTeam() throws InterruptedException {

        Thread localServerThread = new Thread(localServer);
        localServerThread.start();

        globalSettings.outboundQueue.add(new Message("I AM " + ServerSettings.defaultUsername + " " + ServerSettings.defaultPassword));

        Thread messengerThread = new Thread(new Client(globalSettings));

        messengerThread.start();

        sleep(1000);

        closeMessenger(messengerThread);
        closeLocalServer(localServerThread);


        assertTrue(localServerThread.isAlive() == false);
    }

    private void closeMessenger(Thread messengerThread) {
        while(messengerThread.isAlive()) {
            messengerThread.interrupt();
            try {
                sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeLocalServer(Thread localServerThread) {
        while(localServerThread.isAlive()) {
            try {
                sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
