package com.tigerisland.client;

import com.tigerisland.settings.GlobalSettings;
import com.tigerisland.settings.ServerSettings;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertTrue;

public class MockServerTest {

    private GlobalSettings globalSettings;
    private MockServer mockServer;
    private Client client;

    @Before
    public void createLocalServer() throws IOException {
        this.globalSettings = new GlobalSettings();
        this.mockServer = new MockServer(this.globalSettings);
    }

    @Ignore("Skipping test for creation of local server") @Test
    public void testCanCreateLocalServer() {
        assertTrue(mockServer != null);
    }

    @Ignore("Skipping direct pass END_CODE to local server test") @Test
    public void testCanShutoffLocalServerWithEndCode() throws InterruptedException {
        Thread localServerThread = new Thread(mockServer);
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
        Thread localServerThread = new Thread(mockServer);
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

    @Ignore("Skipping passing of messages to local server from messager") @Test
    public void testLocalServerCanReceiveMessagesFromMessager() throws InterruptedException, IOException {

        Thread localServerThread = new Thread(mockServer);
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

        Thread localServerThread = new Thread(mockServer);
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

        Thread localServerThread = new Thread(mockServer);
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
