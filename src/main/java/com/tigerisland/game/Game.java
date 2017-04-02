package com.tigerisland.game;

import com.tigerisland.GameSettings;
import com.tigerisland.InvalidMoveException;
import com.tigerisland.messenger.ConsoleOut;

public class Game implements Runnable {

    public final int gameID;

    protected GameSettings gameSettings;
    protected Board board;
    protected Turn turnState;
    protected TurnInfo turnInfo;
    protected Player currentPlayer;

    public Game(int gameID, GameSettings gameSettings){
        this.gameSettings = new GameSettings(gameSettings);
        this.gameID = gameID;
        this.turnInfo = new TurnInfo(gameID, this.gameSettings);
        this.board = new Board();
    }

    public void run() {
        try {

            placeStartingTile();

            while(true) {

                if(Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException();
                }

                if(!takeAnotherTurn()) {
                    break;
                }

            }
        } catch (InterruptedException exception) {
            ConsoleOut.printGameMessage(gameID, "was INTERRUPTED");

        } catch (InvalidMoveException exception) {
            ConsoleOut.printGameMessage(gameID, "had an INVALID MOVE\t( " + exception.getMessage() + " )\n");

        } finally {
            Player winner = EndConditions.calculateWinner(gameSettings.getPlayerSet().getCurrentPlayer(), gameSettings.getPlayerSet().getPlayerList());
            ConsoleOut.printGameMessage(gameID, "The winner was player " + winner.getPlayerType().getTypeString() + " " + winner.getPlayerColor().getColorString() );
        }
    }

    protected void placeStartingTile() throws InvalidMoveException {
        board.placeHex(new Hex("00", Terrain.VOLCANO), new Location(0, 0));
        board.placeHex(new Hex("00", Terrain.JUNGLE), new Location(-1, 1));
        board.placeHex(new Hex("00", Terrain.LAKE), new Location(0, 1));
        board.placeHex(new Hex("00", Terrain.GRASSLANDS), new Location(1, -1));
        board.placeHex(new Hex("00", Terrain.ROCKY), new Location(0, -1));
    }

    protected Boolean takeAnotherTurn() throws InvalidMoveException, InterruptedException {

        currentPlayer = gameSettings.getPlayerSet().getCurrentPlayer();

        ConsoleOut.printGameMessage(gameID, turnInfo.getMoveID(), "Player COLOR: " + currentPlayer.getPlayerColor().getColorString() + " TYPE: " + currentPlayer.getPlayerType().getTypeString());

        packageTurnState();

        turnState = Move.placeTile(turnState);

        if(EndConditions.noValidMoves(currentPlayer, board)) {
            unpackageTurnState(turnState);
            return false;
        }

        turnState = Move.takeBuildAction(turnState);

        unpackageTurnState(turnState);

        if(EndConditions.playerIsOutOfPieces(currentPlayer)) {
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

        ifAIPickBuildAction();

        turnState.updateTurnState(turnInfo);

        return turnState;

    }

    private void ifAIPickTilePlacement() {
        if(turnState.getPlayer().getPlayerType() != PlayerType.SERVER) {
            turnState.getPlayer().getPlayerAI().pickTilePlacement(turnInfo, turnState);
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
        //TextGUI.printMap(board);
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
