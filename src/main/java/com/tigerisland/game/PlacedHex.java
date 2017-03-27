package com.tigerisland.game;

public class PlacedHex {

    private Hex hex;
    private Location location;
    private boolean toBeExpanded;

    public PlacedHex(Hex hex, Location location){
        this.hex = hex;
        this.location = location;
        this.toBeExpanded = false;
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

    public int getHeight(){return hex.getHeight();}

    public boolean isNotVolcano(){ return hex.isNotVolcano(); }

    public boolean getExpansionStatus() { return this.toBeExpanded; }

    public void setExpansionStatus(boolean status) { this.toBeExpanded = status; }
}
