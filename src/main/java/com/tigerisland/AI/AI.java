package com.tigerisland.AI;

import com.tigerisland.game.Location;
import com.tigerisland.game.PlayerType;
import com.tigerisland.game.Turn;
import com.tigerisland.game.TurnInfo;
import com.tigerisland.messenger.Message;

public class AI {

    private double turnTime;
    private final PlayerType AIType;
    private Turn turnState;
    private TurnInfo turnInfo;
    private Location tileLocation;

    public AI(PlayerType AIType) {
        this.AIType = AIType;
    }

    public AI(AI aiCopy) {
        this.turnTime = aiCopy.turnTime;
        this.AIType = aiCopy.AIType;
        this.turnState = aiCopy.turnState;
        this.turnInfo = aiCopy.turnInfo;
        this.tileLocation = aiCopy.tileLocation;
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
        }
    }

    private String pickSafeTilePlacement() {
        if(turnState.getBoard().getPlacedHexes().size() == 0) {
            return assembleMessage("PLACE GG AT 0 0 0");
        } else {
            Location adjacentLocation = findFirstPlaceableHexOnRight();
            return assembleMessage("PLACE GG AT " + adjacentLocation.getX() + " " + adjacentLocation.getY() + " 0");
        }
    }

    private Location findFirstPlaceableHexOnRight() {
        int highestX = 0;
        int associatedY = 0;
        for(Location loc : turnState.getBoard().locationsOfPlacedHexes()) {
            if(loc.getX() >= highestX) {
                highestX = loc.getX();
                associatedY = loc.getY();
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
        }
    }

    private String pickSafeBuildAction() {
        Location openHex = findFirstBuildableHexOnRight();
        return assembleMessage("BUILD villager AT " + openHex.getX() + " " + openHex.getY());
    }

    private Location findFirstBuildableHexOnRight() {
        int highestX = 0;
        int associatedY = 0;
        for(Location loc : turnState.getBoard().locationsOfPlacedHexes()) {
            if(loc.getX() >= highestX && turnState.getBoard().hexAt(loc).getPieceCount() == 0) {
                highestX = loc.getX();
                associatedY = loc.getY();
            }
        }
        return new Location(highestX, associatedY);
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
