package com.tigerisland.messenger;

import com.tigerisland.GlobalSettings;
import com.tigerisland.ServerSettings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class LocalServer implements Runnable {

    private GlobalSettings globalSettings;
    private InetAddress addr;
    private int port;
    private ServerSocket dummyListener;
    private Boolean running = true;

    public LocalServer(GlobalSettings globalSettings) {
        this.globalSettings = globalSettings;
        try {
            this.addr = InetAddress.getByName(globalSettings.getServerSettings().IPaddress);
        } catch (UnknownHostException exception) {
            exception.printStackTrace();
        }
        this.port = globalSettings.getServerSettings().port;
    }

    public void run() {

        ExecutorService executor = Executors.newFixedThreadPool(1);
        //List<Future<Boolean>> messengerList = new ArrayList<Future<Boolean>>();

        try {
            dummyListener = new ServerSocket(port, 50, addr);

            while (running) {
                Callable<Boolean> messenger = new Messenger(dummyListener.accept(), globalSettings.messagesReceived);
                Future<Boolean> futureBool = executor.submit(messenger);
                try {
                    running = futureBool.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            dummyListener.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    private static class Messenger implements Callable<Boolean> {

        private Socket dummySocket;
        private BlockingQueue<Message> messagesReceived;
        private BufferedReader reader;
        private String message;

        Messenger(Socket socket, BlockingQueue<Message> messagesReceived) {
            this.dummySocket = socket;
            this.messagesReceived = messagesReceived;
        }

        public Boolean call() {
            try {
                reader = new BufferedReader(new InputStreamReader(dummySocket.getInputStream()));

                while(true) {
                   message = reader.readLine();
                   if (message == null) {
                       return true;
                   } else if (message.equals(ServerSettings.END_CODE)) {
                       return false;
                   } else {
                       try {
                           messagesReceived.put(new Message(message));
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                   }
                }

            } catch (IOException exception) {
                exception.printStackTrace();
            }

            return true;
        }
    }
}
