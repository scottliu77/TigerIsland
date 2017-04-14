package com.tigerisland.game.moves;

import com.tigerisland.game.board.Location;
import com.tigerisland.game.board.Tile;

public class TilePlacement {

    private Tile tile = null;
    private Location location = null;
    private int rotation;

    public TilePlacement(Tile tile, Location location, int rotation) {
        this.tile = tile;
        this.location = location;
        this.rotation = rotation;
    }

    public TilePlacement(TilePlacement tilePlacement){
        this.tile = new Tile(tilePlacement.getTile());
        this.location = tilePlacement.getLocation();
        this.rotation = tilePlacement.getRotation();
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
