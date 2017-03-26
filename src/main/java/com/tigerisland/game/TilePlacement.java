package com.tigerisland.game;

public class TilePlacement {

    private Tile tile = null;
    private Location location = null;
    private int rotation;

    TilePlacement(Tile tile, Location location, int rotation) {
        this.tile = tile;
        this.location = location;
        this.rotation = rotation;
    }

    public Tile getTile() {
        return this.tile;
    }

    public Location getLocation() {
        return this.location;
    }

    public int getRotation() {
        return this.rotation;
    }

}
