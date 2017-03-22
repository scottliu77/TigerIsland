package com.tigerisland;

public class BuildAction {

    private Location location = null;
    private Location settlementLocation = null;
    private Player player = null;

    private BuildActionType buildActionType;

    // Village Creation and Totoro Placement (and Tiger Placement)
    BuildAction(Player player, Location location, BuildActionType buildActionType) {
        this.player = player;
        this.location = location;
        this.buildActionType = buildActionType;
    }

    // Expand settlement
    BuildAction(Player player, Location locationToBeExpanded, Location settlementLocation){
        this.player = player;
        this.location = locationToBeExpanded;
        this.settlementLocation = settlementLocation;
        this.buildActionType = BuildActionType.VILLAGEEXPANSION;
    }

    public Location getLocation() {
        return this.location;
    }

    public Location getSettlementLocation() { return this.settlementLocation; }

    public Player getPlayer() {
        return this.player;
    }

    public BuildActionType getBuildActionType() {
        return this.buildActionType;
    }
}
