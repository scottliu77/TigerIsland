package com.tigerisland.messenger;

import com.tigerisland.settings.GlobalSettings;
import com.tigerisland.settings.ServerSettings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class MockServer implements Runnable {

    public static final int LOCAL_CHALLENGES = 2;
    public static final int LOCAL_ROUNDS = 4;

    private GlobalSettings globalSettings;
    private InetAddress addr;
    private int port;
    private ServerSocket dummyListener;
    private Boolean running = true;

    public MockServer(GlobalSettings globalSettings) {
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

        private String matchOpponent = "10";
        private int gameID = 1;

        Messenger(Socket socket, GlobalSettings globalSettings) {
            this.dummySocket = socket;
            this.globalSettings = globalSettings;

            this.messagesReceived = globalSettings.messagesReceived;
            try {
                this.writer = new PrintWriter(dummySocket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }

            sendStartingMessage();
        }

        private void sendStartingMessage() {
            writer.println("WELCOME TO ANOTHER EDITION OF THUNDERDOME!");
        }

        public Boolean call() {
            try {
                writer = new PrintWriter(dummySocket.getOutputStream(), true);

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

                   cleanupMessageQueue();
                }


            } catch (IOException exception) {
                exception.printStackTrace();
            }

            return true;
        }

        private void checkForNewEntry() throws IOException {
            for(Message message : messagesReceived) {
                if(message.getMessageType() == MessageType.CLIENTENTERTOURNAMENT) {
                    if(globalSettings.getServerSettings().tournamentPassword.equals(ServerSettings.defaultTournamentPassword)) {
                        writer.println("TWO SHALL ENTER, ONE SHALL LEAVE");
                        message.setProcessed();
                    }
                }
            }
        }

        private void checkForAuthentication() throws IOException {
            for(Message message : messagesReceived) {
                if(message.getMessageType() == MessageType.CLIENTAUTHENTICATETEAM) {
                    if(globalSettings.getServerSettings().username.equals(ServerSettings.defaultUsername)) {
                        if(globalSettings.getServerSettings().password.equals(ServerSettings.defaultPassword)) {
                            writer.println("WAIT FOR THE TOURNAMENT TO BEGIN 7");
                            message.setProcessed();
                            startChallengeProtocol();
                        }
                    }
                }
            }
        }

        private void startChallengeProtocol() {
            for(int challenge = 1; challenge <= LOCAL_CHALLENGES; challenge++) {

                sendNewChallenge(challenge);

                if(challenge  < LOCAL_CHALLENGES) {
                    writer.println("WAIT FOR THE NEXT CHALLENGE TO BEGIN");
                } else {
                    writer.println("END OF CHALLENGES");
                }
            }
            writer.println("THANK YOU FOR PLAYING! GOODBYE");
        }

        private void sendNewChallenge(int challenge) {
            writer.println("NEW CHALLENGE " + (challenge) + " YOU WILL PLAY " + LOCAL_ROUNDS + " MATCHES");

            for(int round = 1; round <= LOCAL_ROUNDS; round++) {

                writer.println("BEGIN ROUND " + round + " OF " + LOCAL_ROUNDS);
                sendNewRound();

                if(round < LOCAL_ROUNDS) {
                    writer.println("END OF ROUND " + round + " OF " + LOCAL_ROUNDS + " WAIT FOR THE NEXT MATCH");
                } else {
                    writer.println("END OF ROUND " + round + " OF " + LOCAL_ROUNDS);
                }
            }
        }

        private void sendNewRound() {

            matchOpponent = String.valueOf(Integer.parseInt(matchOpponent)+ 1);

            writer.println("NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER " + matchOpponent);

            writer.println("MAKE YOUR MOVE IN GAME " + (gameID) + " WITHIN 1.5 SECONDS: MOVE 0 PLACE GRASS+LAKE");
            writer.println("MAKE YOUR MOVE IN GAME " + (gameID + 1) + " WITHIN 1.5 SECONDS: MOVE 0 PLACE ROCK+LAKE");

            gameID += 2;

            waitForGamesToEnd();
        }

        private void waitForGamesToEnd() {
            int gamesRunning = 2;

            try {
                writer = new PrintWriter(dummySocket.getOutputStream(), true);

                reader = new BufferedReader(new InputStreamReader(dummySocket.getInputStream()));

                while (true) {
                    message = reader.readLine();

                    try {
                        messagesReceived.put(new Message(message));

                        if (endMessageFound()) {
                            gamesRunning = gamesRunning - 1;
                            if(gamesRunning == 0) {
                                return;
                            }
                        }
                        sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        private Boolean endMessageFound() {

            for (Message message : messagesReceived) {
                if (message.getMessageType().getSubtype().equals(MessageType.GAMEOVER.getSubtype())) {
                    messagesReceived.remove(message);
                    return true;
                }
            }
            return false;
        }

        private void cleanupMessageQueue() {
            for(Message message : messagesReceived) {
                if(message.getMessageType() == MessageType.PROCESSED) {
                    messagesReceived.remove(message);
                }
            }
        }
    }
}
