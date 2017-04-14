package com.tigerisland.game.board;

import java.util.ArrayList;

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

    public boolean toBeExpanded() { return this.toBeExpanded; }

    public void setToBeExpanded(boolean status) { this.toBeExpanded = status; }


    public static void printPlacedHexes(ArrayList<PlacedHex> list){
        for(int ii = 0; ii < list.size(); ii++){
            Location loc = list.get(ii).getLocation();
            String locString = "<"+Integer.toString(loc.x)+","+Integer.toString(loc.y)+">";
            Hex hex = list.get(ii).getHex();
            String hexTerrain = "T="+hex.getHexTerrain().getTerrainString();
            String hexHeight = "H="+Integer.toString(hex.getHeight());
            String hexPieceType = hex.getPieceType();
            String hexPieceCount = Integer.toString(hex.getPieceCount());
            String hexID = "ID="+hex.getIDFirstChars(8);
            System.out.println(locString + " : " + hexTerrain + " : " + hexHeight + " : " + hexPieceType + '-' + hexPieceCount + " : " + hexID);
        }
    }
}
