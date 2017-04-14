package com.tigerisland.game;

import com.tigerisland.game.board.Board;
import com.tigerisland.game.board.Location;
import com.tigerisland.game.board.Tile;
import com.tigerisland.game.moves.BuildAction;
import com.tigerisland.game.moves.BuildActionType;
import com.tigerisland.game.moves.TilePlacement;
import com.tigerisland.game.player.Player;
import com.tigerisland.settings.GameSettings;
import com.tigerisland.client.Message;
import com.tigerisland.client.MessageType;

import java.util.concurrent.BlockingQueue;

import static java.lang.Thread.sleep;

public class Turn {

    public final String gameID;
    private String moveID;
    private Tile currentTile;
    private GameSettings gameSettings;
    public final BlockingQueue<Message> inboundMessages;
    public final BlockingQueue<Message> outboundMessages;

    private Player currentPlayer;
    private Board board;
    private TilePlacement tilePlacement;
    private BuildAction buildAction;

    public Turn(GameSettings gameSettings, Board board) {
        this.gameID = gameSettings.getGameID();
        this.inboundMessages = gameSettings.getGlobalSettings().inboundQueue;
        this.outboundMessages = gameSettings.getGlobalSettings().outboundQueue;

        this.gameSettings = gameSettings;

        Player currentPlayer = gameSettings.getPlayerSet().getCurrentPlayer();
        this.currentPlayer = currentPlayer;

        this.board = board;
    }

    public void updateTurnInformation(Message message) {
        moveID = message.getMoveID();
        currentTile = message.getTile();
        this.currentPlayer = gameSettings.getPlayerSet().getPlayerList().get(message.getCurrentPlayerID());
    }

    public void updateTurnInformation(String moveID, Tile currentTile) {
        this.moveID = moveID;
        this.currentTile = currentTile;
        this.currentPlayer = gameSettings.getPlayerSet().getCurrentPlayer();
    }

    public void processMove(Message message) throws InterruptedException, InvalidMoveException {
        currentPlayer = gameSettings.getPlayerSet().getCurrentPlayer();
        parseTilePlacement(message);
        filterBuildAction(message);
        message.setProcessed();
    }

    private Boolean messageIsSafeToUse(Message message) {
        Boolean safe = false;
        if(message.getMessageType() != MessageType.PROCESSED && message.getMessageType() != null) {
            if(message.getGameID() != null) {
                if(message.getMoveID() != null) {
                    safe = true;
                }
            }
        }
        return safe;
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

    public String getMoveID() {
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
