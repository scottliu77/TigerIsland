package com.tigerisland.game;

import com.tigerisland.GameSettings;
import com.tigerisland.InvalidMoveException;
import com.tigerisland.messenger.Message;
import com.tigerisland.messenger.MessageType;

import java.util.concurrent.BlockingQueue;

import static java.lang.Thread.sleep;

public class Turn {

    public final String gameID;
    private int moveID;
    private Tile currentTile;
    private GameSettings gameSettings;
    public final BlockingQueue<Message> inboundMessages;

    private Player currentPlayer;
    private Board board;
    private TilePlacement tilePlacement;
    private BuildAction buildAction;

    public Turn(GameSettings gameSettings, Board board) {
        this.gameID = gameSettings.getGameID();
        this.inboundMessages = gameSettings.getGlobalSettings().inboundQueue;
        this.gameSettings = gameSettings;

        Player currentPlayer = gameSettings.getPlayerSet().getCurrentPlayer();
        this.currentPlayer = new Player(currentPlayer);

        this.board = new Board(board);
    }

    public void updateTurn(Message message) {
        moveID = message.getMoveID();
        currentTile = message.getTile();
        gameSettings.getPlayerSet().setCurrentPlayer(message.getOurPlayerID());
    }

    public void updateTurn(int moveID, Tile currentTile, int currentPlayer) {
        this.moveID = moveID;
        this.currentTile = currentTile;
        gameSettings.getPlayerSet().setCurrentPlayer(currentPlayer);
    }

    public void processMove() throws InterruptedException, InvalidMoveException {
        while(true) {
            for(Message message : inboundMessages) {
                if(message.getMessageType() != MessageType.PROCESSED) {
                    if(message.getGameID().equals(gameID)) {
                        if(message.getMoveID() == getMoveID()) {

                            parseTilePlacement(message);
                            filterBuildAction(message);
                            message.setProcessed();
                            return;

                        }
                    }
                }
            }
            sleep(5);
        }
    }

    private void parseTilePlacement(Message message) throws InvalidMoveException {
        Tile tile = message.getTile();
        Location loc = message.getTileLocation();
        int rotation = message.getOrientation();
        tilePlacement = new TilePlacement(tile, loc, rotation);
    }

    private void filterBuildAction(Message message) {
        if(message.getMessageType() == MessageType.FOUNDSETTLEMENT) {
            parseBuildAction(message, BuildActionType.VILLAGECREATION);
        } else if (message.getMessageType() == MessageType.EXPANDSETTLEMENT) {
            parseBuildAction(message, BuildActionType.VILLAGEEXPANSION);
        } else if (message.getMessageType() == MessageType.BUILDTOTORO) {
            parseBuildAction(message, BuildActionType.TOTOROPLACEMENT);
        } else if (message.getMessageType() == MessageType.BUILDTIGER) {
            parseBuildAction(message, BuildActionType.TIGERPLACEMENT);
        }
    }

    private void parseBuildAction(Message message, BuildActionType buildActionType) {
        if (buildActionType == BuildActionType.VILLAGEEXPANSION) {
            buildAction = new BuildAction(currentPlayer, message.getBuildLocation(), message.getExpandTerrain());
        } else {
            buildAction = new BuildAction(currentPlayer, message.getBuildLocation(), buildActionType);
        }
    }

    public int getMoveID() {
        return moveID;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Board getBoard() {
        return board;
    }

    public TilePlacement getTilePlacement() {
        return tilePlacement;
    }

    public BuildAction getBuildAction() {
        return buildAction;
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }
}
