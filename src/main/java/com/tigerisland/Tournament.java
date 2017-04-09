package com.tigerisland;

import com.tigerisland.messenger.*;

import static java.lang.Thread.sleep;

public class Tournament {

    protected GlobalSettings globalSettings;

    protected Client client;
    protected LocalServer localServer;

    protected Match match;

    Tournament(GlobalSettings globalSettings) {
        this.globalSettings = globalSettings;
        this.client = new Client(globalSettings);
        this.localServer = new LocalServer(globalSettings);
        setupMessage();
    }

    private void setupMessage() {
        if(globalSettings.getServerSettings().offline) {
            System.out.println("TIGERISLAND: Setting up an OFFLINE tournament");
        } else {
            System.out.println("TIGERISLAND: Setting up a SERVER-HOSTED tournament");
        }
    }

    public void run() {
        if(globalSettings.getServerSettings().offline) {
            createLocalServerAndStartThreads();
        } else {
            createAndStartAllThreads();
        }
        System.exit(0);
    }

    protected void createLocalServerAndStartThreads() {

        Thread localServerThread = new Thread(localServer);
        localServerThread.start();

        System.out.println("TIGERISLAND: Local Server is RUNNING");

        createAndStartAllThreads();

        closeLocalServer(localServerThread);
    }

    protected void createAndStartAllThreads() {

        Thread messengerThread = new Thread(this.client);
        messengerThread.start();
        System.out.println("TIGERISLAND: Client service is RUNNING");

        runAuthenticationProtocolToGetPlayerID();

        runChallengeProtocol();

        closeMessenger(messengerThread);
    }

    private void runAuthenticationProtocolToGetPlayerID() {
        Message enterMessage = new Message("ENTER THUNDERDOME " + globalSettings.getServerSettings().tournamentPassword);
        Message authMessage = new Message("I AM " + globalSettings.getServerSettings().username + " " + globalSettings.getServerSettings().password);

        globalSettings.outboundQueue.add(enterMessage);

        globalSettings.outboundQueue.add(authMessage);

        System.out.println("TIGERISLAND: Waiting for PlayerID...");

        while(true) {
            if(playerIDreceived()) {
                break;
            }
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Boolean playerIDreceived() {
        for(Message message : globalSettings.inboundQueue) {
            if(message.getMessageType() == MessageType.PLAYERID) {
                globalSettings.getServerSettings().setPlayerID(message.getOurPlayerID());
                message.setProcessed();
                return true;
            }
        }
        return false;
    }

    private void runChallengeProtocol() {

        while(checkMessages(MessageType.TOURNAMENTEND) == false) {
            if(checkMessages(MessageType.CHALLENGESTARTED)) {
                System.out.println("TIGERISLAND: Starting new challenge");
                runRoundProtocol();
            }

            try {
                sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("TIGERISLAND: Last challenge complete");
    }

    private void runRoundProtocol() {
        while(checkMessages(MessageType.ENDOFCHALLENGE) == false && checkMessages(MessageType.LASTCHALLENGEOVER) == false) {
            if(checkMessages(MessageType.ROUNDSTARTED)) {
                System.out.println("TIGERISLAND: Starting new round");
                runMatchProtocol();
            }

            try {
                sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("TIGERISLAND: Challenge over");
    }

    private void runMatchProtocol() {
        while(checkMessages(MessageType.ROUNDENDED)== false && checkMessages(MessageType.LASTROUNDOVER)== false) {
            if(matchStarted()) {
                System.out.println("TIGERISLAND: Starting new match");
                match = new Match(globalSettings);
                match.run();
            }

            try {
                sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("TIGERISLAND: Round over");
    }

    private Boolean matchStarted() {
        for(Message message : globalSettings.inboundQueue) {
            if(message.getMessageType() == MessageType.MATCHSTARTED) {
                globalSettings.getServerSettings().setOpponentID(message.getOpponentID());
                message.setProcessed();
                return true;
            }
        }
        return false;
    }

    private Boolean checkMessages(MessageType messageType) {
        for(Message message : globalSettings.inboundQueue) {
            if(message.getMessageType() == messageType) {
                message.setProcessed();
                return true;
            }
        }
        return false;
    }

    private void closeMessenger(Thread messengerThread) {
        while(messengerThread.isAlive()) {
            messengerThread.interrupt();
            try {
                sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("TIGERISLAND: Client service has CLOSED");
    }

    private void closeLocalServer(Thread localServerThread) {
        while(localServerThread.isAlive()) {
            try {
                sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("TIGERISLAND: Local server has CLOSED");
    }

}

