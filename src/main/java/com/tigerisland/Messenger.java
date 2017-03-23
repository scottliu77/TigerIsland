package com.tigerisland;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class Messenger implements Runnable {

    protected BlockingQueue<String> outboundQueue;
    static Socket socket;
    protected

    Messenger(GlobalSettings globalSettings) {
        this.outboundQueue = globalSettings.outboundQueue;
        this.socket = globalSettings.socket;
    }

    public void run() {
        while(!Thread.interrupted()) {
            try {
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                writer.println(removeMessageFromQueue());
            } catch(IOException exception) {
                //exception.printStackTrace();
            } catch (InterruptedException exception) {
                //exception.printStackTrace();
            }
        }
    }

    private String removeMessageFromQueue() throws InterruptedException {
        return outboundQueue.take();
    }
}
