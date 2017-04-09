package com.tigerisland.AI;

import com.tigerisland.InvalidMoveException;
import com.tigerisland.game.*;
import com.tigerisland.messenger.Adapter;
import com.tigerisland.messenger.Message;

public abstract class AI {
    private double turnTime;

    protected Turn turnState;

    protected TilePlacement tilePlacement;
    protected BuildActionType buildActionType;
    protected Location buildLocation;
    protected Terrain expandTerrain;

    protected String tilePlacementString;
    protected String buildActionString;

    protected String message;

    public AI() {
    }

    public AI(AI aiCopy) {
        this.turnTime = aiCopy.turnTime;
        this.turnState = aiCopy.turnState;
    }

    public void pickTilePlacementAndBuildAction(Turn turnState) {
        unpackAIsettings(turnState);
        decideOnMove();
        assembleMessage();
        sendMessage(message);
    }

    // Decide on Move should set values for 'tilePlacement', 'buildActionType', and 'buildLocation'
    protected abstract void decideOnMove(); //abstract means that sub-classes will define how decideOnMove works.

    private void unpackAIsettings(Turn turnState) {
        this.turnTime = turnState.getGameSettings().getGlobalSettings().turnTime;
        this.turnState = turnState;
    }

    protected void assembleMessage() {
        message = "GAME " + turnState.gameID + " MOVE " + turnState.getMoveID() + " " + createTilePlacementString() + " " + buildActionString();
    }

    private String createTilePlacementString() {
        Location tileLocation = tilePlacement.getLocation();
        int orientation = tilePlacement.getRotation();
        tilePlacementString = "PLACE " + createTileString() + " AT " + createCubeTileLocationString(tileLocation, orientation);
        return tilePlacementString;
    }

    private String buildActionString() {
        String buildMessageString = null;
        if(buildActionType == BuildActionType.VILLAGECREATION) {
            buildMessageString = "FOUND SETTLEMENT AT";
        } else if(buildActionType == BuildActionType.VILLAGEEXPANSION) {
            buildMessageString = "EXPANDED SETTLEMENT AT";
        } else if(buildActionType == BuildActionType.TOTOROPLACEMENT) {
            buildMessageString = "BUILT TOTORO SANCTUARY AT";
        } else if(buildActionType == BuildActionType.TIGERPLACEMENT) {
            buildMessageString = "BUILT TIGER PLAYGROUND AT";
        }

        buildActionString = buildMessageString + " " + createCubeBuildLocationString(buildLocation);

        if(buildActionType == BuildActionType.VILLAGEEXPANSION) {
            buildActionString += " " + expandTerrain.name();
        }

        return buildActionString;
    }

    protected String createTileString() {
        String leftTerrain = turnState.getCurrentTile().getLeftHex().getHexTerrain().name();
        String rightTerrain = turnState.getCurrentTile().getRightHex().getHexTerrain().name();
        return leftTerrain + "+" + rightTerrain;
    }

    protected String createCubeBuildLocationString(Location unconvertedBuildLocation) {
        Location axialLocation = new Location(unconvertedBuildLocation.x, unconvertedBuildLocation.y);
        Location cubeLocation = Adapter.convertLocationAxialToCube(axialLocation);
        return cubeLocation.x + " " + cubeLocation.y + " " + cubeLocation.z;
    }

    protected String createCubeTileLocationString(Location unconvertedBuildLocation, int unconvertedOrientation) {
        Location axialLocation = new Location(unconvertedBuildLocation.x, unconvertedBuildLocation.y);
        Location cubeLocation = Adapter.convertLocationAxialToCube(axialLocation);
        Integer cubeDegrees = Adapter.convertOrientationToCode(unconvertedOrientation);
        return cubeLocation.x + " " + cubeLocation.y + " " + cubeLocation.z + " " + cubeDegrees;
    }

    private void sendMessage(String message) {
        turnState.outboundMessages.add(new Message(message));
        echoMessageForOfflineTesting(message);
    }

    private void echoMessageForOfflineTesting(String message) {
        if(turnState.getGameSettings().getGlobalSettings().getServerSettings().offline) {
            turnState.inboundMessages.add(new Message(assembleOfflineEchoMessage(message)));
        }
    }

    private String assembleOfflineEchoMessage(String message) {
        return "GAME " + turnState.gameID + " MOVE " + turnState.getMoveID() + " PLAYER " + turnState.getCurrentPlayer().getPlayerID() + " " + tilePlacementString + " " + buildActionString;
    }
}
