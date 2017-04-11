package com.tigerisland.messenger;

import com.tigerisland.GlobalSettings;
import com.tigerisland.ServerSettings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingQueue;

import static java.lang.Thread.sleep;

public class Client implements Runnable {

    private Boolean dummyFeed;

    private String addr;
    private int port;

    protected BlockingQueue<Message> outboundQueue;
    protected BlockingQueue<Message> inboundQueue;
    private PrintWriter writer;
    private BufferedReader reader;
    private Socket socket;

    final boolean offline;

    public Client(GlobalSettings globalSettings) {
        this.dummyFeed = globalSettings.dummyFeed;

        this.addr = globalSettings.getServerSettings().IPaddress;
        this.port = globalSettings.getServerSettings().port;

        this.outboundQueue = globalSettings.outboundQueue;
        this.inboundQueue = globalSettings.inboundQueue;
        this.offline = globalSettings.getServerSettings().offline;
    }

    public void run() {
        if (dummyFeed) {
            runWithDummyFeed();
        } else {
            runWithClient();
        }
    }

    private void runWithDummyFeed() {
        while(true) {

            if(outboundQueue.size() > 0) {
                String message = null;
                try {
                    message = outboundQueue.take().message;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(getTime() + "CLIENT: " + message);
            } else {
                for(Message message : inboundQueue) {
                    if(message.getMessageType() == MessageType.PROCESSED || message.getMessageType() == null) {
                        System.out.println(getTime() + "SERVER: " + message.message);
                        inboundQueue.remove(message);
                    }
                }
            }
        }
    }

    private void runWithClient() {
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
            System.out.println(getTime() + "CLIENT: Interrupted - Client is now closing");
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
            System.out.println(getTime() + "SERVER: " + message);
            inboundQueue.put(new Message(message));
        }
    }

    protected void processOutboundMessages() throws InterruptedException {
        if(outboundQueue.size() > 0) {
            String message = outboundQueue.take().message;
            System.out.println(getTime() + "CLIENT: " + message);
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

    public static String getTime() {
        long yourmilliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yy HH:mm.ss.SS");
        Date resultdate = new Date(yourmilliseconds);
        return "(" + sdf.format(resultdate) + ") ";
    }

}
