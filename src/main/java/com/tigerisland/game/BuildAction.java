package com.tigerisland.game;

public class BuildAction {

    private Location location = null;
    private Terrain expandTerrain = null;
    private Player player = null;

    private BuildActionType buildActionType;

    // Village Creation and Totoro Placement (and Tiger Placement)
    BuildAction(Player player, Location location, BuildActionType buildActionType) {
        this.player = player;
        this.location = location;
        this.buildActionType = buildActionType;
    }

    // Expand settlement
    BuildAction(Player player, Location location, Terrain expandTerrain){
        this.player = player;
        this.location = location;
        this.expandTerrain = expandTerrain;
        this.buildActionType = BuildActionType.VILLAGEEXPANSION;
    }

    public Location getLocation() {
        return this.location;
    }

    public Terrain getExpandTerrain() {
        return this.expandTerrain;
    }

    public Player getPlayer() {
        return this.player;
    }

    public BuildActionType getBuildActionType() {
        return this.buildActionType;
    }
}
