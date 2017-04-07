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
    protected Player currentPlayer;

    public Game(GameSettings gameSettings){
        this.gameSettings = gameSettings;
        this.gameID = gameSettings.getGameID();
        this.board = new Board();
    }

    public void run() {
        try {

            board.placeStartingTile();

            while(true) {

                if(Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException();
                }

                checkForMoveToProcess();

                checkForMakeMove();

                if(gameOver()) {
                    break;
                }

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

    protected void checkForMoveToProcess() throws InvalidMoveException, InterruptedException {
        for(Message message : gameSettings.getGlobalSettings().inboundQueue) {
            if(message.getMessageType().getSubtype().equals(MessageType.BUILDTOTORO.getSubtype())) {
                gameSettings.getPlayerSet().setCurrentPlayer(message.getOurPlayerID());
                takeAnotherTurn();
            } else if(message.getMessageType().getSubtype().equals(MessageType.FORFEITBUILD.getSubtype())) {
                message.setProcessed();
                throw new InvalidMoveException("Server sent invalid move exception");
            }
        }
    }

    protected void checkForMakeMove() throws InvalidMoveException, InterruptedException {
        if(gameSettings.getGlobalSettings().getServerSettings().offline) {
            turnState.getCurrentPlayer().getPlayerAI().pickTilePlacementAndBuildAction(turnState);
        } else {
            for(Message message : gameSettings.getGlobalSettings().inboundQueue) {
                if(message.getMessageType() == MessageType.MAKEMOVE) {
                    message.setProcessed();
                    turnState.updateTurn(message);
                    turnState.getCurrentPlayer().getPlayerAI().pickTilePlacementAndBuildAction(turnState);
                    turnState.processMove();
                }
            }
        }
    }

    protected Boolean takeAnotherTurn() throws InvalidMoveException, InterruptedException {

        packageTurnState();

        turnState = Move.placeTile(turnState);

        if(EndConditions.noValidMoves(currentPlayer, board)) {
            unpackageTurnState(turnState);
            return false;
        }

        turnState = Move.takeBuildAction(turnState);

        unpackageTurnState(turnState);

        if(EndConditions.playerIsOutOfPiecesOfTwoTypes(currentPlayer)) {
            return false;
        }

        return true;
    }

    protected Turn packageTurnState() throws InterruptedException, InvalidMoveException {

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
        if(gameSettings.getGlobalSettings().getServerSettings().offline) {
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

    public int getMoveID() {
        return turnState.getMoveID();
    }
}
