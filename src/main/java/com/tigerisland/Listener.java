package com.tigerisland;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class Listener implements Runnable {

    protected BlockingQueue<String> inboundQueue;
    static Socket socket;

    Listener(GlobalSettings globalSettings) {
        this.inboundQueue = globalSettings.inboundQueue;
        this.socket = globalSettings.socket;
    }

    public void run() {
        while(!Thread.interrupted()) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                addMessageToQueue(reader.readLine());
            } catch (IOException exception) {
                //exception.printStackTrace();
            } catch (InterruptedException exception) {
                //exception.printStackTrace();
            }
        }
    }

    private void addMessageToQueue(String message) throws InterruptedException {
        inboundQueue.put(message);
    }


}