package com.tigerisland.game;

import com.tigerisland.GameSettings;
import com.tigerisland.InvalidMoveException;

public class Game implements Runnable {

    public final int gameID;

    protected GameSettings gameSettings;
    protected Board board;
    protected Turn turnState;
    protected TurnInfo turnInfo;

    public Game(int gameID, GameSettings gameSettings){
        this.gameID = gameID;
        this.turnInfo = new TurnInfo(gameID, gameSettings);
        this.gameSettings = gameSettings;
        this.board = new Board();
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

    protected Boolean takeAnotherTurn() throws InvalidMoveException, InterruptedException {

        packageTurnState();

        turnState = Move.placeTile(turnState);

        if(EndConditions.noValidMoves(gameSettings.getPlayerSet().getCurrentPlayer(), board)) {
            unpackageTurnState(turnState);
            return false;
        }

        turnState = Move.takeBuildAction(turnState);

        unpackageTurnState(turnState);

        if(EndConditions.playerIsOutOfPieces(gameSettings.getPlayerSet().getCurrentPlayer())) {
            return false;
        }

        gameSettings.getPlayerSet().setNextPlayer();
        turnInfo.incrementMoveNumber();

        return true;
    }

    protected Turn packageTurnState() throws InterruptedException, InvalidMoveException {

        turnState = new Turn(gameSettings.getPlayerSet().getCurrentPlayer(), board);

        turnInfo.drawANewTile();

        ifAIPickTilePlacement();

        turnState.updateTilePlacement(turnInfo);

        ifAIPickBuildAction();

        turnState.updateBuildAction(turnInfo);

        return turnState;

    }

    private void ifAIPickTilePlacement() {
        if(turnState.getPlayer().getPlayerType() != PlayerType.SERVER) {
            turnState.getPlayer().getPlayerAI().pickBuildAction(turnInfo, turnState);
        }
    }

    private void ifAIPickBuildAction() {
        if(turnState.getPlayer().getPlayerType() != PlayerType.SERVER) {
            turnState.getPlayer().getPlayerAI().pickBuildAction(turnInfo, turnState);
        }
    }

    protected void unpackageTurnState(Turn turnState) {
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
        return turnInfo.getMoveID();
    }
}
