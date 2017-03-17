package com.tigerisland;

import java.util.*;

public class Settlement {
    String color;
    ArrayList<PlacedHex> hexesInSettlement;

    public Settlement(PlacedHex hexInSettlement, ArrayList<PlacedHex> allPlacedHexes){
        this.color = hexInSettlement.getHex().getPieceColor();
        findHexesInSettlement(hexInSettlement, allPlacedHexes);
    }

    public boolean containsTotoro(){
        for(PlacedHex hex : hexesInSettlement){
            if(hex.getHex().getPieceType().equals(PieceType.TOTORO)){
                return true;
            }
        }
        return false;
    }

    public int size(){
        return hexesInSettlement.size();
    }

    private void findHexesInSettlement(PlacedHex startHex, ArrayList<PlacedHex> allPlacedHexes){
        Queue<PlacedHex> hexesToBeAnalyzed = new LinkedList<PlacedHex>();
        hexesToBeAnalyzed.add(startHex);
        HashSet<PlacedHex> visitedHexes = new HashSet<PlacedHex>();
        while(!hexesToBeAnalyzed.isEmpty()){
            PlacedHex currentPlacedHex = hexesToBeAnalyzed.remove();
            visitedHexes.add(currentPlacedHex);
            hexesInSettlement.add(currentPlacedHex);
            ArrayList<PlacedHex> adjacentHexesToCurrentHex = findAdjacentHexes(startHex, allPlacedHexes);
            for(PlacedHex hexInAdjacentList : adjacentHexesToCurrentHex) {
                if (ownedBySamePlayer(currentPlacedHex.getHex().getPieceColor(), color) && !visitedHexes.contains(hexInAdjacentList)) {
                    hexesToBeAnalyzed.add(hexInAdjacentList);
                }
            }
        }
    }

    private boolean ownedBySamePlayer(String firstPieceColor, String secondPieceColor){
        return firstPieceColor.equals(secondPieceColor);
    }


    /*I know this is ugly and I'll clean it up. Just wanted to push what I had. - Jack*/
    private ArrayList<PlacedHex> findAdjacentHexes(PlacedHex startHex, ArrayList<PlacedHex> allPlacedHexes){
        ArrayList<PlacedHex> adjacentHexes = new ArrayList<PlacedHex>();
        HashMap<Location, PlacedHex> allPlacedHexesMap = getAllPlacedHexesMap(allPlacedHexes);
        Location startingLocation = startHex.getLocation();
        int startingX = startingLocation.x;
        int startingY = startingLocation.y;
        Location firstAdjacentLocation = new Location(startingX + 1, startingY);
        Location secondAdjacentLocation = new Location(startingX + 1, startingY - 1);
        Location thirdAdjacentLocation = new Location(startingX - 1, startingY + 1);
        Location fourthAdjacentLocation = new Location(startingX -1 , startingY);
        Location fifthAdjacentLocation = new Location(startingX, startingY + 1);
        Location sixthAdjacentLocation = new Location(startingX, startingY - 1);
        if(allPlacedHexesMap.containsKey(firstAdjacentLocation)){
            adjacentHexes.add(allPlacedHexesMap.get(firstAdjacentLocation));
        }
        if(allPlacedHexesMap.containsKey(secondAdjacentLocation)){
            adjacentHexes.add(allPlacedHexesMap.get(secondAdjacentLocation));
        }
        if(allPlacedHexesMap.containsKey(thirdAdjacentLocation)){
            adjacentHexes.add(allPlacedHexesMap.get(thirdAdjacentLocation));
        }
        if(allPlacedHexesMap.containsKey(fourthAdjacentLocation)){
            adjacentHexes.add(allPlacedHexesMap.get(fourthAdjacentLocation));
        }
        if(allPlacedHexesMap.containsKey(fifthAdjacentLocation)){
            adjacentHexes.add(allPlacedHexesMap.get(fifthAdjacentLocation));
        }
        if(allPlacedHexesMap.containsKey(sixthAdjacentLocation)){
            adjacentHexes.add(allPlacedHexesMap.get(sixthAdjacentLocation));
        }
        return adjacentHexes;
    }

    private HashMap<Location, PlacedHex> getAllPlacedHexesMap(ArrayList<PlacedHex> allPlacedHexes){
        HashMap<Location, PlacedHex> allPlacedHexesMap = new HashMap<Location, PlacedHex>();
        for(int i = 0;i<allPlacedHexes.size();i++){
            PlacedHex currentHex = allPlacedHexes.get(i);
            allPlacedHexesMap.put(currentHex.getLocation(), currentHex);
        }
        return allPlacedHexesMap;
    }


}
