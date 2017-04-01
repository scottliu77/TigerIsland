package com.tigerisland.AI;

import com.tigerisland.game.*;
import com.tigerisland.messenger.ConsoleOut;
import com.tigerisland.messenger.Message;

public class AI {

    private double turnTime;
    private final PlayerType AIType;
    private Turn turnState;
    private TurnInfo turnInfo;
    private Location safeTileLocation;

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

    public void pickTilePlacement(TurnInfo turnInfo, Turn turnState) {
        unpackAIsettings(turnInfo, turnState);

        if(AIType == PlayerType.HUMAN) {
            DummyAI.pickTilePlacement(turnInfo, turnState);
        } else {
            String message = pickSafeTilePlacement();
            while(true) {
                // TODO timer implementation and better move selection
                break;
            }
            sendMessage(message);
            ConsoleOut.printGameMessage(turnInfo.gameID, message);
        }
    }

    private String pickSafeTilePlacement() {
        String tileString = createTileString();
        if(turnState.getBoard().getPlacedHexes().size() == 0) {
            safeTileLocation = new Location(0, 0);
            return assembleMessage("PLACE " + tileString + " AT 0 0 0");
        } else {
            safeTileLocation = findFirstPlaceableHexOnRight();
            return assembleMessage("PLACE " + tileString + " AT " + safeTileLocation.x + " " + safeTileLocation.y + " 0");
        }
    }

    private String createTileString() {
        Character leftTerrain = turnInfo.getTile().getLeftHex().getHexTerrain().getTerrainChar();
        Character rightTerrain = turnInfo.getTile().getRightHex().getHexTerrain().getTerrainChar();
        return Character.toString(leftTerrain) + Character.toString(rightTerrain);
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

    public void pickBuildAction(TurnInfo turnInfo, Turn turnState) {
        unpackAIsettings(turnInfo, turnState);

        if(AIType == PlayerType.HUMAN) {
           DummyAI.pickBuildAction(turnInfo, turnState);
        } else {
            String message = pickSafeBuildAction();
            while(true) {
                // TODO timer implementation and better move selection
                break;
            }
            sendMessage(message);
            ConsoleOut.printGameMessage(turnInfo.gameID, message);
        }
    }

    private String pickSafeBuildAction() {
        int openX = safeTileLocation.x + 1;
        int openY = safeTileLocation.y;

        return assembleMessage("BUILD villager AT " + openX + " " + openY);
    }

    private void unpackAIsettings(TurnInfo turnInfo, Turn turnState) {
        this.turnTime = turnInfo.getGameSettings().getGlobalSettings().turnTime;
        this.turnInfo = turnInfo;
        this.turnState = turnState;
    }
    private String assembleMessage(String messageComponent) {
        return "GAME " + turnInfo.gameID + " MOVE " + turnInfo.getMoveID() + " " + messageComponent;
    }

    private void sendMessage(String message) {
        turnInfo.inboundMessages.add(new Message(message));
    }
}
