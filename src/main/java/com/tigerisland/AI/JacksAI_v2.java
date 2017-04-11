package com.tigerisland.AI;

import com.tigerisland.game.*;

import java.util.ArrayList;
import java.util.Random;



public class JacksAI_v2 extends AI {

    private ArrayList<TilePlacement> validTilePlacements;
    private ArrayList<Location> validTotoroPlacements;
    private ArrayList<Location> validTigerPlacements;
    private ArrayList<TilePlacement> tilePlacementsForNukingEnemySettlementsCloseToGettingATotoro;
    private ArrayList<TilePlacement> tilePlacementsForNukingEnemySettlementsCloseToGettingATiger;
    private ArrayList<TilePlacement> tilePlacementsThatSetUpPlayerForTotoroPlacement;
    private ArrayList<TilePlacement> tilePlacementsThatCutTotoroOffOfMostOfSettlement;
    private Board tempBoard;
    private Random rand;

    private Location myNextExpansionLocation;

    private ArrayList<Location> plannedSettlementLocations;

    public JacksAI_v2(){
        plannedSettlementLocations = new ArrayList<Location>();
        rand = new Random();
    }

    public void decideOnMove(){
        tilePlacement = null;
        boolean tilePlacementDecided = false;
        tempBoard = new Board(turnState.getBoard());

        gatherTilePlacementInfo();

        if(tilePlacementsThatSetUpPlayerForTotoroPlacement.size() > 0) {
            int randInt = rand.nextInt(tilePlacementsThatSetUpPlayerForTotoroPlacement.size());
            tilePlacement = tilePlacementsThatSetUpPlayerForTotoroPlacement.get(randInt);
            tilePlacementDecided = true;
        }
        else if(tilePlacementsForNukingEnemySettlementsCloseToGettingATotoro.size() > 0) {
            int randInt = rand.nextInt(tilePlacementsForNukingEnemySettlementsCloseToGettingATotoro.size());
            tilePlacement = tilePlacementsForNukingEnemySettlementsCloseToGettingATotoro.get(randInt);
            tilePlacementDecided = true;
        }
        else if(tilePlacementsForNukingEnemySettlementsCloseToGettingATiger.size() > 0) {
            int randInt = rand.nextInt(tilePlacementsForNukingEnemySettlementsCloseToGettingATiger.size());
            tilePlacement = tilePlacementsForNukingEnemySettlementsCloseToGettingATiger.get(randInt);
            tilePlacementDecided = true;
        }
        else if(tilePlacementsThatCutTotoroOffOfMostOfSettlement.size() > 0) {
            int randInt = rand.nextInt(tilePlacementsThatCutTotoroOffOfMostOfSettlement.size());
            tilePlacement = tilePlacementsThatCutTotoroOffOfMostOfSettlement.get(randInt);
            tilePlacementDecided = true;
        }
        if(tilePlacementDecided) {
            try {
                tempBoard.placeTile(tilePlacement);
                tempBoard.updateSettlements();
            } catch (InvalidMoveException e) {
                tilePlacementDecided = false;
                tempBoard = new Board(turnState.getBoard());
            }
        }
        gatherBuildActionInfo();
        if(canPlaceTotoro()){
            placeTotoro();
            resetTotoroLine();
        }
        else if(canPlaceTiger()) {
            placeTiger();
        }
        else if((noCurrentLine() || lineIsInterrupted()) && !tilePlacementDecided){

            startNewLine();
        }
        else if(!lineIsInterrupted() && !tilePlacementDecided){
            extendLine();
        }
        else if(tilePlacementDecided){
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

    private void gatherTilePlacementInfo() {
        this.validTilePlacements = AI_Info.returnValidTilePlacements(turnState.getCurrentTile(), tempBoard);
        this.tilePlacementsForNukingEnemySettlementsCloseToGettingATotoro = AI_Info.findNukableLocationsToStopOpposingPlayerFromMakingTotoroPlacement(Color.BLACK, tempBoard, turnState.getCurrentTile());
        this.tilePlacementsForNukingEnemySettlementsCloseToGettingATiger = AI_Info.findNukableLocationsToStopOpposingPlayerFromMakingTigerPlacement(Color.BLACK, tempBoard, turnState.getCurrentTile());
        this.tilePlacementsThatSetUpPlayerForTotoroPlacement = AI_Info.findTilePlacementsThatEnableTotoroPlacementForSamePlayer(turnState.getCurrentPlayer().getPlayerColor(), turnState.getCurrentTile(), tempBoard);
        this.tilePlacementsThatCutTotoroOffOfMostOfSettlement = AI_Info.findTilePlacementsThatCutTotoroOffOfMostOfSettlement(turnState.getCurrentPlayer().getPlayerColor(), turnState.getCurrentTile(), tempBoard, 3);
    }

    private void gatherBuildActionInfo() {
        this.validTotoroPlacements = AI_Info.returnValidTotoroPlacements(turnState.getCurrentPlayer(), tempBoard);
        this.validTigerPlacements = AI_Info.returnValidTigerPlacements(turnState.getCurrentPlayer().getPlayerColor(), tempBoard);
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
        buildLocation = validTigerPlacements.get(0);
        tilePlacement = validTilePlacements.get(0);
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