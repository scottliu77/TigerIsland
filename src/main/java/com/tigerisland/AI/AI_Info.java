package com.tigerisland.AI;

import com.tigerisland.game.InvalidMoveException;
import com.tigerisland.game.*;

import java.util.ArrayList;

public class AI_Info {
    public static ArrayList<TilePlacement> returnValidTilePlacements(Tile tile, Board board){
        ArrayList<TilePlacement> validTilePlacements = new ArrayList<TilePlacement>();
        int[] boundsArray = getBoundsOfBoard(board);
        int x_lowerBound =  boundsArray[0];
        int x_upperBound = boundsArray[1];
        int y_lowerBound = boundsArray[2];
        int y_upperBound = boundsArray[3];
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
    private static int[] getBoundsOfBoard(Board board){
        int x_lowerBound = Integer.MAX_VALUE;
        int x_upperBound = Integer.MIN_VALUE;
        int y_lowerBound = Integer.MAX_VALUE;
        int y_upperBound = Integer.MIN_VALUE;
        ArrayList<Location> edgeSpaces = board.getEdgeSpaces();
        for(int ii=0; ii<edgeSpaces.size(); ii++){
            x_lowerBound = Math.min(edgeSpaces.get(ii).x, x_lowerBound);
            x_upperBound = Math.max(edgeSpaces.get(ii).x, x_upperBound);
            y_lowerBound = Math.min(edgeSpaces.get(ii).y, y_lowerBound);
            y_upperBound = Math.max(edgeSpaces.get(ii).y, y_upperBound);
        }
        int[] boundsArray = new int[4];
        boundsArray[0] = x_lowerBound;
        boundsArray[1] = x_upperBound;
        boundsArray[2] = y_lowerBound;
        boundsArray[3] = y_upperBound;
        return boundsArray;
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
            ArrayList<Terrain> listOfTerrainsThisSettlementCanExpandIntoIfItHasEnoughPieces = settlement.findTerrainsSettlementCouldExpandTo(board.getPlacedHexes());

            if(listOfTerrainsThisSettlementCanExpandIntoIfItHasEnoughPieces.size() == 0){
                continue;
            }

            ArrayList<Terrain> terrainsThisSettlementHasEnoughPiecesToExpandInto = new ArrayList<Terrain>();
            for(Terrain terrainToExpandInto : listOfTerrainsThisSettlementCanExpandIntoIfItHasEnoughPieces){
                Board tempBoard = new Board(board);
                try{
                    tempBoard.expandVillage(new Player(player), settlement.getLocationsOfHexesInSettlement().get(0), terrainToExpandInto);

                } catch(InvalidMoveException e){
                    continue;
                }
                terrainsThisSettlementHasEnoughPiecesToExpandInto.add(terrainToExpandInto);

            }
            SettlementAndTerrainListPair settlementAndTerrainListPair = new SettlementAndTerrainListPair(settlement, terrainsThisSettlementHasEnoughPiecesToExpandInto);

            validVillageExpansions.add(settlementAndTerrainListPair);


        }
        return validVillageExpansions;
        //Returns a list where every object in the list is a pair including a settlement and the list of terrains it can expand into
    }

    public static ArrayList<Location> returnValidTotoroPlacements(Player player, Board board){
        ArrayList<Location> listOfValidPlacements = new ArrayList<Location>();
        ArrayList<PlacedHex> placedHexes = board.getPlacedHexes();
        for(PlacedHex ph : placedHexes) {
            if(ph.isEmpty() && ph.isNotVolcano()) {
                ArrayList<Settlement> settlements = board.findAdjacentSettlementsToLocation(ph.getLocation());
                for (Settlement ss : settlements) {
                    if (ss.getColor()==player.getPlayerColor() && !ss.containsTotoro() && ss.size()>=Board.SIZE_REQUIRED_FOR_TOTORO) {
                        try{
                            if(player == null){
                                int i= 0;

                            }
                            Board tempBoard = new Board(board);
                            Player tempPlayer = new Player(player);
                            if(tempPlayer == null){
                                int i = 0;
                            }
                            tempBoard.placeTotoro(tempPlayer, ph.getLocation());
                        }
                        catch(InvalidMoveException e){
                            continue;
                        }
                        listOfValidPlacements.add(ph.getLocation());
                            //listOfValidPlacements.add(ph.getLocation());
                            //break;
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

    public static ArrayList<TilePlacement> findTilePlacementsThatEnableTotoroPlacementForSamePlayer(Color color, Tile tile, Board board) {
        ArrayList<TilePlacement> validTilePlacements = returnValidTilePlacements(tile, board);
        ArrayList<TilePlacement> tilePlacementsThatEnableTotoroPlacement = new ArrayList<TilePlacement>();
        for(TilePlacement tilePlacement : validTilePlacements){
            Board tempBoard = new Board(board);
            int initialNumberOfSettlementsToAddTotoro = tempBoard.settlementsThatCouldAcceptTotoroForGivenPlayer(color).size();
            try {
                tempBoard.placeTile(tilePlacement);
            } catch (InvalidMoveException e){
                continue;
            }
            tempBoard.updateSettlements();
            int finalNumberOfSettlementsToAddTotoro = tempBoard.settlementsThatCouldAcceptTotoroForGivenPlayer(color).size();
            if(finalNumberOfSettlementsToAddTotoro > initialNumberOfSettlementsToAddTotoro) {
                tilePlacementsThatEnableTotoroPlacement.add(tilePlacement);
            }
        }
        return tilePlacementsThatEnableTotoroPlacement;
    }

    public static ArrayList<TilePlacement> findTilePlacementsThatCutTotoroOffOfMostOfSettlement(Color color, Tile tile, Board board, int desiredSizeOfRemainingSettlement) {
        ArrayList<TilePlacement> validTilePlacements = returnValidTilePlacements(tile, board);
        int initialNumberOfSettlementsPlayerHasWithoutTotoroOfAtLeastDesiredSize = numberOfSettlementsPlayerHasWithoutTotoroAndOfProperSize(color, board, desiredSizeOfRemainingSettlement);
        ArrayList<TilePlacement> usefulTilePlacements = new ArrayList<TilePlacement>();
        for(TilePlacement tilePlacement : validTilePlacements){
            Board tempBoard = new Board(board);
            try {
                tempBoard.placeTile(tilePlacement);
            } catch(InvalidMoveException e){
                continue;
            }
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



    //returns best expansion at the 0th index in the arraylist (used an arraylist to avoid returning a null value or a made up expansion if there's no real ones we could do
    public static SettlementAndTerrainListPair findExpansionThatGetsRidOfMostVillagers(Player player, Board board){
        ArrayList<SettlementAndTerrainListPair> validVillageExpansions = returnValidVillageExpansions(player, board);
        int numberOfVillagersUsedInMostExpensiveExpansionFound = 0;
        if(validVillageExpansions.size() == 0 || validVillageExpansions.get(0).getTerrainList().size() == 0){
            return null;
        }
        Terrain terrainToExpandInto = null;
        Settlement bestSettlementForRapidExpansion = null;
        for(SettlementAndTerrainListPair settlementAndTerrainListPair : validVillageExpansions){
            for(Terrain terrain : settlementAndTerrainListPair.getTerrainList()) {
                Player tempPlayer = new Player(player);
                Board tempBoard = new Board(board);
                int initialNumberOfVillagersLeft = tempPlayer.getPieceSet().getNumberOfVillagersRemaining();
                try {
                    tempBoard.expandVillage(tempPlayer, settlementAndTerrainListPair.getSettlement().getLocationsOfHexesInSettlement().get(0), terrain);
                } catch(InvalidMoveException e){
                    continue;
                }
                int finalNumberOfVillagersLeft = tempPlayer.getPieceSet().getNumberOfVillagersRemaining();
                int numberOfVillagersUsedInExpansion = initialNumberOfVillagersLeft - finalNumberOfVillagersLeft;
                if(numberOfVillagersUsedInExpansion > numberOfVillagersUsedInMostExpensiveExpansionFound){
                    bestSettlementForRapidExpansion = settlementAndTerrainListPair.getSettlement();
                    numberOfVillagersUsedInMostExpensiveExpansionFound = numberOfVillagersUsedInExpansion;
                    terrainToExpandInto = terrain;
                }
            }
        }

        ArrayList<Terrain> bestTerrainToExpandIntoForThisSettlement = new ArrayList<Terrain>();
        bestTerrainToExpandIntoForThisSettlement.add(terrainToExpandInto);
        SettlementAndTerrainListPair bestPairForExpansion = new SettlementAndTerrainListPair(bestSettlementForRapidExpansion, bestTerrainToExpandIntoForThisSettlement);

        return bestPairForExpansion;
    }

    public static TilePlacement findTilePlacementThatImprovesNextExpansion(Tile tile, Player player, Board board){
        ArrayList<TilePlacement> validTilePlacements = returnValidTilePlacements(tile, board);
        ArrayList<SettlementAndTerrainListPair> bestPossibleTilePlacementExpansionCombos = new ArrayList<SettlementAndTerrainListPair>();
        ArrayList<TilePlacement> tilePlacementsThatWorked = new ArrayList<TilePlacement>();
        for(TilePlacement tilePlacement : validTilePlacements){
            TilePlacement tempTilePlacement = new TilePlacement(tilePlacement);
            Board tempBoard = new Board(board);
            try {
                tempBoard.placeTile(tempTilePlacement);
                tempBoard.updateSettlements();
            } catch(InvalidMoveException e){
                continue;
            }

            tilePlacementsThatWorked.add(tilePlacement);
            bestPossibleTilePlacementExpansionCombos.add(findExpansionThatGetsRidOfMostVillagers(player, tempBoard));
        }
        return bestPossibleTilePlacementExpansionCombo(tilePlacementsThatWorked, bestPossibleTilePlacementExpansionCombos,board, player);

    }

    private static TilePlacement bestPossibleTilePlacementExpansionCombo(ArrayList<TilePlacement> tilePlacements, ArrayList<SettlementAndTerrainListPair> settlementAndTerrainListPairs, Board board, Player player){
        int mostNumberOfVillagersUsed = 0;
        TilePlacement bestTilePlacement = null;
        for(int i = 0; i< tilePlacements.size(); i++){
            Board tempBoard = new Board(board);
            Player tempPlayer = new Player(player);
            try{
                tempBoard.placeTile(new TilePlacement(tilePlacements.get(i)));
                tempBoard.updateSettlements();
                tempBoard.expandVillage(tempPlayer, settlementAndTerrainListPairs.get(i).getSettlement().getLocationsOfHexesInSettlement().get(0), settlementAndTerrainListPairs.get(i).getTerrainList().get(0));
            } catch (InvalidMoveException e){
                continue;
            } catch(NullPointerException e){
                continue;
            }
            int villagersUsed = player.getPieceSet().getNumberOfVillagersRemaining() - tempPlayer.getPieceSet().getNumberOfVillagersRemaining();
            if(villagersUsed > mostNumberOfVillagersUsed){
                mostNumberOfVillagersUsed = villagersUsed;
                bestTilePlacement = tilePlacements.get(i);
            }

        }
        return bestTilePlacement;
    }

    public static TilePlacement findHighestPossibleTilePlacement(Tile tile, Board board, Color opposingColor, Player player){
        ArrayList<TilePlacement> validTilePlacements = returnValidTilePlacements(tile, board);
        int highestHeight = 0;
        TilePlacement highestTilePlacement = null;
        for(TilePlacement tilePlacement : validTilePlacements){
            Location locationOfTilePlacement = tilePlacement.getLocation();
            int heightOfThisTilePlacement;
            if(board.hexExistsAtLocation(locationOfTilePlacement)) {
                heightOfThisTilePlacement = 1 + board.hexAt(locationOfTilePlacement).getHeight();
            }
            else{
                heightOfThisTilePlacement = 1;
            }
            if(heightOfThisTilePlacement > highestHeight && tilePlacementDoesntGiveOpponentATigerPlayground(tilePlacement, board, opposingColor) && tilePlacementDoesntNukeOurOwnSettlement(tilePlacement, board, player)){
                highestHeight = heightOfThisTilePlacement;
                highestTilePlacement = tilePlacement;
            }
        }
        return highestTilePlacement;
    }

    private static boolean tilePlacementDoesntGiveOpponentATigerPlayground(TilePlacement tilePlacement, Board board, Color opposingColor){
        Board tempBoard = new Board(board);
        TilePlacement tempTilePlacement = new TilePlacement(tilePlacement);
        try{
            tempBoard.placeTile(tempTilePlacement);
        } catch (InvalidMoveException e){
            return false;
        }
        if(returnValidTigerPlacements(opposingColor, tempBoard).size() != 0){
            return false;
        }
        return true;
    }

    private static boolean tilePlacementDoesntNukeOurOwnSettlement(TilePlacement tilePlacement, Board board, Player player){
        Board tempBoard = new Board(board);
        int numberOfValidTotoroPlacementsBeforeTilePlacement = returnValidTotoroPlacements(player, tempBoard).size();
        TilePlacement tempTilePlacement = new TilePlacement(tilePlacement);
        try{
            tempBoard.placeTile(tempTilePlacement);
        } catch (InvalidMoveException e){
            return false;
        }
        int numberOfValidTotoroPlacementsAfterTilePlacement = returnValidTotoroPlacements(player, tempBoard).size();
        if(numberOfValidTotoroPlacementsBeforeTilePlacement > numberOfValidTotoroPlacementsAfterTilePlacement){
            return false;
        }

        return true;
    }
}


