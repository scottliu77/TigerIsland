package com.tigerisland.messenger;

import com.tigerisland.GlobalSettings;
import com.tigerisland.ServerSettings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

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

            while(!Thread.currentThread().isInterrupted()) {
                try {
                    addMessageToQueue(reader.readLine());
                } catch (IOException exception) {
                    exception.printStackTrace();
                } catch (InterruptedException exception) {
                    break;
                }
            }
        } catch (IOException exception) {
            return;
        }
    }

    protected void addMessageToQueue(String message) throws InterruptedException {
        inboundQueue.put(new Message(message));
    }


}