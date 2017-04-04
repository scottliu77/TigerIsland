package com.tigerisland.messenger;

import com.tigerisland.GlobalSettings;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class Messenger implements Runnable {

    private GlobalSettings globalSettings;
    protected BlockingQueue<Message> outboundQueue;
    protected BlockingQueue<Message> inboundQueue;
    private PrintWriter writer;
    private Socket socket;
    final boolean offline;

    public Messenger(GlobalSettings globalSettings) {
        this.globalSettings = globalSettings;
        this.outboundQueue = globalSettings.outboundQueue;
        this.inboundQueue = globalSettings.inboundQueue;
        this.offline = globalSettings.getServerSettings().offline;
    }

    public void run() {
        try {
            socket = new Socket(globalSettings.getServerSettings().IPaddress, globalSettings.getServerSettings().port);
            writer = new PrintWriter(socket.getOutputStream(), true);

            while(true) {
                try {
                    writer.println(removeMessageFromQueue());

                } catch (InterruptedException exception) {
                    closeLocalServerAndListener();
                    System.out.println("INTERRUPT: Messenger is now closing");
                    return;
                }
            }
        } catch (IOException exception) {
            return;
        }

    }

    protected String removeMessageFromQueue() throws InterruptedException {
        return outboundQueue.take().message;
    }

    private void closeLocalServerAndListener() {
        if (offline) {
            try {
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                writer.println("THANK YOU FOR PLAYING! GOODBYE");
                inboundQueue.add(new Message("THANK YOU FOR PLAYING! GOODBYE"));
                socket.close();
            } catch(IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}
