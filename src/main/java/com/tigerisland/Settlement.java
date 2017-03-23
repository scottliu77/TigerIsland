package com.tigerisland;

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

    public int size(){
        return hexesInSettlement.size();
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

    public ArrayList<PlacedHex> getHexesInSettlement(){
        return hexesInSettlement;
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

    private boolean ownedBySamePlayer(Color firstPieceColor, Color secondPieceColor){
        return firstPieceColor.equals(secondPieceColor);
    }

    private HashMap<Location, PlacedHex> getAllPlacedHexesAsMap(ArrayList<PlacedHex> allPlacedHexes){
        HashMap<Location, PlacedHex> allPlacedHexesMap = new HashMap<Location, PlacedHex>();
        for(int i = 0;i<allPlacedHexes.size();i++){
            PlacedHex currentHex = allPlacedHexes.get(i);
            allPlacedHexesMap.put(currentHex.getLocation(), currentHex);
        }
        return allPlacedHexesMap;
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

}