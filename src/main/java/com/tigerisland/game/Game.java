package com.tigerisland.game;

import com.tigerisland.GameSettings;
import com.tigerisland.InvalidMoveException;
import com.tigerisland.messenger.ConsoleOut;
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

            placeStartingTile();

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
        for(Message message : gameSettings.getGlobalSettings().inboundQueue) {
            if(message.getMessageType() == MessageType.MAKEMOVE) {
                message.setProcessed();
                turnState.updateTurn(message);
                turnState.getCurrentPlayer().getPlayerAI().pickTilePlacementAndBuildAction(turnState);
                turnState.processMove();
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
