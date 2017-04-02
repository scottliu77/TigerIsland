package com.tigerisland.AI;

import com.tigerisland.game.*;
import com.tigerisland.messenger.Adapter;
import com.tigerisland.messenger.ConsoleOut;
import com.tigerisland.messenger.Message;

public class AI {

    private double turnTime;
    private final PlayerType AIType;
    private Turn turnState;
    private TurnInfo turnInfo;
    private Location safeTileLocation;

    private String tilePlacementString;
    private String buildActionString;
    private String message;

    public AI(PlayerType AIType) {
        this.AIType = AIType;
    }

    public AI(AI aiCopy) {
        this.turnTime = aiCopy.turnTime;
        this.AIType = aiCopy.AIType;
        this.turnState = aiCopy.turnState;
        this.turnInfo = aiCopy.turnInfo;
        this.safeTileLocation = aiCopy.safeTileLocation;
    }

    public void pickTilePlacementAndBuildAction(TurnInfo turnInfo, Turn turnState) {
        unpackAIsettings(turnInfo, turnState);

        if(AIType == PlayerType.HUMAN) {
            message = DummyAI.pickTilePlacementAndBuildAction(turnInfo, turnState);
        } else {
            pickTilePlacement(turnInfo, turnState);
            pickBuildAction(turnInfo, turnState);
            assembleMessage();
        }

        sendMessage(message);
        ConsoleOut.printGameMessage(turnInfo.gameID, message);
    }

    private void pickTilePlacement(TurnInfo turnInfo, Turn turnState) {
        pickSafeTilePlacement();
        while(true) {
            // TODO timer implementation and better move selection
            break;
        }
    }

    private void pickSafeTilePlacement() {
        if(turnState.getBoard().getPlacedHexes().size() == 0) {
            safeTileLocation = new Location(0, 0);
            tilePlacementString = "PLACE " + createTileString() + " AT " + "0 0 0 1";
        } else {
            safeTileLocation = findFirstPlaceableHexOnRight();
            tilePlacementString = "PLACE " + createTileString() + " AT " + createCubeCoordinateString();
        }
    }

    private String createCubeCoordinateString() {
        Location axialLocation = new Location(safeTileLocation.x, safeTileLocation.y);
        Location cubeLocation = Adapter.convertLocationAxialToCube(axialLocation);
        Integer cubeDegrees = Adapter.convertOrientationToCode(0);
        return cubeLocation.x + " " + cubeLocation.y + " " + cubeLocation.z + " " + cubeDegrees;
    }

    private String createTileString() {
        String leftTerrain = turnInfo.getTile().getLeftHex().getHexTerrain().name();
        String rightTerrain = turnInfo.getTile().getRightHex().getHexTerrain().name();
        return leftTerrain + "+" + rightTerrain;
    }

    private Location findFirstPlaceableHexOnRight() {
        int highestX = 0;
        int associatedY = 0;
        for(PlacedHex hex : turnState.getBoard().getPlacedHexes()) {
            Location loc = hex.getLocation();
            if(loc.x >= highestX) {
                highestX = loc.x;
                associatedY = loc.y;
            }
        }
        return new Location(highestX + 1, associatedY);
    }

    private void pickBuildAction(TurnInfo turnInfo, Turn turnState) {
        pickSafeBuildAction();
        while(true) {
            // TODO timer implementation and better move selection
            break;
        }
    }

    private void pickSafeBuildAction() {
        Location axialLocation = new Location(safeTileLocation.x + 1, safeTileLocation.y);
        Location cubeLocation = Adapter.convertLocationAxialToCube(axialLocation);

        buildActionString = "FOUND SETTLEMENT AT " + cubeLocation.x + " " + cubeLocation.y + " " + cubeLocation.z;
    }

    private void unpackAIsettings(TurnInfo turnInfo, Turn turnState) {
        this.turnTime = turnInfo.getGameSettings().getGlobalSettings().turnTime;
        this.turnInfo = turnInfo;
        this.turnState = turnState;
    }

    private void assembleMessage() {
        message = "GAME " + turnInfo.gameID + " MOVE " + turnInfo.getMoveID() + " " + tilePlacementString + " " + buildActionString;
    }

    private void sendMessage(String message) {
        turnInfo.inboundMessages.add(new Message(message));
    }
}
