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

    public Game(GameSettings gameSettings){
        this.gameSettings = gameSettings;
        this.gameID = gameSettings.getGameID();
        this.board = new Board();
        this.offline = gameSettings.getGlobalSettings().getServerSettings().offline;
    }

    public void run() {
        try {

            board.placeStartingTile();

            while(continuePlayingGame()) {
                sleep(1);
            }

        } catch (InterruptedException exception) {
            offlineGenerateGameOverEcho("GAME " + gameID + " MOVE " + turnState.getMoveID() + " PLAYER " + turnState.getCurrentPlayer().getPlayerID() + " FORFEITED: ILLEGAL TILE PLACEMENT");

        } catch (InvalidMoveException exception) {
            offlineGenerateGameOverEcho("GAME " + gameID + " MOVE " + turnState.getMoveID() + " PLAYER " + turnState.getCurrentPlayer().getPlayerID() + " FORFEITED: ILLEGAL TILE PLACEMENT");

        } finally {
            Player winner = EndConditions.calculateWinner(gameSettings.getPlayerSet().getCurrentPlayer(), gameSettings.getPlayerSet().getPlayerList());
            offlineGenerateGameOverEcho("GAME " + gameID + " OVER PLAYER " + winner.getPlayerID() + " " + winner.getScore().getScoreValue() + " PLAYER 0 75");
        }
    }

    private Boolean continuePlayingGame() throws InterruptedException, InvalidMoveException {

        Boolean continueGame = true;

        if(Thread.currentThread().isInterrupted()) {
            throw new InterruptedException();
        }

        checkForGameOver();

        checkForMoveToProcess();

        if(offline) {
            pickOfflineMove();
        } else {
            checkForHaveAIPickAMove();
        }

        if(gameOver()) {
            continueGame = false;
        }

        return continueGame;
    }

    protected void pickOfflineMove() {

    }

    protected  void checkForGameOver() throws InvalidMoveException {
        for(Message message: gameSettings.getGlobalSettings().inboundQueue) {
            if(message.getMessageType().getSubtype().equals("GAMEOVER")) {
                message.setProcessed();
                throw new InvalidMoveException("Game is now over, no moves made");
            }
        }
    }

    protected void checkForMoveToProcess() throws InvalidMoveException, InterruptedException {
        for(Message message : gameSettings.getGlobalSettings().inboundQueue) {
            if(message.getMessageType().getSubtype().equals("BUILDACTION")) {
                message.setProcessed();
                processMove(message);
            }
        }
    }

    private void processMove(Message message) throws InvalidMoveException {

        turnState = packageTurnState(message);

        turnState = Move.placeTile(turnState);

        if(EndConditions.noValidMoves(gameSettings.getPlayerSet().getCurrentPlayer(), board)) {
            unpackageTurnState(turnState);
            throw new InvalidMoveException("No valid moves ");
        }

        turnState = Move.takeBuildAction(turnState);

        unpackageTurnState(turnState);

        if(EndConditions.playerIsOutOfPiecesOfTwoTypes(gameSettings.getPlayerSet().getCurrentPlayer())) {
            throw new InvalidMoveException("Player is out of pieces");
        }

    }

    protected void checkForHaveAIPickAMove() throws InvalidMoveException, InterruptedException {
         for(Message message : gameSettings.getGlobalSettings().inboundQueue) {
            if(message.getMessageType() == MessageType.MAKEMOVE) {
                message.setProcessed();
                pickMove(message);
            }
         }
    }

    private void pickMove(Message message) {
        turnState.updateTurnInformation(message);
        turnState.getCurrentPlayer().getPlayerAI().pickTilePlacementAndBuildAction(turnState);
    }

    protected Turn packageTurnState(Message message) {

        gameSettings.getPlayerSet().setCurrentPlayer(message.getCurrentPlayerID());
        gameSettings.setMoveID(message.getMoveID());

        turnState = new Turn(gameSettings, board);

        return turnState;

    }

    protected void unpackageTurnState(Turn turnState) {
        gameSettings.getPlayerSet().getCurrentPlayer().updatePlayerState(turnState.getCurrentPlayer());
        board = turnState.getBoard();
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

    public String getMoveID() {
        return turnState.getMoveID();
    }
}
