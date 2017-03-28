package com.tigerisland.game;

import com.tigerisland.GameSettings;
import com.tigerisland.InvalidMoveException;

import java.util.concurrent.BlockingQueue;

public class Game implements Runnable {

    public final int gameID;
    private int moveID;

    protected GameSettings gameSettings;
    protected Board board;
    protected Turn turnState;
    protected BlockingQueue inboundQueue;

    public Game(int gameID, GameSettings gameSettings){
        this.gameID = gameID;
        this.moveID = 1;
        this.gameSettings = gameSettings;
        this.board = new Board();
        this.inboundQueue = gameSettings.getGlobalSettings().inboundQueue;
    }

    public void run() {
        try {
            while(true) {

                if(!Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException();
                }

                if(!takeAnotherTurn()) {
                    break;
                }

            }
        } catch (InterruptedException exception) {
            // do something about being interrupted

        } catch (InvalidMoveException exception) {
            // do something about making an invalid move

        } finally {
            EndConditions.calculateWinner(gameSettings.getPlayerSet().getCurrentPlayer(), gameSettings.getPlayerSet().getPlayerList());
            // TODO return winner information or display to screen
        }
    }

    private Boolean takeAnotherTurn() throws InvalidMoveException, InterruptedException {

        packageTurnState();

        turnState = Move.placeTile(turnState);

        if(EndConditions.noValidMoves(gameSettings.getPlayerSet().getCurrentPlayer(), board)) {
            return false;
        }

        turnState = Move.takeBuildAction(turnState);

        if(EndConditions.playerIsOutOfPieces(gameSettings.getPlayerSet().getCurrentPlayer())) {
            return false;
        }

        unpackageTurnState(turnState);

        gameSettings.getPlayerSet().setNextPlayer();
        moveID++;

        return true;
    }

    private Turn packageTurnState() throws InterruptedException, InvalidMoveException {

        turnState = new Turn(gameSettings.getPlayerSet().getCurrentPlayer(), board);

        turnState.updateTilePlacement(gameID, moveID, inboundQueue);
        turnState.updatedBuildAction(gameID, moveID, inboundQueue);

        return turnState;

    }

    private void unpackageTurnState(Turn turnState) {
        gameSettings.getPlayerSet().getCurrentPlayer().updatePlayerState(turnState.getPlayer());
        board = turnState.getBoard();
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }

    public Board getBoard() {
        return board;
    }

    public int getGameID() {
        return gameID;
    }

    public int getMoveID() {
        return moveID;
    }
}
