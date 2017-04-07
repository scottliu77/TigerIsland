package com.tigerisland.AI;

import com.tigerisland.game.*;
import com.tigerisland.messenger.Adapter;
import com.tigerisland.messenger.Message;

public class AI {

    private double turnTime;
    private final PlayerType AIType;

    protected Turn turnState;

    protected TilePlacement tilePlacement;
    protected BuildActionType buildActionType;
    protected Location buildLocation;

    protected String tilePlacementString;
    protected String buildActionString;

    private String message;

    protected SafeAI safeAI;
    protected TotoroLinesAI totoroLinesAI;

    public AI(PlayerType AIType) {
        this.AIType = AIType;
//        if(AIType == PlayerType.SAFEAI) {
//            this = new SafeAI();
//        } else if (AIType == PlayerType.TOTOROLINESAI) {
//            this = new TotoroLinesAI();
//        }
    }
//
//    public AI(AI aiCopy) {
//        this.AIType = aiCopy.AIType;
//        this.turnTime = aiCopy.turnTime;
//        this.turnState = aiCopy.turnState;
//    }

    public void pickTilePlacementAndBuildAction(Turn turnState) {
        unpackAIsettings(turnState);

        if(AIType == PlayerType.HUMAN) {
            HumanInput.pickTilePlacementAndBuildAction(turnState);
        } else if (AIType == PlayerType.SAFEAI) {
            safeAI.pickTilePlacementAndBuildAction();
        } else if (AIType == PlayerType.TOTOROLINESAI) {
            totoroLinesAI.pickTilePlacementAndBuildAction();
        }

        assembleMessage();
        sendMessage(message);
    }

    private void unpackAIsettings(Turn turnState) {
        this.turnTime = turnState.getGameSettings().getGlobalSettings().turnTime;
        this.turnState = turnState;
    }

    private void assembleMessage() {
        message = "GAME " + turnState.gameID + " MOVE " + turnState.getMoveID() + " " + tilePlacementString() + " " + buildActionString();
    }

    private String tilePlacementString() {
        Location tileLocation = tilePlacement.getLocation();
        int orientation = tilePlacement.getRotation();
        return "PLACE " + createTileString() + " AT " + createCubeTileLocationString(tileLocation, orientation);
    }

    private String buildActionString() {
        String buildMessageString = null;
        if(buildActionType == BuildActionType.VILLAGECREATION) {
            buildMessageString = "FOUND SETTLEMENT AT";
        } else if(buildActionType == BuildActionType.VILLAGEEXPANSION) {
            buildMessageString = "EXPANDED SETTLEMENT AT";
        } else if(buildActionType == BuildActionType.TIGERPLACEMENT) {
            buildMessageString = "BUILT TOTORO SANCTUARY AT";
        } else if(buildActionType == BuildActionType.TOTOROPLACEMENT) {
            buildMessageString = "BUILT TIGER PLAYGROUND AT";
        }

        return  buildMessageString + " " + createCubeBuildLocationString(buildLocation);
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
        return "GAME" + turnState.gameID + " MOVE " + turnState.getMoveID() + " PLAYER " + turnState.getCurrentPlayer().getPlayerID() + " " + tilePlacementString + " " + buildActionString;
    }
}
