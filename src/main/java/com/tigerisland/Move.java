package com.tigerisland;

public class Move {

    private Tile tile = null;
    private Location location = null;
    private int rotation;
    private Player player = null;

    private MoveType moveType;

    // TODO Split constructors if/when it makes sense

    // Tile placement constructor
    Move(Tile tile, Location location, int rotation) {
        this.tile = tile;
        this.location = location;
        this.rotation = rotation;
        this.moveType = MoveType.TILEPLACEMENT;
    }

    // Village Creation, Village Expansion, and Totoro Placement have overlapping constructors for now
    Move(Player player, Location location, MoveType moveType) {
        this.player = player;
        this.location = location;
        this.moveType = moveType;
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

    public Player getPlayer() {
        return this.player;
    }

    public MoveType getMoveType() {
        return this.moveType;
    }
}
