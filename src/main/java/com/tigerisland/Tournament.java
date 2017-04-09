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

        while(true) {
            if(playerIDreceived()) {
                break;
            }
            try {
                sleep(5);
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

        while(checkMessages(MessageType.LASTCHALLENGEOVER) == false) {
            if(checkMessages(MessageType.CHALLENGESTARTED)) {
                runRoundProtocol();
            }
            try {
                sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void runRoundProtocol() {

        while(checkMessages(MessageType.LASTROUNDOVER)== false) {
            if(checkMessages(MessageType.ROUNDSTARTED)) {
                runMatchProtocol();
            }
            try {
                sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void runMatchProtocol() {
        while(checkMessages(MessageType.ROUNDENDED)== false) {
            if(matchStarted()) {
                match = new Match(globalSettings);
                match.run();
                return;
            }
            try {
                sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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

