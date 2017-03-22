package com.tigerisland;

public class PlacedHex {
    private Hex hex;
    private Location location;

    public PlacedHex(Hex hex, Location location){
        this.hex = hex;
        this.location = location;
    }

    public PlacedHex(PlacedHex placedHex){
        this.hex = placedHex.hex;//might need clone constructors for these too
        this.location = placedHex.location;
    }

    public Location getLocation() {
        return this.location;
    }

    public Hex getHex(){
        return this.hex;
    }

    public boolean isEmpty(){
        return hex.getPieceType().equals("Empty");
    }


    public boolean isNotVolcano() {
        return !getHex().getHexTerrain().equals(Terrain.VOLCANO);
    }
}
