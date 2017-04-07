package com.tigerisland.AI;

import com.tigerisland.game.*;
import com.tigerisland.messenger.Adapter;

public class SafeAI extends AI {


    public void decideOnMove() {
        pickSafeTilePlacement();
        pickSafeBuildAction();
    }

    private void pickSafeTilePlacement() {
        Location tilePlacementLocation;
        if(turnState.getBoard().getPlacedHexes().size() == 0) {
            tilePlacementLocation = new Location(0, 0);
        } else {
            tilePlacementLocation = findFirstPlaceableHexOnRight();
        }

        tilePlacement = new TilePlacement(turnState.getCurrentTile(), tilePlacementLocation, 0);
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

    private void pickSafeBuildAction() {
        buildLocation = new Location(tilePlacement.getLocation().x + 1, tilePlacement.getLocation().y);
        buildActionType = BuildActionType.VILLAGECREATION;
    }

}
