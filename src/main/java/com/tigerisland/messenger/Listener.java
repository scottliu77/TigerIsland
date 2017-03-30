package com.tigerisland.messenger;

import com.tigerisland.GlobalSettings;
import com.tigerisland.ServerSettings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import static java.lang.Thread.sleep;

public class Listener implements Runnable {

    protected BlockingQueue<Message> inboundQueue;
    private ServerSettings serverSettings;
    private BufferedReader reader;
    private Socket socket;

    public Listener(GlobalSettings globalSettings) {
        this.inboundQueue = globalSettings.inboundQueue;
        this.serverSettings = globalSettings.getServerSettings();
    }

    public void run() {
        try {
            socket = new Socket(serverSettings.IPaddress, serverSettings.port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while(true) {
                try {
                    addMessageToQueue(reader.readLine());
                    cleanupMessageQueue();
                } catch (IOException exception) {
                    System.out.println("EXCEPTION: Listener is now closing");
                    Thread.interrupted();
                    return;
                } catch (InterruptedException exception) {
                    System.out.println("INTERRUPT: Listener is now closing");
                    socket.close();
                    return;
                }
            }
        } catch (IOException exception) {
            return;
        }
    }

    protected void addMessageToQueue(String message) throws InterruptedException {
        checkForEndMessage(message);
        inboundQueue.put(new Message(message));
    }

    private void cleanupMessageQueue() {
        for(Message message : inboundQueue) {
            if(message.getMessageType() == MessageType.PROCESSED) {
                inboundQueue.remove(message);
            }
        }
    }

    private void checkForEndMessage(String message) throws InterruptedException {
        if(message.equals("END")) {
            throw new InterruptedException();
        }
    }


}