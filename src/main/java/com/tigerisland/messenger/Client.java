package com.tigerisland.messenger;

import com.tigerisland.GlobalSettings;
import com.tigerisland.ServerSettings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

import static java.lang.Thread.sleep;

public class Client implements Runnable {

    private Boolean testing;

    private String addr;
    private int port;

    protected BlockingQueue<Message> outboundQueue;
    protected BlockingQueue<Message> inboundQueue;
    private PrintWriter writer;
    private BufferedReader reader;
    private Socket socket;

    final boolean offline;

    public Client(GlobalSettings globalSettings) {
        this.testing = globalSettings.manualTesting;

        this.addr = globalSettings.getServerSettings().IPaddress;
        this.port = globalSettings.getServerSettings().port;

        this.outboundQueue = globalSettings.outboundQueue;
        this.inboundQueue = globalSettings.inboundQueue;
        this.offline = globalSettings.getServerSettings().offline;
    }

    public void run() {
        try {
            socket = new Socket(addr, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                processInboundMessages();
                processOutboundMessages();

                cleanupMessageQueue();
                sleep(1);
            }
        } catch (InterruptedException exception) {
            closeLocalServer();
            System.out.println("CLIENT: Interrupted - Client is now closing");
        } catch (IOException exception) {
            System.out.println(exception);
        } finally {
            try {
                socket.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }


    protected void processInboundMessages() throws InterruptedException, IOException {
        if ((reader.ready())) {
            String message = reader.readLine();
            System.out.println("SERVER: " + message);
            inboundQueue.put(new Message(message));
        }
    }

    protected void processOutboundMessages() throws InterruptedException {
        if(outboundQueue.size() > 0) {
            String message = outboundQueue.take().message;
            System.out.println("CLIENT: " + message);
            writer.println(message);
        }
    }

    private void cleanupMessageQueue() {
        for(Message message : inboundQueue) {
            if(message.getMessageType() == MessageType.PROCESSED) {
                inboundQueue.remove(message);
            } else if(message.getMessageType() == null) {
                inboundQueue.remove(message);
            }
        }
    }

    private void closeLocalServer() {
        try {
            if (offline) {
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                writer.println(ServerSettings.END_CODE);
            }
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }

}
