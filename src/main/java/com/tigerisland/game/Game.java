package com.tigerisland.game;

import com.tigerisland.GameSettings;
import com.tigerisland.InvalidMoveException;
import com.tigerisland.messenger.Message;
import com.tigerisland.messenger.MessageType;

import static java.lang.Thread.sleep;

public class Game implements Runnable {

    public String gameID;

    protected GameSettings gameSettings;
    protected Board board;
    protected Turn turnState;

    protected Boolean offline;

    private String ourPlayerID;

    private String moveID = "0";

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

            calculateResults();

        } catch (InvalidMoveException exception) {
            if(!exception.getMessage().equals("LOST: UNABLE TO BUILD")) {
                gameSettings.getGlobalSettings().outboundQueue.add(new Message("GAME " + gameID + " MOVE " + turnState.getMoveID() + " PLAYER " + turnState.getCurrentPlayer().getPlayerID() + " " + exception.getMessage()));
            }
            calculateResults();

        } catch (Exception exception) {
            calculateResults();
        }

        offlineGenerateGameOverEcho("GAME " + gameID + " OVER PLAYER " + winner.getPlayerID() + " WIN PLAYER " + loser.getPlayerID() + " FORFEITED");
        //offlineGenerateGameOverEcho("GAME " + gameID + " OVER PLAYER " + winner.getPlayerID() + " " + winner.getScore().getScoreValue() + " PLAYER " + loser.getPlayerID() + " " + loser.getScore().getScoreValue());

    }

    private void calculateResults() {
        winner = EndConditions.calculateWinner(gameSettings.getPlayerSet().getCurrentPlayer(), gameSettings.getPlayerSet().getPlayerList());
        loser = EndConditions.getLoser(winner, gameSettings.getPlayerSet().getPlayerList());
    }

    private Boolean continuePlayingGame() throws InterruptedException, InvalidMoveException {

        Boolean continueGame = true;

        if(Thread.currentThread().isInterrupted()) {
            return false;
        }

        if(offline) {
            mockMakeMoveMessage();
            alternateOurPlayerID();
        }


        continueGame = attemptToProcessMove();

        checkForHaveAIPickAMove();


        if(gameOver()) {
            continueGame = false;
        }

        return continueGame;
    }

    protected void mockMakeMoveMessage() throws IndexOutOfBoundsException {

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
                        gameSettings.resetGameID(message.getGameID());
                        gameSettings.getPlayerSet().setCurrentPlayer(ourPlayerID);
                        pickMove(message);
                    }
                }
            }
        }
    }

    private void pickMove(Message message) throws InvalidMoveException {

        turnState.updateTurnInformation(message.getMoveID(), message.getTile(), ourPlayerID);

        if(EndConditions.noValidMoves(turnState.getCurrentPlayer(), turnState.getBoard())) {
            sendUnableToBuildMessage();
        } else {
            turnState.getCurrentPlayer().getPlayerAI().pickTilePlacementAndBuildAction(turnState);
        }
    }

    protected Boolean moveReadyToProcess() {
        for(Message message : gameSettings.getGlobalSettings().inboundQueue) {
            if (message.getGameID() != null && message.getMoveID() != null && message.getMessageType() != null) {
                if (message.getGameID().equals(gameID)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected Boolean attemptToProcessMove() throws InvalidMoveException, InterruptedException {

        for(Message message : gameSettings.getGlobalSettings().inboundQueue) {
            if(message.getGameID() != null && message.getMoveID() != null && message.getMessageType() != null) {
                if (message.getGameID().equals(gameID)) {
                    if (message.getMessageType().getSubtype().equals("BUILDACTION")) {
                        gameSettings.setMoveID(message.getMoveID());
                        gameSettings.resetGameID(message.getGameID());
                        sendMockServerMessage(message);
                        return processMove(message);
                    }
                }
            }
        }
        return true;
    }

    private Boolean processMove(Message message) throws InvalidMoveException, InterruptedException {

        System.out.println("Attempting to process move");

        gameSettings.getPlayerSet().setCurrentPlayer(message.getCurrentPlayerID());
        gameSettings.setMoveID(message.getMoveID());

        turnState.processMove();

        turnState = Move.placeTile(turnState);

        turnState = Move.takeBuildAction(turnState);

        if(EndConditions.playerIsOutOfPiecesOfTwoTypes(gameSettings.getPlayerSet().getCurrentPlayer())) {
            return false;
        }

        if(!offline) {
            TextGUI.printMap(turnState.getBoard());
        }

        return true;
    }

    protected Boolean gameOver() {
        for(Message message : gameSettings.getGlobalSettings().inboundQueue) {

            if(message.getMessageType().getSubtype().equals(MessageType.GAMEOVER.getSubtype())) {
                message.setProcessed();
                return true;
            }
        }

        return false;
    }

    private void sendMockServerMessage(Message message) {
        if(offline) {
            System.out.println("SERVER (Offline): " + message.message);
        }
    }

    private void sendUnableToBuildMessage() throws InvalidMoveException {
        String unableToBuildMessage = "GAME " + gameID + " MOVE " + turnState.getMoveID() + " UNABLE TO BUILD";
        gameSettings.getGlobalSettings().outboundQueue.add(new Message(unableToBuildMessage));
        throw new InvalidMoveException("LOST: UNABLE TO BUILD");
    }

    private void offlineGenerateGameOverEcho(String message) {
        if(offline) {
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
