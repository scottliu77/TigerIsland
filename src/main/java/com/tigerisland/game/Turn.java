package com.tigerisland.game;

import com.tigerisland.InvalidMoveException;
import com.tigerisland.messenger.Message;
import com.tigerisland.messenger.MessageType;

import static java.lang.Thread.sleep;

public class Turn {

    private Player player;
    private Board board;
    private TilePlacement tilePlacement;
    private BuildAction buildAction;

    public Turn(Player player, Board board) {
        this.player = new Player(player);
        this.board = new Board(board);
    }

    public void updateTurnState(TurnInfo turnInfo) throws InterruptedException, InvalidMoveException {
        while(true) {
            for(Message message : turnInfo.inboundMessages) {
                if(message.getMessageType() != MessageType.PROCESSED) {
                    if(message.getGameID().equals(turnInfo.gameID)) {
                        if(message.getMoveID() == turnInfo.getMoveID()) {

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
            buildAction = new BuildAction(player, message.getBuildLocation(), message.getExpandTerrain());
        } else {
            buildAction = new BuildAction(player, message.getBuildLocation(), buildActionType);
        }
    }

    public Player getPlayer() {
        return player;
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

}
