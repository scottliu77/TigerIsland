package com.tigerisland.AI;

import com.tigerisland.game.*;
import com.tigerisland.messenger.Adapter;
import com.tigerisland.messenger.ConsoleOut;
import com.tigerisland.messenger.Message;

public class AI {

    private double turnTime;
    private final PlayerType AIType;
    private Turn turnState;
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
        this.safeTileLocation = aiCopy.safeTileLocation;
    }

    public void pickTilePlacementAndBuildAction(Turn turnState) {
        unpackAIsettings(turnState);

        if(AIType == PlayerType.HUMAN) {
            message = HumanInput.pickTilePlacementAndBuildAction(turnState);
        } else {
            pickTilePlacement(turnState);
            pickBuildAction(turnState);
            assembleMessage();
        }

        sendMessage(message);
        ConsoleOut.printGameMessage(turnState.gameID, message);
    }

    private void pickTilePlacement(Turn turnState) {
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
        String leftTerrain = turnState.getCurrentTile().getLeftHex().getHexTerrain().name();
        String rightTerrain = turnState.getCurrentTile().getRightHex().getHexTerrain().name();
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

    private void pickBuildAction(Turn turnState) {
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

    private void unpackAIsettings(Turn turnState) {
        this.turnTime = turnState.getGameSettings().getGlobalSettings().turnTime;
        this.turnState = turnState;
    }

    private void assembleMessage() {
        message = "GAME " + turnState.gameID + " MOVE " + turnState.getMoveID() + " " + tilePlacementString + " " + buildActionString;
    }

    private void sendMessage(String message) {
        turnState.inboundMessages.add(new Message(message));
    }
}
