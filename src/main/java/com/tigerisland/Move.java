package com.tigerisland;

public class Move {

    private Tile tile = null;
    private Location location = null;
    private Location settlementLocation = null;
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

    // Village Creation and Totoro Placement have overlapping constructors for now
    Move(Player player, Location location, MoveType moveType) {
        this.player = player;
        this.location = location;
        this.moveType = moveType;
    }

    Move(Player player, Location locationToBeExpanded, Location settlementLocation){
        this.player = player;
        this.location = locationToBeExpanded;
        this.settlementLocation = settlementLocation;
        this.moveType = MoveType.VILLAGEEXPANSION;
    }


    public Tile getTile() {
        return this.tile;
    }

    public Location getLocation() {
        return this.location;
    }

    public Location getSettlementLocation() { return this.settlementLocation; }

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
