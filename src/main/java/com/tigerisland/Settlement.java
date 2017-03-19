package com.tigerisland;

import java.util.*;

public class Settlement {
    Color color;
    ArrayList<PlacedHex> hexesInSettlement = new ArrayList<PlacedHex>();

    public Settlement(PlacedHex hexInSettlement, ArrayList<PlacedHex> allPlacedHexes){
        this.color = hexInSettlement.getHex().getPieceColor();
        findHexesInSettlement(hexInSettlement, allPlacedHexes);
    }

    public boolean containsTotoro(){
        for(PlacedHex hex : hexesInSettlement){
            if(hex.getHex().getPieceType().equals("Totoro")){
                return true;
            }
        }
        return false;
    }

    public ArrayList<PlacedHex> getHexesInSettlement(){
        return hexesInSettlement;
    }

    public int size(){
        return hexesInSettlement.size();
    }

    private void findHexesInSettlement(PlacedHex startHex, ArrayList<PlacedHex> allPlacedHexes){
        Queue<PlacedHex> hexesToBeAnalyzed = new LinkedList<PlacedHex>();
        hexesToBeAnalyzed.add(startHex);
        HashSet<PlacedHex> visitedHexes = new HashSet<PlacedHex>();
        visitedHexes.add(startHex);
        while(!hexesToBeAnalyzed.isEmpty()){
            PlacedHex currentPlacedHex = hexesToBeAnalyzed.remove();
            hexesInSettlement.add(currentPlacedHex);
            ArrayList<PlacedHex> adjacentHexesToCurrentHex = findAdjacentHexesForOneHex(currentPlacedHex, allPlacedHexes);
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


    /*I know this is ugly and I'll clean it up. Just wanted to push what I had. - Jack*/
    private ArrayList<PlacedHex> findAdjacentHexesForOneHex(PlacedHex startHex, ArrayList<PlacedHex> allPlacedHexes){
        ArrayList<PlacedHex> adjacentHexes = new ArrayList<PlacedHex>();
        HashMap<Location, PlacedHex> allPlacedHexesMap = getAllPlacedHexesAsMap(allPlacedHexes);
        Location startingLocation = startHex.getLocation();
        int x = startingLocation.x;
        int y = startingLocation.y;
        adjacentHexes.addAll(findListOfAdjacentHexesBasedOnCoordinates(x,y, allPlacedHexes));
        return adjacentHexes;
    }


    private HashMap<Location, PlacedHex> getAllPlacedHexesAsMap(ArrayList<PlacedHex> allPlacedHexes){
        HashMap<Location, PlacedHex> allPlacedHexesMap = new HashMap<Location, PlacedHex>();
        for(int i = 0;i<allPlacedHexes.size();i++){
            PlacedHex currentHex = allPlacedHexes.get(i);
            allPlacedHexesMap.put(currentHex.getLocation(), currentHex);
        }
        return allPlacedHexesMap;
    }

    private ArrayList<PlacedHex> findListOfAdjacentHexesBasedOnCoordinates(int x, int y, ArrayList<PlacedHex> allPlacedHexes) {
        ArrayList<PlacedHex> adjacentHexes = new ArrayList<PlacedHex>();
        for(PlacedHex hex : allPlacedHexes) {
            if(areCoordinatesAdjacent(x, y, hex.getLocation().x, hex.getLocation().y)){
                adjacentHexes.add(hex);
            }
        }
        return  adjacentHexes;
    }

    private boolean areCoordinatesAdjacent(int x, int y, int comparisonX, int comparisonY){
        if(x == comparisonX - 1 && y == comparisonY + 1){
            return true;
        }
        if(x == comparisonX + 1 && y == comparisonY - 1){
            return true;
        }
        if(x == comparisonX && y == comparisonY + 1){
            return true;
        }
        if(x == comparisonX && y == comparisonY - 1){
            return true;
        }
        if(x == comparisonX + 1 && y == comparisonY){
            return true;
        }
        if(x == comparisonX - 1 && y == comparisonY){
            return true;
        }
        return false;
    }
}