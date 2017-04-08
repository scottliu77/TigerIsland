package com.tigerisland.game;

import com.tigerisland.GameSettings;
import com.tigerisland.InvalidMoveException;
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

    private int moveID = 0;

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

            while(continuePlayingGame()) {
                sleep(1);
            }

        } catch (InterruptedException exception) {
            offlineGenerateGameOverEcho("GAME " + gameID + " MOVE " + turnState.getMoveID() + " PLAYER " + turnState.getCurrentPlayer().getPlayerID() + " FORFEITED: TIMEOUT");

        } catch (InvalidMoveException exception) {
            offlineGenerateGameOverEcho("GAME " + gameID + " MOVE " + turnState.getMoveID() + " PLAYER " + turnState.getCurrentPlayer().getPlayerID() + " " + exception.getMessage());
        } finally {
            Player winner = EndConditions.calculateWinner(gameSettings.getPlayerSet().getCurrentPlayer(), gameSettings.getPlayerSet().getPlayerList());
            offlineGenerateGameOverEcho("GAME " + gameID + " OVER PLAYER " + winner.getPlayerID() + " " + winner.getScore().getScoreValue() + " PLAYER 0 75");
        }
    }

    private Boolean continuePlayingGame() throws InterruptedException, InvalidMoveException {


        if(gameSettings.getGlobalSettings().manualTesting) {
            TextGUI.printMap(board);
        }


        Boolean continueGame = true;

        if(Thread.currentThread().isInterrupted()) {
            throw new InterruptedException("LOST: UNABLE TO BUILD");
        }

        if(offline) {
            mockMakeMoveMessage();
        }

        checkForHaveAIPickAMove();

        checkForGameOver();

        continueGame = checkForMoveToProcess();

        if(gameOver()) {
            continueGame = false;
        }

        return continueGame;
    }

    protected void mockMakeMoveMessage() {

        String makeMoveMessage = "MAKE YOUR MOVE IN GAME " + gameSettings.getGameID();
        makeMoveMessage = makeMoveMessage + " WITHIN 1.5 SECONDS:";

        makeMoveMessage = makeMoveMessage + " MOVE " + Integer.toString(moveID + 1) + " PLACE ";

        Tile newTile = gameSettings.getDeck().drawTile();
        String leftTerrain = newTile.getLeftHex().getHexTerrain().name();
        String rightTerrain = newTile.getRightHex().getHexTerrain().name();

        makeMoveMessage = makeMoveMessage + leftTerrain + "+" + rightTerrain;

        gameSettings.getGlobalSettings().inboundQueue.add(new Message(makeMoveMessage));
    }

    protected void checkForHaveAIPickAMove() throws InvalidMoveException, InterruptedException {
        for(Message message : gameSettings.getGlobalSettings().inboundQueue) {
            if(message.getMessageType() == MessageType.MAKEMOVE) {
                message.setProcessed();
                gameSettings.getPlayerSet().setCurrentPlayer(ourPlayerID);
                pickMove(message);
            }
        }
    }

    private void pickMove(Message message) throws InvalidMoveException {
        turnState.updateTurnInformation(message.getMoveID(), message.getTile(), ourPlayerID);
        turnState.getCurrentPlayer().getPlayerAI().pickTilePlacementAndBuildAction(turnState);
    }

    protected  void checkForGameOver() throws InvalidMoveException {
        for(Message message: gameSettings.getGlobalSettings().inboundQueue) {
            if(message.getMessageType().getSubtype().equals("GAMEOVER")) {
                message.setProcessed();
                throw new InvalidMoveException("Game is now over, no moves made");
            }
        }
    }

    protected Boolean checkForMoveToProcess() throws InvalidMoveException, InterruptedException {
        for(Message message : gameSettings.getGlobalSettings().inboundQueue) {
            if(message.getMessageType().getSubtype().equals("BUILDACTION")) {
                return processMove(message);
            }
        }
        return true;
    }

    private Boolean processMove(Message message) throws InvalidMoveException, InterruptedException {

        gameSettings.getPlayerSet().setCurrentPlayer(message.getCurrentPlayerID());
        gameSettings.setMoveID(message.getMoveID());

        turnState.processMove();

        turnState = Move.placeTile(turnState);

        if(EndConditions.noValidMoves(gameSettings.getPlayerSet().getCurrentPlayer(), board)) {
            throw new InvalidMoveException("LOST: UNABLE TO BUILD");
        }

        turnState = Move.takeBuildAction(turnState);

        if(EndConditions.playerIsOutOfPiecesOfTwoTypes(gameSettings.getPlayerSet().getCurrentPlayer())) {
            return false;
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
