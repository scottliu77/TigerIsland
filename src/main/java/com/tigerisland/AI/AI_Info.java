package com.tigerisland.AI;

import com.tigerisland.InvalidMoveException;
import com.tigerisland.game.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AI_Info {
    private static int x_lowerBound;
    private static int x_upperBound;
    private static int y_lowerBound;
    private static int y_upperBound;

    public synchronized static ArrayList<TilePlacement> returnValidTilePlacements(Tile tile, Board board){
        ArrayList<TilePlacement> validTilePlacements = new ArrayList<TilePlacement>();
        getBoundsOfBoard(board);
        for(int xx=x_lowerBound-1; xx<=x_upperBound+1; xx++){
            Location loc;
            int rot;
            for(int yy=y_lowerBound-1; yy<=y_upperBound+1; yy++) {
                //2-hexes on bottom/1-hex on top - Three different orientations
                loc = new Location(xx, yy);
                rot = 0;
                if (board.isALegalTilePlacment(loc, rot)){
                    validTilePlacements.add(new TilePlacement(tile, loc, rot));
                }

                loc = new Location(xx+1, yy);
                rot = 120;
                if (board.isALegalTilePlacment(loc, rot)){
                    validTilePlacements.add(new TilePlacement(tile, loc, rot));
                }

                loc = new Location(xx, yy+1);
                rot = 240;
                if (board.isALegalTilePlacment(loc, rot)){
                    validTilePlacements.add(new TilePlacement(tile, loc, rot));
                }

                //2-hexes on top/1-hex on bottom - Three different orientations
                loc = new Location(xx, yy);
                rot = 60;
                if (board.isALegalTilePlacment(loc, rot)){
                    validTilePlacements.add(new TilePlacement(tile, loc, rot));
                }

                loc = new Location(xx, yy+1);
                rot = 180;
                if (board.isALegalTilePlacment(loc, rot)){
                    validTilePlacements.add(new TilePlacement(tile, loc, rot));
                }

                loc = new Location(xx-1, yy+1);
                rot = 300;
                if (board.isALegalTilePlacment(loc, rot)){
                    validTilePlacements.add(new TilePlacement(tile, loc, rot));
                }
            }
        }
        return validTilePlacements;
    }
    private static void getBoundsOfBoard(Board board){
        x_lowerBound = Integer.MAX_VALUE;
        x_upperBound = Integer.MIN_VALUE;
        y_lowerBound = Integer.MAX_VALUE;
        y_upperBound = Integer.MIN_VALUE;
        ArrayList<Location> edgeSpaces = board.getEdgeSpaces();
        for(int ii=0; ii<edgeSpaces.size(); ii++){
            x_lowerBound = Math.min(edgeSpaces.get(ii).x, x_lowerBound);
            x_upperBound = Math.max(edgeSpaces.get(ii).x, x_upperBound);
            y_lowerBound = Math.min(edgeSpaces.get(ii).y, y_lowerBound);
            y_upperBound = Math.max(edgeSpaces.get(ii).y, y_upperBound);
        }
    }

    public static ArrayList<Location> returnValidVillagePlacements(Board board){
        ArrayList<Location> listOfValidPlacements = new ArrayList<Location>();
        ArrayList<PlacedHex> placedHexes = board.getPlacedHexes();
        for(int ii=0; ii<placedHexes.size(); ii++){
            PlacedHex currentHex = placedHexes.get(ii);
            if(currentHex.isEmpty() && currentHex.getHeight()==1 && currentHex.isNotVolcano())
                listOfValidPlacements.add(currentHex.getLocation());
        }
        return listOfValidPlacements;
    }

    public static ArrayList<SettlementAndTerrainListPair> returnValidVillageExpansions(Player player, Board board){
        ArrayList<SettlementAndTerrainListPair> validVillageExpansions = new ArrayList<SettlementAndTerrainListPair>();
        for(Settlement settlement : board.getSettlements()){
            if(!settlement.getColor().equals(player.getPlayerColor())){
                continue;
            }
            ArrayList<Terrain> listOfTerrainsThisSettlementCanExpandInto = settlement.findTerrainsSettlementCouldExpandTo(board.getPlacedHexes());

            if(listOfTerrainsThisSettlementCanExpandInto.size() == 0){
                continue;
            }
            for(Terrain terrainToExpandInto : listOfTerrainsThisSettlementCanExpandInto){
                Board tempBoard = new Board(board);
                try{
                    tempBoard.expandVillage(player, settlement.getLocationsOfHexesInSettlement().get(0), terrainToExpandInto);
                    SettlementAndTerrainListPair settlementAndTerrainListPair = new SettlementAndTerrainListPair(settlement, listOfTerrainsThisSettlementCanExpandInto);
                    validVillageExpansions.add(settlementAndTerrainListPair);
                } catch(InvalidMoveException e){
                    continue;
                }
            }


        }
        return validVillageExpansions;
        //Returns a list where every object in the list is a pair including a settlement and the list of terrains it can expand into
    }

    public static ArrayList<Location> returnValidTotoroPlacements(Color color, Board board){
        ArrayList<Location> listOfValidPlacements = new ArrayList<Location>();
        ArrayList<PlacedHex> placedHexes = board.getPlacedHexes();
        for(PlacedHex ph : placedHexes) {
            if(ph.isEmpty() && ph.isNotVolcano()) {
                ArrayList<Settlement> settlements = board.findAdjacentSettlementsToLocation(ph.getLocation());
                for (Settlement ss : settlements) {
                    if (ss.getColor()==color && !ss.containsTotoro() && ss.size()>=Board.SIZE_REQUIRED_FOR_TOTORO) {
                        listOfValidPlacements.add(ph.getLocation());
                        break;
                    }
                }
            }
        }
        return listOfValidPlacements;
    }

    public static ArrayList<Location> returnValidTigerPlacements(Color color, Board board){
        ArrayList<Location> listOfValidPlacements = new ArrayList<Location>();
        ArrayList<PlacedHex> placedHexes = board.getPlacedHexes();
        for(PlacedHex ph : placedHexes) {
            if(ph.isEmpty() && ph.getHeight()>=Board.HEIGHT_REQUIRED_FOR_TIGER && ph.isNotVolcano()) {
                ArrayList<Settlement> settlements = board.findAdjacentSettlementsToLocation(ph.getLocation());
                for (Settlement ss : settlements) {
                    if (ss.getColor()==color && !ss.containsTiger()) {
                        listOfValidPlacements.add(ph.getLocation());
                        break;
                    }
                }
            }
        }
        return listOfValidPlacements;
    }

    //ToDo: Make a function meant to just find nukable locations. Then integrate that into this function
    public static ArrayList<TilePlacement> findNukableLocationsToStopOpposingPlayerFromMakingTotoroPlacement(Color opposingPlayerColor, Board board, Tile tile){
        ArrayList<Settlement> opposingSettlementsThatCouldAcceptTotoro = board.settlementsThatCouldAcceptTotoroForGivenPlayer(opposingPlayerColor);
        ArrayList<TilePlacement> validTilePlacements = returnValidTilePlacements(tile, board);
        ArrayList<TilePlacement> tilePlacementsThatCouldPreventTotoroPlacement = new ArrayList<TilePlacement>();

        for(TilePlacement tilePlacement : validTilePlacements){
            ArrayList<Settlement> settlementsAdjacentToLocation = board.findAdjacentSettlementsToLocation(tilePlacement.getLocation());
            for(Settlement settlementAdjacentToLocation : settlementsAdjacentToLocation){
                boolean settlementFound = false;
                for(Settlement settlementThatCouldAcceptTotoro : opposingSettlementsThatCouldAcceptTotoro){
                    if(settlementAdjacentToLocation.equals(settlementThatCouldAcceptTotoro)){
                        settlementFound = true;
                    }
                }
                if(settlementFound && tilePlacementMakesTotoroPlacementInvalid(tilePlacement, opposingPlayerColor, board)){
                    tilePlacementsThatCouldPreventTotoroPlacement.add(tilePlacement);
                }
            }
        }

        return tilePlacementsThatCouldPreventTotoroPlacement;
    }

    private static boolean tilePlacementMakesTotoroPlacementInvalid(TilePlacement tilePlacement, Color color, Board board){
        Board tempBoard = new Board(board);
        int originalNumberOfSettlementsThatCouldAcceptTotoro = tempBoard.settlementsThatCouldAcceptTotoroForGivenPlayer(color).size();
        try {
            tempBoard.placeTile(tilePlacement.getTile(), tilePlacement.getLocation(), tilePlacement.getRotation());
        }catch(InvalidMoveException e){
            return false;
        }
        tempBoard.updateSettlements();
        int finalNumberOfSettlementsThatCouldAcceptTotoro = tempBoard.settlementsThatCouldAcceptTotoroForGivenPlayer(color).size();
        return finalNumberOfSettlementsThatCouldAcceptTotoro == originalNumberOfSettlementsThatCouldAcceptTotoro - 1;
    }

    public static ArrayList<PlacedHex> findEmptyHabitableLevelThreePlacedHexes(Board board) {
        ArrayList<PlacedHex> placedHexes = board.getPlacedHexes();
        ArrayList<PlacedHex> habitableLevelThreeHexes = new ArrayList<PlacedHex>();
        for(int i = 0; i < placedHexes.size(); i++) {
            PlacedHex currentPlacedHex = placedHexes.get(i);
            if(currentPlacedHex.isEmpty() && currentPlacedHex.isNotVolcano()) {
                Hex currentHex = currentPlacedHex.getHex();
                if(currentHex.getHeight() >= board.HEIGHT_REQUIRED_FOR_TIGER) {
                    habitableLevelThreeHexes.add(currentPlacedHex);
                }
            }
        }
        return habitableLevelThreeHexes;
    }

    public static ArrayList<TilePlacement> findNukableLocationsToStopOpposingPlayerFromMakingTigerPlacement(Color opposingPlayerColor, Board board, Tile tile){
        ArrayList<Settlement> opposingSettlementsThatCouldAcceptTiger = board.settlementsThatCouldAcceptTigerForGivenPlayer(opposingPlayerColor);
        ArrayList<TilePlacement> validTilePlacements = returnValidTilePlacements(tile, board);
        ArrayList<TilePlacement> tilePlacementsThatCouldPreventTigerPlacement = new ArrayList<TilePlacement>();

        for(TilePlacement tilePlacement : validTilePlacements){
            ArrayList<Settlement> settlementsAdjacentToLocation = board.findAdjacentSettlementsToLocation(tilePlacement.getLocation());
            for(Settlement settlementAdjacentToLocation : settlementsAdjacentToLocation){
                boolean settlementFound = false;

                for(Settlement settlementThatCouldAcceptTiger : opposingSettlementsThatCouldAcceptTiger){
                    if(settlementAdjacentToLocation.equals(settlementThatCouldAcceptTiger)){
                        settlementFound = true;
                    }
                }
                if(settlementFound && tilePlacementMakesTigerPlacementInvalid(tilePlacement, opposingPlayerColor, board)){
                    tilePlacementsThatCouldPreventTigerPlacement.add(tilePlacement);
                }
            }
        }

        return tilePlacementsThatCouldPreventTigerPlacement;
    }

    private static boolean tilePlacementMakesTigerPlacementInvalid(TilePlacement tilePlacement, Color color, Board board){
        Board tempBoard = new Board(board);
        int originalNumberOfSettlementsThatCouldAcceptTiger = tempBoard.settlementsThatCouldAcceptTigerForGivenPlayer(color).size();
        try {
            tempBoard.placeTile(tilePlacement.getTile(), tilePlacement.getLocation(), tilePlacement.getRotation());
        }catch(InvalidMoveException e){
            return false;
        }
        tempBoard.updateSettlements();
        int finalNumberOfSettlementsThatCouldAcceptTiger = tempBoard.settlementsThatCouldAcceptTigerForGivenPlayer(color).size();
        return finalNumberOfSettlementsThatCouldAcceptTiger == originalNumberOfSettlementsThatCouldAcceptTiger - 1;
    }

    public static ArrayList<TilePlacement> findTilePlacementsThatEnableTotoroPlacementForSamePlayer(Color color, Tile tile, Board board) throws InvalidMoveException{
        ArrayList<TilePlacement> validTilePlacements = returnValidTilePlacements(tile, board);
        ArrayList<TilePlacement> tilePlacementsThatEnableTotoroPlacement = new ArrayList<TilePlacement>();
        for(TilePlacement tilePlacement : validTilePlacements){
            Board tempBoard = new Board(board);
            int initialNumberOfSettlementsToAddTotoro = tempBoard.settlementsThatCouldAcceptTotoroForGivenPlayer(color).size();
            tempBoard.placeTile(tilePlacement);
            tempBoard.updateSettlements();
            int finalNumberOfSettlementsToAddTotoro = tempBoard.settlementsThatCouldAcceptTotoroForGivenPlayer(color).size();
            if(finalNumberOfSettlementsToAddTotoro > initialNumberOfSettlementsToAddTotoro) {
                tilePlacementsThatEnableTotoroPlacement.add(tilePlacement);
            }
        }
        return tilePlacementsThatEnableTotoroPlacement;
    }

    public static ArrayList<TilePlacement> findTilePlacementsThatCutTotoroOffOfMostOfSettlement(Color color, Tile tile, Board board, int desiredSizeOfRemainingSettlement) throws InvalidMoveException{
        ArrayList<TilePlacement> validTilePlacements = returnValidTilePlacements(tile, board);
        int initialNumberOfSettlementsPlayerHasWithoutTotoroOfAtLeastDesiredSize = numberOfSettlementsPlayerHasWithoutTotoroAndOfProperSize(color, board, desiredSizeOfRemainingSettlement);
        ArrayList<TilePlacement> usefulTilePlacements = new ArrayList<TilePlacement>();
        for(TilePlacement tilePlacement : validTilePlacements){
            Board tempBoard = new Board(board);
            tempBoard.placeTile(tilePlacement);
            tempBoard.updateSettlements();
            int finalNumberOfSettlementsPlayerHasWithoutTotoroOfDesiredSize = numberOfSettlementsPlayerHasWithoutTotoroAndOfProperSize(color, tempBoard, desiredSizeOfRemainingSettlement);
            if(finalNumberOfSettlementsPlayerHasWithoutTotoroOfDesiredSize > initialNumberOfSettlementsPlayerHasWithoutTotoroOfAtLeastDesiredSize){
                    usefulTilePlacements.add(tilePlacement);
            }
        }
        return usefulTilePlacements;
    }

    private static int numberOfSettlementsPlayerHasWithoutTotoroAndOfProperSize(Color color, Board board, int desiredSize) {
        int numberOfSettlementsPlayerHasWithoutTotoroOfSizeThreeOrMore = 0;
        for(Settlement settlement : board.getSettlements()){
            if(settlement.getColor() == color && !settlement.containsTotoro() && settlement.size() >= desiredSize){
                numberOfSettlementsPlayerHasWithoutTotoroOfSizeThreeOrMore++;
            }
        }
        return numberOfSettlementsPlayerHasWithoutTotoroOfSizeThreeOrMore;
    }


}
