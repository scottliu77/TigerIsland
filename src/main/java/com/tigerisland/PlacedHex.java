package com.tigerisland;

public class PlacedHex {
    private Hex hex;
    private Location location;

    public PlacedHex(Hex hex, Location location){
        this.hex = hex;
        this.location = location;
    }

    public Location getLocation() {
        return this.location;
    }

    public Hex getHex(){
        return this.hex;
    }

    public String getPieceColor(){
        return hex.getPieceColor();
    }

    public String getPieceType(){
        return hex.getPieceType();
    }

}
