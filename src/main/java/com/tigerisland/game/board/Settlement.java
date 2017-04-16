package com.tigerisland.game.board;

import com.tigerisland.game.pieces.Color;

import java.util.*;

public class Settlement {

    public final Color color;
    ArrayList<PlacedHex> hexesInSettlement = new ArrayList<PlacedHex>();

    public Settlement(PlacedHex hexInSettlement, ArrayList<PlacedHex> allPlacedHexes){
        this.color = hexInSettlement.getHex().getPieceColor();
        findHexesInSettlement(hexInSettlement, allPlacedHexes);
    }

    public Settlement(Settlement settlement) {
        this.color = settlement.color;
        this.hexesInSettlement = new ArrayList<PlacedHex>();
        for(PlacedHex placedHex : settlement.hexesInSettlement) {
            this.hexesInSettlement.add(new PlacedHex(placedHex));
        }
    }

    public boolean equals(Settlement comparisonSettlement){
        if(comparisonSettlement.size() == hexesInSettlement.size() && hexesInSettlement.contains(comparisonSettlement.getHexesInSettlement().get(0))){
            return true;
        }
        return false;
    }

    public int size(){
        return hexesInSettlement.size();
    }

    public boolean containsShaman() {
        for(PlacedHex hex: hexesInSettlement) {
            if(hex.getHex().getPieceType().equals("Shaman")) {
                return true;
            }
        }
        return false;
    }

    public boolean containsTotoro(){
        for(PlacedHex hex : hexesInSettlement){
            if(hex.getHex().getPieceType().equals("Totoro")){
                return true;
            }
        }
        return false;
    }

    public boolean containsTiger(){
        for(PlacedHex hex : hexesInSettlement){
            if(hex.getHex().getPieceType().equals("Tiger")){
                return true;
            }
        }
        return false;
    }

    public boolean isExpandable(ArrayList<PlacedHex> allPlacedHexes){
        for(PlacedHex hexInSettlement : hexesInSettlement){
            ArrayList<PlacedHex> adjacentHexes = findAdjacentHexesFromPlacedHex(hexInSettlement, allPlacedHexes);
            for(PlacedHex adjacentHex : adjacentHexes){
                if(adjacentHex.isEmpty() && adjacentHex.getHex().isNotVolcano()){
                    return true;
                }
            }
        }
        return false;
    }

    private void findHexesInSettlement(PlacedHex startHex, ArrayList<PlacedHex> allPlacedHexes){
        Queue<PlacedHex> hexesToBeAnalyzed = new LinkedList<PlacedHex>();
        hexesToBeAnalyzed.add(startHex);
        HashSet<PlacedHex> visitedHexes = new HashSet<PlacedHex>();
        visitedHexes.add(startHex);
        while(!hexesToBeAnalyzed.isEmpty()){
            PlacedHex currentPlacedHex = hexesToBeAnalyzed.remove();
            hexesInSettlement.add(currentPlacedHex);
            ArrayList<PlacedHex> adjacentHexesToCurrentHex = findAdjacentHexesFromPlacedHex(currentPlacedHex, allPlacedHexes);
            for(PlacedHex hexInAdjacentList : adjacentHexesToCurrentHex) {
                try {
                    if (ownedBySamePlayer(hexInAdjacentList.getHex().getPieceColor(), color) && !visitedHexes.contains(hexInAdjacentList)) {
                        hexesToBeAnalyzed.add(hexInAdjacentList);
                        visitedHexes.add(hexInAdjacentList);
                    }
                }catch(NullPointerException e){
                    continue;
                }
            }
        }
    }

    public ArrayList<Terrain> findTerrainsSettlementCouldExpandTo(ArrayList<PlacedHex> allPlacedHexes){

        HashSet<PlacedHex> hexesInSettlementSet = new HashSet<PlacedHex>();
        hexesInSettlementSet.addAll(hexesInSettlement);
        ArrayList<Terrain> terrainsToExpandInto = new ArrayList<Terrain>();
        for(PlacedHex hexInSettlement : hexesInSettlement){
            ArrayList<PlacedHex> adjacentHexes = findAdjacentHexesFromPlacedHex(hexInSettlement, allPlacedHexes);
            for(PlacedHex adjacentHexToSettlement : adjacentHexes){
                if(!adjacentHexToSettlement.isNotVolcano() || !adjacentHexToSettlement.isEmpty()){
                    continue;
                }
                if(!hexesInSettlementSet.contains(adjacentHexToSettlement) && !terrainsToExpandInto.contains(adjacentHexToSettlement.getHex().getHexTerrain())){
                    terrainsToExpandInto.add(adjacentHexToSettlement.getHex().getHexTerrain());
                }
            }
        }
        return terrainsToExpandInto;
    }

    private boolean ownedBySamePlayer(Color firstPieceColor, Color secondPieceColor){
        return firstPieceColor.equals(secondPieceColor);
    }

    protected ArrayList<PlacedHex> findAdjacentHexesFromPlacedHex(PlacedHex placedHex, ArrayList<PlacedHex> allPlacedHexes) {
        Location location = placedHex.getLocation();
        ArrayList<PlacedHex> adjacentHexes = new ArrayList<PlacedHex>();
        for(PlacedHex hex : allPlacedHexes) {
            if(location.isAdjacentTo(hex.getLocation())){
                adjacentHexes.add(hex);
            }
        }
        return  adjacentHexes;
    }

    public boolean isConfinedUnderTile(Location centerLoc, int rotation){
        int numberOfHexesUnderTile = 0;
        Location loc1 = Location.rotateHexLeft(centerLoc, rotation);
        Location loc2 = Location.rotateHexLeft(centerLoc, rotation + 60);
        for(int i = 0; i < hexesInSettlement.size(); i++){
            PlacedHex currentPlacedHex = hexesInSettlement.get(i);
            Location loc = currentPlacedHex.getLocation();
            if(loc.equalTo(centerLoc) || loc.equalTo(loc1) || loc.equalTo(loc2))
                numberOfHexesUnderTile += 1;
        }
        return hexesInSettlement.size()==numberOfHexesUnderTile;
    }

    public Color getColor() {
        return color;
    }

    public ArrayList<PlacedHex> getHexesInSettlement(){
        return hexesInSettlement;
    }

    public ArrayList<Location> getLocationsOfHexesInSettlement() {
        ArrayList<Location> locationsOfHexesInSettlement = new ArrayList<Location>();
        for(PlacedHex hexInSettlement : hexesInSettlement){
            locationsOfHexesInSettlement.add(hexInSettlement.getLocation());
        }
        return locationsOfHexesInSettlement;
    }

}