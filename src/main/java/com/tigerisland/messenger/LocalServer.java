package com.tigerisland.messenger;

import com.tigerisland.GlobalSettings;
import com.tigerisland.ServerSettings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.*;

public class LocalServer implements Runnable {

    public static final int LOCAL_CHALLENGES = 3;
    public static final int LOCAL_ROUNDS = 4;

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
                Callable<Boolean> messenger = new Messenger(dummyListener.accept(), globalSettings);
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
        private GlobalSettings globalSettings;
        private BlockingQueue<Message> messagesReceived;
        private BufferedReader reader;
        private PrintWriter writer;
        private String message;

        Messenger(Socket socket, GlobalSettings globalSettings) {
            this.dummySocket = socket;
            this.globalSettings = globalSettings;
            this.messagesReceived = globalSettings.messagesReceived;
        }

        public Boolean call() {
            try {
                writer = new PrintWriter(dummySocket.getOutputStream(), true);
                writer.println("WELCOME TO ANOTHER EDITION OF THUNDERDOME!");

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
                           checkForNewEntry();
                           checkForAuthentication();

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

        private void checkForNewEntry() throws IOException {
            for(Message message : messagesReceived) {
                if(message.getMessageType() == MessageType.ENTERTOURNAMENT) {
                    if(globalSettings.getServerSettings().tournamentPassword == ServerSettings.defaultTournamentPassword) {
                        ConsoleOut.printServerMessage("New tournament entry received");
                        writer = new PrintWriter(dummySocket.getOutputStream(), true);
                        writer.println("TWO SHALL ENTER, ONE SHALL LEAVE");
                    }
                }
            }
        }

        private void checkForAuthentication() throws IOException {
            for(Message message : messagesReceived) {
                if(message.getMessageType() == MessageType.ENTERTOURNAMENT) {
                    if(globalSettings.getServerSettings().username == ServerSettings.defaultUsername) {
                        if(globalSettings.getServerSettings().password == ServerSettings.defaultPassword) {
                            ConsoleOut.printServerMessage("New team identified [default]");
                            writer = new PrintWriter(dummySocket.getOutputStream(), true);
                            writer.println("WAIT FOR THE TOURNAMENT TO BEGIN 7");
                        }
                    }
                }
            }
        }


    }

}
