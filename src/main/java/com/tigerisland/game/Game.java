package com.tigerisland.game;

import com.tigerisland.GameSettings;
import com.tigerisland.messenger.Client;
import com.tigerisland.messenger.Message;
import com.tigerisland.messenger.MessageType;

import static java.lang.Thread.sleep;

public class Game implements Runnable {

    public final String gameID;

    protected GameSettings gameSettings;
    protected Board board;
    protected Turn turnState;

    protected Boolean offline;

    private String ourPlayerID;

    private String moveID = "0";

    private Boolean continueGame = true;

    Player winner;
    Player loser;

    public Game(GameSettings gameSettings){
        this.ourPlayerID = gameSettings.getGlobalSettings().getServerSettings().getPlayerID();

        this.gameSettings = gameSettings;
        this.gameID = gameSettings.getGameID();
        this.board = new Board();
        this.offline = gameSettings.getGlobalSettings().getServerSettings().offline;
        this.turnState = new Turn(gameSettings, board);
    }

    public void run() {
        try {

            board.placeStartingTile();

            while (continuePlayingGame()) {
                sleep(1);
            }

        } catch (InvalidMoveException exception) {
            if(!exception.getMessage().equals("LOST: UNABLE TO BUILD")) {
                gameSettings.getGlobalSettings().outboundQueue.add(new Message("GAME " + gameID + " MOVE " + turnState.getMoveID() + " PLAYER " + turnState.getCurrentPlayer().getPlayerID() + " " + exception.getMessage()));
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        offlineCalculateResults();
        offlineGenerateGameOverEcho();

        waitForGameOver();

    }

    private Boolean continuePlayingGame() throws InterruptedException, InvalidMoveException {

        if(Thread.currentThread().isInterrupted()) {
            return false;
        }

        continueGame = attemptToProcessMove();

        checkForHaveAIPickAMove();


        if(isGameOver()) {
            continueGame = false;
        }

        if(offline) {
            moveID = String.valueOf(Integer.valueOf(moveID) + 1);
            offlineMockMakeMoveMessage();
            alternateOurPlayerID();
        }

        return continueGame;
    }

    protected void offlineMockMakeMoveMessage() throws IndexOutOfBoundsException {

        String makeMoveMessage = "MAKE YOUR MOVE IN GAME " + gameSettings.getGameID();
        makeMoveMessage = makeMoveMessage + " WITHIN 1.5 SECONDS:";

        makeMoveMessage = makeMoveMessage + " MOVE " + moveID + " PLACE ";

        Tile newTile = gameSettings.getDeck().drawTile();

        String leftTerrain = newTile.getLeftHex().getHexTerrain().name();
        String rightTerrain = newTile.getRightHex().getHexTerrain().name();

        makeMoveMessage = makeMoveMessage + leftTerrain + "+" + rightTerrain;

        gameSettings.getGlobalSettings().inboundQueue.add(new Message(makeMoveMessage));
    }

    protected void alternateOurPlayerID() {
        String opponentPlayerID = gameSettings.getGlobalSettings().getServerSettings().getOpponentID();
        String truePlayerID = gameSettings.getGlobalSettings().getServerSettings().getPlayerID();
        if(ourPlayerID.equals(truePlayerID)) {
             ourPlayerID = opponentPlayerID;
        } else {
            ourPlayerID = truePlayerID;
        }
    }

    protected void checkForHaveAIPickAMove() throws InterruptedException, InvalidMoveException {

        for(Message message : gameSettings.getGlobalSettings().inboundQueue) {
            if(message.getGameID() != null && message.getMessageType() != null) {
                if (message.getGameID().equals(gameID)) {
                    if (message.getMessageType() == MessageType.MAKEMOVE) {
                        message.setProcessed();
                        gameSettings.setMoveID(message.getMoveID());
                        gameSettings.getPlayerSet().setCurrentPlayer(ourPlayerID);
                        pickMove(message);
                    }
                }
            }
        }
    }

    private void pickMove(Message message) throws InvalidMoveException {

        turnState.updateTurnInformation(message.getMoveID(), message.getTile());

        turnState.getCurrentPlayer().getPlayerAI().pickTilePlacementAndBuildAction(turnState);

    }

    protected Boolean attemptToProcessMove() throws InvalidMoveException, InterruptedException {

        for(Message message : gameSettings.getGlobalSettings().inboundQueue) {
            if(message.getGameID() != null && message.getMoveID() != null && message.getMessageType() != null) {
                if (message.getGameID().equals(gameID)) {
                    if (message.getMessageType().getSubtype().equals("BUILDACTION")) {
                        gameSettings.getPlayerSet().setCurrentPlayer(message.getCurrentPlayerID());
                        gameSettings.setMoveID(message.getMoveID());
                        offlineMockServerMessage(message);
                        return processMove(message);
                    }
                }
            }
        }
        return true;
    }

    private Boolean processMove(Message message) throws InvalidMoveException, InterruptedException {

        turnState.processMove(message);

        turnState = Move.placeTile(turnState);

        turnState = Move.takeBuildAction(turnState);

        if(EndConditions.playerIsOutOfPiecesOfTwoTypes(gameSettings.getPlayerSet().getCurrentPlayer())) {
            return false;
        }

        return true;
    }

    private void waitForGameOver() {
        while(continueGame) {
            if(isGameOver()) {
                continueGame = false;
            }
        }
    }

    protected Boolean isGameOver() {
        for(Message message : gameSettings.getGlobalSettings().inboundQueue) {

            if(message.getMessageType().getSubtype().equals(MessageType.GAMEOVER.getSubtype())) {
                message.setProcessed();
                return true;
            }
        }

        return false;
    }

    private void offlineMockServerMessage(Message message) {
        if(offline) {
            System.out.println(Client.getTime() + "SERVER: " + message.message);
        }
    }

    private void offlineCalculateResults() {
        if(offline) {
            winner = EndConditions.calculateWinner(gameSettings.getPlayerSet().getCurrentPlayer(), gameSettings.getPlayerSet().getPlayerList());
            loser = EndConditions.getLoser(winner, gameSettings.getPlayerSet().getPlayerList());
        }
    }

    private void offlineGenerateGameOverEcho() {
        if(offline) {
            //String message = "GAME " + gameID + " OVER PLAYER " + winner.getPlayerID() + " WIN PLAYER " + loser.getPlayerID() + " FORFEITED";
            String message = "GAME " + gameID + " OVER PLAYER " + winner.getPlayerID() + " " + winner.getScore().getScoreValue() + " PLAYER " + loser.getPlayerID() + " " + loser.getScore().getScoreValue();
            gameSettings.getGlobalSettings().outboundQueue.add(new Message(message));
        }
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }

    public Board getBoard() {
        return board;
    }

    public String getGameID() {
        return gameID;
    }

}
