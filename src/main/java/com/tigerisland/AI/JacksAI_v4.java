package com.tigerisland.AI;

import com.tigerisland.game.*;
import com.tigerisland.game.board.*;
import com.tigerisland.game.moves.BuildActionType;
import com.tigerisland.game.moves.TilePlacement;
import com.tigerisland.game.pieces.Color;

import java.util.ArrayList;
import java.util.Random;

/*
    Builds off of v2. Does not implement the feature added to v3. Instead, it adds two features. The first is that it
    looks for settlements of size 3 or larger that it could expand in hopes of making settlements big enough to accept
    a totoro. The second change is that it looks for hexes where we could settle and immediately be adjacent to a tiger accepting hex.
 */

public class JacksAI_v4 extends AI {

    private ArrayList<TilePlacement> validTilePlacements;
    private ArrayList<Location> validTotoroPlacements;
    private ArrayList<Location> validTigerPlacements;
    private ArrayList<TilePlacement> tilePlacementsForNukingEnemySettlementsCloseToGettingATotoro;
    private ArrayList<TilePlacement> tilePlacementsForNukingEnemySettlementsCloseToGettingATiger;
    private ArrayList<TilePlacement> tilePlacementsThatSetUpPlayerForTotoroPlacement;
    private ArrayList<TilePlacement> tilePlacementsThatCutTotoroOffOfMostOfSettlement;
    private SettlementAndTerrainListPair expansionOnASettlementOfSizeThreeOrMore;
    private SettlementAndTerrainListPair bestExpansion;
    private Location villageCreationThatSetsUpForTigerPlacement;
    private Board tempBoard;
    private Random rand;


    private ArrayList<Location> plannedSettlementLocations;

    public JacksAI_v4(){
        plannedSettlementLocations = new ArrayList<Location>();
        rand = new Random();
    }

    public void decideOnMove(){
        tilePlacement = null;
        buildActionType = null;
        buildLocation = null;
        expandTerrain = null;
        expansionOnASettlementOfSizeThreeOrMore = null;
        villageCreationThatSetsUpForTigerPlacement = null;

        tempBoard = new Board(turnState.getBoard());
        gatherTilePlacementInfo();
        makeAnyPossibleStrategicTilePlacement();
        gatherBuildActionInfo();
        boolean timeToRapidlyExpand = (!hasATotoro() || !hasATiger()) && bestExpansion != null;
        if(timeToRapidlyExpand){
            if(turnState.getCurrentPlayer().getPieceSet().getNumberOfTigersRemaining() == 1 && canPlaceTiger()){
                placeTiger();
                return;
            }
            rapidlyExpand();
            return;
        }
        pickBuildOption();
    }

    private void pickBuildOption() {
        if(canPlaceTotoro()){
            placeTotoro();
            resetTotoroLine();
        }
        else if(canPlaceTiger()) {
            placeTiger();
        }
        else if(villageCreationThatSetsUpForTigerPlacement != null){
            buildActionType = BuildActionType.VILLAGECREATION;
            buildLocation = villageCreationThatSetsUpForTigerPlacement;
            if((noCurrentLine() || lineIsInterrupted()) && tilePlacement == null){
                startNewLine();
            }
            else if(!lineIsInterrupted() && tilePlacement == null){
                extendLine();
            }

        }
        else if(expansionOnASettlementOfSizeThreeOrMore != null && expansionOnASettlementOfSizeThreeOrMore.getTerrainList().size() > 0){
            buildActionType = BuildActionType.VILLAGEEXPANSION;
            buildLocation = expansionOnASettlementOfSizeThreeOrMore.getSettlement().getLocationsOfHexesInSettlement().get(0);
            expandTerrain = expansionOnASettlementOfSizeThreeOrMore.getTerrainList().get(0);
            if((noCurrentLine() || lineIsInterrupted()) && tilePlacement == null){
                startNewLine();
            }
            else if(!lineIsInterrupted() && tilePlacement == null){
                extendLine();
            }
        }
        else if((noCurrentLine() || lineIsInterrupted()) && tilePlacement == null){
            startNewLine();
        }
        else if(!lineIsInterrupted() && tilePlacement == null){
            extendLine();
        }
        else if(tilePlacement != null){
            for(PlacedHex placedHex : tempBoard.getPlacedHexes()){
                if(placedHex.isNotVolcano() && placedHex.isEmpty() && placedHex.getHeight() == 1){
                    buildActionType = BuildActionType.VILLAGECREATION;
                    buildLocation = placedHex.getLocation();
                    return;
                }
            }
            tempBoard = new Board(turnState.getBoard());
            startNewLine();
        }
        else{
            System.out.println("Shouldn't be an option...");
        }
    }

    private void rapidlyExpand() {

        if(tilePlacement == null){
            TilePlacement bestTilePlacementForExpansion = AI_Info.findTilePlacementThatImprovesNextExpansion(turnState.getCurrentTile(), turnState.getCurrentPlayer(), tempBoard);
            if(bestTilePlacementForExpansion != null){
                tilePlacement = bestTilePlacementForExpansion;
            }
            else {
                int randInt = rand.nextInt(validTilePlacements.size());
                tilePlacement = validTilePlacements.get(randInt);
            }
        }
        if(tilePlacement == null){
            tilePlacement = validTilePlacements.get(0);
        }
        try {
            tempBoard.placeTile(tilePlacement);
            tempBoard.updateSettlements();
        } catch (InvalidMoveException e) {
            //tempBoard = new Board(turnState.getBoard());
        }
        //TextGUI.printMap(turnState.getBoard());
        bestExpansion = AI_Info.findExpansionThatGetsRidOfMostVillagers(turnState.getCurrentPlayer(), tempBoard);

        buildLocation = bestExpansion.getSettlement().getLocationsOfHexesInSettlement().get(0);
        buildActionType = BuildActionType.VILLAGEEXPANSION;
        expandTerrain = bestExpansion.getTerrainList().get(0);

        return;
    }

    private void makeAnyPossibleStrategicTilePlacement() {
        if(tilePlacementsThatSetUpPlayerForTotoroPlacement.size() > 0 && hasATotoro()) {
            int randInt = rand.nextInt(tilePlacementsThatSetUpPlayerForTotoroPlacement.size());
            tilePlacement = tilePlacementsThatSetUpPlayerForTotoroPlacement.get(randInt);

        }
        else if(tilePlacementsForNukingEnemySettlementsCloseToGettingATotoro.size() > 0 && hasATotoro()) {
            int randInt = rand.nextInt(tilePlacementsForNukingEnemySettlementsCloseToGettingATotoro.size());
            tilePlacement = tilePlacementsForNukingEnemySettlementsCloseToGettingATotoro.get(randInt);

        }
        else if(tilePlacementsForNukingEnemySettlementsCloseToGettingATiger.size() > 0 && hasATotoro()) {
            int randInt = rand.nextInt(tilePlacementsForNukingEnemySettlementsCloseToGettingATiger.size());
            tilePlacement = tilePlacementsForNukingEnemySettlementsCloseToGettingATiger.get(randInt);

        }
        else if(tilePlacementsThatCutTotoroOffOfMostOfSettlement.size() > 0 && hasATotoro()) {
            int randInt = rand.nextInt(tilePlacementsThatCutTotoroOffOfMostOfSettlement.size());
            tilePlacement = tilePlacementsThatCutTotoroOffOfMostOfSettlement.get(randInt);

        }

        if(tilePlacement != null) {
            try {
                tempBoard.placeTile(tilePlacement);
                tempBoard.updateSettlements();
            } catch (InvalidMoveException e) {
                tempBoard = new Board(turnState.getBoard());
            }
        }
    }

    private void gatherTilePlacementInfo() {
        this.validTilePlacements = AI_Info.returnValidTilePlacements(turnState.getCurrentTile(), tempBoard);
        this.tilePlacementsForNukingEnemySettlementsCloseToGettingATotoro = AI_Info.findNukableLocationsToStopOpposingPlayerFromMakingTotoroPlacement(Color.BLACK, tempBoard, turnState.getCurrentTile());
        this.tilePlacementsForNukingEnemySettlementsCloseToGettingATiger = AI_Info.findNukableLocationsToStopOpposingPlayerFromMakingTigerPlacement(Color.BLACK, tempBoard, turnState.getCurrentTile());
        this.tilePlacementsThatSetUpPlayerForTotoroPlacement = AI_Info.findTilePlacementsThatEnableTotoroPlacementForSamePlayer(turnState.getCurrentPlayer().getPlayerColor(), turnState.getCurrentTile(), tempBoard);
        this.tilePlacementsThatCutTotoroOffOfMostOfSettlement = AI_Info.findTilePlacementsThatCutTotoroOffOfMostOfSettlement(turnState.getCurrentPlayer().getPlayerColor(), turnState.getCurrentTile(), tempBoard, 3);
    }

    private void gatherBuildActionInfo() {
        this.villageCreationThatSetsUpForTigerPlacement = AI_Info.locationToSettleBorderingLevelThreeHex(tempBoard, turnState.getCurrentPlayer());
        this.validTotoroPlacements = AI_Info.returnValidTotoroPlacements(turnState.getCurrentPlayer(), tempBoard);
        this.validTigerPlacements = AI_Info.returnValidTigerPlacements(turnState.getCurrentPlayer().getPlayerColor(), tempBoard);
        this.expansionOnASettlementOfSizeThreeOrMore = AI_Info.findSettlementsWeCouldExpandToBecomeTotoroAccepting(tempBoard, turnState.getCurrentPlayer());
        this.bestExpansion = AI_Info.findExpansionThatGetsRidOfMostVillagers(turnState.getCurrentPlayer(), tempBoard);
    }


    private boolean canPlaceTotoro(){
        return this.validTotoroPlacements.size()>0 && hasATotoro();
    }

    private boolean hasATotoro(){
        return turnState.getCurrentPlayer().getPieceSet().getNumberOfTotoroRemaining()>0;
    }

    private void placeTotoro(){
        buildActionType = BuildActionType.TOTOROPLACEMENT;
        int randInt = rand.nextInt(validTotoroPlacements.size());
        buildLocation = validTotoroPlacements.get(randInt);
        if(tilePlacement == null) {
            tilePlacement = validTilePlacements.get(0);
        }
    }

    private void resetTotoroLine(){
        this.plannedSettlementLocations = new ArrayList<Location>();
    }

    private boolean canPlaceTiger(){
        return this.validTigerPlacements.size() > 0 && hasATiger();
    }

    private boolean hasATiger(){
        return turnState.getCurrentPlayer().getPieceSet().getNumberOfTigersRemaining()>0;
    }

    private void placeTiger(){
        buildActionType = BuildActionType.TIGERPLACEMENT;
        int randInt = rand.nextInt(validTigerPlacements.size());
        buildLocation = validTigerPlacements.get(randInt);
        if(tilePlacement == null) {
            tilePlacement = validTilePlacements.get(0);
        }
    }

    private boolean noCurrentLine(){
        return plannedSettlementLocations.size()==0;
    }

    private void startNewLine(){
        plannedSettlementLocations = new ArrayList<Location>();

        TilePlacement startTilePlacement = chooseStartTilePlacement();
        int xStart = startTilePlacement.getLocation().x + 1;
        int yStart = startTilePlacement.getLocation().y - 1;

        plannedSettlementLocations.add(new Location(xStart,yStart));
        plannedSettlementLocations.add(new Location(xStart,yStart-2));
        plannedSettlementLocations.add(new Location(xStart,yStart-4));
        plannedSettlementLocations.add(new Location(xStart,yStart-1));
        plannedSettlementLocations.add(new Location(xStart,yStart-3));

        tilePlacement = startTilePlacement;
        buildActionType = BuildActionType.VILLAGECREATION;
        buildLocation = new Location(plannedSettlementLocations.remove(0));
    }

    private TilePlacement chooseStartTilePlacement(){
        return validTilePlacements.get(validTilePlacements.size()-1);
    }

    private void extendLine(){
        Location nextLocation = plannedSettlementLocations.remove(0);
        this.buildActionType = BuildActionType.VILLAGECREATION;
        this.buildLocation = nextLocation;
        findNextTilePlacement(nextLocation);
    }

    private void findNextTilePlacement(Location location) {
        if(turnState.getBoard().hexExistsAtLocation(location)){
            this.tilePlacement = validTilePlacements.get(0);
        }
        else{
            this.tilePlacement = placeTileToExtendLine(location);
        }
    }

    private TilePlacement placeTileToExtendLine(Location nextLocation){
        Board currentBoard = turnState.getBoard();
        Tile currentTile = turnState.getCurrentTile();

        if(currentBoard.isALegalTilePlacment(Location.add(nextLocation,new Location(-1,0)),300))
            return new TilePlacement(currentTile, Location.add(nextLocation,new Location(-1,0)), 300);
        else if(currentBoard.isALegalTilePlacment(Location.add(nextLocation,new Location(1,-1)),120))
            return new TilePlacement(currentTile, Location.add(nextLocation,new Location(1,-1)), 120);
        else if(currentBoard.isALegalTilePlacment(Location.add(nextLocation,new Location(-1,1)),240))
            return new TilePlacement(currentTile, Location.add(nextLocation,new Location(-1,1)), 240);
        else if(currentBoard.isALegalTilePlacment(Location.add(nextLocation,new Location(1,0)),180))
            return new TilePlacement(currentTile, Location.add(nextLocation,new Location(1,0)), 180);
        else if(currentBoard.isALegalTilePlacment(Location.add(nextLocation,new Location(-1,1)),300))
            return new TilePlacement(currentTile, Location.add(nextLocation,new Location(-1,1)), 300);
        else if(currentBoard.isALegalTilePlacment(Location.add(nextLocation,new Location(1,0)),120))
            return new TilePlacement(currentTile, Location.add(nextLocation,new Location(1,0)), 120);
        else
            startNewLine();
        return tilePlacement;
    }

    private boolean lineIsInterrupted(){
        for(Location loc : plannedSettlementLocations){
            Hex hex;
            if(turnState.getBoard().hexExistsAtLocation(loc)) {
                hex = turnState.getBoard().hexAt(loc);
                if (!hex.isNotVolcano() || !hex.isEmpty() || hex.getHeight() != 1)
                    return true;
            }
        }
        return false;
    }
}

