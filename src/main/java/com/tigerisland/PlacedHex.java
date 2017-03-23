package com.tigerisland;

public class PlacedHex {
    private Hex hex;
    private Location location;

    public PlacedHex(Hex hex, Location location){
        this.hex = hex;
        this.location = location;
    }

    public PlacedHex(PlacedHex placedHex){
        this.hex = new Hex(placedHex.getHex());
        this.location = new Location(placedHex.getLocation());
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


}
